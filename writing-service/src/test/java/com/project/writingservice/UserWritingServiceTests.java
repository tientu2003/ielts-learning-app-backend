package com.project.writingservice;

import com.project.writingservice.external.data.IdName;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWritingServiceTests {
    private final UserWritingService userWritingService;

    @Test
    void testCreateUserRecord() {
    }

}
