package com.its.service.domain.question.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Document(collection = "questions")
public class Question {

    @Id
    private String questionId;

    private String questionNumber; // 문제 번호
    private String title;           // 문제 제목
    private String content;         // 문제 내용
    private List<String> imageUrl;        // 이미지 URL (문제에 이미지가 있을 경우)
    private Long minorId;           // 소과목 ID (RDBMS의 Minor와 연동)

    private List<Choice> choices;   // 선택지 리스트
    private Explanation explanation; // 해설

}
