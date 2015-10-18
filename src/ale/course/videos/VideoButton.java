package ale.course.videos;

import ale.Configuration;
import ale.Main;
import ale.media.VideoPlayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.InputStream;

public class VideoButton extends HBox{

    Configuration config = new Configuration();

    private String title;
    private String author;
    private String description;
    private InputStream thumbnailIS;

    public VideoButton(InputStream thumbnailIS, String title, String author){
        super(10);
        getStyleClass().add("btn");

        this.thumbnailIS = thumbnailIS;

        if(thumbnailIS == null){
            thumbnailIS = Main.class.getResourceAsStream("img/" + config.getStyle() + "/vidBtnThumbnail.png");
        }

        Image thumbnailImage = new Image(thumbnailIS);
        ImageView thumbnailView = new ImageView();
        thumbnailView.setImage(thumbnailImage);

        thumbnailView.setFitHeight(75);
        thumbnailView.setFitWidth(150);

        Label titleLbl = new Label(title);
        titleLbl.getStyleClass().add("vidTitleLbl");

        Label authorLbl = new Label(author);
        authorLbl.getStyleClass().add("vidAuthorLbl");

        VBox infoBox = new VBox();
        infoBox.getChildren().addAll(titleLbl, authorLbl);

        getChildren().addAll(thumbnailView, infoBox);
    }

    public void setName(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(InputStream thumbnailIS) {
        this.thumbnailIS = thumbnailIS;
    }

}
