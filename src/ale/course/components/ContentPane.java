package ale.course.components;

import ale.SourceType;
import ale.course.quiz.QuizPane;
import ale.course.videos.VideosList;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;

import java.io.File;
import java.net.MalformedURLException;

public class ContentPane {

    public static void main(String[] args) {}

    private TabPane rootPane;

    private Tab textTab;
    private Tab videosTab;
    private Tab picturesTab;
    private Tab resourcesTab;
    private Tab quizTab;

    QuizPane quizPane;
    String subject;
    String superTopic;
    String topic;

    private WebEngine textWebEngine;
    private WebView textWebView;

    private WebEngine resourcesWebEngine;
    private WebView resourcesWebView;

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();

    public TabPane setContentPane(String courseUrl, SourceType sourceType, String subject, String superTopic, String topic){

        textWebView = new WebView();
        textWebView.getStyleClass().add("scrollPane");
        textWebEngine = textWebView.getEngine();

        switch (sourceType){
            case LOCAL:
                File courseContent = new File(courseUrl);
                try{
                    textWebEngine.load(courseContent.toURI().toURL().toString());
                }catch (MalformedURLException malformedUrlExcep){

                }
                break;
            case WEB:
                textWebEngine.load(courseUrl);
                break;
        }

        //textWebView.setPrefWidth(main.primaryStage.getWidth() - (leftPane.getWidth() + 15));

        textTab = new Tab("Text");
        textTab.getStyleClass().add("tab");
        textTab.setContent(textWebView);
        textTab.setClosable(false);

        VideosList vidList = new VideosList();

        videosTab = new Tab("Videos");
        videosTab.getStyleClass().add("tab");
        videosTab.setContent(vidList.videosList());
        videosTab.setClosable(false);

        picturesTab = new Tab("Pictures");
        picturesTab.getStyleClass().add("tab");
        picturesTab.setClosable(false);

        resourcesWebView = new WebView();
        resourcesWebEngine = resourcesWebView.getEngine();
        resourcesWebEngine.load("");

        resourcesTab = new Tab("Resources");
        resourcesTab.getStyleClass().add("tab");
        resourcesTab.setClosable(false);
        resourcesTab.setContent(resourcesWebView);

        this.subject = subject;
        this.superTopic = superTopic;
        this.topic = topic;

        quizPane = new QuizPane(11, 0, subject, superTopic, topic);

        quizTab = new Tab("Quiz");
        quizTab.getStyleClass().add("tab");
        quizTab.setClosable(false);
        quizTab.setContent(quizPane);

        rootPane = new TabPane();
        rootPane.getStyleClass().add("tabPane");
        rootPane.setPrefWidth(width);
        rootPane.setPrefHeight(height);
        rootPane.getTabs().addAll(textTab, videosTab, picturesTab, resourcesTab, quizTab);

        return rootPane;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void resetQuiz(){
        quizPane = null;
        quizPane = new QuizPane(11, 0, subject, superTopic, topic);
        quizTab.setContent(quizPane);
    }

    public ContentPane() {
    }
}
