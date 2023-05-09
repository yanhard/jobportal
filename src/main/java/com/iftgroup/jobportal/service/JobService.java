package com.iftgroup.jobportal.service;

import java.util.List;

import com.iftgroup.jobportal.dto.JobDTO;

public interface JobService {
    List<JobDTO> findAll();
    List<JobDTO> findByKeyword(String keyword);
    List<JobDTO> findByCategory(String category);
    JobDTO findById(Long id);
    JobDTO save(JobDTO jobDTO);
    void deleteById(Long id);
}
