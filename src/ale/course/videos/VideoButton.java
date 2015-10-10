package ale.course.videos;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.InputStream;

public abstract class VideoButton extends HBox{

    private String title;
    private String author;
    private String description;
    private InputStream thumnailIS;

    public void setName(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumnail(InputStream thumbnailIS) {
        this.thumnailIS = thumnailIS;
    }

    public VideoButton(){

    }



}
