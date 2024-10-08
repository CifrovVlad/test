package webapp2.web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final SelenideElement element = $x("/input[@placeholder='Поиск']");

    public MainPage(String baseUrl) {
        Selenide.open(baseUrl);
    }

    /**
     *
     * @param text
     */
    public SerchPage serchElement(String text) {
        element.click();
        element.setValue(text);
        element.sendKeys(Keys.ENTER);
        return new SerchPage();
    }

}
