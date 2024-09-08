package com.its.service.domain.question.repository;

import com.its.service.domain.question.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByMinorId(Long minorId);  // 소과목 ID로 문제 조회
}
