package com.iftgroup.jobportal.repository;

import com.iftgroup.jobportal.entity.Job;

import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class JobRepositoryCustomImpl implements JobRepositoryCustom {

    
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Job> searchJobs(String keyword, String category) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> query = cb.createQuery(Job.class);
        Root<Job> job = query.from(Job.class);

        List<Predicate> predicates = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            Predicate titlePredicate = cb.like(job.get("title"), "%" + keyword + "%");
            Predicate descriptionPredicate = cb.like(job.get("description"), "%" + keyword + "%");
            predicates.add(cb.or(titlePredicate, descriptionPredicate));
        }

        if (category != null && !category.isEmpty()) {
            predicates.add(cb.equal(job.get("category"), category));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
