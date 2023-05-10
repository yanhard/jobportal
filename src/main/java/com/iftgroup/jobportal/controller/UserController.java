package com.iftgroup.jobportal.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iftgroup.jobportal.dto.UpdateProfileRequest;
import com.iftgroup.jobportal.dto.UserDTO;
import com.iftgroup.jobportal.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired 
    private Environment env;

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable(name = "id") Long id, @RequestBody UpdateProfileRequest updateProfileRequest) {
        UserDTO updatedUserDTO = userService.updateProfile(id, updateProfileRequest);
        if (updatedUserDTO != null) {
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/upload-resume")
    public ResponseEntity<Void> uploadResume(@PathVariable(name = "id") Long id, @RequestParam("file") MultipartFile file) {
        UserDTO userDTO = userService.findById(id);
        if (userDTO != null) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = env.getProperty("jobportal.app.upload-dir");
            String filepath = uploadDir + "/" + id + "/" + filename;
            try {
                Path path = Paths.get(filepath).toAbsolutePath().normalize();
                Files.createDirectories(path.getParent());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                userDTO.setResumePath(filepath);
                userService.save(userDTO);
                return ResponseEntity.ok().build();
            } catch (IOException ex) {
                throw new RuntimeException("Failed to store file " + filename, ex);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/upload-cover-letter")
    public ResponseEntity<Void> uploadCoverLetter(@PathVariable(name = "id") Long id, @RequestParam("file") MultipartFile file) {
        UserDTO userDTO = userService.findById(id);
        if (userDTO != null) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = env.getProperty("jobportal.app.upload-dir");
            String filepath = uploadDir + "/" + id + "/" + filename;
            try {
                Path path = Paths.get(filepath).toAbsolutePath().normalize();
                Files.createDirectories(path.getParent());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                userDTO.setCoverLetterPath(filepath);
                userService.save(userDTO);
                return ResponseEntity.ok().build();
            } catch (IOException ex) {
                throw new RuntimeException("Failed to store file " + filename, ex);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
