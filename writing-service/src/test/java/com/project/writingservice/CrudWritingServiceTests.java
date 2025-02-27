package com.project.writingservice;

import com.project.writingservice.external.data.IdName;
import com.project.writingservice.external.data.WritingExam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudWritingServiceTests {

    private final CrudWritingService crudWritingService;

    @Test
    public void testListAll() {
        List<IdName> actual = crudWritingService.getListAllWritingExams();
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        log.info(actual.toString());
    }

    @Test
    public void testGetExamById() {
        WritingExam actual = crudWritingService.getWritingExamById("67b77093f9a55b0edd38956f");
        assertNotNull(actual);
        assertEquals(1, actual.getTask());
    }
}
