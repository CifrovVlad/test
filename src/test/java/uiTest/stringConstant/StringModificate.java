package uiTest.stringConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringModificate {


    public static String getDateFormat(String str){
        return str + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
    }
}
