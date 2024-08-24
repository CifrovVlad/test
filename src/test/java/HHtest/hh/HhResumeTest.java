package HHtest.hh;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.HashMap;
import java.util.Map;


import static com.codeborne.selenide.Selenide.$x;

public class HhResumeTest {

    private final SelenideElement gender = $x("//span[@data-qa='resume-personal-gender']");
    private final SelenideElement age = $x("//span[@data-qa='resume-personal-age']/span");
    private final SelenideElement liveString = $x("//span[@data-qa='resume-personal-address']/ancestor::p");
    private final SelenideElement confirmedNumber = $x("//div[@data-qa='resume-contacts-phone']//span[1]");
    private final SelenideElement city = $x("//span[@data-qa='resume-personal-address']");


    public static String GENDER = "Пол";
    public static String AGE = "Возраст";
    public static String CITY = "Город";
    public static String CONFIRMED_NUMBER = "Подтвержденный номер";
    public static String READY_TO_RELOCATE = "не готов к переезду";

    public HhResumeTest(String url) {
        Selenide.open(url);
    }

    public String getGender() {
        return gender.getText().equals("Мужской") ? "М" : "Ж";
    }
    public int getAge() {
        return Integer.parseInt(age.getText().replaceAll("[^0-9]", ""));
    }
    public boolean getLiveString() {
        return !liveString.getText().split(", ")[1].equals("Не готов к переезду");
    }
    public boolean getConfirmedNumber() {
        return confirmedNumber.isDisplayed();
    }
    public String getCity() {
        return city.getText();
    }
    public Map<String, Object> getMap() {
        return new HashMap<>(){{
                  put(GENDER, getGender());
                  put(AGE, getAge());
                  put(CITY, getCity());
                  put(CONFIRMED_NUMBER, getConfirmedNumber());
                  put(READY_TO_RELOCATE, getLiveString());
        }};
    }
}
