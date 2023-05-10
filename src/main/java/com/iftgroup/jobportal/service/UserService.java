package com.iftgroup.jobportal.service;

import java.util.List;

import com.iftgroup.jobportal.dto.RegisterRequest;
import com.iftgroup.jobportal.dto.UpdateProfileRequest;
import com.iftgroup.jobportal.dto.UserDTO;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    UserDTO save(UserDTO userDTO);
    void deleteById(Long id);
    UserDTO findByEmail(String email);
    boolean registerUser(RegisterRequest registerRequest);
    UserDTO updateProfile(Long id, UpdateProfileRequest updateProfileRequest);
}
