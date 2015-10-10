package ale;


import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Scanner;

public class Configuration {

    Boolean loggedIn = false;

    public void autoLogin(Stage primaryStage){
        /**
         * Checks if user has opted to be remember in config.xml
         * If true, user is logged in automatically
         */

        Login login = new Login();
        String username = "";
        String password = "";

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            File xmlFile = new File("config/config.xml");

            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.normalize();

            Node rememberNode = document.getElementsByTagName("remember").item(0);
            Element rememberElement = (Element)rememberNode;

            if(rememberElement.getAttribute("state").equals("true")){
                Node usernameNode = rememberElement.getElementsByTagName("user").item(0);
                username = usernameNode.getTextContent();

                Node passwordNode = rememberElement.getElementsByTagName("pass").item(0);
                password = passwordNode.getTextContent();

                loggedIn = login.login(primaryStage, username, password, true);
            }


        }catch (ParserConfigurationException parserConfigExcep ){
            System.out.println("<----- Parser Configuration Exception in Auto Login ----->");
            parserConfigExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in in Auto Login ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (SAXException saxExcep){
            System.out.println("<----- SAX Exception in in Auto Login ----->");
            saxExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

    }

    public void createInfo(String username, String password){
        Login login = new Login();
        try{
            File configFile= new File("config.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.txt"));
            String line= "";
            while (bufferedReader.readLine() != null){
                line += bufferedReader.readLine();
                System.out.println("Line = " + line);
                if(line.substring(0, 8).equals("remember")){
                    if (line.substring(9, line.indexOf(" ")).equals("false")){
                        line.replace(line.substring(9, line.indexOf(" ")), "true");
                    }
                    line.replace(line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" ")), username);
                    line.replace(line.substring(line.lastIndexOf(" ") + 1, line.length() - 1), password);

                    FileOutputStream outputStream = new FileOutputStream(configFile);
                    String newline = "hello";
                    //outputStream.write(line.getBytes());
                    outputStream.close();
                }
            }
            bufferedReader.close();
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Create Info ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public String getStyle(){
        String style = "";
        return style;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }
}
