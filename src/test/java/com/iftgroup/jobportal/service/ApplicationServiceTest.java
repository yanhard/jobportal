package com.iftgroup.jobportal.service;

import com.iftgroup.jobportal.dto.ApplicationDTO;
import com.iftgroup.jobportal.entity.Application;
import com.iftgroup.jobportal.entity.Job;
import com.iftgroup.jobportal.entity.User;
import com.iftgroup.jobportal.exception.EntityNotFoundException;
import com.iftgroup.jobportal.repository.ApplicationRepository;
import com.iftgroup.jobportal.repository.JobRepository;
import com.iftgroup.jobportal.repository.UserRepository;
import com.iftgroup.jobportal.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    public void testApplyJobSuccess() {
        // mock data
        Long userId = 1L;
        Long jobId = 2L;
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setUserId(userId);
        applicationDTO.setJobId(jobId);
        applicationDTO.setResume("resume content");
        //applicationDTO.setAppliedDate(LocalDate.now());

        User user = new User();
        user.setId(userId);
        user.setEmail("user@test.com");

        Job job = new Job();
        job.setId(jobId);
        job.setTitle("Software Engineer");
        job.setCategory("IT");

        applicationDTO.setJob(job);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));
        when(applicationRepository.save(Mockito.any(Application.class))).thenReturn(new Application());

        // invoke the applyJob() method
        applicationService.applyJob(applicationDTO, userId);

        // verify that the applicationRepository.save() method was called once
        Mockito.verify(applicationRepository, Mockito.times(1)).save(Mockito.any(Application.class));
    }

    @Test
    public void testApplyJobUserNotFound() {
        // mock data
        Long userId = 1L;
        Long jobId = 2L;
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setUserId(userId);
        applicationDTO.setJobId(jobId);
        applicationDTO.setResume("resume content");
    
        Job job = new Job();
        job.setId(jobId);
        applicationDTO.setJob(job);
    
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());
    
        // invoke the applyJob() method and expect an EntityNotFoundException to be thrown
        assertThrows(EntityNotFoundException.class, () -> {
            applicationService.applyJob(applicationDTO, userId);
        });
    }    
}    