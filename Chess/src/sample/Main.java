package sample;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        ChessPiece.setBoard();
        ViewManager manager = new ViewManager();
        primaryStage = manager.getMainStage();
        primaryStage.getIcons().add(new Image("file:Chess/src/images/icon.png"));
        primaryStage.setTitle("Chess in JavaFX");

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
