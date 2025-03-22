package com.project.writingservice;

import com.project.writingservice.external.data.IdName;
import com.project.writingservice.external.data.WritingExam;

import java.util.List;

public interface CrudWritingService {

    List<IdName> getListAllWritingExams();

    WritingExam getWritingExamById(String id);

    IdName getNextWritingExam(String userId);
    
}
