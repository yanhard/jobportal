package com.iftgroup.jobportal.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.iftgroup.jobportal.dto.UpdateProfileRequest;
import com.iftgroup.jobportal.dto.UserDTO;
import com.iftgroup.jobportal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John Doe");
        // add additional fields as needed
    }

    @Test
    public void testUpdateUser() throws Exception {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setEmail("johndoe@example.com");
        // add additional fields as needed

        when(userService.updateProfile(Mockito.anyLong(), Mockito.any(UpdateProfileRequest.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProfileRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDTO)));
    }

    @Test
    public void testUploadResume() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "resume.pdf", MediaType.APPLICATION_PDF_VALUE, "PDF content".getBytes());

        when(userService.findById(Mockito.anyLong())).thenReturn(userDTO);
        when(userService.save(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(multipart("/api/user/{id}/upload-resume", 1L)
                .file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void testUploadCoverLetter() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "cover_letter.pdf", MediaType.APPLICATION_PDF_VALUE, "PDF content".getBytes());

        when(userService.findById(Mockito.anyLong())).thenReturn(userDTO);
        when(userService.save(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(multipart("/api/user/{id}/upload-cover-letter", 1L)
                .file(file))
                .andExpect(status().isOk());
    }
}
