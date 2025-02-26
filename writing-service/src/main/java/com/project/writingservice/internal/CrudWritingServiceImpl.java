package com.project.writingservice.internal;

import com.project.writingservice.CrudWritingService;
import com.project.writingservice.external.data.IdName;
import com.project.writingservice.external.data.WritingExam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudWritingServiceImpl implements CrudWritingService {

    private final WritingExamRepository writingExamRepository;

    @Override
    public List<IdName> getListAllWritingExams() {
        return writingExamRepository.getAll().stream().map(writingExam ->
                new IdName(writingExam.getId(),writingExam.getContext())
        ).toList();
    }

    @Override
    public WritingExam getWritingExamById(String id) {
        return null;
    }
}

