package com.schonfeld.GUI;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class GuiFunctions {

    public static HBox createPlayerButtons(Button btn1, Button btn2) {
        return new HBox(10, btn1, btn2);
    }

    public static VBox createPlayerControls(Label playerLabel, HBox playerButtons, Label playerScoreLabel) {
        return new VBox(10, playerLabel, playerButtons, playerScoreLabel);
    }

    public static boolean promptForPlayerTwoType() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose player two type");
        alert.setHeaderText("Do you want player two to be a bot?");
        ButtonType buttonTypeOne = new ButtonType("Human");
        ButtonType buttonTypeTwo = new ButtonType("Bot");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeTwo;
    }

    public static void showWinner(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("The winner is " + message);
        alert.showAndWait();
    }

}
