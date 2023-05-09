package com.iftgroup.jobportal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iftgroup.jobportal.dto.UserDTO;
import com.iftgroup.jobportal.entity.User;
import com.iftgroup.jobportal.repository.UserRepository;
import com.iftgroup.jobportal.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        // Mock data
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");
        user1.setFirstName("User");
        user1.setLastName("One");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");
        user2.setFirstName("User");
        user2.setLastName("Two");

        List<User> users = Arrays.asList(user1, user2);

        // Stubbing userRepository.findAll() method to return mock data
        when(userRepository.findAll()).thenReturn(users);

        // Invoke userService.findAll() method
        List<UserDTO> userDTOs = userService.findAll();

        // Verify the result
        assertNotNull(userDTOs, "gak boleh null");
        assertEquals(2, userDTOs.size());
        assertEquals(user1.getId(), userDTOs.get(0).getId());
        assertEquals(user1.getEmail(), userDTOs.get(0).getEmail());
        assertEquals(user1.getFirstName(), userDTOs.get(0).getFirstName());
        assertEquals(user1.getLastName(), userDTOs.get(0).getLastName());

        assertEquals(user2.getId(), userDTOs.get(1).getId());
        assertEquals(user2.getEmail(), userDTOs.get(1).getEmail());
        assertEquals(user2.getFirstName(), userDTOs.get(1).getFirstName());
        assertEquals(user2.getLastName(), userDTOs.get(1).getLastName());
    }

    @Test
    public void testFindById() {
        // Mock data
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setFirstName("User");
        user.setLastName("One");
    
        // Stubbing userRepository.findById() method to return mock data
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    
        // Invoke userService.findById() method
        UserDTO userDTO = userService.findById(1L);
    
        // Verify the result
        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
    }
    

    @Test
    public void testSave() {
        // Mock data
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("testuser@test.com");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setPassword("pass");

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(userDTO.getEmail());
        savedUser.setFirstName(userDTO.getFirstName());
        savedUser.setLastName(userDTO.getLastName());
        savedUser.setPassword(userDTO.getPassword());

        // Stubbing userRepository.save() method to return the saved user entity
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Invoke userService.save() method
        UserDTO savedUserDTO = userService.save(userDTO);

        // Verify the result
        assertNotNull(savedUserDTO);
        assertEquals(savedUser.getId(), savedUserDTO.getId());
        assertEquals(savedUser.getEmail(), savedUserDTO.getEmail());
        assertEquals(savedUser.getFirstName(), savedUserDTO.getFirstName());
        assertEquals(savedUser.getLastName(), savedUserDTO.getLastName());
    }

    @Test
    public void testDeleteById() {
        // Mock data
        User user = new User();
        user.setId(1L);
        user.setFirstName("User 1");
        user.setEmail("user1@example.com");
        user.setPassword("password");

        // Stubbing userRepository.findById() method to return the user entity
        //when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Invoke userService.deleteById() method
        userService.deleteById(1L);

        // Verify the result
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void testFindByEmail() {
        // Mock data
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("User 1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User 2");
        user2.setEmail("user2@example.com");

        List<User> users = Arrays.asList(user1, user2);

        // Stubbing userRepository.findByEmail() method to return mock data
        when(userRepository.findByEmail(user1.getEmail())).thenReturn(user1);

        // Invoke userService.findByEmail() method
        UserDTO userDTO = userService.findByEmail(user1.getEmail());

        // Verify the result
        assertEquals(user1.getId(), userDTO.getId());
        assertEquals(user1.getFirstName(), userDTO.getFirstName());
        assertEquals(user1.getEmail(), userDTO.getEmail());
    }
}