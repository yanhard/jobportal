package com.iftgroup.jobportal.service;

import com.iftgroup.jobportal.dto.ApplicationDTO;

public interface ApplicationService {
    void applyJob(ApplicationDTO applicationDTO, Long userId);
}
