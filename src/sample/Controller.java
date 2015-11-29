package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.sl.usermodel.TextBox;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    private File file;
    private File rand1file;
    private File rand2file;

    private Stage stage;

    InputStream inFile;
    OutputStream outFile;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    XSSFRow row;

    int NumberOfStudents;
    int randnum[] = new int[45];
    int ii = 1, jj  = 0, kk = 0;

    private boolean isClassEnd = false;

    private StudentGroup[] stdgroup = new StudentGroup[6];

    private double xOffset = 0;
    private double yOffset = 0;

    private int minn;
    private int secc;

    private int grade_test = 2;
    private int class_test = 1;
    private int tmpOnion_cnt = 0;

    private StudentGroup[] groups = new StudentGroup[6];
    private Student[] students = new Student[45];

    private ImageView[] onions = new ImageView[4];
    private ImageView[] boxes = new ImageView[6];
    private Label[] labels = new Label[6];
    private ImageView[] tmpOnions = new ImageView[100];

    int random;

    //region FXML_varioubles
    @FXML protected ImageView draggableImage;
    @FXML protected ImageView onion1;
    @FXML protected ImageView onion2;
    @FXML protected ImageView onion3;
    @FXML protected ImageView onion4;
    @FXML protected ImageView menu1;
    @FXML protected ImageView menu2;
    @FXML protected ImageView menu3;
    @FXML protected ImageView menu4;
    @FXML protected Pane p1;
    @FXML protected Pane p2;
    @FXML protected Pane p3;
    @FXML protected Pane p4;
    @FXML protected Pane p5;
    @FXML protected TextField min;
    @FXML protected TextField sec;
    @FXML protected ImageView setex;
    @FXML protected ImageView Box1;
    @FXML protected ImageView Box2;
    @FXML protected ImageView Box3;
    @FXML protected ImageView Box4;
    @FXML protected ImageView Box5;
    @FXML protected ImageView Box6;
    @FXML protected Label lblGroup1;
    @FXML protected Label lblGroup2;
    @FXML protected Label lblGroup3;
    @FXML protected Label lblGroup4;
    @FXML protected Label lblGroup5;
    @FXML protected Label lblGroup6;
    @FXML protected Label adr_excel;
    @FXML protected Label adr_rand1;
    @FXML protected Label adr_rand2;
    @FXML protected Label g1_1;
    @FXML protected Label g1_2;
    @FXML protected Label g1_3;
    @FXML protected Label g1_4;
    @FXML protected Label g1_5;
    @FXML protected Label g1_6;
    @FXML protected Label g2_1;
    @FXML protected Label g2_2;
    @FXML protected Label g2_3;
    @FXML protected Label g2_4;
    @FXML protected Label g2_5;
    @FXML protected Label g2_6;
    @FXML protected Label g3_1;
    @FXML protected Label g3_2;
    @FXML protected Label g3_3;
    @FXML protected Label g3_4;
    @FXML protected Label g3_5;
    @FXML protected Label g3_6;
    @FXML protected Label g4_1;
    @FXML protected Label g4_2;
    @FXML protected Label g4_3;
    @FXML protected Label g4_4;
    @FXML protected Label g4_5;
    @FXML protected Label g4_6;
    @FXML protected Label g5_1;
    @FXML protected Label g5_2;
    @FXML protected Label g5_3;
    @FXML protected Label g5_4;
    @FXML protected Label g5_5;
    @FXML protected Label g5_6;
    @FXML protected Label g6_1;
    @FXML protected Label g6_2;
    @FXML protected Label g6_3;
    @FXML protected Label g6_4;
    @FXML protected Label g6_5;
    @FXML protected Label g6_6;
    @FXML protected Label g6_7;
    @FXML protected TextField TodayClass;
    @FXML protected ComboBox TodayClassList;
    @FXML protected ComboBox Class;
    @FXML protected ComboBox Class2;
    @FXML protected TableView tbvStudents;
    @FXML protected TableView tbvStudents2;
    //endregion

    public void setExcelFileAddress(String adr){
        file = new File(adr);

        adr_excel.setText(file.getAbsolutePath());

        String[] directories = file.list();

        for(int i = 1; i < directories.length - 1; i++) {
            if(directories[i].contains(".xlsx")) {
                Class.getItems().add(directories[i].substring(0, directories[i].length() - 5));
                Class2.getItems().add(directories[i].substring(0, directories[i].length() - 5));
            }
        }
    }

    public void setRand1FileAddress(String adr){
        if(!adr.isEmpty())
            adr_rand1.setText(adr);
    }

    public void setRand2FileAddress(String adr){
        if(!adr.isEmpty())
            adr_rand2.setText(adr);

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
        //region VARS
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
        //endregion

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

        ArrayList<Integer> list = new ArrayList<Integer>(NumberOfStudents);
        for(int i = 1; i <= NumberOfStudents; i++) {
            list.add(i);
        }

        Random rand = new Random();

        ArrayList<Student> listStudent = new ArrayList<Student>(7);
        while(list.size() > 0) {
            int index = rand.nextInt(list.size());
            index = list.remove(index);

            System.out.print(index + "\t");

            listStudent.add(students[index - 1]);
            jj++;

            if(jj == 6 && kk < 5) {
                groups[kk].setStudents(listStudent.toArray(new Student[listStudent.size()]));
                listStudent.clear();
                System.out.println();
                kk++;
                jj = 0;
            }
            if(jj == 7 && kk == 5) {
                groups[kk].setStudents(listStudent.toArray(new Student[listStudent.size()]));
                listStudent.clear();
                jj = 0;
                kk = 0;
            }
        }

        try {
            g1_1.setText(groups[0].getStudent(0).getName());
            g1_2.setText(groups[0].getStudent(1).getName());
            g1_3.setText(groups[0].getStudent(2).getName());
            g1_4.setText(groups[0].getStudent(3).getName());
            g1_5.setText(groups[0].getStudent(4).getName());
            g1_6.setText(groups[0].getStudent(5).getName());

            g2_1.setText(groups[1].getStudent(0).getName());
            g2_2.setText(groups[1].getStudent(1).getName());
            g2_3.setText(groups[1].getStudent(2).getName());
            g2_4.setText(groups[1].getStudent(3).getName());
            g2_5.setText(groups[1].getStudent(4).getName());
            g2_6.setText(groups[1].getStudent(5).getName());

            g3_1.setText(groups[2].getStudent(0).getName());
            g3_2.setText(groups[2].getStudent(1).getName());
            g3_3.setText(groups[2].getStudent(2).getName());
            g3_4.setText(groups[2].getStudent(3).getName());
            g3_5.setText(groups[2].getStudent(4).getName());
            g3_6.setText(groups[2].getStudent(5).getName());

            g4_1.setText(groups[3].getStudent(0).getName());
            g4_2.setText(groups[3].getStudent(1).getName());
            g4_3.setText(groups[3].getStudent(2).getName());
            g4_4.setText(groups[3].getStudent(3).getName());
            g4_5.setText(groups[3].getStudent(4).getName());
            g4_6.setText(groups[3].getStudent(5).getName());

            g5_1.setText(groups[4].getStudent(0).getName());
            g5_2.setText(groups[4].getStudent(1).getName());
            g5_3.setText(groups[4].getStudent(2).getName());
            g5_4.setText(groups[4].getStudent(3).getName());
            g5_5.setText(groups[4].getStudent(4).getName());
            g5_6.setText(groups[4].getStudent(5).getName());

            g6_1.setText(groups[5].getStudent(0).getName());
            g6_2.setText(groups[5].getStudent(1).getName());
            g6_3.setText(groups[5].getStudent(2).getName());
            g6_4.setText(groups[5].getStudent(3).getName());
            g6_5.setText(groups[5].getStudent(4).getName());
            g6_6.setText(groups[5].getStudent(5).getName());
            g6_7.setText(groups[5].getStudent(6).getName());

            isClassEnd = true;
        } catch(Exception e) {
            e.printStackTrace();
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

    //region Menu
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
    //endregion

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
        String sheetname = TodayClass.getText();
        if(!sheetname.isEmpty() && isClassEnd) try {
            String s = file.getAbsolutePath() + "/" + Class.getSelectionModel().getSelectedItem().toString() + ".xlsx";

            inFile = new FileInputStream(s);
            workbook = new XSSFWorkbook(inFile);

            sheet = workbook.createSheet(sheetname);

            row = sheet.createRow(0);
            row.createCell(0).setCellValue("번호");
            row.createCell(1).setCellValue("학생명");
            row.createCell(2).setCellValue("양파갯수");

            for(int i = 0; i < 6; i++){
                for(Student stu : groups[i].getStudents()){
                    if(stu != null) {
                        row = sheet.createRow(ii);
                        row.createCell(0).setCellValue(stu.getNum());
                        row.createCell(1).setCellValue(stu.getName());
                        row.createCell(2).setCellValue(groups[i].getOnion());
                        ii++;

                        XSSFSheet sumsheet = workbook.getSheet("sum");
                        XSSFRow sumrow = sumsheet.getRow(stu.getNum());
                        XSSFCell cell = sumrow.getCell(2);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        int num = Integer.parseInt(cell.getStringCellValue().substring(0,cell.getStringCellValue().length() - 2));

                        cell.setCellValue(num + groups[i].getOnion());
                    }
                }
                ii++;
            }

            outFile = new FileOutputStream(s);

            workbook.write(outFile);
            workbook.close();

            JSONObject jobj = new JSONObject();
            JSONArray jsonarray = new JSONArray();
            jobj.put("ExcelPath", adr_excel.getText());
            jobj.put("Rand1", adr_rand1.getText());
            jobj.put("Rand2", adr_rand2.getText());
            for(int i = 0; i < TodayClassList.getItems().size(); i++)
               jsonarray.add(TodayClassList.getItems().get(i));
            jobj.put("ClassList", jsonarray);

            FileWriter file = new FileWriter("settings.ini");
            file.write(jobj.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        else {
            System.out.println("Input ClassName");
        }
    }

    @FXML
    public void btnSetExcel_Clicked(Event event) {
        DirectoryChooser direcChooser = new DirectoryChooser();

        file = direcChooser.showDialog(stage);
        if(file != null) adr_excel.setText(file.getAbsolutePath());
    }

    @FXML
    public void btnRandFile1_Clicked(Event event){
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("flash files (*.swf)", "*.swf");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show open file dialog
            file = fileChooser.showOpenDialog(stage);
            if(file != null)
                adr_rand1.setText(file.getAbsolutePath());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void btnRandFile2_Clicked(Event event) {
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("flash files (*.swf)", "*.swf");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show open file dialog
            file = fileChooser.showOpenDialog(stage);
            if(file != null)
                adr_rand2.setText(file.getAbsolutePath());
        } catch (Exception e){
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

    @FXML
    public void btnSaveS_Clicked(){
        File excel;
        byte[] bytes = new byte[100];
        try {
            excel = new File("settings.ini");

            if (excel.exists()) {
                bytes = new byte[(int) excel.length()];

                try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(excel))) {
                    is.read(bytes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String setting = new String(bytes, 0, bytes.length);

        try {
            JSONObject jobj = new JSONObject();
            JSONArray jsonarray = new JSONArray();
            jobj.put("ExcelPath", adr_excel.getText());
            jobj.put("Rand1", adr_rand1.getText());
            jobj.put("Rand2", adr_rand2.getText());
//            jsonarray.add();

            FileWriter file = new FileWriter("settings.ini");
            file.write(jobj.toJSONString());
            file.flush();
            file.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void SetTodayClass(){
        TodayClassList.getItems().add(TodayClass.getText());
        //asdasd
    }

    @FXML
    public void SelectTodayClass(){
        TodayClass.setText(TodayClassList.getSelectionModel().getSelectedItem().toString());
    }

    @FXML
    public void SelectClass(){
        Class.setPromptText(Class.getSelectionModel().getSelectedItem().toString());
        try {
            String s = file.getAbsolutePath() + "/" + Class.getSelectionModel().getSelectedItem().toString() + ".xlsx";

            inFile = new FileInputStream(s);
            workbook = new XSSFWorkbook(inFile);

            String sheetname = "sum";
            sheet = workbook.getSheet(sheetname);

            NumberOfStudents = sheet.getPhysicalNumberOfRows() - 1;

            try {
                for (int i = 1; i <= NumberOfStudents; i++) {
                    row = sheet.getRow(i);
                    Cell cell = row.getCell(0);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    int num = Integer.parseInt(cell.getStringCellValue());
                    students[i - 1] = new Student(num, row.getCell(1).getStringCellValue(), 0);
                }
            } catch (NullPointerException e) {
                System.out.println("No sheet");
            }

            File excel;
            byte[] bytes = new byte[100];
            try {
                excel = new File("settings.ini");

                if (excel.exists()) {
                    bytes = new byte[(int) excel.length()];

                    try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(excel))) {
                        is.read(bytes);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String setting = new String(bytes, 0, bytes.length);

            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject)parser.parse(setting);
            JSONArray todayclasslist = (JSONArray)settings.get("ClassList");


            List<String> list = new ArrayList<String>(todayclasslist.size());
            for(int i = 0; i < todayclasslist.size(); i++)
                list.add(todayclasslist.get(i).toString());

            String[] string = list.toArray(new String[list.size()]);

            for(int i = 0; i < string.length; i++)
                TodayClassList.getItems().add(string[i]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void SelectClass2(){
        Class2.setPromptText(Class2.getSelectionModel().getSelectedItem().toString());
        try {
            String s = file.getAbsolutePath() + "/" + Class2.getSelectionModel().getSelectedItem().toString() + ".xlsx";

            inFile = new FileInputStream(s);
            workbook = new XSSFWorkbook(inFile);

            String sheetname = "sum";
            sheet = workbook.getSheet(sheetname);

            NumberOfStudents = sheet.getPhysicalNumberOfRows() - 1;

            try {
                for (int i = 1; i <= NumberOfStudents; i++) {
                    row = sheet.getRow(i);
                    Cell cell = row.getCell(0);
                    cell.setCellType(Cell.CELL_TYPE_STRING);

                    Cell cell2 = row.getCell(2);
                    cell2.setCellType(Cell.CELL_TYPE_STRING);

                    int num = Integer.parseInt(cell.getStringCellValue());
                    int onion;
                    if(cell2.getStringCellValue().length() < 2)
                        onion = Integer.parseInt(cell2.getStringCellValue());
                    else
                        onion = Integer.parseInt(cell2.getStringCellValue().substring(0, cell2.getStringCellValue().length() - 2));
                    students[i - 1] = new Student(num, row.getCell(1).getStringCellValue(), onion);
                }
            } catch (NullPointerException e) {
                System.out.println("No sheet");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<Student> data = FXCollections.observableArrayList();
        ObservableList<Student> data2 = FXCollections.observableArrayList();

        for(int i = 1; i <= 20; i++){
            data.add(students[i - 1]);
        }

        ((TableColumn)tbvStudents.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Student, Integer>("num"));
        ((TableColumn)tbvStudents.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        ((TableColumn)tbvStudents.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<Student, Integer>("onion"));
        tbvStudents.setItems(data);

        for(int i = 21; i <= sheet.getPhysicalNumberOfRows(); i++){
            data2.add(students[i - 1]);
        }

        ((TableColumn)tbvStudents2.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Student, Integer>("num"));
        ((TableColumn)tbvStudents2.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        ((TableColumn)tbvStudents2.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<Student, Integer>("onion"));
        tbvStudents2.setItems(data2);
    }

    @FXML
    public void btnFlash1_Clicked(){
        System.out.println("bt1 Clicked");
        if(!adr_rand1.getText().isEmpty()) {
            try {
                Runtime rt = Runtime.getRuntime();
                Process p;

                p = rt.exec(adr_rand1.getText());
                p.waitFor();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void btnFlash2_Clicked(){
        System.out.println("bt2 Clicked");
        if(!adr_rand2.getText().isEmpty()) {
            try {
                Runtime rt = Runtime.getRuntime();
                Process p;

                p = rt.exec(adr_rand2.getText());
                p.waitFor();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}