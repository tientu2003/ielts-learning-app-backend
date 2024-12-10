package com.project.listeningservice.internal;

import com.project.listeningservice.CrudListeningService;
import com.project.listeningservice.external.data.IdName;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudListeningServiceImpl implements CrudListeningService {
    @Override
    public ListeningExam getListeningExamById(String id) {
        return null;
    }

    @Override
    public ListeningAnswer getListeningAnswer(String id) {
        return null;
    }

    @Override
    public List<IdName> listAllListeningExams() {
        return List.of();
    }


}
