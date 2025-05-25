package com.project.readingservice.internal.util;

import com.project.common.dto.ChatRequest;
import com.project.common.dto.TogetherAIResponse;
import com.project.readingservice.config.TogetherAiClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "togetherAI", url = "https://api.together.xyz", configuration = TogetherAiClientConfig.class)
public interface TogetherAIClient {

    @PostMapping(value = "/v1/chat/completions", consumes = "application/json")
    TogetherAIResponse chatCompletion(@RequestBody ChatRequest request);
}
