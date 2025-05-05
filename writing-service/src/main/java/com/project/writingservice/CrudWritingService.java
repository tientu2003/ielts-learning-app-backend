package com.project.writingservice;

import com.project.writingservice.external.data.BasicWritingDTO;
import com.project.writingservice.external.data.WritingExam;

import java.util.List;

public interface CrudWritingService {

    List<BasicWritingDTO> getListAllWritingExams();

    WritingExam getWritingExamById(String id);

    List<String> listAllTopics();
}
