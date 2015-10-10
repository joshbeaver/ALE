package ale.course.quiz;

import ale.database.Interaction;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MultipleChoice extends VBox{

    Interaction dbInteract = new Interaction();

    String correctAnswer;
    String chosenAnswer;

    Label questionLbl;

    ToggleGroup questionGroup;
    RadioButton question1;
    RadioButton question2;
    RadioButton question3;
    RadioButton question4;


    public MultipleChoice(String questionId) {
        super(30);

        setMaxSize(600, 500);
        getStyleClass().add("rootPane");

        String[] answers = {"", "", "", ""};

        questionLbl = new Label();
        questionLbl.getStyleClass().add("lblMed");
        questionLbl.setWrapText(true);
        questionLbl.setAlignment(Pos.CENTER_RIGHT);

        dbInteract.connectToDB();
        Connection dbCon = dbInteract.getDBCon();

        try{
            String questionSQL = "SELECT * FROM questionbank WHERE " +
                    "id = \"" + questionId + "\";";
            PreparedStatement dbPs = dbCon.prepareStatement(questionSQL);
            ResultSet dbRs = dbPs.executeQuery();

            while (dbRs.next()){
                questionLbl.setText(dbRs.getString("question"));
                System.out.println(questionLbl.getText());
                correctAnswer = dbRs.getString("answer1");

                for (int i = 0; i <= answers.length-1; i++){
                    int num = 20;
                    boolean placed = false;
                    while (placed == false){
                        int r = (int) (Math.random() * (4 - 0)) + 0;
                        System.out.println("Random Number = " + r);
                        if(answers[r].equals("")){
                            System.out.println("Place is null");
                            answers[r] = dbRs.getString("answer" + (i + 1));
                            placed = true;
                            System.out.println("Answer = " + answers[r] + " At Index " + r);
                        }
                    }
                }
            }

        }catch (SQLException sqlExcep){
            System.out.println("<----- SQL Exception in Multiple Choice Question ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

        questionGroup = new ToggleGroup();

        question1 = new RadioButton();
        question1.getStyleClass().add("radioBtn");
        question1.setToggleGroup(questionGroup);
        question1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chosenAnswer = question1.getText();
            }
        });
        question1.setText(answers[0]);

        question2 = new RadioButton();
        question2.getStyleClass().add("radioBtn");
        question2.setToggleGroup(questionGroup);
        question2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chosenAnswer = question2.getText();
            }
        });
        question2.setText(answers[1]);

        question3 = new RadioButton();
        question3.getStyleClass().add("radioBtn");
        question3.setToggleGroup(questionGroup);
        question3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chosenAnswer = question3.getText();
            }
        });
        question3.setText(answers[2]);

        question4 = new RadioButton();
        question4.getStyleClass().add("radioBtn");
        question4.setToggleGroup(questionGroup);
        question4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chosenAnswer = question4.getText();
            }
        });
        question4.setText(answers[3]);

        VBox questionVbox = new VBox(10);
        questionVbox.getStyleClass().add("rootPane");


        questionVbox.getChildren().addAll(question1, question2, question3, question4);

        getChildren().addAll(questionLbl, questionVbox);


    }

    protected boolean isCorrect(){
    /*
        question1.setDisable(true);
        question2.setDisable(true);
        question3.setDisable(true);
        question4.setDisable(true);
        */

        boolean isCorrect = false;
        if(correctAnswer.equals(chosenAnswer)){
            isCorrect = true;
            if(question1.isSelected()){
                question1.setStyle("-fx-text-fill: #00CC00;" +
                        "-fx-mark-color: #00CC00;");
            }else if(question2.isSelected()){
                question2.setStyle("-fx-text-fill: #00CC00;" +
                        "-fx-mark-color: #00CC00;");
            }else if(question3.isSelected()){
                question3.setStyle("-fx-text-fill: #00CC00;" +
                        "-fx-mark-color: #00CC00;");
            }else if(question4.isSelected()){
                question4.setStyle("-fx-text-fill: #00CC00;" +
                        "-fx-mark-color: #00CC00;");
            }
        }else{
            if(question1.isSelected()){
                question1.setStyle("-fx-text-fill: #FF0000;");
            }else if(question2.isSelected()){
                question2.setStyle("-fx-text-fill: #FF0000;" +
                        "-fx-mark-color: #FF0000;");
            }else if(question3.isSelected()){
                question3.setStyle("-fx-text-fill: #FF0000;" +
                        "-fx-mark-color: #FF0000;");
            }else if(question4.isSelected()){
                question4.setStyle("-fx-text-fill: #FF0000;" +
                        "-fx-mark-color: #FF0000;");
            }
        }
        return isCorrect;
    }
}
