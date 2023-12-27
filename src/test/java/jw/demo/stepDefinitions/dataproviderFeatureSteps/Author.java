package jw.demo.stepDefinitions.dataproviderFeatureSteps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private String title;
    private String author;
    private String yearOfPublishing;

}
