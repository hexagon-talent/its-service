package com.its.service.domain.subject.controller;

import com.its.service.common.response.SuccessResponse;
import com.its.service.common.response.factory.ResponseFactory;
import com.its.service.domain.classification.service.ClassificationCommandService;
import com.its.service.domain.classification.service.ClassificationQueryService;
import com.its.service.domain.subject.dto.request.CreateSubjectRequest;
import com.its.service.domain.subject.dto.response.SubjectResponse;
import com.its.service.domain.subject.dto.response.SubjectResponses;
import com.its.service.domain.subject.service.SubjectCommandService;
import com.its.service.domain.subject.service.SubjectQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
@Tag(name = "시험", description = "Subject, Schedule API")
public class SubjectController {

    private final SubjectCommandService subjectCommandService;
    private final SubjectQueryService subjectQueryService;

    @Operation(summary = "✅ 과목 생성", description = "새로운 과목을 생성하는 API")
    @PostMapping("/subject")
    public ResponseEntity<SuccessResponse<SubjectResponse>> createSubject(@RequestBody CreateSubjectRequest request) {
        var response = subjectCommandService.createSubject(request);
        return ResponseFactory.created(response);
    }

    @Operation(summary = "✅ 모든 과목 조회", description = "모든 과목을 조회하는 API")
    @GetMapping("/subjects")
    public ResponseEntity<SuccessResponse<SubjectResponses>> getAllSubjects() {
        var response = subjectQueryService.getAllSubjects();
        return ResponseFactory.success(response);
    }

}




