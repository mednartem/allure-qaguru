package steps;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BaseSteps {

    @Step("Open main page")
    public void openMainPage() {
        open("https://github.com/");
    }

    @Step("Authorization to github")
    public void authToGitHub(String name, String password) {
        $(byText("Sign in")).click();
        $("#login_field").setValue(name);
        $("#password").setValue(password).pressEnter();
    }

    @Step("Open repository {repositoryName}")
    public void openRepository(String repositoryName) {
        $(byName("q")).setValue(repositoryName).pressEnter();
        $(by("href", repositoryName)).click();
        $("[data-content='Issues']").click();
    }

    @Step("Create issue with title '{titleIssue}' and type {typeIssue}")
    public void crateIssue(String titleIssue, String typeIssue) {
        $("[data-hotkey='c']").click();
        $(".btn-link.muted-link").click();
        $("#labels-select-menu").$x(".//summary").click();
        $(byText(typeIssue)).click();
        $("#issue_title").setValue(titleIssue).click();
        $(byText("Submit new issue")).click();
    }

    @Step("Assert created issue")
    public void assertCreatedIssue(String issueTitle, String assignee, String issueType) {
        $(".js-issue-title").shouldHave(text(issueTitle));
        $("[aria-label='Select assignees']").shouldHave(text(assignee));
        $("[id^='event']").find(".IssueLabel").shouldHave(text(issueType));
    }
}



