package com.its.service.domain.question.controller;

import com.its.service.common.response.SuccessResponse;
import com.its.service.common.response.factory.ResponseFactory;
import com.its.service.common.validation.annotation.ExistQuestion;
import com.its.service.domain.auth.security.oauth2.dto.oauth2.CustomOAuth2User;
import com.its.service.domain.question.dto.request.CreateChoiceRequest;
import com.its.service.domain.question.dto.request.CreateExplanationRequest;
import com.its.service.domain.question.dto.request.CreateQuestionBulkRequest;
import com.its.service.domain.question.dto.request.CreateQuestionRequest;
import com.its.service.domain.question.dto.response.ChoicesResponse;
import com.its.service.domain.question.dto.response.QuestionResponse;
import com.its.service.domain.question.dto.response.ExplanationResponse;
import com.its.service.domain.question.dto.response.QuestionResponses;
import com.its.service.domain.question.service.choice.ChoiceCommandService;
import com.its.service.domain.question.service.choice.ChoiceQueryService;
import com.its.service.domain.question.service.explanation.ExplanationCommandService;
import com.its.service.domain.question.service.explanation.ExplanationQueryService;
import com.its.service.domain.question.service.question.QuestionCommandService;
import com.its.service.domain.question.service.question.QuestionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Validated
@Tag(name ="문제", description = "Question API")
public class QuestionController {
    private final QuestionQueryService questionQueryService;
    private final QuestionCommandService questionCommandService;
    private final ChoiceCommandService choiceCommandService;
    private final ChoiceQueryService choiceQueryService;
    private final ExplanationCommandService explanationCommandService;
    private final ExplanationQueryService explanationQueryService;


    @Operation(summary = "✅ [관리자] 단일 문제 등록", description = "새로운 한개의 문제를 등록하는 API")
    @PostMapping
    public ResponseEntity<SuccessResponse<QuestionResponse>> createSingleQuestion(
            @Valid @RequestBody CreateQuestionRequest request,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        var response = questionCommandService.createSingleQuestion(request);
        return ResponseFactory.created(response);
    }

    @Operation(summary = "✅ [관리자] 여러 문제 등록", description = "여러 문제를 일괄 등록하는 API")
    @PostMapping("/bulk")
    public ResponseEntity<SuccessResponse<QuestionResponses>> createBulkQuestions(
            @Valid @RequestBody CreateQuestionBulkRequest request,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        var response = questionCommandService.createBulkQuestions(request);
        return ResponseFactory.created(response);

    }

    @Operation(summary = "✅ [사용자] 문제 조회", description = "문제를 조회하는 API")
    @GetMapping("/{questionId}")
    public ResponseEntity<SuccessResponse<QuestionResponse>> getQuestionById(@PathVariable String questionId) {
        var response = questionQueryService.getQuestionById(questionId);
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ [관리자] 문제 삭제", description = "등록된 문제를 삭제하는 API")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(
            @ExistQuestion @PathVariable("questionId") String questionId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        questionCommandService.deleteQuestionById(questionId);
        return ResponseFactory.noContent();
    }

    @Operation(summary = "✅ [관리자] 선지 추가", description = "특정 문제에 선지를 추가하는 API")
    @PostMapping("/{questionId}/choices")
    public ResponseEntity<SuccessResponse<QuestionResponse>> addChoice(
            @PathVariable String questionId,
            @Valid @RequestBody CreateChoiceRequest request) {
        var response = choiceCommandService.addChoice(questionId, request);
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ [관리자] 선지 삭제", description = "특정 문제에서 선지를 삭제하는 API")
    @DeleteMapping("/{questionId}/choices/{choiceNumber}")
    public ResponseEntity<SuccessResponse<QuestionResponse>> deleteChoice(
            @PathVariable String questionId,
            @PathVariable Integer choiceNumber) {
        var response = choiceCommandService.deleteChoice(questionId, choiceNumber);
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ [관리자] 선지 조회", description = "특정 문제의 모든 선지를 조회하는 API")
    @GetMapping("/{questionId}/choices")
    public ResponseEntity<SuccessResponse<ChoicesResponse>> getChoicesByQuestionId(@PathVariable String questionId) {
        var response = choiceQueryService.getChoicesByQuestionId(questionId);
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ [관리자] 해설 조회", description = "특정 문제의 해설을 조회하는 API")
    @GetMapping("{questionId}/explanation")
    public ResponseEntity<SuccessResponse<ExplanationResponse>> getExplanationByQuestionId(
            @PathVariable String questionId) {
        var response = explanationQueryService.getExplanationByQuestionId(questionId);
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ [관리자] 해설 수정", description = "특정 문제의 해설을 수정하는 API")
    @PutMapping("{questionId}/explanation")
    public ResponseEntity<SuccessResponse<QuestionResponse>> updateExplanation(
            @PathVariable String questionId,
            @RequestBody CreateExplanationRequest request) {

        var response = explanationCommandService.updateExplanation(questionId, request);
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ [관리자] 해설 제거", description = "특정 문제의 해설을 제거하는 API")
    @DeleteMapping("{questionId}/explanation")
    public ResponseEntity<SuccessResponse<QuestionResponse>> deleteExplanation(
            @PathVariable String questionId) {

        var response = explanationCommandService.deleteExplanation(questionId);
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ [사용자] 어떤 과목(차시)에 소과목에 속한 문제들 조회", description = "특정 소과목의 모든 문제를 조회하는 API")
    @GetMapping("{subjectId}/{minorId}")
    public ResponseEntity<SuccessResponse<QuestionResponses>> getQuestionsByMinorId(
            @PathVariable Long subjectId,
            @PathVariable Long minorId) {

        var response = questionQueryService.getQuestionsByMinorAndSubject(subjectId,minorId);
        return ResponseFactory.success(response);
    }

}
