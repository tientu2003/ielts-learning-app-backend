package com.project.readingservice;

import com.project.readingservice.external.util.ReadingScore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@Slf4j
@SpringBootTest
public class ReadingScoreUtilTests {

    @Autowired
    ReadingScore readingScore;

    @Test
    void shouldCheckCorrectAnswer(){
        List<Boolean> correct =  readingScore.checkAnswerAndCalculateScore(List.of( "More abcd","abcd"),
                List.of("(More) abcd","(More) abcd"));
        for (Boolean b : correct) {
            assertTrue(b);
        }
    }
}
