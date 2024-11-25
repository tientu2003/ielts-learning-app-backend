package com.project.readingservice.internal.util;

import com.project.readingservice.external.util.UserSuggestion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserSuggestionImp implements UserSuggestion {


    @Override
    public List<String> getAnswerSuggestion(UUID userId, String testId, List<Boolean> correct) {
        return List.of();
    }

    @Override
    public String getGeneralSuggestion(UUID userId) {
        return "";
    }

    @Override
    public String calculateUserPerformance(UUID userId) {
        return "";
    }
}
