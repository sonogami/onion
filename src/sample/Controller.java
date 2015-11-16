package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    private int minn;
    private int secc;

    private int grade_test = 2;
    private int class_test = 5;

    private Student[] students = new Student[] {new Student("김민준",3),new Student("김재원",3),new Student("양파맨",1)};

    private ImageView[] onions = new ImageView[4];

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
    @FXML
    protected Pane p5;
    @FXML
    protected TextField min;
    @FXML
    protected TextField sec;

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

    public void setDragOnion(Stage stage){
        onions[0] = onion1;
        onions[1] = onion2;
        onions[2] = onion3;
        onions[3] = onion4;

        for(int i=0; i<4; i++) {
            ImageView o = onions[i];
            onions[i].setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = o.getLayoutX() + 60;
                    yOffset = o.getLayoutY() + 60;
                }
            });
            onions[i].setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    o.setX(event.getSceneX() - xOffset);
                    o.setY(event.getSceneY() - yOffset);
                }
            });
        }
    }

    @FXML
    public void btnExit_Clicked(Event event) {
        System.exit(0);
    }

    @FXML
    public void btnMin_Clicked(Event event) {stage.setIconified(true);}

    @FXML
    public void btnStart_Clicked(Event event) {
        String minText = min.getText();
        String secText = sec.getText();
        System.out.println(minText + "분 " + secText + "초");
        minn = 0;
        secc = 0;
    }

    @FXML
    public void menu1(Event event) {
        p1.setVisible(true);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(false);
    }

    @FXML
    public void menu2(Event event) {
        p1.setVisible(false);
        p2.setVisible(true);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(false);
    }

    @FXML
    public void menu3(Event event) {
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(true);
        p4.setVisible(false);
        p5.setVisible(false);
    }

    @FXML
    public void menu4(Event event) {
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(true);
        p5.setVisible(false);
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
        for(int i = 2; i < 4; i++) {
            for(int j = 1; j < 7; j++)
                workbook.createSheet(i + "학년" + j + "반");
        }


        HSSFSheet sheet = workbook.getSheet(grade_test + "학년" + class_test + "반");

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
            outFile = new FileOutputStream("output.xls");
            workbook.write(outFile);
            outFile.close();

            System.out.println("출력 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Settings_Clicked(Event event){
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(true);
    }
}