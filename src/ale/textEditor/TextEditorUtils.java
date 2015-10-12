package ale.textEditor;


import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditorUtils {

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

}
