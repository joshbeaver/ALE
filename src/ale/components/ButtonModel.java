package ale.components;

import ale.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.InputStream;

abstract public class ButtonModel extends VBox{

    private final ImageView btnIconImageView = new ImageView();
    private Image btnIconImage = null;
    private Label nameLbl = new Label();

    private String lblStyle = "lbl";
    private double fontSize;

    public ButtonModel(ButtonType buttonType, InputStream imageIS) {
        super();

        if(buttonType != null){
            switch (buttonType){
                case ROOT:
                    getStyleClass().add("rootBtn");
                    setMaxWidth(320);
                    setMaxHeight(280);
                    setMinWidth(320);
                    setMinHeight(280);
                    lblStyle = "lblLar";
                    nameLbl.setMinWidth(280);
                    nameLbl.setMaxWidth(280);
                    break;
                case BRANCH:
                    getStyleClass().add("branchBtn");
                    setMaxWidth(320);
                    setMaxHeight(280);
                    setMinWidth(320);
                    setMinHeight(280);
                    lblStyle = "lblMed";
                    nameLbl.setMinWidth(280);
                    nameLbl.setMaxWidth(280);
                    break;
                case LEAF:
                    getStyleClass().add("leafBtn");
                    setPrefSize(250, 250);
                    lblStyle = "lblMed";
                    nameLbl.setMaxWidth(250);
                    imageIS = Main.class.getResourceAsStream("img/styleDark/Courses/courseBtn.png");
                    break;
            }
        }else{

        }

        if(imageIS !=  null){
            btnIconImage = new Image(imageIS);
        }else {
            btnIconImage = new Image(Main.class.getResourceAsStream("img/styleDark/defaultBtn.png"));
        }

        nameLbl.getStyleClass().add(lblStyle);
        nameLbl.setWrapText(true);
        nameLbl.setAlignment(Pos.CENTER);
        nameLbl.setTextAlignment(TextAlignment.CENTER);

        btnIconImageView.setImage(btnIconImage);

        getChildren().addAll(btnIconImageView, nameLbl);

    }

    public void setImage(InputStream imageIS) {
        btnIconImage = new Image(imageIS);
    }

    public void setName(String name) {
        nameLbl.setText(name);
    }

    public String getName() {
        return null;
    }

    public void setLblStyle(String lblStyle) {
        this.lblStyle = lblStyle;
        nameLbl.getStyleClass().removeAll("lblLar");
        nameLbl.getStyleClass().add(lblStyle);
    }

    public String getLblStyle() {
        return lblStyle;
    }

    public void setNameFontSize(double fontSize) {
        this.fontSize = fontSize;
        nameLbl.setStyle("-fx-font-size: " + fontSize + "px;");
    }

    public double getNameFontSize() {
        return fontSize;
    }
}
