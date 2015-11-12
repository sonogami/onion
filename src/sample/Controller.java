package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

public class Controller {

    int grade_test = 2;
    int class_test = 5;
    static final String[] student_test = new String[]{"김민준", "김재원", "양파", "인간", "진주시", "에릭테임즈", "에릭해커", "임창민"};
    static final int[] onion_test = new int[]{100, 1, 2, 3, 100, 4040, 19, 31};

    static HSSFRow row;     // 열
    static HSSFCell cell;   // 행

    @FXML
    protected TextField text;

    @FXML
    protected PasswordField pwfield;

    @FXML
    protected void login() {
//        // getText() 테스트
//        String id = text.getText();
//        String pw = pwfield.getText();

        HSSFWorkbook workbook = new HSSFWorkbook();

        //Sheet명 설정
        HSSFSheet sheet = workbook.createSheet(grade_test + "학년" + class_test + "반");

        //출력
        row = sheet.createRow(0);
        row.createCell(0).setCellValue("학생명");
        row.createCell(1).setCellValue("양파갯수");

        for(int i = 0;  i < student_test.length; i++) {
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(student_test[i]);
            row.createCell(1).setCellValue(onion_test[i]);
        }

        // 출력 파일 위치및 파일명 설정
        FileOutputStream outFile;
        try {
            outFile = new FileOutputStream(grade_test + "_" + class_test + ".xls");
            workbook.write(outFile);
            outFile.close();

            System.out.println("출력 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
