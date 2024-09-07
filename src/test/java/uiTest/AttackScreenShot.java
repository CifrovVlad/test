package uiTest;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AttackScreenShot {

    @io.qameta.allure.Attachment(value = "screenshot", type = "image/png", fileExtension = "png")
    public byte[] screenShot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
