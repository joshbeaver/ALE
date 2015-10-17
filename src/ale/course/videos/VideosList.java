package ale.course.videos;


import ale.media.VideoPlayer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

            Button videoThumbnail = new Button();
            videoThumbnail.setOnAction(e -> {
                VideoPlayer videoPlayer = new VideoPlayer();
                videoPlayer.start("theDigestiveSystem");
            });
            videoThumbnail.setPrefHeight(75);
            videoThumbnail.setPrefWidth(150);

            Label vidTitleLbl = new Label("Video Item Number " + i);
            vidTitleLbl.getStyleClass().add("vidTitleLbl");

            Label vidAuthorLbl = new Label("Author Number " + i);
            vidAuthorLbl.getStyleClass().add("vidAuthorLbl");

            VBox vidInfoBox = new VBox();
            vidInfoBox.getChildren().addAll(vidTitleLbl, vidAuthorLbl);

            HBox videoItem = new HBox(10);
            videoItem.getChildren().addAll(videoThumbnail, vidInfoBox);
            videosBox.getChildren().add(videoItem);
        }

        videosScrollPane.setContent(videosBox);

        return videosScrollPane;
    }

}
