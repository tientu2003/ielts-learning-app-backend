package com.project.writingservice.internal;

import com.project.writingservice.CrudWritingService;
import com.project.writingservice.external.data.IdName;
import com.project.writingservice.external.data.WritingExam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudWritingServiceImpl implements CrudWritingService {
    @Override
    public List<IdName> getListAllWritingExams() {
        return List.of();
    }

    @Override
    public WritingExam getWritingExamById(String id) {
        return null;
    }
}
