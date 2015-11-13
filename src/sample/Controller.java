package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

public class Controller {

    int grade_test = 2;
    int class_test = 5;
//    static final String[] student_test = new String[]{"김민준", "김재원", "양파", "인간", "진주시", "에릭테임즈", "에릭해커", "임창민"};
//    static final int[] onion_test = new int[]{100, 1, 2, 3, 100, 4040, 19, 31};

    static HSSFRow row;     // 열


    public void btnExit_Clicked(Event event) {
        System.exit(0);
    }
}
