package com.project.listeningservice.internal.model.data;

import com.project.listeningservice.external.data.IdName;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class MongoIdName {
    @Field(name = "_id")
    private String id;
    @Field(name = "test_name")
    private String testName;

    public IdName toIdName(){
        return new IdName(id, testName);
    }
}
