package com.project.listeningservice.internal;

import com.project.listeningservice.CrudListeningService;
import com.project.listeningservice.external.data.IdName;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;
import com.project.listeningservice.internal.model.data.ListeningExamRepository;
import com.project.listeningservice.internal.model.data.MongoIdName;
import com.project.listeningservice.internal.model.data.MongoListeningExam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CrudListeningServiceImpl implements CrudListeningService {

    final ListeningExamRepository listeningExamRepository;

    public CrudListeningServiceImpl(ListeningExamRepository listeningExamRepository) {
        this.listeningExamRepository = listeningExamRepository;
    }

    @Override
    public ListeningExam getListeningExamById(String id) {
        Optional<MongoListeningExam> mongoListeningExam = listeningExamRepository.findById(id);
        return mongoListeningExam.map(MongoListeningExam::toListeningExam).orElse(null);
    }

    @Override
    public ListeningAnswer getListeningAnswer(String id) {
        Optional<MongoListeningExam> mongoListeningExam = listeningExamRepository.findById(id);
        return mongoListeningExam.map(MongoListeningExam::toListeningAnswer).orElse(null);
    }

    @Override
    public List<IdName> listAllListeningExams() {
        return listeningExamRepository.getAllIdNames().parallelStream().map(MongoIdName::toIdName).collect(Collectors.toList());
    }


}
