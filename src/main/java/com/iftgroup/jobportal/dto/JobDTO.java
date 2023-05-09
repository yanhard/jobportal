package com.iftgroup.jobportal.dto;

import com.iftgroup.jobportal.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private String category;

    public JobDTO(Job job) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.category = job.getCategory();
    }

    public Job toEntity() {
        Job job = new Job();
        job.setId(this.id);
        job.setTitle(this.title);
        job.setDescription(this.description);
        job.setCategory(this.category);
        return job;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
