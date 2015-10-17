package ale.course.components;


import ale.Configuration;
import ale.Main;
import ale.SourceType;
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

public class LeafPane {

    public static void setLeafPane(double prefWidth, double prefHeight, BorderPane parentPane, String rootName,
                                   String branchName) {

        Configuration config = new Configuration();

        ScrollPane leafPane = new ScrollPane();
        leafPane.setPrefSize(prefWidth, prefHeight);
        leafPane.getStyleClass().add("scrollPane");

        VBox leafVbox = new VBox();
        leafVbox.setPrefSize(prefWidth, prefHeight);
        leafVbox.getStyleClass().add("rootPane");

        FlowPane leafButtonsPane = new FlowPane();
        leafButtonsPane.setPrefWidth(prefWidth);
        leafButtonsPane.getStyleClass().add("flowPane");
        leafButtonsPane.setHgap(15);
        leafButtonsPane.setVgap(15);
        leafButtonsPane.setPadding(new Insets(15, 15, 15, 15));

        try{
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(new File("config\\courses.xml"));
            document.normalize();

            NodeList rootNodesList = document.getElementsByTagName("root");

            for (int i = 0; i < rootNodesList.getLength(); i++){
                Node rootNode = rootNodesList.item(i);
                Element rootElement = (Element)rootNode;

                    NodeList branchNodeList = rootElement.getElementsByTagName("branch");

                    for(int x = 0; x < branchNodeList.getLength(); x++){
                        Node branchNode = branchNodeList.item(x);
                        Element branchElement = (Element)branchNode;

                        if(branchElement.getAttribute("name").equals(branchName)){
                            NodeList leafNodeList = branchElement.getElementsByTagName("leaf");

                            for(int z = 0; z < leafNodeList.getLength(); z++){
                                Node leafNode = leafNodeList.item(z);
                                Element leafElement = (Element)leafNode;

                                Node typeNode = leafElement.getElementsByTagName("type").item(0);
                                SourceType sourceType = null;
                                switch (typeNode.getTextContent()){
                                    case "local":
                                        sourceType = SourceType.LOCAL;
                                        break;
                                    case "web":
                                        sourceType = SourceType.WEB;
                                        break;
                                }

                                Node urlNode = leafElement.getElementsByTagName("url"). item(0);

                                InputStream imageIS = Main.class.getResourceAsStream("img/" + config.getStyle() +
                                        "/" + "Courses/courseBtn.png");

                                LeafButton leafButton = new LeafButton(imageIS, leafElement.getAttribute("name"),
                                        branchName, rootName, urlNode.getTextContent(), sourceType, parentPane,
                                        prefWidth, prefHeight);

                                Node labelStyleNode = leafElement.getElementsByTagName("labelStyle").item(0);
                                leafButton.setLblStyle(labelStyleNode.getTextContent());

                                leafButtonsPane.getChildren().add(leafButton);
                            }
                        }
                    }
                    //break;
            }

            leafPane.setContent(leafButtonsPane);

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

        parentPane.setCenter(leafPane);

    }

}
