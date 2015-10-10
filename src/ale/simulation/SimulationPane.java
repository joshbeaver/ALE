package ale.simulation;

import ale.SourceType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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

public class SimulationPane{

    static WebView simWebView = new WebView();
    static WebEngine simWebEngine = simWebView.getEngine();

    public static ScrollPane getSimulationPane(double prefWidth, double prefHeight) {
        ScrollPane simPane = new ScrollPane();
        simPane.setPrefSize(prefWidth, prefHeight);
        simPane.getStyleClass().add("scrollPane");

        VBox simsVbox = new VBox();
        simsVbox.setPrefSize(prefWidth, prefHeight);
        simsVbox.getStyleClass().add("rootPane");

        try{
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(new File("config\\simulations.xml"));
            document.normalize();

            Node rootNode = document.getDocumentElement();

            NodeList groupNodesList = document.getElementsByTagName("group");

            for (int i = 0; i < groupNodesList.getLength(); i++){
                Node groupNode = groupNodesList.item(i);
                Element groupElement = (Element)groupNode;

                FlowPane simButtonsPane = new FlowPane();
                simButtonsPane.setPrefWidth(prefWidth);
                simButtonsPane.getStyleClass().add("flowPane");

                NodeList simsNodeList = groupElement.getElementsByTagName("simulation");

                for(int x = 0;  x < simsNodeList.getLength(); x++){
                    Element simElement = (Element)simsNodeList.item(x);

                    Node simNameNode = simElement.getElementsByTagName("name").item(0);
                    Element simNameElement = (Element)simNameNode;

                    Node simFontSizeNode = simElement.getElementsByTagName("fontSize").item(0);
                    Element simFontSizeElement = (Element)simFontSizeNode;

                    Node simUrlNode = simElement.getElementsByTagName("url").item(0);
                    Element simUrlElement = (Element)simUrlNode;

                    SimulationButton simBtn = new SimulationButton(simNameElement.getTextContent());
                    simBtn.setNameFontSize(Double.parseDouble(simFontSizeElement.getTextContent()));
                    simBtn.setOnClicked(simUrlElement.getTextContent(), simPane, SourceType.WEB,
                            simWebEngine, simWebView);

                    simWebView.setPrefSize(prefWidth, prefHeight);

                    simButtonsPane.getChildren().add(simBtn);

                }

                TitledPane groupPane = new TitledPane(groupElement.getAttribute("name"), simButtonsPane);
                groupPane.getStyleClass().add("titledPane");
                groupPane.setPrefSize(prefWidth, prefHeight);
                simsVbox.getChildren().add(groupPane);
            }

            simPane.setContent(simsVbox);

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

        return simPane;

    }
}
