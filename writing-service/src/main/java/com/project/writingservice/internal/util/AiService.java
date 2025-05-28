package com.project.writingservice.internal.util;

import com.project.common.SuggestionService;
import com.project.common.TopicProficiency;
import com.project.writingservice.external.user.AiScoringRequest;
import com.project.writingservice.internal.entity.user.MongoUserWritingRecord;
import com.project.writingservice.internal.entity.user.UserWritingRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AiService {

    private final AiScoringServiceClient client;
    private final UserWritingRecordRepository userWritingRecordRepository;
    private final SuggestionService suggestionService;

    @Async
    public void sendUserAnswerToAi(String recordId, String question, List<String> answer ) {
        AiScoringRequest request = new AiScoringRequest(recordId, question, answer);
        try {
            WritingScore response = client.evaluateUserAnswer(request);
            response.calculateFinalScore();
            Optional<MongoUserWritingRecord> record =  userWritingRecordRepository.findById(recordId);
            record.ifPresent(userWritingRecord -> {
                userWritingRecord.setScore(response);
                TopicProficiency.builder()
                        .build();
                userWritingRecordRepository.save(userWritingRecord);
            });
            suggestionService.generateNewRecommendation();
        } catch (HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                log.error("AI Scoring Process Failed with 500: {}", e.getResponseBodyAsString());
            } else {
                log.error("AI Scoring Process Failed with status {}: {}", e.getStatusCode(), e.getResponseBodyAsString());
            }
        } catch (Exception e) {
            log.error("AI Scoring Process Failed with Unknown Error {}: {}", e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "Unknown");
        }

    }
}
