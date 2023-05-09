package com.iftgroup.jobportal.repository;

import com.iftgroup.jobportal.entity.Job;
import java.util.List;

public interface JobRepositoryCustom {
    List<Job> searchJobs(String keyword, String category);
}
