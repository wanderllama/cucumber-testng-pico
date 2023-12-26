package jw.demo.stepDefinitions.shareDataBetweenSteps;

import lombok.Data;

@Data
public class DataObjectForContainer {
    private Long resultCountStepTwo;
    private Long resultCountStepThree;
    private String dataFromStepOne;
    private String dataFromStepTwo;
    private String combinedData;
}
