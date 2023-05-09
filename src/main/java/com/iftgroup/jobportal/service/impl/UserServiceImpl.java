package com.iftgroup.jobportal.service.impl;

import java.util.List;

import com.iftgroup.jobportal.dto.UserDTO;
import com.iftgroup.jobportal.entity.User;
import com.iftgroup.jobportal.repository.UserRepository;
import com.iftgroup.jobportal.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDTO findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserDTO(user);
        }
        return null;
    }

}