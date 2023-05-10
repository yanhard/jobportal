package com.iftgroup.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.iftgroup.jobportal.dto.RegisterRequest;
import com.iftgroup.jobportal.dto.UpdateProfileRequest;
import com.iftgroup.jobportal.dto.UserDTO;
import com.iftgroup.jobportal.entity.User;
import com.iftgroup.jobportal.exception.EntityNotFoundException;
import com.iftgroup.jobportal.repository.UserRepository;
import com.iftgroup.jobportal.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }
    
    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? new UserDTO(user) : null;
     }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userRepository.save(userDTO.toEntity());
        return new UserDTO(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserDTO(user);
        }
        return null;
    }    

    @Override
    public boolean registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return false;
        }
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return true;
    }
    
    @Override
    public UserDTO updateProfile(Long id, UpdateProfileRequest updateProfileRequest) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (updateProfileRequest.getEmail() != null) {
            user.setEmail(updateProfileRequest.getEmail());
        }
        if (updateProfileRequest.getResumePath() != null) {
            user.setResumePath(updateProfileRequest.getResumePath());
        }
        if (updateProfileRequest.getCoverLetterPath() != null) {
            user.setCoverLetterPath(updateProfileRequest.getCoverLetterPath());
        }
        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser);
    }
}