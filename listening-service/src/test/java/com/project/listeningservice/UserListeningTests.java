package com.project.listeningservice;

import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.ListeningSummary;
import com.project.listeningservice.external.user.UserAnswer;
import com.project.listeningservice.external.user.UserSimpleRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserListeningTests {

    @Autowired
    UserListeningService userListeningService;

    private final String userId = "992d1bdc-8a46-40e2-aaf6-c9c8cd79ed1a";

    @Test
    public void createNewUserAnswer() {
        List<String> answers = List.of("94635550", "CLARK HOUSE", "UNIVERSITY DRIVE", "MONDAY", "THURSDAY", "ONE MONTH",
                "A", "C", "B", "C", "COMPUTER AS TEACHER", "UNIVERSITY OF MELBOURNE", "TOP FLOOR", "GROUND FLOOR", "3:10", "PALM LOUNGE",
                "C", "B", "B", "A", "C", "A", "B", "B", "A", "C", "C", "E", "F", "PLASTIC", "PROCESSING", "SEASONED", "POLISHED", "COST",
                "GRAIN PATTERN", "WORDS","0.8","0.1","BLACK VELVET");
        UserAnswer listeningAnswer = UserAnswer.builder()
                .testId("6772e00df8a76ea9f7956412")
                .createdAt("2024/12/31 12:01:01")
                .timeTaken("13")
                .answers(answers)
                .build();
        String actualId =  userListeningService.createUserAnswer(listeningAnswer,userId);

        assertNotNull(actualId);

        DetailRecord actualRecord  = userListeningService.getListeningDetailRecord(actualId);

        assertNotNull(actualRecord);

        assertThat(actualRecord.getScore()).isEqualTo(9.0);

        assertThat(actualRecord.getUserId()).isEqualTo(userId);

    }

    @Test
    public void getScoreSummary() {
        ListeningSummary actual =  userListeningService.getListeningScore(userId);
        assertNotNull(actual);

        assertThat(actual.getUserId()).isEqualTo(userId);

        assertThat(actual.getAverageScore()).isEqualTo(9.0);
    }


    @Test
    public void checkHistoryList(){

        List<UserSimpleRecord> actual =  userListeningService.listAllListeningHistory(userId);

        assertNotNull(actual);

        assertThat(actual.getFirst().getScore()).isEqualTo(9.0);

    }
}
