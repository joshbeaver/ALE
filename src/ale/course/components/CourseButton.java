package ale.course.components;

import ale.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class CourseButton extends VBox {

    private final ImageView courseBtnImageView = new ImageView();
    private Image courseBtnImage = new Image(Main.class.getResourceAsStream("img/styleDark/Courses/courseBtn.png"));
    private Label courseNameLbl = new Label();

    public CourseButton(String courseName){
        super();
        courseBtnImageView.setImage(courseBtnImage);
        courseNameLbl.setText(courseName);
        courseNameLbl.getStyleClass().add("lblMed");
        courseNameLbl.setAlignment(Pos.CENTER);
        courseNameLbl.setTextAlignment(TextAlignment.CENTER);
        courseNameLbl.setMinWidth(250);
        courseNameLbl.setWrapText(true);
        setPrefWidth(250);
        getChildren().addAll(courseBtnImageView, courseNameLbl);
    }

    public void setCourseName(String courseName){
        this.courseNameLbl.setText(courseName);
    }

    public void setImage(String imageUrl){
        courseBtnImage = new Image(imageUrl);
    }

    public void setOnClicked(String courseUrl,String subject, String superTopic, String topic, ScrollPane rootPane){
        setOnMouseClicked(e -> {
            ContentPane leafPane = new ContentPane();
            leafPane.setHeight(800);
            leafPane.setWidth(1400);
          //  rootPane.setContent(leafPane.setContentPane(courseUrl, subject, superTopic, topic));

        });
    }

}
