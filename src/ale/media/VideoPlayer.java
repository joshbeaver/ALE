package ale.media;

import ale.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class VideoPlayer {

    public static void main(String[] args) {

    }

    Rectangle2D screenSize;
    double screenWidth;
    double screenHeight;

    Boolean maximized;

    MediaPlayer mediaPlayer;
    MediaView mediaView;

    VBox mediaBox;

    StackPane timeStackPane;
    Slider timeSlider;
    ProgressBar timeProgress;

    Button pausePlayBtn;
    Boolean mediaActive;
    Button volumeBtn;
    Boolean volumeActive;
    Slider volumeSlider;
    Double volumeLevel;
    Label currentTimeLbl;
    Label totalTimeLbl;
    DecimalFormat timeFormat;

    double mediaWidth;
    double mediaHeight;

    public void start(String video) {

        Stage primaryStage = new Stage();

        maximized = false;

        screenSize = Screen.getPrimary().getVisualBounds();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();

        final File file = new File("C:\\Users\\Josh\\Documents\\ALE\\ALE\\res\\videos\\" + video + ".mp4");
        Media media = new Media(file.toURI().toString());
        //final File fileOnline = new File("192.168.1.12/theDigestiveSystem.mp4");

        FileInputStream vidIS;

        try{
            vidIS = new FileInputStream("192.168.1.12/theDigestiveSystem.mp4");
        }catch (Exception e){

        }
        //Media mediaOnline = new Media(vidIS);
        //System.out.println(mediaOnline);

        mediaPlayer = new MediaPlayer(media);

        mediaView = new MediaView(mediaPlayer);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaActive = false;

        pausePlayBtn = new Button();
        pausePlayBtn.getStyleClass().add("playBtn");
        pausePlayBtn.setOnAction(e ->{
            if(mediaActive  == false){
                play();
            }else {
                pause();
            }
        });

        volumeActive = true;

        volumeBtn = new Button();
        volumeBtn.getStyleClass().add("volumeBtn");
        volumeBtn.setOnAction(e -> {
            if(volumeActive == false){
                mediaPlayer.setMute(false);
                volumeBtn.getStyleClass().removeAll("volumeMuteBtn");
                volumeBtn.getStyleClass().add("volumeBtn");
                volumeActive = true;
            }else{
                mediaPlayer.setMute(true);
                volumeBtn.getStyleClass().removeAll("volumeBtn");
                volumeBtn.getStyleClass().add("volumeMuteBtn");
                volumeActive = false;
            }
        });

        volumeLevel = 1.0;
        volumeSlider = new Slider();
        volumeSlider.setTranslateY(10);

        timeFormat = new DecimalFormat("#.00");

        currentTimeLbl = new Label("0");
        currentTimeLbl.getStyleClass().add("timeLbl");
        currentTimeLbl.setTranslateY(10);
        currentTimeLbl.setPadding(new Insets(0,0,10,10));

        Label slash = new Label("/");
        slash.getStyleClass().add("timeLbl");
        slash.setTranslateY(10);

        totalTimeLbl = new Label("");
        totalTimeLbl.getStyleClass().add("timeLbl");
        totalTimeLbl.setTranslateY(10);

        HBox mediaControlsBox = new HBox();
        //mediaControlsBox.getStyleClass().add("mediaBox");
        mediaControlsBox.getChildren().addAll(pausePlayBtn, volumeBtn, volumeSlider);

        timeSlider = new Slider();
        timeSlider.getStyleClass().add("slider");

        timeProgress = new ProgressBar();
        timeProgress.getStyleClass().add("mediaProgressBar");

        timeStackPane = new StackPane();
        timeStackPane.getChildren().addAll(timeSlider);

        mediaBox = new VBox();
        mediaBox.getChildren().addAll(timeStackPane, mediaControlsBox);
        mediaBox.setAlignment(Pos.BOTTOM_CENTER);
        mediaBox.setOpacity(0);

        StackPane rootPane = new StackPane();
        rootPane.setStyle("-fx-background-color: black;");
        rootPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)){
                    if (event.getClickCount() == 2) {
                        if (maximized == true){
                            minimize(primaryStage, rootPane, mediaWidth, mediaHeight);
                            maximized = false;
                        }else {
                            maximize(primaryStage, rootPane, screenWidth, screenHeight);
                            maximized = true;
                        }

                    }if (event.getClickCount() == 1){
                        if(mediaActive  == false){
                            play();
                        }else {
                            pause();
                        }
                    }
                }
            }
        });

        Timeline fadeIn = new Timeline();
        Timeline fadeOut = new Timeline();

        mediaBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fadeIn.play();
            }
        });

        mediaBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fadeOut.play();
            }
        });

        rootPane.getChildren().addAll(mediaView, mediaBox);
        rootPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());
        primaryStage.setTitle("ALE Media Player");
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("Double click to exit fullscreen");
                primaryStage.setScene(new Scene(rootPane, mediaWidth, mediaHeight));
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.show();

        timeProgress.setPrefWidth(primaryStage.getWidth());

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {

                mediaWidth = mediaPlayer.getMedia().getWidth();
                mediaHeight = mediaPlayer.getMedia().getHeight();

                mediaBox.setMaxWidth(mediaWidth);
                mediaBox.setMinHeight(20);

                timeSlider.setMin(0.0);
                timeSlider.setValue(0.0);
                timeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());

                totalTimeLbl.setText(timeFormat.format(mediaPlayer.getTotalDuration().toMinutes()) + "");

                mediaPlayer.setVolume(1);

                volumeSlider.setMin(0.0);
                volumeSlider.setMax(1);
                volumeSlider.setValue(1);
                volumeSlider.setPrefWidth(100);
                volumeSlider.setMaxWidth(100);

                mediaView.setFitWidth(mediaWidth);
                mediaView.setFitHeight(mediaHeight);

                primaryStage.setWidth(mediaWidth);
                primaryStage.setHeight(mediaHeight);

                fadeIn.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0), new KeyValue(mediaBox.opacityProperty(), 0.0))
                        ,new KeyFrame(new Duration(200), new KeyValue(mediaBox.opacityProperty(), 1)));

                fadeOut.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0), new KeyValue(mediaBox.opacityProperty(), 1))
                        ,new KeyFrame(new Duration(600), new KeyValue(mediaBox.opacityProperty(), 0.0)));
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                timeSlider.setValue(mediaPlayer.getCurrentTime().toSeconds());
                currentTimeLbl.setText(timeFormat.format(mediaPlayer.getCurrentTime().toMinutes()) + "");

            }
        });

        timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
            }
        });

        timeSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
            }
        });

        volumeSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.setVolume(volumeSlider.getValue());
            }
        });

        volumeSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.setVolume(volumeSlider.getValue());
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                mediaPlayer.stop();
                primaryStage.close();
            }
        });


    }

    private void maximize(Stage primaryStage, Pane rootPane ,double screenWidth, double screenHeight){
        mediaView.setFitWidth(screenWidth);
        mediaView.setFitHeight(screenHeight);

        mediaBox.setPrefWidth(screenWidth);
        mediaBox.setMaxWidth(screenWidth);

        timeSlider.setPrefWidth(screenWidth);

        rootPane.setPrefWidth(screenWidth);
        rootPane.setPrefHeight(screenHeight);

        primaryStage.setFullScreen(true);
    }

    private void minimize(Stage primaryStage, Pane rootPane ,double mediaWidth, double mediaHeight){
        mediaView.setFitWidth(mediaWidth);
        mediaView.setFitHeight(mediaHeight);

        mediaBox.setPrefWidth(mediaWidth);

        timeSlider.setPrefWidth(mediaWidth);

        rootPane.setPrefWidth(mediaWidth);
        rootPane.setPrefHeight(mediaHeight);

        primaryStage.setFullScreen(false);
    }

    private void play(){
        mediaPlayer.play();
        pausePlayBtn.getStyleClass().removeAll("playBtn");
        pausePlayBtn.getStyleClass().add("pauseBtn");
        pausePlayBtn.setText("");
        mediaActive = true;
    }

    private void pause(){
        mediaPlayer.pause();
        pausePlayBtn.setText("");
        pausePlayBtn.getStyleClass().removeAll("pauseBtn");
        pausePlayBtn.getStyleClass().add("playBtn");
        mediaActive = false;
    }

    public VideoPlayer() {
    }
}
