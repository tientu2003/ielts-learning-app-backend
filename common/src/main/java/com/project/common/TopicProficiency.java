package com.project.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicProficiency {
    private String topic;
    // 1 -> Listening
    // 2 -> Reading
    private Integer skill;
    private Double difficulty;
    // Chỉ số thành thạo
    // bandWeight là trọng số tổng điểm của bài thi nếu bài thi được 9.0 thì trọng số là 1
    // lpi =  bandWeight, sectionWeight, accuracy
    private Double accuracy;
    private Date dateTaken;

    public Double getTopicProficiencyIndex(){
        return accuracy*difficulty*timeDecay(Date.from(dateTaken.toInstant()));
    }

    public Double getFinalWeight(){
        return difficulty*timeDecay(Date.from(dateTaken.toInstant()));
    }

    private static Double timeDecay(Date testDate) {
        LocalDate testLocalDate = testDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now();
        long daysAgo = ChronoUnit.DAYS.between(testLocalDate, currentDate);
        return Math.exp(-daysAgo / 30.0);
    }

}
