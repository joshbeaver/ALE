package ale.dialog;

import ale.Configuration;
import ale.Main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

public abstract class ConfirmationDialog extends Application {

    static Configuration config = new Configuration();

    static Stage primaryStage = new Stage();

    static int returnValue = 2;

    public static void main(String[] args) {
        launch(args);
    }

    public static int start(String message) {

        Button closeBtn = new Button();
        closeBtn.getStyleClass().add("loginSystemBtn");
        closeBtn.setOnAction(e -> {
            primaryStage.close();
        });

        Button minimizeBtn = new Button();
        minimizeBtn.getStyleClass().add("loginSystemBtn");
        minimizeBtn.setOnAction(e -> {
            primaryStage.setIconified(true);
        });

        HBox sysBtnBox = new HBox(5);
        sysBtnBox.setAlignment(Pos.TOP_RIGHT);
        sysBtnBox.getStyleClass().add("rootPane");
        sysBtnBox.getChildren().addAll(closeBtn);

        Label messageLbl = new Label(message);
        messageLbl.setAlignment(Pos.CENTER);
        messageLbl.setWrapText(true);
        messageLbl.getStyleClass().add("lbl6");

        Button yesBtn = new Button("Yes");
        yesBtn.getStyleClass().add("btn");
        yesBtn.setOnAction(e -> {
            returnValue = 0;
            primaryStage.close();
        });

        Button noBtn = new Button("No");
        noBtn.getStyleClass().add("btn");
        noBtn.setOnAction(e -> {
            returnValue = 1;
            primaryStage.close();
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("btn");
        cancelBtn.setOnAction(e -> {
            returnValue = 2;
            primaryStage.close();
        });

        HBox btnBox = new HBox(15);
        btnBox.getStyleClass().add("rootPane");
        btnBox.setAlignment(Pos.BASELINE_CENTER);
        btnBox.getChildren().addAll(yesBtn, noBtn, cancelBtn);

        VBox confirmationBox = new VBox(15);
        confirmationBox.getStyleClass().add("rootPane");
        confirmationBox.getChildren().addAll(messageLbl, btnBox);

        BorderPane rootPane = new BorderPane();
        rootPane.getStyleClass().add("rootPane");
        rootPane.getStylesheets().add(Main.class.getResource("css/" + config.getStyle() + ".css").toExternalForm());
        rootPane.setPadding(new Insets(10,10,10,10));
        //rootPane.setTop(sysBtnBox);
        rootPane.setCenter(confirmationBox);

        primaryStage.setTitle("Confirmation Dialog");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setMaxWidth(300);
        primaryStage.setScene(new Scene(rootPane));
        primaryStage.show();

        return returnValue;
    }
}
