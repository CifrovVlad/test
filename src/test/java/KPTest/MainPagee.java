package KPTest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private static final String URL = "https://www.kinopoisk.ru";

    private final SelenideElement SearchFilmElement = $x("//*[@id = 'find_film']");
    private final SelenideElement SearchResultElement = $x("//form[id = 'formSearchMain']//input[@value = 'поиск']");


    public void searchFilm() {
        SearchFilmElement.click();
        SearchResultElement.sendKeys("Бэтмен");
        SearchResultElement.submit();
    }
}
