package com.project.writingservice.external.data;

import com.project.common.dto.BasicExamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BasicWritingDTO extends BasicExamDTO {
    private Integer task;
}
