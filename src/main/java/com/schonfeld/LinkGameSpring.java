package com.schonfeld;

import com.schonfeld.GUI.GuiFunctions;
import com.schonfeld.model.enums.Move;
import com.schonfeld.service.GameManagerService;
import com.schonfeld.service.GameService;
import com.schonfeld.service.PlayerService;
import com.schonfeld.model.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class LinkGameSpring extends Application {

    public static void main(String[] args) {
        Application.launch();
    }
    @Override
    public void init() {
        SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Autowired
    private GameManagerService gameManagerService;
    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;
    private Integer gameId;
    private Label currentChainLabel;
    private Label playerOneScoreLabel;
    private Label playerTwoScoreLabel;
    private Label currentTurnLabel;
    private Button playerOneTakeLeftButton;
    private Button playerOneTakeRightButton;
    private Button playerTwoTakeLeftButton;
    private Button playerTwoTakeRightButton;
    private Button restartButton;


    @Override
    public void start(Stage primaryStage) {

        boolean isPlayerTwoBot = GuiFunctions.promptForPlayerTwoType();

        if (isPlayerTwoBot)
            gameId = gameManagerService.createNewGameWithBot("Player 1");
        else
            gameId = gameManagerService.createNewGameWithTwoPlayers("Player 1", "Player 2");

        Player playerOne = gameService.getPlayerOne(gameId);
        Player playerTwo = gameService.getPlayerTwo(gameId);
        // Initialize labels
        currentChainLabel = new Label("Current chain: " + gameService.getChain(gameId).toString());
        playerOneScoreLabel = new Label(playerOne.getName() + ": " + playerOne.getScore());
        playerTwoScoreLabel = new Label(playerTwo.getName() + ": " + playerTwo.getScore());
        currentTurnLabel = new Label("Current turn: " + gameService.getCurrentPlayer(gameId).getName());

        // Initialize buttons
        playerOneTakeLeftButton = new Button("Take link from the left");
        playerOneTakeRightButton = new Button("Take link from the right");
        playerTwoTakeLeftButton = new Button("Take link from the left");
        playerTwoTakeRightButton = new Button("Take link from the right");
        restartButton = new Button("Restart");
        playerTwoTakeLeftButton.setDisable(true);
        playerTwoTakeRightButton.setDisable(true);

        // Add action listeners to buttons
        playerOneTakeLeftButton.setOnAction(event -> handlePlayerClick(playerOne, Move.PICK_LEFT));
        playerOneTakeRightButton.setOnAction(event -> handlePlayerClick(playerOne, Move.PICK_RIGHT));
        playerTwoTakeLeftButton.setOnAction(event -> handlePlayerClick(playerTwo, Move.PICK_LEFT));
        playerTwoTakeRightButton.setOnAction(event -> handlePlayerClick(playerTwo, Move.PICK_RIGHT));
        restartButton.setOnAction(event -> start(primaryStage));

        VBox layout = createLayout();
        // Set the scene and show the stage
        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private VBox createLayout() {
        VBox layout = new VBox(10);
        HBox playerOneButtons = GuiFunctions.createPlayerButtons(playerOneTakeLeftButton, playerOneTakeRightButton);
        HBox playerTwoButtons = GuiFunctions.createPlayerButtons(playerTwoTakeLeftButton, playerTwoTakeRightButton);
        Label playerOneLabel = new Label(gameService.getPlayerOne(gameId).getName());
        Label playerTwoLabel = new Label(gameService.getPlayerTwo(gameId).getName());
        VBox playerOneControls = GuiFunctions.createPlayerControls(playerOneLabel, playerOneButtons, playerOneScoreLabel);
        VBox playerTwoControls = GuiFunctions.createPlayerControls(playerTwoLabel, playerTwoButtons, playerTwoScoreLabel);
        HBox playersControls = new HBox(50, playerOneControls, playerTwoControls);
        playersControls.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(currentChainLabel, currentTurnLabel, playersControls, restartButton);

        return layout;
    }

    private void handlePlayerClick(Player player, Move curMove) {
        gameService.handlePlayerClick(player, curMove, gameId);
        switchPlayer();
        updateDisplay();
        if (gameService.getChain(gameId).isEmpty()) {
            GuiFunctions.showWinner(gameService.getWinner(gameId));
            disableAllButtons();
        }
    }

    private void disablePlayerButtons(boolean playOneDisable, boolean playTwoDisable) {
        playerOneTakeLeftButton.setDisable(playOneDisable);
        playerOneTakeRightButton.setDisable(playOneDisable);
        playerTwoTakeLeftButton.setDisable(playTwoDisable);
        playerTwoTakeRightButton.setDisable(playTwoDisable);
    }

    private void disableAllButtons() {
        playerOneTakeLeftButton.setDisable(true);
        playerOneTakeRightButton.setDisable(true);
        playerTwoTakeLeftButton.setDisable(true);
        playerTwoTakeRightButton.setDisable(true);
    }

    private void switchPlayer() {
        gameService.switchPlayer(gameId);
        if (gameService.getCurrentPlayer(gameId).getId().equals(gameService.getPlayerOne(gameId).getId()))
            disablePlayerButtons(false, true);
        else
            disablePlayerButtons(true, false);
        if (gameService.getCurrentPlayer(gameId).isBot()) {
            makeBotMove();
        }
    }

    private void updateDisplay() {
        currentChainLabel.setText("Current chain: " + gameService.getChain(gameId).toString());
        playerOneScoreLabel.setText(gameService.getPlayerOne(gameId).getName() + ": " + gameService.getPlayerOne(gameId).getScore());
        playerTwoScoreLabel.setText(gameService.getPlayerTwo(gameId).getName() + ": " + gameService.getPlayerTwo(gameId).getScore());
        currentTurnLabel.setText("Current turn: " + gameService.getCurrentPlayer(gameId).getName());
    }

    private void makeBotMove() {
        if (gameService.getChain(gameId).isEmpty())
            return;
        gameService.makeBotMove(gameId);
        updateDisplay();
        switchPlayer();
    }

}