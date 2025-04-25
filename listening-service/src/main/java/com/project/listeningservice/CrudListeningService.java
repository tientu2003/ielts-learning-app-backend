package com.project.listeningservice;

import com.project.common.constraints.Topic;
import com.project.common.dto.BasicExamDTO;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;

import java.util.List;

public interface CrudListeningService {

    ListeningExam getListeningExamById(String id);

    ListeningAnswer getListeningAnswer(String id);

    List<BasicExamDTO> listAllListeningExams();

    List<Topic> listAllTopics();

}
