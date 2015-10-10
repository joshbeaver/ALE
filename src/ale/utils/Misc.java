package ale.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Misc {

    public String stripHTMLTags(String htmlText) {

        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer sb = new StringBuffer(htmlText.length());
        while(matcher.find()) {
            matcher.appendReplacement(sb, " ");
        }
        matcher.appendTail(sb);
        return(sb.toString().trim());

    }

    public String getMyDocumentsPath(){
        String path = "";

        try {
            Process p =  Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion" +
                    "\\Explorer\\Shell Folders\" /v personal");
            p.waitFor();

            InputStream in = p.getInputStream();
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();

            path = new String(b);
            path = path.split("\\s\\s+")[4];

        } catch(Throwable t) {
            t.printStackTrace();
        }

        System.out.println("Documents Path: " + path);
        return path;
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

    public String getOS(){
        String osName = "";

        System.out.println("Operating System: " + System.getProperty("os.name"));

        return osName;
    }

    public Misc() {
    }
}
