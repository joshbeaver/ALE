package ale.tools.calculators;

import ale.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SimpleCalculator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    TextField outputField;
    TextField inputField;

    Button zeroBtn;
    Button oneBtn;
    Button twoBtn;
    Button threeBtn;
    Button fourBtn;
    Button fiveBtn;
    Button sixBtn;
    Button sevenBtn;
    Button eightBtn;
    Button nineBtn;
    Button pointBtn;

    Button multiplyBtn;
    Button divideBtn;
    Button addBtn;
    Button subtractBtn;

    Button equalsBtn;

    Button delBtn;
    Button acBtn;

    Button mrBtn;
    Button mcBtn;
    Button msBtn;
    Button mplusBtn;
    Button mminusBtn;

    GridPane calculatorGridPane;

    @Override
    public void start(Stage primaryStage) {

        zeroBtn = new Button("0");
        zeroBtn.setPrefWidth(37);
        zeroBtn.getStyleClass().add("calc");

        oneBtn = new Button("1");
        oneBtn.setPrefWidth(37);
        oneBtn.getStyleClass().add("calc");

        twoBtn = new Button("2");
        twoBtn.setPrefWidth(37);
        twoBtn.getStyleClass().add("calc");

        threeBtn = new Button("3");
        threeBtn.setPrefWidth(37);
        threeBtn.getStyleClass().add("calc");

        fourBtn = new Button("4");
        fourBtn.setPrefWidth(37);
        fourBtn.getStyleClass().add("calc");

        fiveBtn = new Button("5");
        fiveBtn.setPrefWidth(37);
        fiveBtn.getStyleClass().add("calc");

        sixBtn = new Button("6");
        sixBtn.setPrefWidth(37);
        sixBtn.getStyleClass().add("calc");

        sevenBtn = new Button("7");
        sevenBtn.setPrefWidth(37);
        sevenBtn.getStyleClass().add("calc");

        eightBtn = new Button("8");
        eightBtn.setPrefWidth(37);
        eightBtn.getStyleClass().add("calc");

        nineBtn = new Button("9");
        nineBtn.setPrefWidth(37);
        nineBtn.getStyleClass().add("calc");

        pointBtn = new Button(".");
        pointBtn.setPrefWidth(37);
        pointBtn.getStyleClass().add("calc");

        multiplyBtn = new Button("X");
        multiplyBtn.setPrefWidth(37);
        multiplyBtn.getStyleClass().add("calc");

        divideBtn = new Button("/");
        divideBtn.setPrefWidth(37);
        divideBtn.getStyleClass().add("calc");

        addBtn = new Button("+");
        addBtn.setPrefWidth(37);
        addBtn.getStyleClass().add("calc");

        subtractBtn = new Button("-");
        subtractBtn.setPrefWidth(37);
        subtractBtn.getStyleClass().add("calc");

        equalsBtn = new Button("=");
        equalsBtn.setPrefWidth(111);
        equalsBtn.getStyleClass().add("calc");

        delBtn = new Button("DEL");
        delBtn.setPrefWidth(37);
        delBtn.getStyleClass().add("calc");

        acBtn = new Button("AC");
        acBtn.setPrefWidth(37);
        acBtn.getStyleClass().add("calc");

        mcBtn = new Button("MC");
        mcBtn.setPrefWidth(37);
        mcBtn.getStyleClass().add("calc");

        mrBtn = new Button("MR");
        mrBtn.setPrefWidth(37);
        mrBtn.getStyleClass().add("calc");

        msBtn = new Button("MS");
        msBtn.setPrefWidth(37);
        msBtn.getStyleClass().add("calc");

        mplusBtn = new Button("M+");
        mplusBtn.setPrefWidth(37);
        mplusBtn.getStyleClass().add("calc");

        mminusBtn = new Button("M-");
        mminusBtn.setPrefWidth(37);
        mminusBtn.getStyleClass().add("calc");

        outputField = new TextField();
        outputField.getStyleClass().add("calcField");
        outputField.setEditable(false);

        inputField = new TextField();
        inputField.getStyleClass().add("calcField");
        inputField.setEditable(false);

        calculatorGridPane = new GridPane();
        calculatorGridPane.setRowIndex(outputField, 0);
        calculatorGridPane.setColumnIndex(outputField, 0);
        calculatorGridPane.setColumnSpan(outputField, 5);

        calculatorGridPane.setRowIndex(inputField, 1);
        calculatorGridPane.setColumnIndex(inputField, 0);
        calculatorGridPane.setColumnSpan(inputField, 5);

        calculatorGridPane.setRowIndex(mcBtn, 2);
        calculatorGridPane.setColumnIndex(mcBtn, 0);
        calculatorGridPane.setRowIndex(mrBtn, 2);
        calculatorGridPane.setColumnIndex(mrBtn, 1);
        calculatorGridPane.setRowIndex(msBtn, 2);
        calculatorGridPane.setColumnIndex(msBtn, 2);
        calculatorGridPane.setRowIndex(mplusBtn, 2);
        calculatorGridPane.setColumnIndex(mplusBtn, 3);
        calculatorGridPane.setRowIndex(mminusBtn, 2);
        calculatorGridPane.setColumnIndex(mminusBtn, 4);

        calculatorGridPane.setRowIndex(sevenBtn, 3);
        calculatorGridPane.setColumnIndex(sevenBtn, 0);
        calculatorGridPane.setRowIndex(eightBtn, 3);
        calculatorGridPane.setColumnIndex(eightBtn, 1);
        calculatorGridPane.setRowIndex(nineBtn, 3);
        calculatorGridPane.setColumnIndex(nineBtn, 2);
        calculatorGridPane.setRowIndex(delBtn, 3);
        calculatorGridPane.setColumnIndex(delBtn, 3);
        calculatorGridPane.setRowIndex(acBtn, 3);
        calculatorGridPane.setColumnIndex(acBtn, 4);

        calculatorGridPane.setRowIndex(fourBtn, 4);
        calculatorGridPane.setColumnIndex(fourBtn, 0);
        calculatorGridPane.setRowIndex(fiveBtn, 4);
        calculatorGridPane.setColumnIndex(fiveBtn, 1);
        calculatorGridPane.setRowIndex(sixBtn, 4);
        calculatorGridPane.setColumnIndex(sixBtn, 2);
        calculatorGridPane.setRowIndex(multiplyBtn, 4);
        calculatorGridPane.setColumnIndex(multiplyBtn, 3);
        calculatorGridPane.setRowIndex(divideBtn, 4);
        calculatorGridPane.setColumnIndex(divideBtn, 4);

        calculatorGridPane.setRowIndex(oneBtn, 5);
        calculatorGridPane.setColumnIndex(oneBtn, 0);
        calculatorGridPane.setRowIndex(twoBtn, 5);
        calculatorGridPane.setColumnIndex(twoBtn, 1);
        calculatorGridPane.setRowIndex(threeBtn, 5);
        calculatorGridPane.setColumnIndex(threeBtn, 2);
        calculatorGridPane.setRowIndex(addBtn, 5);
        calculatorGridPane.setColumnIndex(addBtn, 3);
        calculatorGridPane.setRowIndex(subtractBtn, 5);
        calculatorGridPane.setColumnIndex(subtractBtn, 4);

        calculatorGridPane.setRowIndex(zeroBtn, 6);
        calculatorGridPane.setColumnIndex(zeroBtn, 0);
        calculatorGridPane.setRowIndex(pointBtn, 6);
        calculatorGridPane.setColumnIndex(pointBtn, 1);
        calculatorGridPane.setRowIndex(equalsBtn, 6);
        calculatorGridPane.setColumnIndex(equalsBtn, 2);
        calculatorGridPane.setColumnSpan(equalsBtn, 3);

        calculatorGridPane.getChildren().addAll(outputField, inputField,
                sevenBtn, eightBtn, nineBtn, delBtn, acBtn,
                fourBtn, fiveBtn, sixBtn, multiplyBtn, divideBtn,
                oneBtn, twoBtn, threeBtn, addBtn, subtractBtn,
                zeroBtn, pointBtn, equalsBtn,
                mcBtn, mrBtn, msBtn, mplusBtn, mminusBtn);

        calculatorGridPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());

        primaryStage.setScene(new Scene(calculatorGridPane, 300, 300));
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
