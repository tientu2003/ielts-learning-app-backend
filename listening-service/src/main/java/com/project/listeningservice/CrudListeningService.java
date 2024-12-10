package com.project.listeningservice;

import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;
import com.project.listeningservice.external.data.IdName;

import java.util.List;

public interface CrudListeningService {

    ListeningExam getListeningExamById(String id);

    ListeningAnswer getListeningAnswer(String id);

    List<IdName> listAllListeningExams();

}
