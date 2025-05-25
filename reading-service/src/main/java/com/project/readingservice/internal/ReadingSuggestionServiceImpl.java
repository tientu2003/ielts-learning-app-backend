package com.project.readingservice.internal;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.SuggestionService;
import com.project.common.dto.BasicExamDTO;
import com.project.common.dto.ChatMessage;
import com.project.common.dto.ChatRequest;
import com.project.common.dto.TogetherAIResponse;
import com.project.readingservice.CRUDReadingService;
import com.project.readingservice.internal.util.TogetherAIClient;
import com.project.readingservice.internal.util.UserService;
import com.project.readingservice.internal.model.user.AiSuggestion;
import com.project.readingservice.internal.model.user.AiSuggestionRepository;
import com.project.readingservice.internal.model.user.UserReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadingSuggestionServiceImpl implements SuggestionService {


    final UserService userService;

    final CRUDReadingService crudReadingService;

    final LanguageProficiencyService languageProficiencyService;

    final UserReadingRepository userReadingRepository;

    final TogetherAIClient client;

    final AiSuggestionRepository aiSuggestionRepository;

    @Override
    public void generateNewRecommendation() {

        String model = "meta-llama/Llama-3.3-70B-Instruct-Turbo-Free";
        String prompt = """
                You are an expert English language tutor. Analyze the user's English Reading performance across different topics and provide personalized recommendations to improve reading skill.
                
                For each topic the user has studied, you are given the following data:
                - **TopicProficiencyIndex**: A score indicating the user's overall ability in this topic.
                - **TopicConsistencyIndex**: A measure of how consistently the user performs in this topic.
                - **averageTopicScore**: The average score the user has achieved on tests related to this topic.
                - **TopicAccuracy**: The percentage of correct answers or writing accuracy in this topic.
                
                Instructions:
                1. Identify weak topics based on low TopicProficiencyIndex, low averageTopicScore, or low TopicAccuracy.
                2. Check TopicConsistencyIndex to detect unstable performance, even in topics where proficiency seems high.
                3. Recommend specific areas for improvement per topic, prioritizing topics not practiced recently.
                4. Suggest learning strategies or activities
                
                Present the evaluation in a clear, friendly, and motivational tone to encourage the user to keep learning and improving.
                """;
        List<ChatMessage> messages = List.of(
                new ChatMessage("system", prompt),
                new ChatMessage("user", constructContent()));
        ChatRequest request = new ChatRequest(model, messages, false);
        TogetherAIResponse response = client.chatCompletion(request);

        if (response != null) {
            aiSuggestionRepository.save(AiSuggestion.builder()
                    .userId(userService.getUserId())
                    .createdAt(new Date())
                    .suggestion(response.getChoices().getFirst().getMessage().getContent())
                    .build());
        }
    }

    private String constructContent() {
        StringBuilder content = new StringBuilder();
        content.append("Here is the user's performance data for different topics:\n\n");
        List<LanguageProficiencyDTO> data = languageProficiencyService.getAllTopicProficiencyIndexs(null);
        for (LanguageProficiencyDTO topic : data) {
            content.append("Topic: ").append(topic.getTopic()).append("\n");
            content.append("- TopicProficiencyIndex: ").append(String.format("%.2f", topic.getTpi())).append("\n");
            content.append("- TopicConsistencyIndex: ").append(String.format("%.2f", topic.getTpi())).append("\n");
            content.append("- AverageTopicScore: ").append(String.format("%.2f", topic.getAverageScore())).append("\n");
            content.append("- TopicAccuracy: ").append(String.format("%.2f%%", topic.getAverageAccuracy() * 100)).append("\n");
        }
        return content.toString();
    }

    @Override
    public String getPersonalRecommendation() {
        AiSuggestion results =  aiSuggestionRepository.findByUserIdWithLastestDate(userService.getUserId());
        if(results == null) {
            return "";
        }
        return results.getSuggestion();
    }

    @Override
    public BasicExamDTO getSuggestedNextExam() {
        String userId = userService.getUserId();
        List<BasicExamDTO> allExams = crudReadingService.listAllReadingTestName();

        // Get user's language proficiency data for all topics
        List<LanguageProficiencyDTO> topicProficiencies = languageProficiencyService.getAllTopicProficiencyIndexs(
                languageProficiencyService.getAllTopicsByUserId()
        );
        if (topicProficiencies == null) {
            return allExams.isEmpty() ? null : allExams.get((int) (Math.random() * allExams.size()));
        }

        // Get user's recent exam history (last 2 months)
        Date twoMonthsAgo = Date.from(LocalDate.now().minusWeeks(2)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<String> recentExamIds = userReadingRepository.findByUserIdAndDateAfter(userId, twoMonthsAgo);

        // Filter out recently taken exams
        List<BasicExamDTO> eligibleExams = allExams.stream()
                .filter(exam -> !recentExamIds.contains(exam.getId()))
                .toList();

        if (eligibleExams.isEmpty()) {
            return null;
        }

        // Find weakest topics based on TPI (Topic Proficiency Index)
        List<String> weakestTopics = topicProficiencies.stream()
                .sorted(Comparator.comparing(LanguageProficiencyDTO::getTpi))
                .map(LanguageProficiencyDTO::getTopic)
                .limit(3)
                .toList();

        // Score each eligible exam based on multiple factors
        return eligibleExams.stream()
                .map(exam -> new ExamScore(exam, calculateExamScore(exam, weakestTopics, topicProficiencies, true)))
                .max(Comparator.comparing(ExamScore::getScore))
                .map(ExamScore::getExam)
                .orElse(eligibleExams.getFirst());
    }
}
