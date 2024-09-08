package com.its.service.domain.question.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class Choice {
    private String choiceNumber;   // 선택지 번호
    private String text;            // 선택지 텍스트
    private List<String> imageUrl;        // 선택지 이미지 URL (선택지에 이미지가 있을 경우)
}
