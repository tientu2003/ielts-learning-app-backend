package com.project.writingservice.internal;

import com.project.writingservice.CrudWritingService;
import com.project.writingservice.external.data.IdName;
import com.project.writingservice.external.data.WritingExam;
import com.project.writingservice.internal.entity.MongoWritingExam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudWritingServiceImpl implements CrudWritingService {

    private final WritingExamRepository writingExamRepository;

    @Override
    public List<IdName> getListAllWritingExams() {
        return writingExamRepository.findAll().parallelStream().map(writingExam ->
                new IdName(writingExam.getId(),writingExam.getContext())
        ).toList();
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
    public IdName getNextWritingExam(String userId) {
        // retrieve Id and Name of WritingExam Random from list
        List<IdName> list = getListAllWritingExams();
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }
}

