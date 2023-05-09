package com.iftgroup.jobportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.iftgroup.jobportal.dto.ApplicationDTO;
import com.iftgroup.jobportal.entity.Application;
import com.iftgroup.jobportal.entity.Job;
import com.iftgroup.jobportal.entity.User;
import com.iftgroup.jobportal.repository.ApplicationRepository;
import com.iftgroup.jobportal.repository.JobRepository;
import com.iftgroup.jobportal.repository.UserRepository;
import com.iftgroup.jobportal.service.ApplicationService;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void applyJob(ApplicationDTO applicationDTO, Long userId) {
        Application application = applicationDTO.toEntity();
        User user = userRepository.findById(userId).orElse(null);
        Job job = jobRepository.findById(application.getJob().getId()).orElse(null);
        if (user != null && job != null) {
            application.setUser(user);
            application.setJob(job);
            applicationRepository.save(application);
        }
    }

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

}
