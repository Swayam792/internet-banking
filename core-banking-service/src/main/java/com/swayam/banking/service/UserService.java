package com.swayam.banking.service;

import com.swayam.banking.dto.User;
import com.swayam.banking.entity.UserEntity;
import com.swayam.banking.model.mapper.UserMapper;
import com.swayam.banking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserMapper userMapper = new UserMapper();

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User readUser(String identification) {
        UserEntity userEntity = userRepository.findByIdentificationNumber(identification).get();
        return userMapper.convertToDto(userEntity);
    }

    public List<User> readUsers(Pageable pageable) {
        return userMapper.convertToDtoList(userRepository.findAll(pageable).getContent());
    }
}