package ale.course.quiz;

import ale.course.components.ContentPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ResultsPage extends VBox {

    ContentPane cl = new ContentPane();

    Label resultsLbl;
    Label titleLbl;
    Label scoreLbl;

    Button tryAgainBtn;
    Button submitBtn;
    VBox btnBox;

    int scoreCount = 0;

    public ResultsPage(MultipleChoice[] questionsArr) {
        super(30);
        getStyleClass().add("rootPane");
        setAlignment(Pos.TOP_CENTER);


        resultsLbl = new Label("Results");
        resultsLbl.getStyleClass().add("lbl");

        titleLbl = new Label("You Scored");
        titleLbl.getStyleClass().add("lblMed");

        scoreLbl = new Label("");
        scoreLbl.getStyleClass().add("lblMed");
        scoreLbl.setVisible(false);

        tryAgainBtn = new Button("Try Again");
        tryAgainBtn.getStyleClass().add("btn");
        tryAgainBtn.setVisible(false);
        tryAgainBtn.setOnAction(e -> {
            cl.resetQuiz();
        });

        submitBtn = new Button("Submit");
        submitBtn.getStyleClass().add("btn");
        submitBtn.setOnAction(e -> {
            for (int i = 0; i <= questionsArr.length -1; i++){
                if (questionsArr[i].isCorrect()){
                    scoreCount++;
                }else{

                }

                scoreLbl.setText(scoreCount + " Out of 10");
                scoreLbl.setVisible(true);

                tryAgainBtn.setVisible(true);
                submitBtn.setDisable(true);
            }
        });

        btnBox = new VBox(50);
        btnBox.getChildren().addAll(submitBtn, tryAgainBtn);

        getChildren().addAll(resultsLbl, titleLbl, scoreLbl, submitBtn, tryAgainBtn);

    }
}
