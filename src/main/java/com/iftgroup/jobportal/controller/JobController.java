package com.iftgroup.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iftgroup.jobportal.dto.ApplicationDTO;
import com.iftgroup.jobportal.dto.JobDTO;
import com.iftgroup.jobportal.dto.UserDTO;
import com.iftgroup.jobportal.service.ApplicationService;
import com.iftgroup.jobportal.service.JobService;
import com.iftgroup.jobportal.service.UserService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    
    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/")
    public List<JobDTO> getAllJobs() {
        return jobService.findAll();
    }

    @GetMapping("/search")
    public List<JobDTO> searchJobs(@RequestParam(name = "keyword") String keyword) {
        return jobService.findByKeyword(keyword);
    }

    @GetMapping("/category")
    public List<JobDTO> getJobsByCategory(@RequestParam(name = "category") String category) {
        return jobService.findByCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable(name = "id") Long id) {
        JobDTO jobDTO = jobService.findById(id);
        if (jobDTO != null) {
            return ResponseEntity.ok(jobDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        JobDTO savedJobDTO = jobService.save(jobDTO);
        return ResponseEntity.ok(savedJobDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable(name = "id") Long id, @RequestBody JobDTO jobDTO) {
        JobDTO currentJobDTO = jobService.findById(id);
        if (currentJobDTO != null) {
            jobDTO.setId(id);
            JobDTO savedJobDTO = jobService.save(jobDTO);
            return ResponseEntity.ok(savedJobDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable(name = "id") Long id) {
        jobService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyJob(@PathVariable(name = "id") Long id, @RequestBody ApplicationDTO applicationDTO) {
        JobDTO jobDTO = jobService.findById(id);
        if (jobDTO != null) {
            ApplicationDTO newApplication = new ApplicationDTO();
            newApplication.setJob(jobDTO.toEntity()); // set job from jobDTO to application
            newApplication.setResume(applicationDTO.getResume());
            newApplication.setCoverLetter(applicationDTO.getCoverLetter());
            applicationService.applyJob(newApplication, applicationDTO.getUserId());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }    
}
