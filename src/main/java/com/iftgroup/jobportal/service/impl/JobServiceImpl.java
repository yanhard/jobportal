package com.iftgroup.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iftgroup.jobportal.dto.JobDTO;
import com.iftgroup.jobportal.entity.Job;
import com.iftgroup.jobportal.repository.JobRepository;
import com.iftgroup.jobportal.service.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(JobDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> findByKeyword(String keyword) {
        List<Job> jobs = jobRepository.findByTitleContainingIgnoreCase(keyword);
        return jobs.stream().map(JobDTO::new).collect(Collectors.toList());
    }

    @Override
    public JobDTO findById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return job != null ? new JobDTO(job) : null;
    }

    @Override
    public JobDTO save(JobDTO jobDTO) {
        Job job = jobRepository.save(jobDTO.toEntity());
        return new JobDTO(job);
    }

    @Override
    public List<JobDTO> findByCategory(String category) {
    List<Job> jobs = jobRepository.findByCategory(category);
    List<JobDTO> jobDTOs = new ArrayList<>();
    for (Job job : jobs) {
        jobDTOs.add(new JobDTO(job));
    }
    return jobDTOs;
}
    @Override
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }
}
