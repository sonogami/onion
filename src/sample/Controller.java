package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Controller {

    private File file;

    static XSSFRow row;     // 열
    private Stage stage;

    FileInputStream inFile;
    XSSFWorkbook workbook;
    XSSFSheet sheet;

    private double xOffset = 0;
    private double yOffset = 0;

    private int minn;
    private int secc;

    private int grade_test = 2;
    private int class_test = 5;
    private int tmpOnion_cnt = 0;

    private StudentGroup[] groups = new StudentGroup[6];
    private Student[] students = new Student[45];

    private ImageView[] onions = new ImageView[4];
    private ImageView[] boxes = new ImageView[6];
    private Label[] labels = new Label[6];
    private ImageView[] tmpOnions = new ImageView[100];

    //region FXML_varioubles
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
    @FXML
    protected ImageView setex;
    @FXML
    protected ImageView Box1;
    @FXML
    protected ImageView Box2;
    @FXML
    protected ImageView Box3;
    @FXML
    protected ImageView Box4;
    @FXML
    protected ImageView Box5;
    @FXML
    protected ImageView Box6;
    @FXML
    protected Label lblGroup1;
    @FXML
    protected Label lblGroup2;
    @FXML
    protected Label lblGroup3;
    @FXML
    protected Label lblGroup4;
    @FXML
    protected Label lblGroup5;
    @FXML
    protected Label lblGroup6;
    @FXML
    protected Label adr_excel;
    //endregion

    public void setExcelFileAddress(String adr){
        try {
            adr_excel.setText(adr);

            file = new File(adr);
            System.out.println(file.getAbsolutePath());

            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(grade_test + "-" + class_test);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

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
        groups[0] = new StudentGroup();
        groups[0].setStudents(students);
        groups[1] = new StudentGroup();
        groups[1].setStudents(students);
        groups[2] = new StudentGroup();
        groups[2].setStudents(students);
        groups[3] = new StudentGroup();
        groups[3].setStudents(students);
        groups[4] = new StudentGroup();
        groups[4].setStudents(students);
        groups[5] = new StudentGroup();
        groups[5].setStudents(students);

        // -- start
        onions[0] = onion1;
        onions[1] = onion2;
        onions[2] = onion3;
        onions[3] = onion4;

        boxes[0] = Box1;
        boxes[1] = Box2;
        boxes[2] = Box3;
        boxes[3] = Box4;
        boxes[4] = Box5;
        boxes[5] = Box6;

        labels[0] = lblGroup1;
        labels[1] = lblGroup2;
        labels[2] = lblGroup3;
        labels[3] = lblGroup4;
        labels[4] = lblGroup5;
        labels[5] = lblGroup6;

        for(int i=0; i<4; i++) {
            ImageView o = onions[i];
            o.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = o.getLayoutX() + 60;
                    yOffset = o.getLayoutY() + 60;
                }
            });
            o.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    o.setX(event.getSceneX() - xOffset);
                    o.setY(event.getSceneY() - yOffset);
                    int colBox = 7;
                    int o_x = (int)(o.getX() + o.getLayoutX()) + 60;
                    int o_y = (int)(o.getY() + o.getLayoutY()) + 60;
                    int b_x, b_y, b_w, b_h;
                    for(int i=0; i<6; i++) {
                        b_x = (int)(boxes[i].getX() + boxes[i].getLayoutX());
                        b_y = (int)(boxes[i].getY() + boxes[i].getLayoutY());
                        b_h = (int)boxes[i].getFitHeight();
                        b_w = (int)boxes[i].getFitWidth();
                        if((o_x - 3 <= b_x + b_w && o_x + 3 >= b_x) && (o_y - 3 <= b_y + b_h && o_y + 3 >= b_y)) {
                            colBox = i;
                        }
                        if(i == colBox)
                            boxes[i].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
                        else
                            boxes[i].setStyle("");
                    }

                }
            });
            o.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int o_x = (int)(o.getX() + o.getLayoutX()) + 60;
                    int o_y = (int)(o.getY() + o.getLayoutY()) + 60;
                    int b_x, b_y, b_w, b_h;
                    for(int i=0; i<6; i++) {
                        b_x = (int)(boxes[i].getX() + boxes[i].getLayoutX());
                        b_y = (int)(boxes[i].getY() + boxes[i].getLayoutY());
                        b_h = (int)boxes[i].getFitHeight();
                        b_w = (int)boxes[i].getFitWidth();
                        if((o_x - 3 <= b_x + b_w && o_x + 3 >= b_x) && (o_y - 3 <= b_y + b_h && o_y + 3 >= b_y)) {
                            groups[i].plusOnion(o);
                            labels[i].setText(((groups[i].getOnion()>=0)?"+":"") + groups[i].getOnion() + "점");
                            tmpOnions[tmpOnion_cnt] = new ImageView();
                            tmpOnions[tmpOnion_cnt].setImage(o.getImage());
                            p1.getChildren().add(tmpOnions[tmpOnion_cnt]);
                            tmpOnions[tmpOnion_cnt].setX(boxes[i].getLayoutX() + 10 + (groups[i].getOnion_cnt()-1) * 40);
                            tmpOnions[tmpOnion_cnt].setY(boxes[i].getLayoutY() + 8);
                            tmpOnions[tmpOnion_cnt].setFitWidth(30);
                            tmpOnions[tmpOnion_cnt].setFitHeight(30);
                            //p1.getChildren().removeAll(tmpOnions);
                            tmpOnion_cnt ++;
                            break;
                        }
                    }
                    o.setX(0);
                    o.setY(0);
                    for(int i=0; i<6; i++)
                        boxes[i].setStyle("");
                }
            });
        }
    }

    @FXML
    public void btnExit_Clicked(Event event) {
        System.exit(0);
    }

    @FXML
    public void btnMin_Clicked(Event event) {
        stage.setIconified(true);}

    @FXML
    public void btnSetGroup_Clicked(Event event){
        for(int i = 0; i < sheet.getPhysicalNumberOfRows(); i++){
            row = sheet.getRow(i + 1);
            students[i] = new Student(Integer.getInteger(row.getCell(0).getStringCellValue()), row.getCell(1).getStringCellValue());
        }
    }

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
//        // 출력 파일 위치및 파일명 설정
//        FileOutputStream outFile;
//        try {
//            outFile = new FileOutputStream("output.xls");
//            workbook.write(outFile);
//            outFile.close();
//
//            System.out.println("출력 완료");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    public void btnSetExcel_Clicked(Event event){
        setex.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xls files (*.xls)", "*.xls");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show open file dialog
                file = fileChooser.showOpenDialog(stage);

                adr_excel.setText(file.getAbsolutePath());

                try {
                    inFile = new FileInputStream(file);
                    workbook = new XSSFWorkbook(inFile);
                    sheet = workbook.getSheet(grade_test + "학년" + class_test + "반");

                    //출력
                    row = sheet.createRow(0);
                    row.createCell(0).setCellValue("번호");
                    row.createCell(1).setCellValue("학생명");
                    row.createCell(0).setCellValue("양파갯수");

                    for (int i = 0; i < students.length; i++) {
                        row = sheet.createRow(i + 1);
                        row.createCell(0).setCellValue(1);
                        row.createCell(1).setCellValue(students[i].getName());
                        row.createCell(2).setCellValue(students[i].getNum());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void btnRandFile1_Clicked(Event event){
        setex.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("flash files (*.swf)", "*.swf");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show open file dialog
                file = fileChooser.showOpenDialog(stage);
            }
        });
    }

    @FXML
    public void btnRandFile2_Clicked(Event event){
        setex.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("flash files (*.swf)", "*.swf");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show open file dialog
                file = fileChooser.showOpenDialog(stage);
            }
        });
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