package ale.tools.calculators;

import ale.Main;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Calculator {

    public static void main(String[] args) {
    }

    public void start(String calc) {

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        try{
            webEngine.load("http://localhost:63342/ALE/res/" + calc + ".html");
        }catch (Exception e){
            e.printStackTrace();
        }

        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);
        //primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Scientific Calculator");
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setScene(new Scene(browser, 300, 468));
        primaryStage.show();
    }

    public Calculator() {
    }
}
