package com.iftgroup.jobportal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iftgroup.jobportal.dto.JobDTO;
import com.iftgroup.jobportal.entity.Job;
import com.iftgroup.jobportal.repository.JobRepository;
import com.iftgroup.jobportal.service.impl.JobServiceImpl;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @InjectMocks
    private JobServiceImpl jobService;

    @Mock
    private JobRepository jobRepository;

    @Test
    public void testFindAll() {
        // Mock data
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Job 1");
        job1.setDescription("Job 1 Description");
        job1.setCategory("Category 1");

        Job job2 = new Job();
        job2.setId(2L);
        job2.setTitle("Job 2");
        job2.setDescription("Job 2 Description");
        job2.setCategory("Category 2");

        List<Job> jobs = Arrays.asList(job1, job2);

        // Stubbing jobRepository.findAll() method to return mock data
        when(jobRepository.findAll()).thenReturn(jobs);

        // Invoke jobService.findAll() method
        List<JobDTO> jobDTOs = jobService.findAll();

        // Verify the result
        assertEquals(2, jobDTOs.size());
        assertEquals(job1.getId(), jobDTOs.get(0).getId());
        assertEquals(job1.getTitle(), jobDTOs.get(0).getTitle());
        assertEquals(job1.getDescription(), jobDTOs.get(0).getDescription());
        assertEquals(job1.getCategory(), jobDTOs.get(0).getCategory());

        assertEquals(job2.getId(), jobDTOs.get(1).getId());
        assertEquals(job2.getTitle(), jobDTOs.get(1).getTitle());
        assertEquals(job2.getDescription(), jobDTOs.get(1).getDescription());
        assertEquals(job2.getCategory(), jobDTOs.get(1).getCategory());
    }

    @Test
    public void testSave() {
        // Mock data
        JobDTO jobDTO = new JobDTO();
        jobDTO.setTitle("Job Title");
        jobDTO.setDescription("Job Description");
        jobDTO.setCategory("Job Category");

        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setCategory(jobDTO.getCategory());

        Job savedJob = new Job();
        savedJob.setId(1L);
        savedJob.setTitle(jobDTO.getTitle());
        savedJob.setDescription(jobDTO.getDescription());
        savedJob.setCategory(jobDTO.getCategory());

        // Stubbing jobRepository.save() method to return the saved job entity
        when(jobRepository.save(any(Job.class))).thenReturn(savedJob);

        // Invoke jobService.save() method
        JobDTO savedJobDTO = jobService.save(jobDTO);

        // Verify the result
        assertNotNull(savedJobDTO);
        assertEquals(savedJob.getId(), savedJobDTO.getId());
        assertEquals(savedJob.getTitle(), savedJobDTO.getTitle());
        assertEquals(savedJob.getDescription(), savedJobDTO.getDescription());
        assertEquals(savedJob.getCategory(), savedJobDTO.getCategory());
    }

    @Test
    public void testFindByCategory() {
        // Mock data
        String category = "Category 1";
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Job 1");
        job1.setDescription("Job 1 Description");
        job1.setCategory(category);

        Job job2 = new Job();
        job2.setId(2L);
        job2.setTitle("Job 2");
        job2.setDescription("Job 2 Description");
        job2.setCategory(category);

        List<Job> jobs = Arrays.asList(job1, job2);

        // Stubbing jobRepository.findByCategory() method to return mock data
        when(jobRepository.findByCategory(category)).thenReturn(jobs);

        // Invoke jobService.findByCategory() method
        List<JobDTO> jobDTOs = jobService.findByCategory(category);

        // Verify the result
        assertEquals(2, jobDTOs.size());
        assertEquals(job1.getId(), jobDTOs.get(0).getId());
        assertEquals(job1.getTitle(), jobDTOs.get(0).getTitle());
        assertEquals(job1.getDescription(), jobDTOs.get(0).getDescription());
        assertEquals(job1.getCategory(), jobDTOs.get(0).getCategory());

        assertEquals(job2.getId(), jobDTOs.get(1).getId());
        assertEquals(job2.getTitle(), jobDTOs.get(1).getTitle());
        assertEquals(job2.getDescription(), jobDTOs.get(1).getDescription());
        assertEquals(job2.getCategory(), jobDTOs.get(1).getCategory());
    }

    @Test
    public void testFindByKeyword() {
        // Mock data
        String keyword = "developer";
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Software Developer");
        job1.setDescription("Job 1 Description");
        job1.setCategory("Category 1");

        Job job2 = new Job();
        job2.setId(2L);
        job2.setTitle("Frontend Developer");
        job2.setDescription("Job 2 Description");
        job2.setCategory("Category 2");

        List<Job> jobs = Arrays.asList(job1, job2);

        // Stubbing jobRepository.findByTitleContainingOrDescriptionContaining() method
        // to return mock data
        when(jobRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(jobs);

        // Invoke jobService.findByKeyword() method
        List<JobDTO> jobDTOs = jobService.findByKeyword(keyword);

        // Verify the result
        assertEquals(2, jobDTOs.size());
        assertEquals(job1.getId(), jobDTOs.get(0).getId());
        assertEquals(job1.getTitle(), jobDTOs.get(0).getTitle());
        assertEquals(job1.getDescription(), jobDTOs.get(0).getDescription());
        assertEquals(job1.getCategory(), jobDTOs.get(0).getCategory());

        assertEquals(job2.getId(), jobDTOs.get(1).getId());
        assertEquals(job2.getTitle(), jobDTOs.get(1).getTitle());
        assertEquals(job2.getDescription(), jobDTOs.get(1).getDescription());
        assertEquals(job2.getCategory(), jobDTOs.get(1).getCategory());
    }

    @Test
    public void testFindById() {
        // Mock data
        Job job = new Job();
        job.setId(1L);
        job.setTitle("Job 1");
        job.setDescription("Job 1 Description");
        job.setCategory("Category 1");

        // Stubbing jobRepository.findById() method to return mock data
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        // Invoke jobService.findById() method
        JobDTO jobDTO = jobService.findById(1L);

        // Verify the result
        assertNotNull(jobDTO);
        assertEquals(job.getId(), jobDTO.getId());
        assertEquals(job.getTitle(), jobDTO.getTitle());
        assertEquals(job.getDescription(), jobDTO.getDescription());
        assertEquals(job.getCategory(), jobDTO.getCategory());
    }

    @Test
    public void testDeleteById() {
        // Mock data
        Long jobId = 1L;

        // Invoke jobService.deleteById() method
        jobService.deleteById(jobId);

        // Verify the result
        Mockito.verify(jobRepository, Mockito.times(1)).deleteById(jobId);
    }

}
