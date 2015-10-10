package ale.course.components;

import ale.SourceType;
import ale.components.ButtonModel;
import ale.components.ButtonType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class LeafButton extends ButtonModel{

    String rootName = null;
    String branchName = null;
    String leafName = null;

    SourceType sourceType;

    double prefWidth;
    double prefHeight;

    public LeafButton(String leafName, String branchName, String rootName, String courseUrl, SourceType sourceType,
                      BorderPane parentPane, double prefWidth, double prefHeight) {
        super(ButtonType.LEAF, null);
        setName(leafName);

        this.rootName = rootName;
        this.branchName = branchName;
        this.leafName = leafName;

        this.sourceType = sourceType;

        this.prefWidth = prefWidth;
        this.prefHeight= prefHeight;

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setOnClicked(courseUrl, parentPane, prefWidth, prefHeight);
            }
        });
    }

    public void setOnClicked(String courseUrl, BorderPane rootPane,
                             double prefWidth, double prefHeight){
            ContentPane contentPane = new ContentPane();
            contentPane.setHeight(prefHeight);
            contentPane.setWidth(prefWidth);
            rootPane.setCenter(contentPane.setContentPane(courseUrl, sourceType, rootName, branchName, leafName));
    }
}
