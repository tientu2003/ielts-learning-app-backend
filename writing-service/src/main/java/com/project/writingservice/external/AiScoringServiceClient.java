package com.project.writingservice.external;

import com.project.writingservice.external.user.AiScoringRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-scoring-service", url = "${ai.scoring.host}")
public interface AiScoringServiceClient {
    @PostMapping("/score")
    void evaluateUserAnswer(@RequestBody AiScoringRequest request);
}

