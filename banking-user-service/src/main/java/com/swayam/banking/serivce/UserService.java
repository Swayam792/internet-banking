package com.swayam.banking.serivce;

import com.swayam.banking.dto.Status;
import com.swayam.banking.dto.User;
import com.swayam.banking.dto.UserUpdateRequest;
import com.swayam.banking.entity.UserEntity;
import com.swayam.banking.exception.GlobalErrorCode;
import com.swayam.banking.exception.InvalidBankingUserException;
import com.swayam.banking.exception.InvalidEmailException;
import com.swayam.banking.exception.UserAlreadyRegisteredException;
import com.swayam.banking.mapper.UserMapper;
import com.swayam.banking.repository.UserRepository;
import com.swayam.banking.rest.BankingCoreRestClient;
import com.swayam.banking.rest.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    private final KeycloakUserService keycloakUserService;
    private final UserRepository userRepository;

    private UserMapper userMapper = new UserMapper();

    private final BankingCoreRestClient bankingCoreRestClient;

    @Autowired
    public UserService(KeycloakUserService keycloakUserService, UserRepository userRepository, BankingCoreRestClient bankingCoreRestClient) {
        this.keycloakUserService = keycloakUserService;
        this.userRepository = userRepository;
        this.bankingCoreRestClient = bankingCoreRestClient;
    }
    public User createUser(User user) {

        List<UserRepresentation> userRepresentations = keycloakUserService.readUserByEmail(user.getEmail());
        if (userRepresentations.size() > 0) {
            throw new UserAlreadyRegisteredException("This email already registered as a user. Please check and retry.", GlobalErrorCode.ERROR_EMAIL_REGISTERED);
        }

        UserResponse userResponse = bankingCoreRestClient.readUser(user.getIdentification());

        if (userResponse.getId() != null) {

            if (!userResponse.getEmail().equals(user.getEmail())) {
                throw new InvalidEmailException("Incorrect email. Please check and retry.", GlobalErrorCode.ERROR_INVALID_EMAIL);
            }

            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setEmail(userResponse.getEmail());
            userRepresentation.setEmailVerified(false);
            userRepresentation.setEnabled(false);
            userRepresentation.setUsername(userResponse.getEmail());

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setValue(user.getPassword());
            credentialRepresentation.setTemporary(false);
            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

            Integer userCreationResponse = keycloakUserService.createUser(userRepresentation);

            if (userCreationResponse == 201) {
                log.info("User created under given username {}", user.getEmail());

                List<UserRepresentation> userRepresentations1 = keycloakUserService.readUserByEmail(user.getEmail());
                user.setAuthId(userRepresentations1.get(0).getId());
                user.setStatus(Status.PENDING);
                user.setIdentification(userResponse.getIdentificationNumber());
                UserEntity save = userRepository.save(userMapper.convertToEntity(user));
                return userMapper.convertToDto(save);
            }

        }

        throw new InvalidBankingUserException("We couldn't find user under given identification. Please check and retry", GlobalErrorCode.ERROR_USER_NOT_FOUND_UNDER_NIC);

    }

    public List<User> readUsers(Pageable pageable) {
        Page<UserEntity> allUsersInDb = userRepository.findAll(pageable);
        List<User> users = userMapper.convertToDtoList(allUsersInDb.getContent());
        users.forEach(user -> {
            UserRepresentation userRepresentation = keycloakUserService.readUser(user.getAuthId());
            user.setId(user.getId());
            user.setEmail(userRepresentation.getEmail());
            user.setIdentification(user.getIdentification());
        });
        return users;
    }

    public User readUser(Long userId) {
        return userMapper.convertToDto(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
    }

    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (userUpdateRequest.getStatus() == Status.APPROVED) {
            UserRepresentation userRepresentation = keycloakUserService.readUser(userEntity.getAuthId());
            userRepresentation.setEnabled(true);
            userRepresentation.setEmailVerified(true);
            keycloakUserService.updateUser(userRepresentation);
        }

        userEntity.setStatus(userUpdateRequest.getStatus());
        return userMapper.convertToDto(userRepository.save(userEntity));
    }
}
