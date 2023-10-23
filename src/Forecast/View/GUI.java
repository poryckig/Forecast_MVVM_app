package Forecast.View;

import Forecast.ViewModels.ForecastViewModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class GUI {

    private static Scene guiScene;

    private static Pane root;
    private final static double SCENE_WIDTH = 200;
    private final static double SCENE_HEIGHT = 300;

    private ForecastViewModel viewModel = new ForecastViewModel();

    private TextField hour1TextField;
    private TextField day1TextField;
    private TextField days5TextField;
    private TextField airQualityTextField;
    private TextField neighborsTextField;

    public GUI() {
        initializeScene();
        bindViewModel();
    }

    private void initializeScene() {
        root = new Pane();

        guiScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        createLabels(root);
        createTextFields(root);
        createButtons(root);
    }

    private void bindViewModel() {
        hour1TextField.textProperty().bindBidirectional(viewModel.getLocation());
        day1TextField.textProperty().bindBidirectional(viewModel.getLocation());
        days5TextField.textProperty().bindBidirectional(viewModel.getLocation());
        airQualityTextField.textProperty().bindBidirectional(viewModel.getLocation());
        neighborsTextField.textProperty().bindBidirectional(viewModel.getLocation());
    }

    private void createLabels(Pane root) {
        create1HourLabel(root);

        create1DayLabel(root);

        create5DaysLabel(root);

        createAirQualityLabel(root);

        createNeighborsLabel(root);
    }

    private void createTextFields(Pane root) {
        create1HourTextField(root);

        create1DayTextField(root);

        create5DaysTextField(root);

        createAirQualityTextField(root);

        createNeighborsTextField(root);
    }

    private void createButtons(Pane root) {
        create1HourButton(root);

        create1DayButton(root);

        create5DaysButton(root);

        createAirQualityButton(root);

        createNeighborsButton(root);
    }

    // ---------------- LABELS ----------------
    private void create1HourLabel(Pane root){
        Label label = new Label("1 Hour");

        label.setFont(new Font(13));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(SCENE_WIDTH / 2 - 20);
        label.setLayoutY(15);

        root.getChildren().add(label);
    }

    private void create1DayLabel(Pane root){
        Label label = new Label("1 Day");

        label.setFont(new Font(13));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(SCENE_WIDTH / 2 - 20);
        label.setLayoutY(70);

        root.getChildren().add(label);
    }

    private void create5DaysLabel(Pane root){
        Label label = new Label("5 Days");

        label.setFont(new Font(13));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(SCENE_WIDTH / 2 - 20);
        label.setLayoutY(125);

        root.getChildren().add(label);
    }

    private void createAirQualityLabel(Pane root){
        Label label = new Label("Air Quality");

        label.setFont(new Font(13));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(SCENE_WIDTH / 2 - 20);
        label.setLayoutY(185);

        root.getChildren().add(label);
    }

    private void createNeighborsLabel(Pane root){
        Label label = new Label("Neighbors");

        label.setFont(new Font(13));
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(SCENE_WIDTH / 2 - 20);
        label.setLayoutY(240);

        root.getChildren().add(label);
    }

    // ---------------- TEXTFIELDS ----------------
    private void create1HourTextField(Pane root){
        hour1TextField = new TextField("");
        hour1TextField.setFont(new Font(13));
        hour1TextField.setAlignment(Pos.CENTER);
        hour1TextField.setPrefWidth(125);
        hour1TextField.setLayoutX(5);
        hour1TextField.setLayoutY(35);
        root.getChildren().add(hour1TextField);
    }

    private void create1DayTextField(Pane root){
        day1TextField = new TextField("");
        day1TextField.setFont(new Font(13));
        day1TextField.setAlignment(Pos.CENTER);
        day1TextField.setPrefWidth(125);
        day1TextField.setLayoutX(5);
        day1TextField.setLayoutY(95);
        root.getChildren().add(day1TextField);
    }

    private void create5DaysTextField(Pane root){
        days5TextField = new TextField("");
        days5TextField.setFont(new Font(13));
        days5TextField.setAlignment(Pos.CENTER);
        days5TextField.setPrefWidth(125);
        days5TextField.setLayoutX(5);
        days5TextField.setLayoutY(145);
        root.getChildren().add(days5TextField);
    }

    private void createAirQualityTextField(Pane root){
        airQualityTextField = new TextField("");
        airQualityTextField.setFont(new Font(13));
        airQualityTextField.setAlignment(Pos.CENTER);
        airQualityTextField.setPrefWidth(125);
        airQualityTextField.setLayoutX(5);
        airQualityTextField.setLayoutY(205);
        root.getChildren().add(airQualityTextField);
    }

    private void createNeighborsTextField(Pane root){
        neighborsTextField = new TextField("");
        neighborsTextField.setFont(new Font(13));
        neighborsTextField.setAlignment(Pos.CENTER);
        neighborsTextField.setPrefWidth(125);
        neighborsTextField.setLayoutX(5);
        neighborsTextField.setLayoutY(260);
        root.getChildren().add(neighborsTextField);
    }

    // ---------------- BUTTONS ----------------
    private void create1HourButton(Pane root){
        Button button = new Button("Search");
        button.setFont(new Font(13));
        button.setAlignment(Pos.CENTER);
        button.setLayoutX(140);
        button.setLayoutY(35);

        button.setOnAction(e -> viewModel.search1HourForecast);
        root.getChildren().add(button);

        /*
        ForecastViewModel actionEvent = new ForecastViewModel();
        actionEvent.clicked1HourButton(button, hour1TextField);

        */
    }

    private void create1DayButton(Pane root){
        Button button = new Button("Search");
        button.setFont(new Font(13));
        button.setAlignment(Pos.CENTER);
        button.setLayoutX(140);
        button.setLayoutY(95);
        root.getChildren().add(button);

        ForecastViewModel actionEvent = new ForecastViewModel();
        actionEvent.clicked1DayButton(button, day1TextField);
    }

    private void create5DaysButton(Pane root){
        Button button = new Button("Search");
        button.setFont(new Font(13));
        button.setAlignment(Pos.CENTER);
        button.setLayoutX(140);
        button.setLayoutY(145);
        root.getChildren().add(button);

        ForecastViewModel actionEvent = new ForecastViewModel();
        actionEvent.clicked5DaysButton(button, days5TextField);
    }

    private void createAirQualityButton(Pane root){
        Button button = new Button("Search");
        button.setFont(new Font(13));
        button.setAlignment(Pos.CENTER);
        button.setLayoutX(140);
        button.setLayoutY(205);
        root.getChildren().add(button);

        ForecastViewModel actionEvent = new ForecastViewModel();
        actionEvent.clickedAirQualityButton(button, airQualityTextField);
    }

    private void createNeighborsButton(Pane root){
        Button button = new Button("Search");
        button.setFont(new Font(13));
        button.setAlignment(Pos.CENTER);
        button.setLayoutX(140);
        button.setLayoutY(260);
        root.getChildren().add(button);

        ForecastViewModel actionEvent = new ForecastViewModel();
        actionEvent.clickedNeighborsButton(button, neighborsTextField);
    }

    public Scene getGuiScene() {
        return guiScene;
    }
}
