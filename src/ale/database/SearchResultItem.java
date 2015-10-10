package ale.database;


import ale.Main;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchResultItem extends HBox{

    private String title;
    private String description;

    public SearchResultItem(String title) {
        super();
        setPrefHeight(70);
        setMinWidth(400);
        setSpacing(10);
        getStyleClass().add("searchResultItem");

        final ImageView resultImageView = new ImageView();
        final Image resultImage = new Image(Main.class.getResourceAsStream("img/styleDark/sampleSearchResultIcon.png"));
        resultImageView.setImage(resultImage);

        Label titleLbl = new Label(title);
        titleLbl.getStyleClass().add("searchResultTitleLbl");
        Label descriptionLbl = new Label(description);
        descriptionLbl.getStyleClass().add("searchResultTitleLbl");
        VBox textBox = new VBox(5);
        textBox.getChildren().addAll(titleLbl, descriptionLbl);
        getChildren().addAll(resultImageView, textBox);

        setOnMouseClicked(e -> {

        });
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScrollPane displayInfoPane(){
        ScrollPane infoPane = new ScrollPane();
        infoPane.getStyleClass().add("scrollPane");



        return infoPane;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
