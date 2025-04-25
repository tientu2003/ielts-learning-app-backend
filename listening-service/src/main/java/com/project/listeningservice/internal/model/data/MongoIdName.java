package com.project.listeningservice.internal.model.data;

import com.project.common.constraints.CefrLevel;
import com.project.common.constraints.Topic;
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
    @Field(name = "topics")
    private List<Topic> topics;
    @Field(name = "levels")
    private List<CefrLevel> levels;
    public BasicExamDTO toIdName(){
        return new BasicExamDTO(id, testName, topics, levels);
    }
}
