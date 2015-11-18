package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

            if(excel.exists()) {
                 bytes = new byte[(int) excel.length()];

                try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(excel))) {
                    is.read(bytes);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        String address_excel = new String(bytes,0,bytes.length);

        primaryStage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("onion.fxml"));
        Parent root = loader.load();

        ((Controller)loader.getController()).setDragFunction(primaryStage);
        ((Controller)loader.getController()).setDragOnion(primaryStage);
        ((Controller)loader.getController()).setExcelFileAddress(address_excel);

        Scene scene = new Scene(root, 1100, 733);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Onion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
