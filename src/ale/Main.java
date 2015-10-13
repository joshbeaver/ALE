package ale;

import ale.chat.Client;
import ale.course.components.RootPane;
import ale.database.Interaction;
import ale.database.Search;
import ale.dialog.ConfirmationDialog;
import ale.profile.ProfilePane;
import ale.simulation.SimulationPane;
import ale.textEditor.TextEditorPane;
import ale.textEditor.TextEditorUtils;
import ale.utils.Misc;
import ale.xml.XMLParser;
import ale.tools.calculators.Calculator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.sql.*;

public class Main extends Application {

    Client client = new Client();
    Misc utils = new Misc();
    XMLParser xmlParser = new XMLParser();
    Configuration config = new Configuration();
    Interaction database = new Interaction();
    Search search = new Search();
    SplashScreen splashScreen = new SplashScreen();

    Insets defaultInsets = new Insets(15,15,15,15);

    String superUser = "";
    String chatRoom = "";

    // Get Screen Size
    //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    Rectangle2D screenSize;
    double width;
    double height;
    double programWidth;
    double programHeight;

    // Panes
    BorderPane rootPane;
    VBox navigationPane;
    HBox topPane;
    HBox miscContainer;
    ScrollPane searchResultScrollPane;

    //Dashboard
    HBox dashBoardBase;
    ScrollPane dashboardPane;
    GridPane dashboardGridPane;
    Tooltip dashboardToolTip;

    //Chat Area
    VBox chatBox;
    TextField chatRoomField;
    TextArea messageArea;
    TextArea messageInputArea;

    //Notebook
    ScrollPane notebookPane;
    FlowPane notebookFlowPane;
    MenuBar notebookMenuBar;
    Tooltip notebookToolTip;


    ProfilePane profilePane;

    //Simulations
    ScrollPane simsPanel;
    Tooltip simsToolTip;

    //Text Editor
    Tooltip textEditorToolTip;
    HTMLEditor textEditor;
    TextEditorPane textEditorPane;

    //Wolfram Alpha
    ScrollPane wolframPane;
    Tooltip wolframToolTip;
    WebView wolframWeb;
    WebEngine wolframWebEngine;

    //Wikipedia
    ScrollPane wikipediaPane;
    Tooltip wikipediaToolTip;
    WebEngine wikipediaWebEngine;
    WebView wikipediaWeb;

    //Settings
    ScrollPane settingsPanel;
    GridPane settingsGridPanel;
    Tooltip settingsToolTip;

    //Buttons
    Button dashboardBtn;
    Button profileBtn;
    Button coursesBtn;
    Button simsBtn;
    Button notebookBtn;
    Button textEditorBtn;
    Button wolframBtn;
    Button wikipediaBtn;
    Button settingsBtn;

    TextField searchBar;

    Button closeBtn;
    Button minimizeBtn;

    //Other
    String calculatorName = "scientificCalculator";
    Button calcBtn;

    @Override
    public void start(Stage primaryStage) throws Exception {

        /** Gets screen size of primary monitor */
        try {
            screenSize = Screen.getPrimary().getVisualBounds();
            width = screenSize.getWidth(); // gd.getDisplayMode().getWidth();
            height = screenSize.getHeight(); // gd.getDisplayMode().getHeight();
        }catch(Exception excep){
            System.out.println("<----- Exception in  Get Screen Size ----->");
            excep.printStackTrace();
            System.out.println("<---------->\n");
        }

        xmlParser.parseUserInfo();
        superUser = xmlParser.getSuperUser();

//------------------------------------------------------------------------------------------------------> Top Pane Start

        closeBtn = new Button("");
        closeBtn.getStyleClass().add("systemBtn");
        closeBtn.setOnAction(e -> {
            systemClose();
        });

        minimizeBtn = new Button("");
        minimizeBtn.getStyleClass().add("systemBtn");
        minimizeBtn.setOnAction(e -> {
            systemMinimize(primaryStage);
        });

        searchResultScrollPane = new ScrollPane();
        searchResultScrollPane.getStyleClass().add("scrollPane");
        searchResultScrollPane.setPadding(defaultInsets);

        searchBar = new TextField();
        searchBar.getStyleClass().add("searchBar");
        searchBar.setMinWidth(200);
        searchBar.setTranslateY(6);
        //searchBar.setAlignment(Pos.CENTER_RIGHT);
        searchBar.setPromptText("Search");
        searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    if(searchBar.getText().equals("")){
                       searchBar.requestFocus();
                    }else{
                        rootPane.setCenter(searchResultScrollPane);
                        search(searchBar.getText());
                        if (searchBar.getText().equals("-l -a results")){
                            search("");
                        }
                    }
                }
            }
        });

        miscContainer = new HBox();

        calcBtn = new Button();
        calcBtn.getStyleClass().addAll("calcBtn");
        calcBtn.setOnAction(e -> {
            Calculator calculator = new Calculator();
            calculator.start(calculatorName);
        });

        miscContainer.getChildren().addAll(searchBar, calcBtn);

        topPane = new HBox(1);
        topPane.getStyleClass().add("topPanel");
        topPane.setPrefWidth(width);
        topPane.setAlignment(Pos.CENTER_RIGHT);
        topPane.setPadding(new Insets(0,0,0,0));
        topPane.getChildren().addAll(miscContainer,minimizeBtn, closeBtn);

//--------------------------------------------------------------------------------------------------------> Top Pane End

        try {
            splashScreen.setProgress(0.1);
            System.out.println("Loading at 10%");
        }catch (Exception excep){
            System.out.println("<----- Exception in Load Progress ----->");
            excep.printStackTrace();
            System.out.println("<---------->\n");
        }

//----------------------------------------------------------------------------------------------> Navigation Panel Start

        Line initDivider = new Line();
        initDivider.setStartX(0.0f);
        initDivider.setEndX(205.0f);
        initDivider.setStroke(Color.GRAY);
        initDivider.setOpacity(0.4);

        // <----- Dashboard ----->
        dashboardToolTip = new Tooltip("Dashboard");

        dashboardBtn = new Button("");
        dashboardBtn.getStyleClass().add("dashboardBtn");
        dashboardBtn.getStyleClass().add("dashboardBtnSelected");
        dashboardBtn.setTooltip(dashboardToolTip);
        dashboardBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(dashBoardBase);
            dashboardBtn.getStyleClass().add("dashboardBtnSelected");
        });

        // <----- Profile ----->
        Tooltip profileToolTip = new Tooltip("Profile");

        profilePane = new ProfilePane();

        profileBtn = new Button();
        profileBtn.getStyleClass().add("profileBtn");
        profileBtn.setTooltip(profileToolTip);
        profileBtn.setOnAction(e -> {
            resetBtns();
            profilePane.setProfilePane(superUser, primaryStage.getWidth() - (navigationPane.getWidth() + 15),
                    primaryStage.getHeight() - (topPane.getHeight()), rootPane, primaryStage);
            profileBtn.getStyleClass().add("profileBtnSelected");
        });

        // <----- Courses ----->
        Tooltip courseToolTip = new Tooltip("Courses");

        coursesBtn = new Button("");
        coursesBtn.getStyleClass().add("coursesBtn");
        coursesBtn.setTooltip(courseToolTip);
        coursesBtn.setOnAction(e -> {
            resetBtns();
            RootPane courseRootPane = new RootPane();
            courseRootPane.setRootPane(primaryStage.getWidth() - (navigationPane.getWidth() + 15),
                     primaryStage.getHeight() - (topPane.getHeight()), rootPane);
            coursesBtn.getStyleClass().add("coursesBtnSelected");
        });

        Line mainDivider = new Line();
        mainDivider.setStartX(0.0f);
        mainDivider.setEndX(205.0f);
        mainDivider.setStroke(Color.GRAY);
        mainDivider.setOpacity(0.4);

        // <----- Notebook ----->
        notebookToolTip = new Tooltip("Notebook");

        notebookBtn = new Button();
        notebookBtn.getStyleClass().add("notebookBtn");
        notebookBtn.setTooltip(simsToolTip);
        notebookBtn.setOnAction(e -> {
            resetBtns();
            miscContainer.getChildren().remove(calcBtn);
            rootPane.setCenter(notebookPane);
            miscContainer.getChildren().addAll(notebookMenuBar, calcBtn);
            notebookBtn.getStyleClass().add("notebookBtnSelected");
        });

        // <----- Simulations ----->
        simsToolTip = new Tooltip("Simulations");

        simsBtn = new Button();
        simsBtn.getStyleClass().add("simsBtn");
        simsBtn.setTooltip(simsToolTip);
        simsBtn.setOnAction(e -> {
            resetBtns();
            if(simsPanel == null){
                simsPanel = SimulationPane.getSimulationPane(primaryStage.getWidth() - (navigationPane.getWidth() + 15),
                        primaryStage.getHeight() - (topPane.getHeight()));
            }
            rootPane.setCenter(simsPanel);
            System.out.println(programWidth);
            simsBtn.getStyleClass().add("simsBtnSelected");
        });

        // <----- Text Editor ----->
        textEditorToolTip = new Tooltip("Text Editor");

        textEditorPane = new TextEditorPane();
        textEditorBtn = new Button();
        textEditorBtn.getStyleClass().add("textEditorBtn");
        textEditorBtn.setTooltip(textEditorToolTip);
        textEditorBtn.setOnAction(e -> {
            resetBtns();
            textEditorPane.setTextEditorPane(null, false, primaryStage.getWidth() - (navigationPane.getWidth() + 15),
                    primaryStage.getHeight() - (topPane.getHeight()), miscContainer, rootPane, primaryStage);
            textEditorBtn.getStyleClass().add("textEditorBtnSelected");
        });

        Line toolsDivider = new Line();
        toolsDivider.setStartX(0.0f);
        toolsDivider.setEndX(205.0f);
        toolsDivider.setStroke(Color.GRAY);
        toolsDivider.setOpacity(0.4);

        // <----- Wolfram Alpha ----->
        wolframToolTip = new Tooltip("Wolfram Alpha");

        wolframBtn = new Button();
        wolframBtn.getStyleClass().add("wolframBtn");
        wolframBtn.setTooltip(wolframToolTip);
        wolframBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(wolframPane);
            wolframBtn.getStyleClass().add("wolframBtnSelected");
        });

        // <----- Wikipedia ----->
        wikipediaToolTip = new Tooltip("Wikipedia");

        wikipediaBtn = new Button();
        wikipediaBtn.getStyleClass().add("wikipediaBtn");
        wikipediaBtn.setTooltip(wikipediaToolTip);
        wikipediaBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(wikipediaWeb);
            wikipediaBtn.getStyleClass().add("wikipediaBtnSelected");
        });

        Line sitesDivider = new Line();
        sitesDivider.setStartX(0.0f);
        sitesDivider.setEndX(205.0f);
        sitesDivider.setStroke(Color.GRAY);
        sitesDivider.setOpacity(0.4);

        // <----- Settings ----->
        settingsToolTip = new Tooltip("Settings");

        settingsBtn = new Button();
        settingsBtn.getStyleClass().add("settingsBtn");
        settingsBtn.setTooltip(settingsToolTip);
        settingsBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(settingsPanel);
            settingsBtn.getStyleClass().add("settingsBtnSelected");
        });

        navigationPane = new VBox(0);
        navigationPane.getStyleClass().add("navigationPane");
        navigationPane.getChildren().addAll(initDivider, dashboardBtn, profileBtn, coursesBtn, mainDivider, notebookBtn,
                simsBtn, textEditorBtn, toolsDivider, wolframBtn, wikipediaBtn, sitesDivider, settingsBtn);


        topPane = new HBox(1);
        topPane.getStyleClass().add("topPanel");
        topPane.setPrefWidth(width);
        topPane.setAlignment(Pos.CENTER_RIGHT);
        topPane.getChildren().addAll(miscContainer, minimizeBtn, closeBtn);

//------------------------------------------------------------------------------------------------> Navigation Panel End

        splashScreen.setProgress(0.2);
        System.out.println("Loading at 20%");

//-----------------------------------------------------------------------------------------------> Dashboard Pane Start

        final WebView webVid = new WebView();
        final WebEngine webVidEngine = webVid.getEngine();
        webVid.setPrefHeight(860);
        webVid.setPrefWidth(width - 118);
        webVidEngine.loadContent("");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        yAxis.setLabel("Score");
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(
                xAxis, yAxis);

        lineChart.setTitle("Line Chart");
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("My Data");

        // Populating the series with data
        series.getData().add(new XYChart.Data<Number, Number>(0.25, 36));
        series.getData().add(new XYChart.Data<Number, Number>(1, 23));
        series.getData().add(new XYChart.Data<Number, Number>(2, 114));
        series.getData().add(new XYChart.Data<Number, Number>(3, 15));
        series.getData().add(new XYChart.Data<Number, Number>(4, 124));
        lineChart.getData().add(series);
        lineChart.setPrefWidth(700);
        lineChart.setPrefHeight(600);
        lineChart.setLegendVisible(false);

        chatRoomField = new TextField();
        chatRoomField.getStyleClass().add("textField");
        chatRoomField.setPromptText("Enter Chat Room");
        chatRoomField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    chatRoom = chatRoomField.getText();
                    client.connect(messageArea, messageInputArea, superUser, chatRoom);
                }
            }
        });

        messageArea = new TextArea();
        messageArea.getStyleClass().add("textArea");
        messageArea.setWrapText(true);
        messageArea.setEditable(false);
        messageArea.setPrefHeight(740);

        messageInputArea = new TextArea();
        messageInputArea.getStyleClass().add("textArea");
        messageInputArea.setWrapText(true);
        messageInputArea.setPrefHeight(100);
        messageInputArea.setPromptText("Enter Message");
        messageInputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    client.send(messageArea, messageInputArea, superUser, chatRoom);
                    event.consume();
                }
            }
        });

        chatBox = new VBox();
        chatBox.setPrefWidth(250);
        chatBox.setMaxWidth(250);
        chatBox.getStyleClass().add("chatBox");
        chatBox.getChildren().addAll(chatRoomField,messageArea, messageInputArea);

        //client.test(messageArea, messageInputArea);

        dashboardGridPane = new GridPane();
        dashboardGridPane.getStyleClass().add("gridPane");
        dashboardGridPane.setVgap(5);
        dashboardGridPane.setHgap(5);
        dashboardGridPane.setGridLinesVisible(false);

        dashboardGridPane.setColumnIndex(lineChart, 0);
        dashboardGridPane.setRowIndex(lineChart, 0);
        dashboardGridPane.getChildren().addAll(lineChart);

        dashboardPane = new ScrollPane();
        dashboardPane.getStyleClass().add("scrollPane");
        dashboardPane.setPrefWidth(width - 400);
        dashboardPane.setPrefHeight(860);
        dashboardPane.setContent(dashboardGridPane);

        dashBoardBase = new HBox();
        dashBoardBase.setPrefWidth(width - (navigationPane.getWidth() + chatBox.getWidth()));
        dashBoardBase.setPrefHeight(860);
        dashBoardBase.getChildren().addAll(dashboardPane, chatBox);

//-------------------------------------------------------------------------------------------------> Dashboard Pane End

        splashScreen.setProgress(0.3);
        System.out.println("Loading at 30%");

        splashScreen.setProgress(0.4);
        System.out.println("Loading at 40%");

//-------------------------------------------------------------------------------------------------> Notebook Pane Start

        TextEditorPane textEditorPane2 = new TextEditorPane();
        TextEditorUtils textEditorUtils = new TextEditorUtils();

        MenuItem newMenuItem = new MenuItem("New");
        newMenuItem.setOnAction(e -> {
            textEditorPane2.clearTextEditorPane();
            textEditorPane2.setTextEditorPane(null, false, primaryStage.getWidth() - (navigationPane.getWidth() + 15),
                    primaryStage.getHeight() - (topPane.getHeight()), miscContainer, rootPane, primaryStage);
        });

        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(e -> {
            textEditorPane2.setTextEditorPane(textEditorUtils.openNote(primaryStage), false, primaryStage.getWidth() -
                            (navigationPane.getWidth() + 15), primaryStage.getHeight() - (topPane.getHeight()),
                    miscContainer, rootPane, primaryStage);
        });

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(e -> {
            textEditorPane2.saveNote(primaryStage);
        });

        MenuItem saveAsNoteMenuItem = new MenuItem("Save As Note");
        saveAsNoteMenuItem.setOnAction(e -> {
            textEditorPane2.saveAsNote(primaryStage);
        });

        MenuItem saveAsDocMenuItem = new MenuItem("Save As Document");
        saveAsDocMenuItem.setOnAction(e -> {
            textEditorPane2.saveDocument(primaryStage);
        });

        Menu saveAsMenuItem = new Menu("Save As");
        saveAsMenuItem.getItems().addAll(saveAsNoteMenuItem, saveAsDocMenuItem);

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem);

        Menu viewNotebooksMenu = new Menu("View Notebooks");
        viewNotebooksMenu.setOnAction(e -> {
            notebookPane.setContent(notebookFlowPane);
        });

        notebookMenuBar = new MenuBar();
        notebookMenuBar.getStyleClass().add("menuBtn");
        notebookMenuBar.getMenus().addAll(fileMenu, viewNotebooksMenu);

        notebookFlowPane = new FlowPane();
        notebookFlowPane.getStyleClass().add("flowPane");

        notebookPane = new ScrollPane();
        notebookPane.getStyleClass().add("scrollPane");
        notebookPane.setContent(notebookFlowPane);


//---------------------------------------------------------------------------------------------------> Notebook Pane End

        splashScreen.setProgress(0.5);
        System.out.println("Loading at 50%");

        splashScreen.setProgress(0.6);
        System.out.println("Loading at 60%");

        splashScreen.setProgress(0.7);
        System.out.println("Loading at 70%");

//-------------------------------------------------------------------------------------------------> Wolfram Pane Start

        Boolean wolframActive = false;
        wolframWeb = new WebView();
        try {
            wolframWeb.getStyleClass().add("scrollPane");
            wolframWebEngine = wolframWeb.getEngine();
            if (wolframActive == false){
                wolframWebEngine.load("http://www.wolframalpha.com/");
                wolframActive = true;
            }
            wolframPane = new ScrollPane();
            wolframPane.setContent(wolframWeb);
        }catch(Exception excep){
            System.out.println("<----- Exception in Wolfram Alpha Web ----->");
            excep.printStackTrace();
            System.out.println("<---------->\n");
        }

//---------------------------------------------------------------------------------------------------> Wolfram Pane End

        splashScreen.setProgress(0.8);
        System.out.println("Loading at 80%");

//------------------------------------------------------------------------------------------------> Wikipedia Pane Start

        Boolean wikipediaActive = false;
        wikipediaWeb = new WebView();
        try {
            wikipediaWeb.getStyleClass().add("scrollPane");
            wikipediaWebEngine = wikipediaWeb.getEngine();
            if(wikipediaActive == false){
                wikipediaWebEngine.load("https://en.wikipedia.org/wiki/Main_Page");
                wikipediaActive = true;
            }
            wikipediaPane = new ScrollPane();
            wikipediaPane.setContent(wikipediaWeb);
        }catch(Exception e){
            e.printStackTrace();
        }

//--------------------------------------------------------------------------------------------------> Wikipedia Pane End

        splashScreen.setProgress(0.9);
        System.out.println("Loading at 90%");

//-------------------------------------------------------------------------------------------------> Settings Pane Start

        Label appearanceSettingsLbl = new Label("Appearance");
        appearanceSettingsLbl.getStyleClass().add("lbl");

        Button logoutBtn = new Button("Log Out");
        logoutBtn.getStyleClass().add("btn");
        logoutBtn.setOnAction(e -> {

            if(JOptionPane.showConfirmDialog(null, "Are you sure you want to logout") == 0){
                config.resetStayLoggedIn();
                systemClose();
            }
        });

        settingsGridPanel = new GridPane();
        settingsGridPanel.getStyleClass().add("gridPane");
        settingsGridPanel.setPadding(defaultInsets);
        settingsGridPanel.setVgap(5);
        settingsGridPanel.setHgap(5);

        settingsGridPanel.setRowIndex(appearanceSettingsLbl, 0);
        settingsGridPanel.setColumnIndex(appearanceSettingsLbl, 0);

        settingsGridPanel.setRowIndex(logoutBtn, 5);
        settingsGridPanel.setColumnIndex(logoutBtn, 0);

        settingsGridPanel.getChildren().addAll(appearanceSettingsLbl, logoutBtn);

        settingsPanel = new ScrollPane();
        settingsPanel.getStyleClass().add("scrollPane");
        settingsPanel.setContent(settingsGridPanel);

//---------------------------------------------------------------------------------------------------> Settings Pane End

        rootPane = new BorderPane();
        rootPane.setLeft(navigationPane);
        rootPane.setTop(topPane);
        rootPane.setCenter(dashBoardBase);
        rootPane.getStyleClass().add("rootPane");
        rootPane.getStylesheets().add(Main.class.getResource("css/" + config.getStyle() + ".css").toExternalForm());

        splashScreen.setProgress(1);
        System.out.println("Loading at 100%");

        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setScene(new Scene(rootPane, width, height));
        primaryStage.show();

        //<----- Setting Component Sizes ----->
        /** Component sizes are set
         * After primary stage is shown*/
        dashboardGridPane.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + 237));
        dashboardGridPane.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());

        //dashboardPane.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + chatBox.getWidth()));
        //dashboardPane.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());

        wikipediaPane.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + 15));
        wikipediaPane.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());

        wolframPane.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + 15));
        wolframPane.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());

        wolframWeb.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + 15));
        wolframWeb.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());

        notebookMenuBar.setTranslateX(-(primaryStage.getWidth() - (calcBtn.getWidth() + closeBtn.getWidth()
                + minimizeBtn.getWidth() + navigationPane.getWidth() + notebookMenuBar.getWidth())));
        notebookMenuBar.setPrefHeight(topPane.getHeight());

        notebookPane.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + 15));
        notebookPane.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());

        notebookFlowPane.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + 15));
        notebookFlowPane.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());

        settingsGridPanel.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + 15));
        settingsGridPanel.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());

        searchResultScrollPane.setPrefWidth(primaryStage.getWidth() - (navigationPane.getWidth() + 15));
        searchResultScrollPane.setPrefHeight(primaryStage.getHeight() - topPane.getHeight());
        //<---------->

        programWidth = primaryStage.getWidth();
        programHeight = primaryStage.getHeight();
        setProgramHeight(primaryStage.getHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void systemClose(){

        if(client.getIsConnected() == true){
            client.disconnect(messageArea, messageInputArea, superUser, chatRoom);
        }

        try{
            if (database.getDBCon() != null){
                database.closeDBCon();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            File xmlFile = new File("userInfo.xml");
            if(xmlFile.exists() == true){
                xmlFile.delete();
            }

        }catch (Exception excep){
            System.out.println("<----- Exception in Delete XML File ----->");
            excep.printStackTrace();
            System.out.println("<---------->\n");
        }
        System.exit(0);
    }

    public void systemMinimize(Stage primaryStage){
        primaryStage.setIconified(true);
    }

    public void resetBtns(){

        dashboardBtn.getStyleClass().removeAll("dashboardBtnSelected");
        profileBtn.getStyleClass().removeAll("profileBtnSelected");
        coursesBtn.getStyleClass().removeAll("coursesBtnSelected");
        notebookBtn.getStyleClass().removeAll("notebookBtnSelected");
        simsBtn.getStyleClass().removeAll("simsBtnSelected");
        textEditorBtn.getStyleClass().removeAll("textEditorBtnSelected");
        wolframBtn.getStyleClass().removeAll("wolframBtnSelected");
        wikipediaBtn.getStyleClass().removeAll("wikipediaBtnSelected");
        settingsBtn.getStyleClass().removeAll("settingsBtnSelected");

        miscContainer.getChildren().removeAll(notebookMenuBar);
    }

    private void search(String searchQuery){
        searchResultScrollPane.setContent(search.searchResultsBox(searchQuery, null));
    }

    //<----- Mutators ----->
    public void setProgramWidth(double programWidth) {
        this.programWidth = programWidth;
    }

    public void setProgramHeight(double programHeight) {
        this.programHeight = programHeight;
    }

    public Main() {}
}
