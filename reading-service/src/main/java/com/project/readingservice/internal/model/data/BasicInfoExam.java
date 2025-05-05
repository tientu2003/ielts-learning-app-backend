package com.project.readingservice.internal.model.data;

import com.project.common.constraints.CefrLevel;
import com.project.common.dto.BasicExamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicInfoExam {

    @Id
    private String id;

    @Field(name = "test_name")
    private String testName;

    @Field(name = "topics")
    private List<String>  topics;

    @Field(name = "levels")
    private List<CefrLevel>  levels;

    @Field(name = "difficulties")
    private List<Double> difficulties;

    public BasicExamDTO toDTO() {
        return new BasicExamDTO(id, testName, topics, difficulties , levels);
    }
}
