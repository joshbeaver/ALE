package ale.xml;


import ale.utils.Misc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XMLParser {

    static Misc utils = new Misc();

    //User Info
    private static String superUser;
    private static String password;
    private static String email;
    private static String age;
    private static String school;
    private static String city;
    private static String country;

    static String width;

    //Message Info
    private static String type;
    private static String room;
    private static String username;
    private static String content;


    public static void parseUserInfo(){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try{
            File xmlFile = new File("userInfo.xml");

            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.normalize();

            NodeList userInfoNodeList = document.getElementsByTagName("userinfo");
            Node userInfoNode = userInfoNodeList.item(0);
            Element userInfoElement = (Element)userInfoNode;

            System.out.println("<----- XML Parser Output ----->");

            Node usernameNode = userInfoElement.getElementsByTagName("username").item(0);
            Element usernameElement = (Element)usernameNode;

            superUser = usernameElement.getTextContent();
            System.out.println(superUser);

            Node passwordNode = userInfoElement.getElementsByTagName("password").item(0);
            Element passwordElement = (Element)passwordNode;

            //password = passwordElement.getTextContent();
            //System.out.println(password);

            Node emailNode = userInfoElement.getElementsByTagName("email").item(0);
            Element emailElement = (Element)emailNode;

            email = emailElement.getTextContent();
            System.out.println(email);

            Node ageNode = userInfoElement.getElementsByTagName("age").item(0);
            Element ageElement = (Element)ageNode;

            age = ageElement.getTextContent();
            System.out.println(age);

            Node schoolNode = userInfoElement.getElementsByTagName("school").item(0);
            Element schoolElement = (Element)schoolNode;

            school = schoolElement.getTextContent();
            System.out.println(school);

            Node cityNode = userInfoElement.getElementsByTagName("city").item(0);
            Element cityElement = (Element)cityNode;

            city = cityElement.getTextContent();
            System.out.println(city);

            Node countryNode = userInfoElement.getElementsByTagName("country").item(0);
            Element countryElement = (Element)countryNode;

            country = countryElement.getTextContent();
            System.out.println(country);

            Node widthNode = userInfoElement.getElementsByTagName("width").item(0);
            Element widthElement = (Element)widthNode;

            width = schoolElement.getTextContent();
            System.out.println(width);

            System.out.println("<---------->\n");

        }catch (ParserConfigurationException parserConfigExcep ){
            System.out.println("<----- Parser Configuration Exception in Parse XML ----->");
            parserConfigExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Parse XML ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (SAXException saxExcep){
            System.out.println("<----- SAX Exception in Parse XML ----->");
            saxExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

    }

    public static void parseMessage(String message){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try{

            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            InputSource inSource = new InputSource();
            inSource.setCharacterStream(new StringReader(message));
            Document document = documentBuilder.parse(inSource);
            document.normalize();

            NodeList messageNodeList = document.getElementsByTagName("message");
            Node messageNode = messageNodeList.item(0);
            Element messageElement = (Element)messageNode;

            System.out.println("<----- XML Parser Output ----->");

            Node typeNode = messageElement.getElementsByTagName("type").item(0);
            Element typeElement = (Element)typeNode;

            type = typeElement.getTextContent();
            System.out.println(type);

            Node roomNode = messageElement.getElementsByTagName("room").item(0);
            Element roomElement = (Element)roomNode;

            room = roomElement.getTextContent();
            System.out.println(room);

            Node usernameNode = messageElement.getElementsByTagName("username").item(0);
            Element usernameElement = (Element)usernameNode;

            username = usernameElement.getTextContent();
            System.out.println(username);

            Node contentNode = messageElement.getElementsByTagName("content").item(0);
            Element contentElement = (Element)contentNode;

            content = contentElement.getTextContent();
            System.out.println(content);

            System.out.println("<---------->\n");

        }catch (ParserConfigurationException parserConfigExcep ){
            System.out.println("<----- Parser Configuration Exception in Parse XML ----->");
            parserConfigExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Parse XML ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (SAXException saxExcep){
            System.out.println("<----- SAX Exception in Parse XML ----->");
            saxExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

    }


    public XMLParser() {
    }

    public static String getSuperUser() {
        return superUser;
    }

    public static String getPassword() {
        return password;
    }

    public static String getEmail() {
        return email;
    }

    public static String getAge() {
        return age;
    }

    public static String getSchool() {
        return school;
    }

    public static String getCity() { return city; }

    public static String getCountry() { return country; }

    public static String getWidth() { return width; }

    public static String getType() { return type; }

    public static String getRoom() { return room; }

    public static String getUsername() { return username; }

    public static String getContent() { return content; }
}
