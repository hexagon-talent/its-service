package com.its.service.domain.classification.repository;

import com.its.service.domain.classification.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
