package com.project.writingservice.internal.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WritingScore {
    private Double CC;
    private Double LS;
    private Double ICP;
    private Double SMP;
    private Double ALW;
    private Double VILW;
    private Double LR;
    private Double VV;
    private Double ASWF;
    private Double GR;
    private Double MCSS;
    private Double CCG;
    private Double TA;
    private Double CR;
    private Double CCI;
    private Double RSE;

    private Double finalScore;

    public void calculateFinalScore () {
        double ave = (this.LR + this.GR + this.CC + this.TA) / 4;
        this.finalScore =  Math.round(ave * 2) / 2.0;
    }

}
