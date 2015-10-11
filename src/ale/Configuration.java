package ale;


import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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


        }catch (ParserConfigurationException parserConfigExcep){
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

    public void setStayLoggedIn(String username, String password){
        /** Modifies the config xml
         * To auto login the user
         * And remember their credentials
         */

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            File xmlFile = new File("config/config.xml");

            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.normalize();

            Node rememberNode = document.getElementsByTagName("remember").item(0);
            Element rememberElement = (Element)rememberNode;

            Node stateNode = rememberElement.getAttributeNode("state");
            stateNode.setTextContent("true");

            Node usernameNode = rememberElement.getElementsByTagName("user").item(0);
            Element usernameElement = (Element)usernameNode;
            usernameElement.setTextContent(username);


            Node passwordNode = rememberElement.getElementsByTagName("pass").item(0);
            Element passwordElement = (Element)passwordNode;
            passwordElement.setTextContent(password);

            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File("config/config.xml"));
            transformer.transform(source, result);


        }catch (ParserConfigurationException parserConfigExcep){
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
        }catch (TransformerConfigurationException transformerConfigExcep){
            System.out.println("<----- Transformer Config Exception in in Auto Login ----->");
            transformerConfigExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (TransformerException transformerExcep) {
            System.out.println("<----- Transformer Config Exception in in Auto Login ----->");
            transformerExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public void resetStayLoggedIn(){
        /** Modifies the config xml
         * To auto login the user
         * And remember their credentials
         */

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            File xmlFile = new File("config/config.xml");

            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.normalize();

            Node rememberNode = document.getElementsByTagName("remember").item(0);
            Element rememberElement = (Element)rememberNode;

            Node stateNode = rememberElement.getAttributeNode("state");
            stateNode.setTextContent("false");

            Node usernameNode = rememberElement.getElementsByTagName("user").item(0);
            Element usernameElement = (Element)usernameNode;
            usernameElement.setTextContent("");


            Node passwordNode = rememberElement.getElementsByTagName("pass").item(0);
            Element passwordElement = (Element)passwordNode;
            passwordElement.setTextContent("");

            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File("config/config.xml"));
            transformer.transform(source, result);


        }catch (ParserConfigurationException parserConfigExcep){
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
        }catch (TransformerConfigurationException transformerConfigExcep){
            System.out.println("<----- Transformer Config Exception in in Auto Login ----->");
            transformerConfigExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (TransformerException transformerExcep) {
            System.out.println("<----- Transformer Config Exception in in Auto Login ----->");
            transformerExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public String getStyle(){
        String style = "";

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            File xmlFile = new File("config/config.xml");

            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.normalize();

            Node appearanceNode = document.getElementsByTagName("appearance").item(0);
            Element appearanceElement = (Element)appearanceNode;

            Node styleNode = appearanceElement.getElementsByTagName("style").item(0);

            style = styleNode.getTextContent();

        }catch (ParserConfigurationException parserConfigExcep){
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

        return style;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }
}
