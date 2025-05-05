package com.project.common.dto;

import java.util.List;

public record ChatRequest(
        String model,
        List<ChatMessage> messages,
        boolean stream
) {}
