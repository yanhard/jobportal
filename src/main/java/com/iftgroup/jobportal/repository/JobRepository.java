package com.iftgroup.jobportal.repository;

import com.iftgroup.jobportal.entity.Job;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContainingIgnoreCase(String keyword);
    List<Job> findByCategory(String category);
}
