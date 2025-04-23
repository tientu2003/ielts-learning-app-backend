package com.project.listeningservice.internal.model.data;

import com.project.common.Topic;
import com.project.common.dto.BasicExamDTO;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class MongoIdName {
    @Field(name = "_id")
    private String id;
    @Field(name = "test_name")
    private String testName;
    @Field(name = "topic_1")
    private Topic topic1;
    @Field(name = "topic_2")
    private Topic topic2;
    @Field(name = "topic_3")
    private Topic topic3;
    @Field(name = "topic_4")
    private Topic topic4;
    public BasicExamDTO toIdName(){
        return new BasicExamDTO(id, testName, List.of(topic1,topic2,topic3,topic4));
    }
}
