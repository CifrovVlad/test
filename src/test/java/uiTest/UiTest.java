package uiTest;

import config.Conf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static io.qameta.allure.Allure.attachment;
@Tag("ui")
public class UiTest extends Conf {
AttackScreenShot attackScreenShot = new AttackScreenShot();

    @Test
    @DisplayName("поиск в Google")
    void testGoogleSearch() {
        step("Открываем главню страницу", () -> {
            open("https://www.google.com/");
            attackScreenShot.screenShot();
        });
        step("Набираем текст поиска", () -> {
            $("#APjFqb").setValue("хозяева").pressEnter();
            attackScreenShot.screenShot();

        });
        step("Переходим по 1 ссылке", () -> {
            $x("//*[@id='rso']/div[1]").click();
            attackScreenShot.screenShot();
        });

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

    @Test
    @DisplayName("регистрация на сайте")
    void testSetColumRegistration() {
        open("https://practice-automation.com/form-fields/");
        $("#name-input").setValue("Vlad");
        $("input[type=password]").setValue("123456");
        $("#drink3").click();
        $("#color2").click();
        $("#automation").click();
        $("#automation > option:nth-child(2)").click();
        $("#email").setValue("cifrovvladislav@mail.ru");
        $("#message").setValue("Hello world");

    }

}

