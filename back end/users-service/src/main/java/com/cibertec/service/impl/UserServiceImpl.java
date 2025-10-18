package com.cibertec.service.impl;

import com.cibertec.dto.UserRequest;
import com.cibertec.dto.UserResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.User;
import com.cibertec.repository.UserRepository;
import com.cibertec.service.UserService;
import com.cibertec.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserResponse> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("User not found with id: " + id)
        ));
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userRequest)));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User userFound = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("User not found with id: " + id)
        );
        userFound.setEmail(userRequest.email());
        userFound.setPassword(userRequest.password());
        userFound.setFullName(userRequest.fullName());
        userFound.setPhone(userRequest.phone());
        userFound.setStatus(userRequest.status());
        return userMapper.toDto(userRepository.save(userFound));
    }

    @Override
    public void deleteUser(Long id) {
        User userFound = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("User not found with id: " + id)
        );
        userRepository.delete(userFound);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFound("User not found with email: " + email)
        ));
    }
}
