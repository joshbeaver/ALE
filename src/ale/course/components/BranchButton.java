package ale.course.components;

import ale.components.ButtonModel;
import ale.components.ButtonType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.InputStream;

public class BranchButton extends ButtonModel{

    BorderPane parentPane = null;

    String rootName = "";
    String branchName = "";

    double prefWidth = 0;
    double prefHeight = 0;

    public BranchButton(InputStream imageIS, String name, String rootName, BorderPane parentPane, double prefWidth,
                        double prefHeight) {
        super(ButtonType.BRANCH, imageIS);
        setName(name);

        this.rootName = rootName;
        this.branchName = name;

        this.parentPane = parentPane;
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
        LeafPane leafPane = new LeafPane();
        leafPane.setLeafPane(prefWidth, prefHeight, parentPane, rootName, branchName);
    }
}
