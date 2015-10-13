package ale.textEditor;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class TextEditorPane {

    private static TextEditorUtils teUtils = new TextEditorUtils();

    private static BorderPane textEditorPane;
    private static HTMLEditor textEditor;
    private static Button saveDocBtn;

    private static XWPFRun tmpRun;
    private static XWPFDocument document;

    public static void setTextEditorPane(String text, boolean addSaveBtn, double prefWidth, double prefHeight, HBox btnContainer,
                                         BorderPane parentPane, Stage primaryStage){

        if(textEditorPane == null){

            textEditor = new HTMLEditor();

            if(text != null){
                textEditor.setHtmlText(text);
            }

            /** Prevents Scroll on Space Pressed */
            textEditor.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                        if (event.getCode() == KeyCode.SPACE) {
                            event.consume();
                        }
                    }
                }
            });

            document = new XWPFDocument();
            XWPFParagraph tmpParagraph = document.createParagraph();
            tmpRun = tmpParagraph.createRun();

            Tooltip saveToolTip = new Tooltip("Save");

            if(addSaveBtn == true){
                saveDocBtn = new Button();
                saveDocBtn.getStyleClass().add("saveBtn");
                saveDocBtn.setTooltip(saveToolTip);
                saveDocBtn.setOnAction(e -> {
                    tmpRun.setText(teUtils.stripHTMLTags(textEditor.getHtmlText()));
                    teUtils.saveDocument(document, primaryStage);
                });
                btnContainer.getChildren().addAll(saveDocBtn);
            }


            textEditor.setPrefWidth(prefWidth);
            textEditor.setPrefHeight(prefHeight);
            textEditorPane = new BorderPane();
            textEditorPane.getStyleClass().add("scrollPane");
            textEditorPane.setCenter(textEditor);
        }

        parentPane.setCenter(textEditorPane);
    }

    public void clearTextEditorPane(){
        textEditor = null;
        textEditorPane = null;
        saveDocBtn = null;
        tmpRun = null;
        document = null;
    }

    public static void saveDocument(Stage primaryStage){
        tmpRun.setText(teUtils.stripHTMLTags(textEditor.getHtmlText()));
        tmpRun.setFontSize(12);
        teUtils.saveDocument(document, primaryStage);
    }

    public static void saveAsNote(Stage primaryStage){
        teUtils.saveAsNote(textEditor.getHtmlText(), primaryStage);
    }

    public static void saveNote(Stage primaryStage){ teUtils.saveNote(textEditor.getHtmlText(), primaryStage); }




}
