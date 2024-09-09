package com.its.service.domain.question.service.choice;

import com.its.service.common.error.code.MinorErrorCode;
import com.its.service.common.error.code.QuestionErrorCode;
import com.its.service.common.error.code.SubjectErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.repository.MinorRepository;
import com.its.service.domain.question.dto.request.CreateChoiceRequest;
import com.its.service.domain.question.dto.response.QuestionResponse;
import com.its.service.domain.question.entity.Choice;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.ChoiceMapper;
import com.its.service.domain.question.mapper.QuestionMapper;
import com.its.service.domain.question.repository.QuestionRepository;
import com.its.service.domain.subject.entity.Subject;
import com.its.service.domain.subject.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChoiceCommandService {
    private final QuestionRepository questionRepository;
    private final MinorRepository minorRepository;
    private final SubjectRepository subjectRepository;

    private final QuestionMapper questionMapper;
    private final ChoiceMapper choiceMapper;
    /*
    * 선택지 추가
    * */
    @Transactional
    public QuestionResponse addChoice(String questionId, CreateChoiceRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
        Minor minor = minorRepository.findById(question.getMinorId()).orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));

        Subject subject = subjectRepository.findById(question.getSubjectId())
                .orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));
        // 요청 DTO를 엔티티로 변환하여 선택지 추가
        Choice addedChoice = choiceMapper.toEntity(request);
        question.getChoices().add(addedChoice);

        // 데이터 저장
        questionRepository.save(question);

        // 추가된 선택지를 다시 DTO로 변환하여 반환
        return questionMapper.toQuestionResponse(question, minor, subject);
    }

    /*
    * 선택지 제거
    * */
    @Transactional
    public QuestionResponse deleteChoice(String questionId, Integer choiceNumber) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
        Minor minor = minorRepository.findById(question.getMinorId())
                .orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));
        Subject subject = subjectRepository.findById(question.getSubjectId())
                .orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));

        // 선택지 삭제
        question.getChoices().removeIf(choice -> choice.getChoiceNumber().equals(choiceNumber.toString()));
        questionRepository.save(question);

        // 수정된 문제를 반환
        return questionMapper.toQuestionResponse(question, minor, subject);
    }
}
