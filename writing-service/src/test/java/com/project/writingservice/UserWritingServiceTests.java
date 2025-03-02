package com.project.writingservice;

import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.external.user.UserSimpleRecord;
import com.project.writingservice.external.user.WritingSummary;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWritingServiceTests {
    private final UserWritingService userWritingService;

    @Test
    @Disabled
    void testCreateUserRecord() {
        UserAnswer userAnswer = UserAnswer.builder()
                .answer("New User Answer")
                .createdAt(new Date())
                .duration("12m")
                .examId("67b77093f9a55b0edd389574")
                .build();

        userWritingService.createUserAnswer(userAnswer);

        List<UserSimpleRecord> list = userWritingService.getAllUserHistoryRecords("Temp");

        assertNotNull(list);

        assertFalse(list.isEmpty());

        assertEquals("Test name",list.getFirst().getName());
        assertEquals(7.5, list.getFirst().getScore());

        DetailRecord actual = userWritingService.getUserAnswer(list.getFirst().getId());
        assertNotNull(actual);

        assertEquals("Some people think news has no connection to people’s lives. So then it is a waste of time to read the newspaper and watch television news programs. To what extent do you agree or disagree?",
                actual.getContext()
                );

    }

    @Test
    @Disabled
    void getSummary(){
       WritingSummary actual = userWritingService.getWritingSummary("Temp");
       assertNotNull(actual);

       assertEquals(7.5,actual.getAverageScore());
       assertNotNull(actual.getNextTestId());
    }

}
