package Forecast;

import javafx.application.Application;
import javafx.stage.Stage;

import Forecast.View.GUI;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GUI gui = new GUI();
        gui.initializeView(primaryStage, gui);
    }

    public static void main(String[] args){
        launch(args);
    }
}
