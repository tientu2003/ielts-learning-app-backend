package com.project.listeningservice.external.util;

import com.project.common.dto.ChatRequest;
import com.project.listeningservice.config.TogetherAiClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "togetherAI", url = "https://api.together.xyz", configuration = TogetherAiClientConfig.class)
public interface TogetherAIClient {

    @PostMapping(value = "/v1/chat/completions", consumes = "application/json")
    String chatCompletion(@RequestBody ChatRequest request);
}
