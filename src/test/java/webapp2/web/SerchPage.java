package webapp2.web;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$x;

public class SerchPage {
    private final ElementsCollection elements = $$x("//h2//a");


    public String searchHref() {
        return elements.first().getAttribute("href");
    }
}
