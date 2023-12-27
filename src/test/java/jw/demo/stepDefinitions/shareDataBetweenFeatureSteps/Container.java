package jw.demo.stepDefinitions.shareDataBetweenFeatureSteps;

import lombok.Data;

@Data
public class Container {
    private DataObjectForContainer dataObject;
    private String googleUrl;

    public Container(DataObjectForContainer obj) {
        this.dataObject = obj;
    }

}
