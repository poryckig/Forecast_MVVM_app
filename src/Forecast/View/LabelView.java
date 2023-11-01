package Forecast.View;

import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class LabelView extends Label {

    public LabelView(String text, int font, Pos position, double layoutX, double layoutY) {
        this.setText(text);
        this.setFont(Font.font(font));
        this.setAlignment(position);
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
    }
}
