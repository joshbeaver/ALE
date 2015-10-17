package ale.course.components;

import ale.Configuration;
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

public class BranchPane {

    public static void setBranchPane(double prefWidth, double prefHeight, BorderPane parentPane, String parentName) {

        Configuration config = new Configuration();

        ScrollPane branchPane = new ScrollPane();
        branchPane.setPrefSize(prefWidth, prefHeight);
        branchPane.getStyleClass().add("scrollPane");

        VBox branchVbox = new VBox();
        branchVbox.setPrefSize(prefWidth, prefHeight);
        branchVbox.getStyleClass().add("rootPane");

        FlowPane branchButtonsPane = new FlowPane();
        branchButtonsPane.setPrefWidth(prefWidth);
        branchButtonsPane.getStyleClass().add("flowPane");
        branchButtonsPane.setHgap(15);
        branchButtonsPane.setVgap(15);
        branchButtonsPane.setPadding(new Insets(15, 15, 15, 15));

        try{
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(new File("config\\courses.xml"));
            document.normalize();

            NodeList rootNodesList = document.getElementsByTagName("root");

            for (int i = 0; i < rootNodesList.getLength(); i++){
                Node rootNode = rootNodesList.item(i);
                Element rootElement = (Element)rootNode;

                if(rootElement.getAttribute("name").equals(parentName)){
                    NodeList branchNodeList = rootElement.getElementsByTagName("branch");

                    for(int x = 0; x < branchNodeList.getLength(); x++){
                        Node branchNode = branchNodeList.item(x);
                        Element branchElement = (Element)branchNode;

                        Node iconNode = branchElement.getElementsByTagName("icon").item(0);
                        Node locatorNode = branchElement.getElementsByTagName("locator").item(0);

                        LocatorType locatorType = null;

                        switch (locatorNode.getTextContent()){
                            case "default":
                                locatorType = LocatorType.DEFAULT;
                                break;
                        }

                        InputStream imageIS = null;

                        switch (locatorType){
                            case DEFAULT:
                                imageIS = Main.class.getResourceAsStream("img/" + config.getStyle() + "/" +
                                        iconNode.getTextContent());
                                break;
                        }

                        BranchButton branchButton = new BranchButton(imageIS, branchElement.getAttribute("name"),
                                parentName, parentPane, prefWidth, prefHeight);

                        Node labelStyleNode = branchElement.getElementsByTagName("labelStyle").item(0);
                        branchButton.setLblStyle(labelStyleNode.getTextContent());

                        branchButtonsPane.getChildren().add(branchButton);

                    }
                }

            }

            branchPane.setContent(branchButtonsPane);

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

        parentPane.setCenter(branchPane);

    }
}
