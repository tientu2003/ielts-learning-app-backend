package com.project.readingservice;

import com.project.readingservice.external.user.BasicReadingHistory;
import com.project.readingservice.external.user.DetailReadingTestRecord;
import com.project.readingservice.external.user.GeneralAssessment;
import com.project.readingservice.external.user.UserAnswer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserReadingServiceTests {

    @Autowired
    private UserReadingService userReadingService;

    final UUID testUserId = UUID.fromString("992d1bdc-8a46-40e2-aaf6-c9c8cd79ed1a");

    @Test
    void shouldSaveCorrectUserAnswerData(){

        List<String> testAnswers = List.of(
                "FALSE","NOT GIVEN","FALSE","FALSE","TRUE","TRUE","CHEMICAL ENGINEERING",
                "ASCANIO SOBRERO","GUNPOWDER","STOCKHOLM","DETONATOR","PNEUMATIC DRILL","COST",
                "G","F","C","D","A","B","E","THEORY OF MIND","CHOCOLATE",
                "INFORMATION","FOUR","OLDER","ADULTS","CHALLENGING","F","A","C","I","M","K","H",
                "D","A","C","F","D","C");



        UserAnswer testUserAnswer = UserAnswer.builder()
                .testId("671bdfc73bb6d5496f4e9db9")
                .answers(testAnswers)
                .createdAt(Timestamp.from(Instant.now()))
                .timeTaken(Duration.ofMinutes(50))
                .build();

        DetailReadingTestRecord newRecord = userReadingService.saveUserAnswerData(
                testUserId, testUserAnswer);

        assertNotNull(newRecord);

        assertThat(newRecord.getUserId()).isEqualTo(testUserId);

        assertThat(newRecord.getScore()).isEqualTo(9.0);

        assertThat(newRecord.getTimeTaken()).isEqualTo(Duration.ofMinutes(50));

        newRecord.getUserAnswers().forEach( result -> assertTrue(result.getCheck()));

    }

    @Test
    void shouldListAllUserReadingHistory(){

        List<BasicReadingHistory> actualReadingHistory =  userReadingService.listUserReadingTestHistory(testUserId);

        assertThat(actualReadingHistory).isNotNull();

        BasicReadingHistory actualFirstOne = actualReadingHistory.getFirst();

        assertThat(actualFirstOne.getId()).isEqualTo(testUserId);

        assertThat(actualFirstOne.getScore()).isEqualTo(9.0);

        assertThat(actualFirstOne.getTestName()).isEqualTo("Recent IELTS Reading Actual test 80\n");
    }


    @Test
    void shouldGetCorrectUserReadingGeneralAssessment(){

        GeneralAssessment actual = userReadingService.getReadingGeneralAssessment(testUserId);

        assertThat(actual).isNotNull();

        assertThat(actual.getAllScores()).isInstanceOf(List.class);

        assertThat(actual.getUserId()).isEqualTo(testUserId);

        assertThat(actual.getAverageScore()).isEqualTo(9.0);

    }

}
