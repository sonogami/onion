package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
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

        primaryStage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("onion.fxml"));
        Parent root = loader.load();

        ((Controller) loader.getController()).setDragFunction(primaryStage);
        ((Controller) loader.getController()).setDragOnion(primaryStage);

        String setting = new String(bytes, 0, bytes.length);

        JSONParser parser = new JSONParser();
        JSONObject settings = (JSONObject)parser.parse(setting);


        String address_excel = settings.get("ExcelPath").toString();
        String address_rand1 = settings.get("Rand1").toString();
        String address_rand2 = settings.get("Rand2").toString();

        System.out.println("afasdf");

        ((Controller) loader.getController()).setExcelFileAddress(address_excel);
        ((Controller) loader.getController()).setRand1FileAddress(address_rand1);
        ((Controller) loader.getController()).setRand2FileAddress(address_rand2);

        Scene scene = new Scene(root, 1100, 733);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
