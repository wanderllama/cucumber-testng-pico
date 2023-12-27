package jw.demo.stepDefinitions.shareDataBetweenFeatureSteps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jw.demo.util.driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

public class StepDefOne {
    private Container containerObj;

    public StepDefOne(Container containerObj) {
        this.containerObj = containerObj;
    }

    @Given("I navigate to  {string}")
    public void iNavigateTo(String url) {
        containerObj.setGoogleUrl(url);
        Driver.setDriver();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().get(url);
    }

    @Given("I use String {string} from step one in stepDefOne to search")
    public void iUseStringFromStepOneInStepDefOneToSearch(String data) {
        containerObj.getDataObject().setDataFromStepOne(data);
        Driver.getDriver().findElement(By.tagName("textarea")).sendKeys(data, Keys.ENTER);
    }

    @Then("I want to use data from step two in stepDefOne to search")
    public void iWantToUseDataFromStepTwoInStepDefOneToSearch() {
        String newSearch = containerObj.getDataObject().getDataFromStepTwo() +
                containerObj.getDataObject().getDataFromStepOne();
        Driver.getDriver().get(containerObj.getGoogleUrl());

        Driver.getDriver().findElement(By.tagName("textarea")).sendKeys(newSearch, Keys.ENTER);

        Long resultCount = Long.parseLong(Driver.getDriver().findElement(
                        By.xpath("//div[@id='result-stats']"))
                .getText().replaceAll("[^0-9]", ""));

        containerObj.getDataObject().setResultCountStepThree(resultCount);
        System.out.println("url: " + containerObj.getGoogleUrl() + " was used in all three steps but only passed into step one");
    }


}
