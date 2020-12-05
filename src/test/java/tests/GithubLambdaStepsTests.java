package tests;


import com.codeborne.selenide.logevents.SelenideLogger;
import helper.UserModel;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helper.GetPropFromFile.getProperty;
import static io.qameta.allure.Allure.step;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class GithubLambdaStepsTests {

    UserModel userModel = new UserModel(getProperty("login_github"), getProperty("password_github"));
    String titleIssue = randomAlphabetic(10);
    String label = "bug";
    String dataForSearch = "/mednartem/allure-qaguru";

    @Test
    void createIssue() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Open main page", () -> open("https://github.com/"));
        step("Authorization to github", () -> {
            $(byText("Sign in")).click();
            $("#login_field").setValue(userModel.getUserName());
            $("#password").setValue(userModel.getPassword());
            $(byName("commit")).click();
        });
        step("Open repository " + dataForSearch, () -> {
            $(byName("q")).setValue(dataForSearch).pressEnter();
            $(by("href", dataForSearch)).click();
            $("[data-content=\"Issues\"]").click();
        });
        step("Create issue", () -> {
            $("[data-hotkey=\"c\"]").click();
            $(".btn-link.muted-link").click();
            $("#labels-select-menu").$x(".//summary").click();
            $(byText(label)).click();
            $("#issue_title").setValue(titleIssue).click();
            $(byText("Submit new issue")).click();
        });
        step("Assert created issue", () -> {
            $(".js-issue-title").shouldHave(text(titleIssue));
            $(".assignee.link-gray-dar").shouldHave(text(userModel.getUserName()));
            $("[id^='event']").find(".IssueLabel").shouldHave(text(label));
        });

    }
}
