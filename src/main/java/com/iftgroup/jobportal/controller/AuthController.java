package com.iftgroup.jobportal.controller;

import com.iftgroup.jobportal.dto.LoginRequest;
import com.iftgroup.jobportal.dto.RegisterRequest;
import com.iftgroup.jobportal.service.UserService;
import com.iftgroup.jobportal.service.impl.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        // memeriksa apakah user ada dan password benar
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        boolean isPasswordValid = passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword());

        if (isPasswordValid) {
            // jika password valid, autentikasi user dan simpan dalam sesi
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            return ResponseEntity.ok("Login successful");
        } else {
            // jika password tidak valid, kirim respon gagal
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: incorrect email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        boolean isRegistered = userService.registerUser(registerRequest);
        if (isRegistered) {
            return ResponseEntity.ok("Registration successful");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Registration failed: user already exists");
        }
    }

}
