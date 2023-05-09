package com.iftgroup.jobportal.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iftgroup.jobportal.dto.ApplicationDTO;
import com.iftgroup.jobportal.dto.JobDTO;
import com.iftgroup.jobportal.service.ApplicationService;
import com.iftgroup.jobportal.service.JobService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class JobControllerTest {

    @InjectMocks
    private JobController jobController;

    @Mock
    private JobService jobService;

    @Mock
    private ApplicationService applicationService;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
        objectMapper = new ObjectMapper(); // Membuat instance ObjectMapper
    }


    @Test
    public void testGetAllJobs() throws Exception {
        // create a MockMvc instance
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();

        // mock data
        JobDTO job1 = new JobDTO();
        job1.setId(1L);
        job1.setTitle("Software Engineer");
        job1.setCategory("IT");

        JobDTO job2 = new JobDTO();
        job2.setId(2L);
        job2.setTitle("Data Scientist");
        job2.setCategory("IT");

        when(jobService.findAll()).thenReturn(Arrays.asList(job1, job2));

        // perform the GET request and check the result
        mockMvc.perform(get("/api/jobs/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Software Engineer"))
                .andExpect(jsonPath("$[0].category").value("IT"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Data Scientist"))
                .andExpect(jsonPath("$[1].category").value("IT"));
    }

    @Test
    public void testSearchJobs() throws Exception {
        // create a MockMvc instance
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();

        // mock data
        JobDTO job1 = new JobDTO();
        job1.setId(1L);
        job1.setTitle("Software Engineer");
        job1.setCategory("IT");

        String keyword = "Software";

        when(jobService.findByKeyword(keyword)).thenReturn(Arrays.asList(job1));

        // perform the GET request and check the result
        mockMvc.perform(get("/api/jobs/search").param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Software Engineer"))
                .andExpect(jsonPath("$[0].category").value("IT"));
    }

    @Test
    public void testGetJobsByCategory() throws Exception {
        // create a MockMvc instance
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();

        // mock data
        String category = "IT";

        JobDTO job1 = new JobDTO();
        job1.setId(1L);
        job1.setTitle("Software Engineer");
        job1.setCategory(category);

        JobDTO job2 = new JobDTO();
        job2.setId(2L);
        job2.setTitle("Data Scientist");
        job2.setCategory(category);

        when(jobService.findByCategory(category)).thenReturn(Arrays.asList(job1, job2));

        // perform the GET request and check the result
        MvcResult mvcResult = mockMvc.perform(get("/api/jobs/category")
                .param("category", category))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Software Engineer"))
                .andExpect(jsonPath("$[0].category").value(category))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Data Scientist"))
                .andExpect(jsonPath("$[1].category").value(category))
                .andReturn(); // add this to get MvcResult

        // print the response
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(jsonResponse);
    }

    @Test
    public void testGetJobById() throws Exception {
        // create a MockMvc instance
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();

        // mock data
        Long id = 1L;

        JobDTO job1 = new JobDTO();
        job1.setId(id);
        job1.setTitle("Software Engineer");
        job1.setCategory("IT");

        when(jobService.findById(id)).thenReturn(job1);

        // perform the GET request and check the result
        MvcResult mvcResult = mockMvc.perform(get("/api/jobs/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.category").value("IT"))
                .andReturn(); // add this to get MvcResult

        // print the response
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(jsonResponse);
    }

    @Test
    public void testCreateJob() throws Exception {
        // create a MockMvc instance
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();

        // mock data
        JobDTO job1 = new JobDTO();
        job1.setTitle("Software Engineer");
        job1.setCategory("IT");

        JobDTO savedJob = new JobDTO();
        savedJob.setId(1L);
        savedJob.setTitle("Software Engineer");
        savedJob.setCategory("IT");

        when(jobService.save(job1)).thenReturn(savedJob);

        // perform the POST request and check the result
        MvcResult mvcResult = mockMvc.perform(post("/api/jobs/")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(job1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.category").value("IT"))
                .andReturn();

        // print the response
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(jsonResponse);
    }

    @Test
    public void testUpdateJob() throws Exception {
        // mock data
        Long jobId = 1L;
        JobDTO existingJob = new JobDTO();
        existingJob.setId(jobId);
        existingJob.setTitle("Software Engineer");
        existingJob.setCategory("IT");

        JobDTO updatedJob = new JobDTO();
        updatedJob.setId(jobId);
        updatedJob.setTitle("Senior Software Engineer");
        updatedJob.setCategory("IT");

        when(jobService.findById(jobId)).thenReturn(existingJob);
        when(jobService.save(updatedJob)).thenReturn(updatedJob);

        // perform the PUT request and check the result
        MvcResult mvcResult = mockMvc.perform(put("/api/jobs/{id}", jobId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedJob)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(jobId))
                .andExpect(jsonPath("$.title").value("Senior Software Engineer"))
                .andExpect(jsonPath("$.category").value("IT"))
                .andReturn();

        // print the response
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(jsonResponse);
    }

    @Test
    public void testDeleteJob() throws Exception {
        Long jobId = 1L;

        // Simulate the behavior of jobService.deleteById()
        doNothing().when(jobService).deleteById(jobId);

        mockMvc.perform(delete("/api/jobs/" + jobId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(jobService, times(1)).deleteById(jobId);
    }

    @Test
    public void testApplyJob() throws Exception {
        Long jobId = 1L;
        Long userId = 2L;

        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(jobId);

        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setUserId(userId);
        applicationDTO.setResume("resume content");
        applicationDTO.setCoverLetter("cover letter content");

        when(jobService.findById(jobId)).thenReturn(jobDTO);
        doNothing().when(applicationService).applyJob(any(ApplicationDTO.class), eq(userId));

        mockMvc.perform(post("/api/jobs/" + jobId + "/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applicationDTO)))
                .andExpect(status().isOk());

        verify(jobService, times(1)).findById(jobId);
        verify(applicationService, times(1)).applyJob(any(ApplicationDTO.class), eq(userId));
    }
}
