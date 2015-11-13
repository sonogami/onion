package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

public class Controller {
    static HSSFRow row;     // 열
    Pane p1, p2;
    private Stage stage;

    @FXML
    protected ImageView draggableImage;


    public void setDragFunction(Stage stage) {
        this.stage = stage;

        draggableImage.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        draggableImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public void btnExit_Clicked(Event event) {
        System.exit(0);
    }

    public void btnMin_Clicked(Event event) {stage.setIconified(true);}

    public void menu1(Event event) {

    }

    public void menu2(Event event) {

    }

    private double xOffset = 0;
    private double yOffset = 0;


    public void btnExcel_Clicked(Event event) {/*
        Student students[] = new Student[]{};
        HSSFWorkbook workbook = new HSSFWorkbook();

        //Sheet명 설정
        HSSFSheet sheet = workbook.createSheet(grade_test + "학년" + class_test + "반");

        //출력
        row = sheet.createRow(0);
        row.createCell(0).setCellValue("학생명");
        row.createCell(1).setCellValue("양파갯수");

        for(int i = 0;  i < student_test.length; i++) {
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(students[i].getName());
            row.createCell(1).setCellValue(students[i].getOnion());
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
        }*/
    }
}
