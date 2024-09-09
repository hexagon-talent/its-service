package com.its.service.domain.subject.repository;

import com.its.service.domain.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    // 같은 연도의 모든 Subject 조회
    @Query("SELECT subject " +
            "FROM Subject subject " +
            "WHERE YEAR(subject.examDate) = ?1")
    List<Subject> findByExamYear(int year);
}
