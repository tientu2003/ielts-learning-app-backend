package com.project.writingservice.internal.entity.user;

import com.project.writingservice.external.data.WritingExam;
import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.internal.util.WritingScore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("writing_user")
@AllArgsConstructor
@NoArgsConstructor
public class  MongoUserWritingRecord {
    @Id
    private String id;

    private String userId;

    private String examId;

    private Date createAt;

    private String duration;

    private WritingScore score;

    private List<String> answer;

    private String topic;

    public MongoUserWritingRecord(UserAnswer userAnswer, String userId, String topic) {
        this.userId = userId;
        this.answer = userAnswer.getAnswer();
        this.examId = userAnswer.getExamId();
        this.createAt = new Date();
        this.duration = userAnswer.getDuration();
        this.topic = topic;
    }

    public DetailRecord toDetailRecord(WritingExam writingExam) {
        return DetailRecord.builder()
                .context(writingExam.getContext())
                .diagram_url(writingExam.getDiagram_url())
                .task(writingExam.getTask())
                .finalScore(this.score.getFinalScore())
                .scores(this.score)
                .duration(this.duration)
                .userAnswer(this.answer)
                .build();
    }
}
