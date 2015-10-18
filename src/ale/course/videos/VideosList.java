package ale.course.videos;


import ale.media.VideoPlayer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VideosList {

    private VBox videosBox;
    private ScrollPane videosScrollPane;

    public ScrollPane videosList(){

        videosScrollPane = new ScrollPane();
        videosScrollPane.getStyleClass().add("scrollPane");
        videosScrollPane.setPadding(new Insets(15,15,15,15));

        videosBox = new VBox(20);
        videosBox.getStyleClass().add("rootPane");

        for(int i = 0; i <= 20; i++){

            VideoButton videoBtn = new VideoButton(null, "Video Item Number " + i, "Author Number " + i);
            videoBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    VideoPlayer videoPlayer = new VideoPlayer();
                    videoPlayer.start("theDigestiveSystem");
                }
            });

            videosBox.getChildren().add(videoBtn);
        }

        videosScrollPane.setContent(videosBox);

        return videosScrollPane;
    }

}
