package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("onion.fxml"));
        Parent root = loader.load();

        ((Controller)loader.getController()).setDragFunction(primaryStage);
        ((Controller)loader.getController()).setDragOnion1(primaryStage);
        ((Controller)loader.getController()).setDragOnion2(primaryStage);
        ((Controller)loader.getController()).setDragOnion3(primaryStage);
        ((Controller)loader.getController()).setDragOnion4(primaryStage);

        Scene scene = new Scene(root, 1100, 733);
        primaryStage.setTitle("Onion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
