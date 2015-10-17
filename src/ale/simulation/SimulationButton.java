package ale.simulation;

import ale.*;
import ale.components.ButtonModel;
import ale.components.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.InputStream;

public class SimulationButton extends ButtonModel {

    Configuration config = new Configuration();

    SourceType simType;

    public SimulationButton(String simName, InputStream imageIS){
        super(ButtonType.LEAF, imageIS);
        setName(simName);
    }

    public void setOnClicked(String simUrl, ScrollPane rootPane, SourceType simType, WebEngine webEngine,
                             WebView webView){
        setOnMouseClicked(e -> {
            this.simType = simType;

            if(e.getButton() == MouseButton.PRIMARY){
                switch (this.simType){
                    case WEB:
                        webEngine.load("");
                        webEngine.load(simUrl);
                        rootPane.setContent(webView);
                        break;
                    case LOCAL:
                        break;
                }
            }if(e.getButton() == MouseButton.SECONDARY){

            }
        });
    }
}