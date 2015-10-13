package ale.textEditor;


import ale.utils.Format;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditorUtils {

    private static File stagedFile = null;

    protected String stripHTMLTags(String htmlText) {

        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer sb = new StringBuffer(htmlText.length());
        while(matcher.find()) {
            matcher.appendReplacement(sb, " ");
        }
        matcher.appendTail(sb);
        return(sb.toString().trim());

    }

    public static void saveDocument(XWPFDocument document, Stage stage){
        try {
            FileChooser saveDocument = new FileChooser();
            saveDocument.setTitle("Save Document");
            saveDocument.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Document(*.docx)", "*.docx"));
            File file = null;
            if (stage != null){
                file = saveDocument.showSaveDialog(stage);
            }
            document.write(new FileOutputStream(file));

        }catch(IOException ioExcep){
            System.out.println("<----- IO Exception in Save Document ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public static void saveAsNote(String htmlText, Stage primaryStage){
        try {
            FileChooser saveDocument = new FileChooser();
            saveDocument.setTitle("Save Document");
            saveDocument.getExtensionFilters().add(new FileChooser.ExtensionFilter("Note (*.md)", "*.md"));
            File file = null;
            if (primaryStage != null){
                file = saveDocument.showSaveDialog(primaryStage);
            }

            if(file != null){
                stagedFile = file;
            }

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(htmlText);
            fileWriter.close();

        }catch(IOException ioExcep){
            System.out.println("<----- IO Exception in Save Document ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public static void saveNote(String htmlText, Stage primaryStage){
        try {
            if(stagedFile != null){

                FileWriter fileWriter = new FileWriter(stagedFile);
                fileWriter.write(htmlText);
                fileWriter.close();

            }else {
                saveAsNote(htmlText, primaryStage);
            }

        }catch(IOException ioExcep){
            System.out.println("<----- IO Exception in Save Document ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public String openNote(Stage primaryStage){
        String noteContent = "";

        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Note");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Note (*.md)", "*.md")
            );
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file != null){
                stagedFile = file;
                Scanner scanner = new Scanner(file);
                while(scanner.hasNext()){
                    noteContent += scanner.nextLine();
                }
                scanner.close();
            }
        }catch (IOException ioExcep){
            ioExcep.printStackTrace();
        }
        return noteContent;
    }

}
