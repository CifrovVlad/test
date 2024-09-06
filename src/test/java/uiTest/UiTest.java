package uiTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.step;

public class UiTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.browserVersion = "127.0.0.1";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @Test
    @DisplayName("поиск в Google")
    void testGoogleSearch() {
        open("https://www.google.com/");
        $("#APjFqb").setValue("хозяева").pressEnter();
        $x("//*[@id='rso']/div[1]").click();

    }

    @Test
    @DisplayName("поиск в Youtube")
    void testYoutubeSearch() {
        open("https://www.youtube.com/");
        $("input#search").setValue("хозяева").pressEnter();
        $("#avatar-section").click();
        $(byText("Видео")).click();
        $("#thumbnail[href='/watch?v=WbaHFgplGKw']").click();
        $(byTitle("Во весь экран (f)")).click();

    }
}

