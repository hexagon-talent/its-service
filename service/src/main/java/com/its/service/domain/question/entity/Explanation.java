package com.its.service.domain.question.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class Explanation {
    private String text;            // 해설 텍스트
    private List<String> imageUrl;        // 해설 이미지 URL (해설에 이미지가 있을 경우)
}
