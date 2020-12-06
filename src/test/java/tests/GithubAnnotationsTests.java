package tests;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.UserModel;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;
import steps.BaseSteps;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static config.GetPropFromFile.getProperty;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class GithubAnnotationsTests {

    BaseSteps steps = new BaseSteps();
    UserModel userModel = new UserModel(getProperty("login_github"), getProperty("password_github"));
    String issueTitle = randomAlphabetic(10);
    String issueType = "bug";
    String repository = "/mednartem/allure-qaguru";

    @Test
    void createIssue() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.clickViaJs = true;

        steps.openMainPage();
        steps.authToGitHub(userModel.getUserName(), userModel.getPassword());
        steps.openRepository(repository);
        steps.crateIssue(issueTitle, issueType);
        steps.assertCreatedIssue(issueTitle, userModel.getUserName(), issueType);
        closeWebDriver();
    }
}
