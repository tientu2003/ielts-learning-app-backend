package com.project.readingservice;

import com.project.readingservice.external.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CRUDReadingServiceTests {

    @Autowired
    private CRUDReadingService crudReadingService;

    @Test
    void shouldReturnListNameTest(){
        List<String> ids = crudReadingService.listAllReadingTestName();
        assertThat(ids).isNotNull();

        assertThat(ids).isNotEmpty();

        assertThat(ids).contains("Recent IELTS Reading Actual test 80\n");
    }

    @Test
    void shouldGetCorrectAnswerTest(){

        String testId = "671bdfc73bb6d5496f4e9db9";

        String testName = "Recent IELTS Reading Actual test 80\n";

        List<String> testAnswers = List.of(
            "FALSE","NOT GIVEN","FALSE","FALSE","TRUE","TRUE","CHEMICAL ENGINEERING",
            "ASCANIO SOBRERO","GUNPOWDER","STOCKHOLM","DETONATOR","PNEUMATIC DRILL","COST",
            "G","F","C","D","A","B","E","THEORY OF MIND [OR] TOM [OR] CHILDREN’S TOM","CHOCOLATE",
            "INFORMATION","FOUR/4","OLDER","ADULTS","(MORE) CHALLENGING","F","A","C","I","M","K","H",
            "D","A","C","F","D","C");


        AnswerData testAnswerData = AnswerData.builder()
                .testId(testId)
                .testName(testName)
                .answers(testAnswers)
                .recommendations(List.of("null"))
                .build();

        AnswerData actualAnswerData = crudReadingService.getAnswerTest(testName);


        assertThat(actualAnswerData).isNotNull();

        assertThat(actualAnswerData.getTestId()).isEqualTo("671bdfc73bb6d5496f4e9db9");

        assertThat(actualAnswerData.getTestName()).isEqualTo(testName);

        assertEquals(actualAnswerData.getAnswers(), testAnswers);

        assertThat(actualAnswerData.getRecommendations()).isEqualTo(List.of());

    }

    @Test
    void shouldReturnCorrectDisplayedReadingTestData(){

        String expectedName = "Recent IELTS Reading Actual test 80\n";

        String testId = "671bdfc73bb6d5496f4e9db9";

        QuestionGroup expectedQuestionGroup = QuestionGroup.builder()
                .readingTestType(ReadingTestType.UNKNOWN)
                .context(List.of("Do the following statements agree with the information given in Reading Passage 1?"))
                .questions(List.of("The first Nobel Prize was awarded in 1895."))
                .build()
        ;


        Paragraph expectedParagraph =  Paragraph.builder()
                .title("A")
                .paragraph("Since 1901, the Nobel Prize has been honoring" +
                        " men and women from all corners of the globe for outstanding achievements in physics," +
                        " chemistry, medicine, literature, and for work in peace. The foundations for the prize " +
                        "were laid in 1895 when Alfred Nobel wrote his last will, leaving much of his wealth to the " +
                        "establishment of the Nobel Prize.")
                .build();

        Passage expectedPassage = Passage.builder()
                .articleName("Alfred Nobel")
                .paragraphs(List.of(expectedParagraph))
                .questionGroups(List.of(expectedQuestionGroup))
                .build();

        ReadingTestData.builder()
                .id(testId)
                .name(expectedName)
                .passages(List.of(expectedPassage))
                .build();

        ReadingTestData actual =  crudReadingService.getReadingTestData(expectedName);

        assertNotNull(actual);

        assertThat(actual.getId()).isEqualTo(testId);

        assertThat(actual.getName()).isEqualTo(expectedName);

        assertInstanceOf(List.class, actual.getPassages());

        for( Object element : actual.getPassages()){
            assertInstanceOf(Passage.class, element);
        }

        Passage passage =  actual.getPassages().getFirst();

        assertThat(passage.getArticleName()).isEqualTo(expectedPassage.getArticleName());

        boolean flag = false;

        for(Paragraph paragraph : passage.getParagraphs()) {
            if(paragraph.getTitle().equals(expectedParagraph.getTitle()) && paragraph.getParagraph().equals(expectedParagraph.getParagraph())){
                flag = true;
                break;
            }
        }

        assertThat(flag).isTrue();

        QuestionGroup actualQuestionGroup = passage.getQuestionGroups().getFirst();

        assertTrue(actualQuestionGroup.getQuestions().contains(expectedQuestionGroup.getQuestions().getFirst()));

        assertTrue(actualQuestionGroup.getContext().contains(expectedQuestionGroup.getContext().getFirst()));

    }

    @Test
    void shouldCreateNewReadingTest(){

        // Create Paragraph instance
        Paragraph paragraph1 = Paragraph.builder()
                .title("Introduction")
                .paragraph("This is the introductory paragraph for the passage.")
                .build();

        // Create QuestionGroup instance
        QuestionGroup questionGroup1 = QuestionGroup.builder()
                .context(Arrays.asList("Context for question group 1"))
                .diagramUrl("http://example.com/diagram1")
                .readingTestType(ReadingTestType.MultipleChoice)
                .questions(Arrays.asList("What is the main topic?", "Who is the author?"))
                .build();

        // Create Passage instance
        Passage passage1 = Passage.builder()
                .difficulty("Medium")
                .topic("Sample Topic")
                .articleName("Sample Article")
                .paragraphs(Arrays.asList(paragraph1))
                .questionGroups(Arrays.asList(questionGroup1))
                .build();

        // Create NewReadingTest instance
        NewReadingTest newReadingTest = NewReadingTest.builder()
                .name("Sample Test")
                .level("Intermediate")
                .passages(Arrays.asList(passage1))
                .answers(Arrays.asList("Answer 1", "Answer 2"))
                .recommendations(Arrays.asList("Review paragraph 1", "Focus on multiple choice questions"))
                .build();


        ReadingTestData receivedReadingTestData = crudReadingService.Activity and Fragment LifecycreateNewReadingTest(newReadingTest);

        assertNotNull(receivedReadingTestData);

        List<String> list = crudReadingService.listAllReadingTestName();

        assertThat(list).contains("Sample Test");
    }

    @Test
    void shouldDeleteCorrectReadingTest(){

        String Id = crudReadingService.getReadingTestData("Sample Test").getId();

        assertNotNull(Id);
        crudReadingService.deleteReadingTest(Id);

        assertNull(crudReadingService.getReadingTestData("Sample Test"));

    }
}
