package com.iftgroup.jobportal.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private Long id;
    private String fullName;
    private String email;
    private String resumePath;
    private String coverLetterPath;
}
