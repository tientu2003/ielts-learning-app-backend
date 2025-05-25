package com.project.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class TogetherAIResponse {
    String id;
    String object;
    long created;
    String model;
    List<Choice> choices;
    Usage usage;

    @Data
    public static class Choice {
        String finish_reason;
        long seed;
        Object logprobs;
        int index;
        Message message;
    }

    @Data
    public static class Message {
        String role;
        String content;
        Object[] tool_calls;
    }

    @Data
    public static class Usage {
        int prompt_tokens;
        int completion_tokens;
        int total_tokens;
    }

}
