package com.project.writingservice.internal;

import com.project.writingservice.CrudWritingService;
import com.project.writingservice.external.data.BasicWritingDTO;
import com.project.writingservice.external.data.WritingExam;
import com.project.writingservice.internal.entity.data.MongoWritingExam;
import com.project.writingservice.internal.entity.data.WritingExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudWritingServiceImpl implements CrudWritingService {

    final WritingExamRepository writingExamRepository;

    @Override
    public List<BasicWritingDTO> getListAllWritingExams() {
        return writingExamRepository.findAll().parallelStream().map(MongoWritingExam::toBasicExamDTO).toList();
    }

    @Override
    public WritingExam getWritingExamById(String id) {
        Optional<MongoWritingExam> optionalWritingExam = writingExamRepository.findById(id);
        if(optionalWritingExam.isPresent()) {
            MongoWritingExam mongoWritingExam = optionalWritingExam.get();
            return mongoWritingExam.toWritingExam();
        }
        return null;
    }

    @Override
    public List<String> listAllTopics() {
        return writingExamRepository.listAllTopics();
    }
}

