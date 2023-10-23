package Forecast;

import Forecast.View.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Forecast");
        GUI gui = new GUI();
        primaryStage.setScene(gui.getGuiScene());
        primaryStage.show();

        stage = primaryStage;
    }

    public static void main(String[] args){
        launch(args);
    }
}
