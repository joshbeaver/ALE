package ale.course.components;

import ale.LocatorType;
import ale.Main;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class RootPane {

    public static void setRootPane(double prefWidth, double prefHeight, BorderPane parentPane) {
        ScrollPane rootPane = new ScrollPane();
        rootPane.setPrefSize(prefWidth, prefHeight);
        rootPane.getStyleClass().add("scrollPane");

        VBox rootVbox = new VBox();
        rootVbox.setPrefSize(prefWidth, prefHeight);
        rootVbox.getStyleClass().add("rootPane");

        FlowPane rootButtonsPane = new FlowPane();
        rootButtonsPane.setPrefWidth(prefWidth);
        rootButtonsPane.getStyleClass().add("flowPane");
        rootButtonsPane.setHgap(15);
        rootButtonsPane.setVgap(15);
        rootButtonsPane.setPadding(new Insets(15, 15, 15, 15));

        try{
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(new File("config\\courses.xml"));
            document.normalize();

            Node coursesNode = document.getDocumentElement();

            NodeList rootNodesList = document.getElementsByTagName("root");

            for (int i = 0; i < rootNodesList.getLength(); i++){
                Node rootNode = rootNodesList.item(i);
                Element rootElement = (Element)rootNode;


                Node iconNode = rootElement.getElementsByTagName("icon").item(0);
                Node locatorNode = rootElement.getElementsByTagName("locator").item(0);

                LocatorType locatorType = null;

                if(locatorNode.getTextContent().equals("default")) locatorType = LocatorType.DEFAULT;

                InputStream imageIS = null;

                switch (locatorType){
                    case DEFAULT:
                        imageIS = Main.class.getResourceAsStream(iconNode.getTextContent());
                        break;
                }

                RootButton rootButton = new RootButton(imageIS, rootElement.getAttribute("name"), parentPane, prefWidth,
                        prefHeight);

                Node labelStyleNode = rootElement.getElementsByTagName("labelStyle").item(0);
                rootButton.setLblStyle(labelStyleNode.getTextContent());

                rootButtonsPane.getChildren().add(rootButton);
            }

            rootPane.setContent(rootButtonsPane);

        }catch (ParserConfigurationException parserConfigExcep){
            System.out.println("<----- Parser Configuration Exception in Create Simulation Pane ----->");
            parserConfigExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Create Simulation Pane ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (SAXException saxExcep){
            System.out.println("<----- SAX Exception in Create Simulation Pane ----->");
            saxExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

        parentPane.setCenter(rootPane);

    }

}
