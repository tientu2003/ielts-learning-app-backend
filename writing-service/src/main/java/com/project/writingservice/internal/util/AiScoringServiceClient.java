package com.project.writingservice.internal.util;

import com.project.writingservice.config.HuggingSpaceClientConfig;
import com.project.writingservice.external.user.AiScoringRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ai-scoring-service",
        url = "${ai.scoring.host}",
        configuration = HuggingSpaceClientConfig.class
)
public interface AiScoringServiceClient {
    @PostMapping(value = "/generate", consumes = "application/json")
    WritingScore evaluateUserAnswer(@RequestBody AiScoringRequest request);
}

