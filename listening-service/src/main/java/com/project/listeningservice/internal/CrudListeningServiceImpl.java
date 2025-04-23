package com.project.listeningservice.internal;

import com.project.common.dto.BasicExamDTO;
import com.project.listeningservice.CrudListeningService;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;
import com.project.listeningservice.internal.model.data.ListeningExamRepository;
import com.project.listeningservice.internal.model.data.MongoIdName;
import com.project.listeningservice.internal.model.data.MongoListeningExam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudListeningServiceImpl implements CrudListeningService {

    final ListeningExamRepository listeningExamRepository;

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
    public List<BasicExamDTO> listAllListeningExams() {
        return listeningExamRepository.getAllIdNames().parallelStream().map(MongoIdName::toIdName).toList();
    }


}
