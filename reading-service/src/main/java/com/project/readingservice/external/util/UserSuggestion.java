package com.project.readingservice.external.util;

import java.util.List;
import java.util.UUID;

public interface UserSuggestion {

    List<String> getAnswerSuggestion(UUID userId, String testId, List<Boolean> correct);

    String getGeneralSuggestion(UUID userId);

    String calculateUserPerformance(UUID userId);
}
