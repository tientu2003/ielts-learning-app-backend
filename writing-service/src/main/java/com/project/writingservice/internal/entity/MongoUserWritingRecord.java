package com.project.writingservice.internal.entity;

import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.internal.util.WritingScore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@Document("writing_user")
@AllArgsConstructor
@NoArgsConstructor
public class MongoUserWritingRecord {
    @Id
    private String id;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "exam_id")
    private String examId;

    @Field(name = "create_at")
    private Date createAt;

    @Field(name = "duration")
    private String duration;

    @Field(name = "score")
    private WritingScore score;

    @Field(name = "answer")
    private String answer;

    public MongoUserWritingRecord(String userId, UserAnswer userAnswer, WritingScore score) {
        this.userId = userId;
        this.answer = userAnswer.getAnswer();
        this.examId = userAnswer.getExamId();
        this.createAt = userAnswer.getCreatedAt();
        this.duration = userAnswer.getDuration();
        this.score = score;
    }
}
