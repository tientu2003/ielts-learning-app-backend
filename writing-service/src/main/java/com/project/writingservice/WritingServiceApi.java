package com.project.writingservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/listening")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WritingServiceApi {
    private final CrudWritingService crudWritingService;
    private final UserWritingService userWritingService;


}
