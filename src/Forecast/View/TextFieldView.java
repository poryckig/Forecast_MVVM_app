package Forecast.View;

import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;

public class TextFieldView extends TextField {

    public TextFieldView(int font, Pos position, double prefWidth, double layoutX, double layoutY) {
        this.setFont(Font.font(font));
        this.setAlignment(position);
        this.setPrefWidth(prefWidth);
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
    }
}
