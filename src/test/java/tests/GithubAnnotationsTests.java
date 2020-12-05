package tests;


import com.codeborne.selenide.Configuration;
import helper.UserModel;
import org.junit.jupiter.api.Test;
import steps.BaseSteps;

import static helper.GetPropFromFile.getProperty;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class GithubAnnotationsTests {
    UserModel userModel = new UserModel(getProperty("login_github"), getProperty("password_github"));
    String titleIssue = randomAlphabetic(10);
    String issueType = "bug";
    String dataForSearch = "/mednartem/allure-qaguru";

    @Test
    void createIssue() {
        Configuration.clickViaJs = true;
        BaseSteps steps = new BaseSteps();

        steps.openMainPage();
        steps.authToGitHub(userModel.getUserName(), userModel.getPassword());
        steps.openRepository(dataForSearch);
        steps.crateIssue(titleIssue, issueType);
        steps.assertCreatedIssue(titleIssue, userModel.getUserName(), issueType);
    }
}
