package HHtest.hh;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ModelPage {
    private String gender;
    private int age;
    private String city;
    private boolean number;
    private boolean LiveCity;

    public ModelPage(String gender, int age, String city, boolean number, boolean liveCity) {
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.number = number;
        LiveCity = liveCity;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public boolean isNumber() {
        return number;
    }

    public boolean isLiveCity() {
        return LiveCity;
    }
}
