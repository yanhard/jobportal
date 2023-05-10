package com.iftgroup.jobportal.dto;

import com.iftgroup.jobportal.entity.Application;
import com.iftgroup.jobportal.entity.Job;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private Long id;
    private Long userId;
    private Long jobId;
    @Nonnull
    private String resume;
    private String coverLetter;
    private Job job;

    public ApplicationDTO(Application application) {
        this.id = application.getId();
        this.userId = application.getUser().getId();
        this.jobId = application.getJob().getId();
        this.resume = application.getResume();
        this.coverLetter = application.getCoverLetter();
        this.job = application.getJob();
    }

    public Application toEntity() {
        Application application = new Application();
        application.setId(this.id);
        application.setResume(this.resume);
        application.setCoverLetter(this.coverLetter);
        application.setJob(this.job);
        return application;
    }
}
