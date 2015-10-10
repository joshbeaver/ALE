package ale.simulation;

import ale.*;
import ale.components.ButtonModel;
import ale.components.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SimulationButton extends ButtonModel {

    SourceType simType;

    public SimulationButton(String simName){
        super(ButtonType.LEAF, Main.class.getResourceAsStream("img/styleDark/Simulations/simulationBtn.png"));
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