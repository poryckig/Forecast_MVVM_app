package Forecast.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import Forecast.ViewModels.ForecastViewModel;       // do ViewModel

public class GUI {

    private static Scene guiScene;
    private static Pane root;
    private final static double SCENE_WIDTH = 200;
    private final static double SCENE_HEIGHT = 300;

    private TextField hour1TextField;
    private TextField day1TextField;
    private TextField days5TextField;
    private TextField airQualityTextField;
    private TextField neighborsTextField;
    private ForecastViewModel viewModel;    // do ViewModel

    public void initializeView(Stage stage, GUI gui) {
        viewModel = new ForecastViewModel(gui);
        initializeScene();
        bindViewModel();                 // do ViewModel
        initializeStage(stage);
    }

    public void displayResponse(String response) {
        System.out.println(response);
    }

    private void initializeScene() {
        root = new Pane();

        guiScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        createLabels(root);
        createTextFields(root);
        createButtons(root);
    }

    private void bindViewModel() {            // do ViewModel
        hour1TextField.textProperty().bindBidirectional(viewModel.getLocation1Hour());
        day1TextField.textProperty().bindBidirectional(viewModel.getLocation1Day());
        days5TextField.textProperty().bindBidirectional(viewModel.getLocation5Days());
        airQualityTextField.textProperty().bindBidirectional(viewModel.getLocationAirQuality());
        neighborsTextField.textProperty().bindBidirectional(viewModel.getLocationNeighbors());
    }

    private void initializeStage(Stage stage) {
        stage.setTitle("Forecast");
        stage.setScene(guiScene);
        stage.show();
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
        LabelView label = new LabelView("1 Hour", 13, Pos.CENTER, SCENE_WIDTH / 2 - 20, 15);
        root.getChildren().add(label);
    }

    private void create1DayLabel(Pane root){
        LabelView label = new LabelView("1 Day", 13, Pos.CENTER, SCENE_WIDTH / 2 - 20, 70);
        root.getChildren().add(label);
    }

    private void create5DaysLabel(Pane root){
        LabelView label = new LabelView("5 Days", 13, Pos.CENTER, SCENE_WIDTH / 2 - 20, 125);
        root.getChildren().add(label);
    }

    private void createAirQualityLabel(Pane root){
        LabelView label = new LabelView("Air Quality", 13, Pos.CENTER, SCENE_WIDTH / 2 - 20, 185);
        root.getChildren().add(label);
    }

    private void createNeighborsLabel(Pane root){
        LabelView label = new LabelView("Neighbors", 13, Pos.CENTER, SCENE_WIDTH / 2 - 20, 240);
        root.getChildren().add(label);
    }

    // ---------------- TEXTFIELDS ----------------
    private void create1HourTextField(Pane root){
        hour1TextField = new TextFieldView(13, Pos.CENTER, 125, 5, 35);
        root.getChildren().add(hour1TextField);
    }

    private void create1DayTextField(Pane root){
        day1TextField = new TextFieldView(13, Pos.CENTER, 125, 5, 95);
        root.getChildren().add(day1TextField);
    }

    private void create5DaysTextField(Pane root){
        days5TextField = new TextFieldView(13, Pos.CENTER, 125, 5, 145);
        root.getChildren().add(days5TextField);
    }

    private void createAirQualityTextField(Pane root){
        airQualityTextField = new TextFieldView(13, Pos.CENTER, 125, 5, 205);
        root.getChildren().add(airQualityTextField);
    }

    private void createNeighborsTextField(Pane root){
        neighborsTextField = new TextFieldView(13, Pos.CENTER, 125, 5, 260);
        root.getChildren().add(neighborsTextField);
    }

    // ---------------- BUTTONS ----------------
    private void create1HourButton(Pane root) {
        ButtonView button = new ButtonView("Search", 13, Pos.CENTER, 140, 35);

        button.setOnAction(e -> viewModel.search1HourForecast());
        root.getChildren().add(button);
    }

    private void create1DayButton(Pane root){
        ButtonView button = new ButtonView("Search", 13, Pos.CENTER, 140, 95);

        button.setOnAction(e -> viewModel.search1DayForecast());
        root.getChildren().add(button);
    }

    private void create5DaysButton(Pane root){
        ButtonView button = new ButtonView("Search", 13, Pos.CENTER, 140, 145);

        button.setOnAction(e -> viewModel.search5DaysForecast());
        root.getChildren().add(button);
    }

    private void createAirQualityButton(Pane root){
        ButtonView button = new ButtonView("Search", 13, Pos.CENTER, 140, 205);

        button.setOnAction(e -> viewModel.searchAirQuality());
        root.getChildren().add(button);
    }

    private void createNeighborsButton(Pane root){
        ButtonView button = new ButtonView("Search", 13, Pos.CENTER, 140, 260);

        button.setOnAction(e -> viewModel.searchNeighbors());
        root.getChildren().add(button);
    }
}
