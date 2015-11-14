package sample;

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
    private Stage stage;

    private double xOffset = 0;
    private double yOffset = 0;

    private int grade_test = 2;
    private int class_test = 5;

    private Student[] students = new Student[] {new Student("김민준",3),new Student("김재원",3),new Student("양파맨",1)};

    @FXML
    protected ImageView draggableImage;
    @FXML
    protected ImageView onion1;
    @FXML
    protected ImageView onion2;
    @FXML
    protected ImageView onion3;
    @FXML
    protected ImageView onion4;
    @FXML
    protected ImageView menu1;
    @FXML
    protected ImageView menu2;
    @FXML
    protected ImageView menu3;
    @FXML
    protected ImageView menu4;
    @FXML
    protected Pane p1;
    @FXML
    protected Pane p2;
    @FXML
    protected Pane p3;
    @FXML
    protected Pane p4;

    //

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

    @FXML
    public void btnExit_Clicked(Event event) {
        System.exit(0);
    }

    @FXML
    public void btnMin_Clicked(Event event) {stage.setIconified(true);}

    @FXML
    public void menu1(Event event) {
        p1.setVisible(true);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
    }

    @FXML
    public void menu2(Event event) {
        p1.setVisible(false);
        p2.setVisible(true);
        p3.setVisible(false);
        p4.setVisible(false);
    }

    @FXML
    public void menu3(Event event) {
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(true);
        p4.setVisible(false);
    }

    @FXML
    public void menu4(Event event) {
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(true);
    }


    @FXML
    public void btnOnion(Event event){
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

    @FXML
    public void btnExcel_Clicked(Event event) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        //Sheet명 설정
        HSSFSheet sheet = workbook.createSheet(grade_test + "학년" + class_test + "반");

        //출력
        row = sheet.createRow(0);
        row.createCell(0).setCellValue("학생명");
        row.createCell(1).setCellValue("양파갯수");

        for(int i = 0;  i < students.length; i++) {
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
        }
    }
}