package com.project.listeningservice;

import com.project.listeningservice.external.data.IdName;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class CrudListeningExamTests {


    @Autowired
    CrudListeningService crudListeningService;

    @Test
    void loadAllIdName(){
        List<IdName> actual = crudListeningService.listAllListeningExams();
        assertThat(actual).isNotNull();

        assertThat(actual).isNotEmpty();

        IdName expectedIdName = new IdName("6772529ff8a76ea9f79563a0","Recent IELTS Listening Actual test 29");

        assertThat(actual).contains(expectedIdName);
    }
    @Test
    void loadOneListeningExamById(){
        String testId = "6772529ff8a76ea9f79563a0";
        ListeningExam actual =  crudListeningService.getListeningExamById(testId);
        assertThat(actual).isNotNull();

        assertThat(actual.getExamId()).isEqualTo(testId);
        assertThat(actual.getExamName()).isEqualTo("Recent IELTS Listening Actual test 29");

        ListeningExam.Recording actualRecording = actual.getRecording().getFirst();

        assertThat(actualRecording.getAudioUrl())
                .isEqualTo("https://s4-media1.study4.com/media/ielts_media/sound/03_IELTS-Recent-Actual-Test-With-Answers-Practice-Test-29-Section1.mp3");

        ListeningExam.Recording.QuestionGroup actualQuestionGroup = actualRecording.getQuestionGroups().getFirst();
        assertThat(actualQuestionGroup.getQuestionType()).isEqualTo("table");
        assertThat(actualQuestionGroup.getContextTable().getFirst()).isEqualTo(List.of("Example \n Full Name:","Answer \n Jane Bond"));

    }


    @Test
    void loadOneListeningAnswerById(){
        String testId = "6772529ff8a76ea9f79563a0";
        ListeningAnswer actualListeningAnswer = crudListeningService.getListeningAnswer(testId);

        assertThat(actualListeningAnswer).isNotNull();
        assertThat(actualListeningAnswer.getId()).isEqualTo(testId);
        assertThat(actualListeningAnswer.getAnswers()).contains("94635550");
    }
}
