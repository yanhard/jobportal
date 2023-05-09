package com.iftgroup.jobportal.repository;

import com.iftgroup.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
