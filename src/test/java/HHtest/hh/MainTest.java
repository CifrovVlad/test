package HHtest.hh;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;
import core.BaseTest;

import java.util.HashMap;
import java.util.Map;

public class MainTest extends BaseTest {

    private static final String URL = "https://hh.ru/applicant/resumes/view?resume=1edf0c93ff095811d20039ed1f6a3638497073";

    @Test
    public void test() {
        HhResumeTest hhResumeTest = new HhResumeTest(URL);
        Map<String, Object> params = new HashMap<>();
       params.put(hhResumeTest.GENDER, "M");
       params.put(hhResumeTest.AGE, 25);
       params.put(hhResumeTest.CITY, "Санкт-Петербург");
       params.put(hhResumeTest.CONFIRMED_NUMBER, true);
       params.put(hhResumeTest.READY_TO_RELOCATE, false);

       Map<String, Object> map = hhResumeTest.getMap();
     Assert.assertEquals(params, map);
    }
    @Test
    public void test2() {
        HhResumeTest hhResumeTest = new HhResumeTest(URL);
        ModelPage modelPage = new ModelPage("M", 25, "Санкт-Петербург", true, false);
        ModelPage modelPage1 = new ModelPage(hhResumeTest.getGender(), hhResumeTest.getAge(), hhResumeTest.getCity(), true, false);

        EqualsBuilder.reflectionEquals(modelPage1, modelPage);
    }
}
