package tests;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.UserModel;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static config.GetPropFromFile.getProperty;
import static io.qameta.allure.Allure.step;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class GithubLambdaStepsTests {

    UserModel userModel = new UserModel(getProperty("login_github"), getProperty("password_github"));
    String issueTitle = randomAlphabetic(10);
    String issueType = "bug";
    String repository = "/mednartem/allure-qaguru";

    @Test
    void createIssue() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.clickViaJs = true;

        step("Open main page", () -> open("https://github.com/"));
        step("Authorization to github", () -> {
            $(byText("Sign in")).click();
            $("#login_field").setValue(userModel.getUserName());
            $("#password").setValue(userModel.getPassword()).pressEnter();
        });
        step("Open repository " + repository, () -> {
            $(byName("q")).setValue(repository).pressEnter();
            $(by("href", repository)).click();
            $("[data-content='Issues']").click();
        });
        step("Create issue", () -> {
            $("[data-hotkey='c']").click();
            $(".btn-link.muted-link").click();
            $("#labels-select-menu").$x(".//summary").click();
            $(byText(issueType)).click();
            $("#issue_title").setValue(issueTitle).click();
            $(byText("Submit new issue")).click();
        });
        step("Assert created issue", () -> {
            $(".js-issue-title").shouldHave(text(issueTitle));
            $("[aria-label='Select assignees']").shouldHave(text(userModel.getUserName()));
            $("[id^='event']").find(".IssueLabel").shouldHave(text(issueType));
        });
        closeWebDriver();
    }
}
