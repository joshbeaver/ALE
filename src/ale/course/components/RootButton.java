package ale.course.components;

import ale.components.ButtonModel;
import ale.components.ButtonType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.InputStream;

public class RootButton extends ButtonModel {

    BorderPane parentPane = null;

    String rootName = "";

    double prefWidth = 0;
    double prefHeight = 0;

    public RootButton(InputStream imageIS, String name, BorderPane parentPane, double prefWidth, double prefHeight) {
        super(ButtonType.ROOT, imageIS);
        setName(name);

        this.parentPane = parentPane;
        this.rootName = name;
        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setOnClicked();
            }
        });
    }

    public void setOnClicked(){
        BranchPane branchPane = new BranchPane();
        branchPane.setBranchPane(prefWidth, prefHeight, parentPane, rootName);
    }
}
