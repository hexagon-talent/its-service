package com.its.service.domain.question.service.question;

import com.its.service.common.error.code.MinorErrorCode;
import com.its.service.common.error.code.QuestionErrorCode;
import com.its.service.common.error.code.SubjectErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.repository.MinorRepository;
import com.its.service.domain.classification.service.ClassificationQueryService;
import com.its.service.domain.question.dto.response.QuestionResponse;
import com.its.service.domain.question.dto.response.QuestionResponses;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.QuestionMapper;
import com.its.service.domain.question.repository.QuestionRepository;
import com.its.service.domain.subject.entity.Subject;
import com.its.service.domain.subject.repository.SubjectRepository;
import com.its.service.domain.subject.service.SubjectQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionQueryService {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final SubjectQueryService subjectQueryService;
    private final ClassificationQueryService classificationQueryService;
    /*
    * 특정 문제 조회
    * */
    public QuestionResponse getQuestionById(String questionId) {

        Question question = findQuestionById(questionId);

        // minorId를 이용하여 Minor, Major, Subject 데이터를 조회
        Minor minor = classificationQueryService.findMinorById(question.getMinorId());
        Subject subject = subjectQueryService.findSubjectById(question.getSubjectId());


        return questionMapper.toQuestionResponse(question, minor, subject);

    }

    /*
     * 소과목에 속하는 문제들을 조회
     * */
    public QuestionResponses getQuestionsByMinorAndSubject(Long subjectId, Long minorId) {

        Minor minor = classificationQueryService.findMinorById(minorId);
        Subject subject = subjectQueryService.findSubjectById(subjectId);
        List<Question> questions = questionRepository.findByMinorIdAndSubjectId(minorId, subjectId);


        return questionMapper.toQuestionResponses(questions, minor, subject);
    }

    public Question findQuestionById(String questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
    }
}
