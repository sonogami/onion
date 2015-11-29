package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
    private boolean rotated = false;


    private int minn;
    private int secc;
    private int hour;

    private String s;

    private int v = 0;

    private boolean ParalyzeLeader = true;
    private int tmpOnion_cnt = 0;
    private int[] jojang = new int[6];

    private StudentGroup[] groups = new StudentGroup[6];
    private Student[] students = new Student[45];

    private ImageView[] onions = new ImageView[4];
    private ImageView[] boxes = new ImageView[6];
    private Label[] labels = new Label[6];
    private ImageView[] tmpOnions = new ImageView[100];
    private Label[] grLabels = new Label[6];

    private Task<Void> task;

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
    @FXML protected Pane p6;
    @FXML protected TextField min;
    @FXML protected TextField sec;
    @FXML protected ImageView setex;
    @FXML protected ImageView Box1;
    @FXML protected ImageView Box2;
    @FXML protected ImageView Box3;
    @FXML protected ImageView Box4;
    @FXML protected ImageView Box5;
    @FXML protected ImageView Box6;
    @FXML protected Label lblTimer;
    @FXML protected Label lblGroup1;
    @FXML protected Label lblGroup2;
    @FXML protected Label lblGroup3;
    @FXML protected Label lblGroup4;
    @FXML protected Label lblGroup5;
    @FXML protected Label lblGroup6;
    @FXML protected Label adr_excel;
    @FXML protected Label adr_rand1;
    @FXML protected Label adr_rand2;
    @FXML protected TextField TodayClass;
    @FXML protected ComboBox TodayClassList;
    @FXML protected ComboBox Class;
    @FXML protected ComboBox Class2;
    @FXML protected TableView tbvStudents;
    @FXML protected TableView tbvStudents2;
    @FXML protected ImageView btn_jojang;
    @FXML protected Label fl1;
    @FXML protected Label fl2;
    @FXML protected Label fl3;
    @FXML protected Label fl4;
    @FXML protected Label fl5;
    @FXML protected Label fl6;
    //endregion

    // settings.ini에서 엑셀 파일들을 읽어와서 디렉터리 내부의 엑셀 파일들 출력
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

    // 랜덤파일1 주소 지정
    public void setRand1FileAddress(String adr){
        if(!adr.isEmpty())
            adr_rand1.setText(adr);
    }

    // 랜덤파일2 주소 지정
    public void setRand2FileAddress(String adr){
        if(!adr.isEmpty())
            adr_rand2.setText(adr);

    }

    // 메인화면 드래그 기능
    public void setDragFunction(Stage stage) {
        this.stage = stage;

        // 클릭 시
        draggableImage.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // 좌표 가져옴
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        // 드래그 시
        draggableImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // 좌표 이동
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });


    }

    // 양파 드래그 앤 드롭 기능
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
            // 마우스 클릭 시
            o.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // 양파 좌표 가져옴
                    xOffset = o.getLayoutX() + 60;
                    yOffset = o.getLayoutY() + 60;
                }
            });
            // 마우스 드래그 시
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
                        // 양파 좌표 이동
                        b_x = (int)(boxes[i].getX() + boxes[i].getLayoutX());
                        b_y = (int)(boxes[i].getY() + boxes[i].getLayoutY());
                        b_h = (int)boxes[i].getFitHeight();
                        b_w = (int)boxes[i].getFitWidth();
                        // 양파의 위치가 박스 위에 있으면
                        if((o_x - 3 <= b_x + b_w && o_x + 3 >= b_x) && (o_y - 3 <= b_y + b_h && o_y + 3 >= b_y)) {
                            colBox = i;
                        }
                        if(i == colBox) // 박스 스타일 바꿈
                            boxes[i].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
                        else
                            boxes[i].setStyle("");
                    }

                }
            });
            // 마우스를 뗄 시
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
                        // 양파가 박스 위에 있으면
                        if((o_x - 3 <= b_x + b_w && o_x + 3 >= b_x) && (o_y - 3 <= b_y + b_h && o_y + 3 >= b_y)) {
                            groups[i].plusOnion(o);
                            labels[i].setText(((groups[i].getOnion()>=0)?"+":"") + groups[i].getOnion() + "점");
                            tmpOnions[tmpOnion_cnt] = new ImageView();
                            tmpOnions[tmpOnion_cnt].setImage(o.getImage());
                            // 박스에 양파 추가
                            p1.getChildren().add(tmpOnions[tmpOnion_cnt]);
                            tmpOnions[tmpOnion_cnt].setX(boxes[i].getLayoutX() + 10 + (groups[i].getOnion_cnt()-1) * 40);
                            tmpOnions[tmpOnion_cnt].setY(boxes[i].getLayoutY() + 8);
                            tmpOnions[tmpOnion_cnt].setFitWidth(30);
                            tmpOnions[tmpOnion_cnt].setFitHeight(30);
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

    // 종료 버튼
    @FXML
    public void btnExit_Clicked(Event event) {
        System.exit(0);
    }

    // 최소화 버튼
    @FXML
    public void btnMin_Clicked(Event event) {
        stage.setIconified(true);}

    // 그룹 지정 버튼
    @FXML
    public void btnSetGroup_Clicked(Event event){
        if(!TodayClass.getText().isEmpty()) {
            ArrayList<Integer> list = new ArrayList<Integer>(NumberOfStudents);
            ArrayList<Integer> list2 = new ArrayList<Integer>(6);

            Random rand = new Random();

            for (int i = 1; i <= NumberOfStudents; i++) {
                list.add(i);
            }

            for (int i = 1; i <= 6; i++) {
                list2.add(i);
            }

            // 조장 고정 기능이 켜져 있으면
            if (ParalyzeLeader)
                for (int i = 5; i > -1; i--) {
                    list.remove(jojang[i] - 1);
                }

            ArrayList<Student> listStudent = new ArrayList<Student>(7);

            while (list.size() > 0) {
                // 숫자 랜덤으로 가져옴
                int index = rand.nextInt(list.size());

                index = list.remove(index);

                if ((jj < 5 && kk < 5) || (jj < 6 && kk == 5)) {
                    listStudent.add(students[index - 1]);
                    jj++;
                }
                index++;

                if (jj == 5 && kk < 5) {
                    // 조장 고정 기능이 켜져 있으면
                    if (ParalyzeLeader) {
                        int ind3x = rand.nextInt(list2.size());
                        ind3x = list2.remove(ind3x);
                        ind3x = jojang[ind3x - 1];

                        listStudent.add(students[ind3x - 1]);
                    } else {
                        index = rand.nextInt(list.size());

                        index = list.remove(index);


                        listStudent.add(students[index - 1]);
                    }

                    groups[kk].setStudents(listStudent.toArray(new Student[listStudent.size()]));
                    listStudent.clear();
                    kk++;
                    jj = 0;
                }
                if (jj == 6 && kk == 5) {
                    // 조장 고정 기능이 켜져 있으면
                    if (ParalyzeLeader) {
                        int ind3x = rand.nextInt(list2.size());
                        ind3x = list2.remove(ind3x);
                        ind3x = jojang[ind3x - 1];

                        listStudent.add(students[ind3x - 1]);
                    } else {
                        index = rand.nextInt(list.size());

                        index = list.remove(index);

                        listStudent.add(students[index - 1]);
                    }

                    groups[kk].setStudents(listStudent.toArray(new Student[listStudent.size()]));
                    listStudent.clear();
                    jj = 0;
                    kk = 0;
                }
            }

            // 메인화면에 학생명 출력
            try {
                p1.getChildren().removeAll(grLabels);
                StringBuffer sb;
                for (int i = 0; i < 6; i++)
                    grLabels[i] = new Label();
                p1.getChildren().addAll(grLabels);
                for (int i = 0; i < 6; i++) {
                    sb = new StringBuffer();
                    for (int j = 5; j >= 0; j--) {
                        if (i < 5)
                            sb.append(groups[i].getStudent(j).getName() + "   ");
                        else
                            sb.append(groups[i].getStudent(j + 1).getName() + "   ");
                    }
                    grLabels[i].setText(sb.toString());
                    grLabels[i].setLayoutX(361);
                    grLabels[i].setLayoutY(242 + 80 * i);
                }
                grLabels[5].setText(grLabels[5].getText() + groups[5].getStudent(0).getName());

                isClassEnd = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //region Menu
    // 메뉴 1
    @FXML
    public void menu1(Event event) {
        p1.setVisible(true);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(false);
    }

    // 메뉴 2
    @FXML
    public void menu2(Event event) {
        p1.setVisible(false);
        p2.setVisible(true);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(false);
    }

    // 메뉴 3
    @FXML
    public void menu3(Event event) {
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(true);
        p4.setVisible(false);
        p5.setVisible(false);
    }

    // 메뉴 4
    @FXML
    public void menu4(Event event) {
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(true);
        p5.setVisible(false);
    }
    //endregion

    // 양파
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

    // 엑셀 저장 버튼
    @FXML
    public void btnExcel_Clicked(Event event) {
        String sheetname = TodayClass.getText();
        if(!sheetname.isEmpty() && isClassEnd) try {
            s = file.getAbsolutePath() + "/" + Class.getSelectionModel().getSelectedItem().toString() + ".xlsx";

            inFile = new FileInputStream(s);
            workbook = new XSSFWorkbook(inFile);

            sheet = workbook.createSheet(sheetname);

            // sum sheet에서 학생정보 가져옴
            row = sheet.createRow(0);
            row.createCell(0).setCellValue("번호");
            row.createCell(1).setCellValue("학생명");
            row.createCell(2).setCellValue("양파갯수");

            for(int i = 0; i < 6; i++){
                // 그룹별로 저장
                for(Student stu : groups[i].getStudents()){
                    if(stu != null) {
                        row = sheet.createRow(ii);

                        // 번호, 이름 및 양파 갯수 저장
                        row.createCell(0).setCellValue(stu.getNum());
                        row.createCell(1).setCellValue(stu.getName());
                        row.createCell(2).setCellValue(groups[i].getOnion());
                        ii++;

                        // sum에 양파 갯수 변동 반영
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

            // 출력
            outFile = new FileOutputStream(s);

            workbook.write(outFile);
            workbook.close();

            // settings.ini 업데이트
            JSONObject jobj = new JSONObject();
            jobj.put("ExcelPath", adr_excel.getText());
            jobj.put("Rand1", adr_rand1.getText());
            jobj.put("Rand2", adr_rand2.getText());

            FileWriter file = new FileWriter("settings.ini");
            file.write(jobj.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        else { // 오늘의 수업 명이 입력되지 않았을 시
            System.out.println("Input ClassName");
        }
    }

    // 엑셀 파일 디렉터리 선택
    @FXML
    public void btnSetExcel_Clicked(Event event) {
        DirectoryChooser direcChooser = new DirectoryChooser();

        file = direcChooser.showDialog(stage);
        if(file != null) adr_excel.setText(file.getAbsolutePath());
    }

    // 랜덤파일(플래시) 선택
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

    // 위 함수와 동일
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

    // 설정 메뉴
    @FXML
    public void Settings_Clicked(Event event){
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(true);
    }

    // settings.ini 저장
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

            FileWriter file = new FileWriter("settings.ini");
            file.write(jobj.toJSONString());
            file.flush();
            file.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 오늘의 수업명
    @FXML
    public void SetTodayClass(){
        TodayClassList.getItems().add(TodayClass.getText());
        //asdasd
    }

    // 수업 선택
    @FXML
    public void SelectTodayClass(){
        TodayClass.setText(TodayClassList.getSelectionModel().getSelectedItem().toString());
    }

    // 수업하는 반(엑셀파일) 선택
    @FXML
    public void SelectClass(){
        Class.setPromptText(Class.getSelectionModel().getSelectedItem().toString());
        try {
            // 디렉토리에서 엑셀파일 선택
            String s = file.getAbsolutePath() + "/" + Class.getSelectionModel().getSelectedItem().toString() + ".xlsx";

            // read
            inFile = new FileInputStream(s);
            workbook = new XSSFWorkbook(inFile);

            for(int i = 1 ; i < workbook.getNumberOfSheets(); i++){
                sheet = workbook.getSheetAt(i);
                TodayClassList.getItems().add(sheet.getSheetName());
            }

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

                    if(row.getCell(3).getStringCellValue().equals("o")){
                        jojang[v] = num;
                        v++;
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("No sheet");
            }

            for(int i =0 ; i < 6; i++){
                System.out.println(jojang[i]);
            }

            // 메인화면 초기화
            p1.getChildren().removeAll(tmpOnions);
            p1.getChildren().removeAll(grLabels);
            TodayClass.setText("");

            for(int i = 0 ; i < 6; i++) {
                groups[i].setOnion_cnt(0);
                labels[i].setText("+0점");
            }

            tmpOnion_cnt = 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 반 선택 in 전체학생 조회
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

        // 20개 단위로 저장
        for(int i = 1; i <= 20; i++){
            data.add(students[i - 1]);
        }

        // TableView에 출력
        ((TableColumn)tbvStudents.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Student, Integer>("num"));
        ((TableColumn)tbvStudents.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        ((TableColumn)tbvStudents.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<Student, Integer>("onion"));
        tbvStudents.setItems(data);

        for(int i = 21; i <= sheet.getPhysicalNumberOfRows(); i++){
            data2.add(students[i - 1]);
        }

        // TableView에 출력
        ((TableColumn)tbvStudents2.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Student, Integer>("num"));
        ((TableColumn)tbvStudents2.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        ((TableColumn)tbvStudents2.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<Student, Integer>("onion"));
        tbvStudents2.setItems(data2);
    }

    // 플래시1 실행
    @FXML
    public void btnFlash1_Clicked(){
        System.out.println("bt1 Clicked");
        if(!adr_rand1.getText().isEmpty()) {
            try {
                Runtime rt = Runtime.getRuntime();
                Process p;

                p = rt.exec("start " + adr_rand1.getText());
                p.waitFor();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    // 플래시2 실행
    @FXML
    public void btnFlash2_Clicked(){
        System.out.println("bt2 Clicked");
        if(!adr_rand2.getText().isEmpty()) {
            try {
                Runtime rt = Runtime.getRuntime();
                Process p;

                p = rt.exec("start " + adr_rand2.getText());
                p.waitFor();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    // 조장 고정 버튼
    @FXML
    public void btnJoJang_Clicked(){
        ParalyzeLeader = !ParalyzeLeader;
        if(!rotated) {
            btn_jojang.setRotate(180);
        } else {
            btn_jojang.setRotate(0);
        }

        rotated = !rotated;
    }


    // 타이머 시작
    @FXML
    public void btnStart_Clicked() {
        if(task != null && task.isRunning())
            return;

        minn = Integer.parseInt(min.getText());
        secc = Integer.parseInt(sec.getText());

        minn += secc / 60;
        secc %= 60;

        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(true) {

                    if(isCancelled()) {
                        break;
                    }

                    secc--;
                    if(secc < 0) { minn--; secc=59; }
                    if(minn < 0) { hour--; minn=59; }

                    String timerText = String.format("%02d:%02d:%02d", hour, minn, secc);
                    updateMessage(timerText);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        if(isCancelled()) {
                            break;
                        }
                    }
                }
                return null;
            }
        };

        lblTimer.textProperty().bind(task.messageProperty());

        Thread thread = new Thread(task);
        thread.start();
    }

    // 타이머 종료
    @FXML
    public void btnStop_Clicked() {
        task.cancel();
    }

    // 전체화면
    @FXML
    public void btnFullscreen_Clicked(){
        if(grLabels[0] == null || grLabels[0].getText().equals(""))
            return;
        fl1.setText(grLabels[0].getText());
        fl2.setText(grLabels[1].getText());
        fl3.setText(grLabels[2].getText());
        fl4.setText(grLabels[3].getText());
        fl5.setText(grLabels[4].getText());
        fl6.setText(grLabels[5].getText());
        p6.setVisible(true);
    }

    // 전체화면 종료
    @FXML
    public void btnExitFull_Clicked(){
        p6.setVisible(false);
    }
}