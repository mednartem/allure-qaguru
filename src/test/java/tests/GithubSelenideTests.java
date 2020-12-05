package tests;


import com.codeborne.selenide.Configuration;
import helper.UserModel;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helper.GetPropFromFile.getProperty;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class GithubSelenideTests {


    UserModel userModel = new UserModel(getProperty("login_github"), getProperty("password_github"));
    String titleIssue = randomAlphabetic(10);
    String label = "bug";

    String dataForSearch = "/mednartem/allure-qaguru";

    @Test
    void createIssue() {
        Configuration.clickViaJs = true;

        open("https://github.com/");

        //auth
        $(byText("Sign in")).click();
        $("#login_field").setValue(userModel.getUserName());
        $("#password").setValue(userModel.getPassword());
        $(byName("commit")).click();

        //open repository
        $(byName("q")).setValue(dataForSearch).pressEnter();
        $(by("href", dataForSearch)).click();
        $("[data-content=\"Issues\"]").click();

        //create issue
        $("[data-hotkey=\"c\"]").click();
        $(".btn-link.muted-link").click();
        $("#labels-select-menu").$x(".//summary").click();
        $(byText(label)).click();
        $("#issue_title").setValue(titleIssue).click();
        $(byText("Submit new issue")).click();

        //assert created issue
        $(".js-issue-title").shouldHave(text(titleIssue));
        $(".assignee.link-gray-dark").shouldHave(text(userModel.getUserName()));
        $("[id^='event']").find(".IssueLabel").shouldHave(text(label));

    }
}
