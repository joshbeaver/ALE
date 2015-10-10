package ale;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    ProgressBar progressBar  = new ProgressBar(0);

    Login login = new Login();

    @Override
    public void start(Stage primaryStage) {

        Label loadingLbl = new Label("Loading...");
        loadingLbl.getStyleClass().add("lbl");
        loadingLbl.setTranslateX(5);

        progressBar.setPrefWidth(290);
        progressBar.setTranslateX(5);

        VBox rootPane = new VBox();
        rootPane.getStyleClass().add("rootPane");
        rootPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());

        rootPane.getChildren().addAll(loadingLbl, progressBar);

        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setScene(new Scene(rootPane, 300, 80));
        primaryStage.show();

        if (progressBar != null){
            try {
                Main main = new Main();
                Stage mainStage = new Stage();
                main.start(mainStage);
                while (progressBar.getProgress() != 100){
                    if(progressBar.getProgress() == 100){
                        primaryStage.close();
                    }
                }
            }catch (Exception excep){

            }
        }else {
            System.out.println("Progress bar is null");
        }

    }

    public void setProgress(double progress){
        progressBar.setProgress(progress);
    }

}
