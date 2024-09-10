package com.its.service.domain.question.repository;

import com.its.service.domain.question.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByMinorIdAndSubjectId(Long minorId, Long subjectId);  // 소과목 ID로 문제 조회
    boolean existsQuestionByMinorIdAndSubjectIdAndQuestionNumber(Long minorId, Long subjectId, String questionNumber);
}
