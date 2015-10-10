package ale.xml;

import ale.utils.Misc;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLCreator {

    public static void createUserInfo(String username, String password, String email, String age, String school, String city,
                               String country){

        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //Root Element
            Element userInfoElement = document.createElement("userinfo");
            document.appendChild(userInfoElement);

            Element usernameElement = document.createElement("username");
            userInfoElement.appendChild(usernameElement);
            usernameElement.setTextContent(username);

            //Element passwordElement = document.createElement("password");
            //userInfoElement.appendChild(passwordElement);
            //passwordElement.setTextContent(password);

            Element emailElement = document.createElement("email");
            userInfoElement.appendChild(emailElement);
            emailElement.setTextContent(email);

            Element ageElement = document.createElement("age");
            userInfoElement.appendChild(ageElement);
            ageElement.setTextContent(age);

            Element schoolElement = document.createElement("school");
            userInfoElement.appendChild(schoolElement);
            schoolElement.setTextContent(school);

            Element cityElement = document.createElement("city");
            userInfoElement.appendChild(cityElement);
            cityElement.setTextContent(city);

            Element countryElement = document.createElement("country");
            userInfoElement.appendChild(countryElement);
            countryElement.setTextContent(country);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File("UserInfo.xml"));
            transformer.transform(source, result);


        }catch (ParserConfigurationException parseConfigExcep){
            System.out.println("<----- Parser Config Exception in Create User Info XML ----->");
            parseConfigExcep.printStackTrace();
            System.out.println("<---------->");
        }catch (TransformerException transformerExcep){
            System.out.println("<----- Transformer Exception in Create User Info XML ----->");
            transformerExcep.printStackTrace();
            System.out.println("<---------->");
        }

    }

    public static void createNote(String noteName){
        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //Root Element
            Element userInfoElement = document.createElement("note");
            document.appendChild(userInfoElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File(noteName +  ".xml"));
            transformer.transform(source, result);
        }catch(ParserConfigurationException parseConfigExcep){
            System.out.println("<----- Parser Config Exception in Create Note XML ----->");
            parseConfigExcep.printStackTrace();
            System.out.println("<---------->");
        }catch (TransformerException transformerExcep){
            System.out.println("<----- Transformer Exception in Create Note XML ----->");
            transformerExcep.printStackTrace();
            System.out.println("<---------->");
        }
    }

}
