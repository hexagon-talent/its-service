package com.its.service.domain.classification.controller;

import com.its.service.common.response.SuccessResponse;
import com.its.service.common.response.factory.ResponseFactory;
import com.its.service.domain.classification.dto.request.CreateMajorRequest;
import com.its.service.domain.classification.dto.request.CreateMinorRequest;
import com.its.service.domain.classification.dto.request.CreateSubjectRequest;
import com.its.service.domain.classification.dto.response.*;
import com.its.service.domain.classification.service.ClassificationCommandService;
import com.its.service.domain.classification.service.ClassificationQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classification")
@RequiredArgsConstructor
@Tag(name = "분류", description = "Subject, Major, Minor API")
public class ClassificationController {

    private final ClassificationCommandService classificationCommandService;
    private final ClassificationQueryService classificationQueryService;

    @Operation(summary = "✅ 과목 생성", description = "새로운 과목을 생성하는 API")
    @PostMapping("/subject")
    public ResponseEntity<SuccessResponse<SubjectResponse>> createSubject(@RequestBody CreateSubjectRequest request) {
        var response = classificationCommandService.createSubject(request);
        return ResponseFactory.created(response);
    }

    @Operation(summary = "✅ 대과목 생성", description = "새로운 대과목을 생성하는 API")
    @PostMapping("/major")
    public ResponseEntity<SuccessResponse<MajorResponse>> createMajor(@RequestBody CreateMajorRequest request) {
        var response = classificationCommandService.createMajor(request);
        return ResponseFactory.created(response);
    }

    @Operation(summary = "✅ 소과목 생성", description = "새로운 소과목을 생성하는 API")
    @PostMapping("/minor")
    public ResponseEntity<SuccessResponse<MinorResponse>> createMinor(@RequestBody CreateMinorRequest request) {
        var response = classificationCommandService.createMinor(request);
        return ResponseFactory.created(response);
    }

    @Operation(summary = "✅ 모든 과목 조회", description = "모든 과목을 조회하는 API")
    @GetMapping("/subjects")
    public ResponseEntity<SuccessResponse<SubjectResponses>> getAllSubjects() {
        var response = classificationQueryService.getAllSubjects();
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ 모든 대과목 조회", description = "모든 대과목을 조회하는 API")
    @GetMapping("/majors")
    public ResponseEntity<SuccessResponse<MajorResponses>> getAllMajors() {
        var response = classificationQueryService.getAllMajors();
        return ResponseFactory.success(response);
    }

    @Operation(summary = "✅ 모든 소과목 조회", description = "모든 소과목을 조회하는 API")
    @GetMapping("/minors")
    public ResponseEntity<SuccessResponse<MinorResponses>> getAllMinors() {
        var response = classificationQueryService.getAllMinors();
        return ResponseFactory.success(response);
    }
}




