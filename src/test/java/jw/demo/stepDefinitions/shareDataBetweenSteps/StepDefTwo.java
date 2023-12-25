package jw.demo.stepDefinitions.shareDataBetweenSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import jw.demo.util.driver.Driver;
import org.openqa.selenium.By;

public class StepDefTwo {

    private Container containerObj;

    public StepDefTwo(Container containerObj) {
        this.containerObj = containerObj;
    }


    @And("I want to use data from step one in stepDefTwo and update search with {string}")
    public void iWantToUseDataFromStepOneInStepDefTwoToSearch(String data) {
        Long resultCount = Long.parseLong(Driver.getDriver().findElement(
                        By.xpath("//div[@id='result-stats']"))
                .getText().replaceAll("[^0-9]", ""));

        System.out.println(resultCount);

        containerObj.getDataObject().setResultCountStepTwo(resultCount);
        containerObj.getDataObject().setDataFromStepTwo(data);
    }

    @Then("show all information")
    public void showAllInformation() {
        System.out.println("this is the last step and can see all information saved to container/context object from previous steps thanks to dependency injection");

        System.out.println("step 1 data was " + containerObj.getDataObject().getDataFromStepOne());
        System.out.println("step 2 data was " + containerObj.getDataObject().getDataFromStepTwo());

        System.out.println(containerObj.getDataObject().getResultCountStepTwo() + " was the number of results in step 2");
        System.out.println(containerObj.getDataObject().getResultCountStepThree() + " was the number of results in step 3");
    }
}
