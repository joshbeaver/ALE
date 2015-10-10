package ale;

import ale.database.Interaction;
import ale.utils.PasswordHash;
import ale.utils.Format;
import ale.utils.Misc;
import ale.xml.XMLCreator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class Login extends Application {

    Interaction dbInteract = new Interaction();
    XMLCreator xmlCreator = new XMLCreator();
    Misc utils = new Misc();
    Configuration config = new Configuration();
    Format format = new Format();
    PasswordHash passwordHash = new PasswordHash();

    Connection dbCon;
    PreparedStatement dbStm;
    ResultSet dbRs;

    String superUser;

    //Login
    BorderPane loginPane;
    VBox loginBox;
    VBox loginBtnBox;
    HBox loginTopPanel;
    TextField usernameLoginField;
    PasswordField passwordLoginField;
    Button loginCloseBtn;
    Button loginMinimizeBtn;
    Label loginLbl;
    Label errorLbl;
    Button loginBtn;
    Button signupBtn;
    RadioButton rememberMeBtn;

    //Sign Up
    BorderPane signUpPane;
    VBox signUpBox;
    VBox signUpBtnBox;
    HBox signUpTopPanel;
    TextField usernameField;
    PasswordField passwordField;
    PasswordField repeatPasswordField;
    Button signUpCloseBtn;
    Button signUpMinimizeBtn;
    Label signUpLbl;
    TextField emailField;
    TextField ageField;
    TextField schoolField;
    TextField cityField;
    TextField countryField;
    Button finalSignUpBtn;
    Label signUpErrorLbl;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        dbInteract.connectToDB();

        loginLbl = new Label("Login");
        loginLbl.getStyleClass().add("lbl");
        loginLbl.setTranslateX(-94);

        errorLbl = new Label("");
        errorLbl.getStyleClass().add("errorLbl");
        errorLbl.setTranslateX(65);
        errorLbl.setTranslateY(10);
        errorLbl.setVisible(false);

        usernameLoginField = new TextField();
        usernameLoginField.getStyleClass().add("loginTextField");
        usernameLoginField.setMaxWidth(250);
        usernameLoginField.setTranslateX(25);
        usernameLoginField.setTranslateY(10);
        usernameLoginField.setPromptText("Username");
        usernameLoginField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    passwordLoginField.requestFocus();
                }
            }
        });

        passwordLoginField = new PasswordField();
        passwordLoginField.getStyleClass().add("loginTextField");
        passwordLoginField.setMaxWidth(250);
        passwordLoginField.setTranslateX(25);
        passwordLoginField.setTranslateY(10);
        passwordLoginField.setPromptText("Password");
        passwordLoginField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    login(primaryStage, usernameLoginField.getText(), passwordLoginField.getText(), false);
                }if(event.getCode() == KeyCode.UP){
                    usernameLoginField.requestFocus();
                }
            }
        });

        loginCloseBtn = new Button("");
        loginCloseBtn.getStyleClass().add("loginSystemBtn");
        loginCloseBtn.setOnAction(e -> {
            System.exit(0);
        });

        loginMinimizeBtn = new Button("");
        loginMinimizeBtn.getStyleClass().add("loginSystemBtn");
        loginMinimizeBtn.setOnAction(e -> {
            primaryStage.setIconified(true);
        });

        loginTopPanel = new HBox();
        loginTopPanel.getStyleClass().add("loginTopPanel");
        loginTopPanel.setAlignment(Pos.CENTER_RIGHT);
        loginTopPanel.setPadding(new Insets(0,0,0,0));
        loginTopPanel.getChildren().addAll(loginLbl, loginMinimizeBtn, loginCloseBtn);

        loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("loginBtn");
        loginBtn.setPrefWidth(250);
        loginBtn.setTranslateX(25);
        loginBtn.setTranslateY(15);
        loginBtn.setOnAction(e -> {
            login(primaryStage, usernameLoginField.getText(), passwordLoginField.getText(), false);
        });

        signupBtn = new Button("Sign Up");
        signupBtn.getStyleClass().add("signupBtn");
        signupBtn.setPrefWidth(250);
        signupBtn.setTranslateX(25);
        signupBtn.setTranslateY(15);
        signupBtn.setOnAction(e -> {
            usernameLoginField.setText(null);
            passwordLoginField.setText(null);
            Scene signUpScene = new Scene(signUpPane, 300, 430);
            primaryStage.setScene(signUpScene);
        });

        rememberMeBtn = new RadioButton("Stay Logged In");
        rememberMeBtn.getStyleClass().add("radioBtn");
        rememberMeBtn.setTranslateX(85);
        rememberMeBtn.setTranslateY(20);

        loginBtnBox = new VBox(10);
        loginBtnBox.getChildren().addAll(loginBtn, signupBtn, rememberMeBtn, errorLbl);

        loginBox = new VBox(10);
        loginBox.getChildren().addAll(usernameLoginField, passwordLoginField, loginBtnBox);

        loginPane = new BorderPane();
        loginPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());
        loginPane.getStyleClass().add("loginPane");
        loginPane.setTop(loginTopPanel);
        loginPane.setCenter(loginBox);


        /**
         * Automatically logs the user in
         * if the user has opted to be remembered
         */
        config.autoLogin(primaryStage);

//-----------------------------------------------------------------------------------------------------> Login Pane End

//-------------------------------------------------------------------------------------------------> Sign Up Pane Start

        signUpLbl = new Label("Sign Up");
        signUpLbl.getStyleClass().add("lbl");
        signUpLbl.setTranslateX(-45);

        signUpErrorLbl = new Label();
        signUpErrorLbl.getStyleClass().add("errorLbl");
        signUpErrorLbl.setTranslateX(25);
        signUpErrorLbl.setTranslateY(7);

        signUpCloseBtn = new Button();
        signUpCloseBtn.getStyleClass().add("loginSystemBtn");
        signUpCloseBtn.setOnAction(e -> {
            System.exit(0);
        });

        signUpMinimizeBtn = new Button();
        signUpMinimizeBtn.getStyleClass().add("loginSystemBtn");
        signUpMinimizeBtn.setOnAction(e -> {
            primaryStage.setIconified(true);
        });

        signUpTopPanel = new HBox();
        signUpTopPanel.getStyleClass().add("loginTopPanel");
        signUpTopPanel.setAlignment(Pos.CENTER_RIGHT);
        signUpTopPanel.setPadding(new Insets(0, 0, 0, 0));
        signUpTopPanel.getChildren().addAll(signUpLbl, signUpMinimizeBtn, signUpCloseBtn);

        usernameField = new TextField();
        usernameField.getStyleClass().add("loginTextField");
        usernameField.setMaxWidth(250);
        usernameField.setTranslateX(25);
        usernameField.setTranslateY(10);
        usernameField.setPromptText("Username");
        usernameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    passwordField.requestFocus();
                }
            }
        });

        passwordField = new PasswordField();
        passwordField.getStyleClass().add("loginTextField");
        passwordField.setMaxWidth(250);
        passwordField.setTranslateX(25);
        passwordField.setTranslateY(10);
        passwordField.setPromptText("Password");
        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    repeatPasswordField.requestFocus();
                }
                if(event.getCode() == KeyCode.UP){
                    usernameField.requestFocus();
                }
            }
        });

        repeatPasswordField = new PasswordField();
        repeatPasswordField.getStyleClass().add("loginTextField");
        repeatPasswordField.setMaxWidth(250);
        repeatPasswordField.setTranslateX(25);
        repeatPasswordField.setTranslateY(10);
        repeatPasswordField.setPromptText("Repeat Password");
        repeatPasswordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    emailField.requestFocus();
                }
                if(event.getCode() == KeyCode.UP){
                    passwordField.requestFocus();
                }
            }
        });

        emailField = new TextField();
        emailField.getStyleClass().add("loginTextField");
        emailField.setMaxWidth(250);
        emailField.setTranslateX(25);
        emailField.setTranslateY(10);
        emailField.setPromptText("Email");
        emailField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    ageField.requestFocus();
                }
                if(event.getCode() == KeyCode.UP){
                    repeatPasswordField.requestFocus();
                }
            }
        });

        ageField = new TextField();
        ageField.getStyleClass().add("loginTextField");
        ageField.setMaxWidth(250);
        ageField.setTranslateX(25);
        ageField.setTranslateY(10);
        ageField.setPromptText("Age (Optional)");
        ageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    schoolField.requestFocus();
                }
                if(event.getCode() == KeyCode.UP){
                    emailField.requestFocus();
                }
            }
        });

        schoolField = new TextField();
        schoolField.getStyleClass().add("loginTextField");
        schoolField.setMaxWidth(250);
        schoolField.setTranslateX(25);
        schoolField.setTranslateY(10);
        schoolField.setPromptText("School (Optional)");
        schoolField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    cityField.requestFocus();
                }
                if(event.getCode() == KeyCode.UP){
                    ageField.requestFocus();
                }
            }
        });

        cityField = new TextField();
        cityField.getStyleClass().add("loginTextField");
        cityField.setMaxWidth(250);
        cityField.setTranslateX(25);
        cityField.setTranslateY(10);
        cityField.setPromptText("City (Optional)");
        cityField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    countryField.requestFocus();
                }
                if(event.getCode() == KeyCode.UP){
                    schoolField.requestFocus();
                }
            }
        });

        countryField = new TextField();
        countryField.getStyleClass().add("loginTextField");
        countryField.setMaxWidth(250);
        countryField.setTranslateX(25);
        countryField.setTranslateY(10);
        countryField.setPromptText("Country (Optional)");
        countryField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.UP){
                    cityField.requestFocus();
                }
            }
        });

        finalSignUpBtn = new Button("Sign Up");
        finalSignUpBtn.getStyleClass().add("loginBtn");
        finalSignUpBtn.setPrefWidth(250);
        finalSignUpBtn.setTranslateX(25);
        finalSignUpBtn.setTranslateY(15);
        finalSignUpBtn.setOnAction(e -> {
            signUp(primaryStage);
        });

        signUpBtnBox = new VBox(10);
        signUpBtnBox.getChildren().addAll(finalSignUpBtn, signUpErrorLbl);

        signUpBox = new VBox(10);
        signUpBox.setPadding(new Insets(0, 0, 0, 0));
        signUpBox.getChildren().addAll(usernameField, passwordField, repeatPasswordField, emailField, ageField,
                schoolField, cityField, countryField,signUpBtnBox);

        signUpPane = new BorderPane();
        signUpPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());
        signUpPane.getStyleClass().add("loginPane");
        signUpPane.setTop(signUpTopPanel);
        signUpPane.setCenter(signUpBox);

//---------------------------------------------------------------------------------------------------> Sign Up Pane End

        loginPane.getStyleClass().add("loginPane");
        loginPane.getStylesheets().add(Main.class.getResource("css/" + config.getStyle() + ".css").toExternalForm());

        signUpPane.getStyleClass().add("loginPane");
        signUpPane.getStylesheets().add(Main.class.getResource("css/" + config.getStyle() + ".css").toExternalForm());

        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setScene(new Scene(loginPane, 300, 265));

        if(config.getLoggedIn() == false) primaryStage.show();

    }

    protected boolean login(Stage primaryStage, String username, String password, boolean hashedState){

        Boolean loginStatus = false;

        if(connectToDB() == true){
            if(validLogin(username, password, hashedState, false) == true){

                if(usernameLoginField != null){
                    superUser = usernameLoginField.getText();
                }

                try{
                    createUserInfoXML(username);
                    loginStatus = true;
                    primaryStage.hide();
                    Main main = new Main();
                    Stage mainStage = new Stage();
                    main.start(mainStage);

                    //SplashScreen splashScreen = new SplashScreen();
                    //splashScreen.start(mainStage);
                    //SimpleCalculator calc = new SimpleCalculator();
                    //calc.start(mainStage);
                    //((Node)(e.getSource())).getScene().getWindow().hide();


                    dbCon.close();

                } catch (Exception excep){
                    System.out.println("<----- Exception in Valid Login ----->");
                    excep.printStackTrace();
                    System.out.println("<---------->");
                }

            }else {
                errorLbl.setText("Incorrect username or password");
                errorLbl.setVisible(true);
                passwordLoginField.clear();
            }
        }else{
            errorLbl.setText("Could Not Connect To Database");
            errorLbl.setVisible(true);
        }

        return loginStatus;

    }

    private void signUp(Stage primaryStage){
        if(connectToDB() == true){
            if(validSignUp(usernameField.getText(), passwordField.getText(), repeatPasswordField.getText(),
                    emailField.getText(), ageField.getText(), schoolField.getText()) == null){

                String username = usernameField.getText();
                String email = emailField.getText();
                String password = "";
                try {
                    password = passwordHash.createHash(passwordField.getText());
                }catch (NoSuchAlgorithmException noSuchAlgExcep){
                    System.out.println("<----- No Such Algorithm Exception in Password Hash/Sign Up ----->");
                    noSuchAlgExcep.printStackTrace();
                    System.out.println("<---------->\n");
                }catch (InvalidKeySpecException invalidKeyExcep){
                    System.out.println("<----- Invalid Key Exception in Password Hash/Sign Up ----->");
                    invalidKeyExcep.printStackTrace();
                    System.out.println("<---------->\n");
                }
                String age = ageField.getText();

                if(age.equals("")){
                    age = "null";
                }

                String school = schoolField.getText();
                String city = cityField.getText();
                String country = countryField.getText();

                try{
                    PreparedStatement psSignUp = null;
                    psSignUp = dbCon.prepareStatement("INSERT INTO userInfo VALUES(" + "\'"
                            + username + "\'" + "," + "\'" + email + "\'" + "," + "\'" + password
                            + "\'" + "," + age + "," + null + "," + "\'" + school + "\'" + "," + "\'" + city
                            + "\'" + "," + "\'" + country + "\'" + "," + "null" + ")" + ";");

                    System.out.println("INSERT INTO userInfo VALUES(" + "\'"
                            + username + "\'" + "," + "\'" + email + "\'" + "," + "\'" + password
                            + "\'" + "," + age + "," + null + "," + "\'" + school + "\'" + "," + "\'" + city
                            + "\'" + "," + "\'" + country + "\'" + "," + "null" + ")" + ";");

                    psSignUp.executeUpdate();

                    createUserInfoXML(usernameField.getText());

                    Main main = new Main();
                    SplashScreen splashScreen = new SplashScreen();
                    Stage mainStage = new Stage();
                    primaryStage.hide();
                    main.start(mainStage);

                    dbCon.close();
                    primaryStage.close();

                }catch (SQLException sqlExcep){
                    JOptionPane.showMessageDialog(null, "Internal Sign Up Error");
                    System.out.println("<----- SQL Exception in Sign Up ----->");
                    sqlExcep.printStackTrace();
                    System.out.println("<---------->\n");
                }catch (Exception excep){
                    System.out.println("<----- Exception in Sign Up ----->");
                    excep.printStackTrace();
                    System.out.println("<---------->\n");
                }
            }else {
                signUpErrorLbl.setText(validSignUp(usernameField.getText(), passwordField.getText(),
                        repeatPasswordField.getText(), emailField.getText(), ageField.getText(),
                        schoolField.getText()));
            }
        }else {
            signUpErrorLbl.setText("Could Not Connect To Database");
        }
    }

    private boolean validLogin(String username, String password, boolean hashedState, boolean stayLoggedIn){
        boolean validUser = false;
        try {
            String hashPassword;

            if(rememberMeBtn != null){
                if(rememberMeBtn.isSelected()) stayLoggedIn = true;
            }

            if (hashedState == false){
                hashPassword = this.passwordHash.createHash(passwordLoginField.getText());
                System.out.println(hashPassword);
            }else {
                hashPassword = password;
            }

            dbStm = dbCon.prepareStatement("SELECT * FROM userInfo WHERE username = " + "\"" + username + "\""
                    + ";" );

            dbRs = dbStm.executeQuery();

            while (dbRs.next()){
                String databaseUsername = "";
                String databasePassword = "";

                databaseUsername = dbRs.getString("username");
                System.out.println("Database Username = " + databaseUsername + " Input Username = " + username);
                databasePassword = dbRs.getString("password");
                System.out.println("Database Password = " + databasePassword + " Input Password = " + hashPassword);

                if(hashedState == false){
                    if(((username.equals(databaseUsername) == true)
                            && ((passwordHash.validatePassword(password, databasePassword)) == true))){
                        validUser = true;
                        if(stayLoggedIn == true){
                            config.setStayLoggedIn(databaseUsername, databasePassword);
                        }
                        System.out.println("Valid User");
                    }
                }
                if (hashedState == true){
                    if(((username.equals(databaseUsername) == true)
                            && ((hashPassword.equals(databasePassword)) == true))){
                        validUser = true;
                        System.out.println("Valid User");
                    }
                }
            }
        }catch (SQLException sqlExcep){
            System.out.println("<----- SQL Exception in Sign Up ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (NoSuchAlgorithmException noSuchAlgExcep){
            System.out.println("<----- No Such Algorithm Exception in Password Hash/Sign Up ----->");
            noSuchAlgExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (InvalidKeySpecException invalidKeyExcep){
            System.out.println("<----- Invalid Key Exception in Password Hash/Sign Up ----->");
            invalidKeyExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

        return validUser;
    }

    private String validSignUp(String username, String password, String repeatPassword, String email, String age,
                              String school){

        String error = null;

        dbInteract.connectToDB();
        Connection dbCon = dbInteract.getDBCon();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        if(password.equals(repeatPassword) == false){
            error = "Passwords do not match";
        }

        if(username.equals("")){
            error = "Enter username";
        }else {
            try{
                preparedStatement = dbCon.prepareStatement("SELECT * FROM userInfo" +
                        " WHERE username = " + "\"" + username + "\"" + ";");

                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    if (resultSet.getString("username").equals(username)){
                        error = "Username is taken";
                    }
                }

            }catch (SQLException sqlExcep){
                System.out.println("<----- SQL Exception in Valid Sign Up (Check Username) ----->");
                sqlExcep.printStackTrace();
                System.out.println("<---------->\n");
            }
        }

        if (format.checkUsernameFormat(username) != null) error = format.checkUsernameFormat(username);

        if(password.equals("")) error = "Enter password";

        if(format.checkPasswordFormat(password) != null) error = format.checkPasswordFormat(password);

        if(email.equals("")){
            error = "Enter email";
        }else {
            try{
                preparedStatement = dbCon.prepareStatement("SELECT * FROM userInfo" +
                        " WHERE email = " + "\"" + email + "\"" + ";");

                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    if (resultSet.getString("email").equals(email)){
                        error = "Email is already in use";
                    }
                }

            }catch (SQLException sqlExcep){
                System.out.println("<----- SQL Exception in Valid Sign Up (Check Email) ----->");
                sqlExcep.printStackTrace();
                System.out.println("<---------->\n");
            }
        }


    if(format.checkEmailFormat(email) != null){
            error = format.checkEmailFormat(email);
        }

        if(!age.equals("")){
            if(Integer.parseInt(age) > 0 || Integer.parseInt(age) < 100){
            }else{
                error = "Enter valid age";
            }
        }

        return error;
    }

    private boolean connectToDB(){
        boolean estCon = false;
        try{
            dbCon = DriverManager.getConnection("jdbc:mysql://192.168.1.6:3306/ale", "Root", "oqu#$XQgHFzDj@1MGg1G8");
            estCon = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return estCon;
    }

    private void createUserInfoXML(String superUser){

        try {

            dbStm = dbCon.prepareStatement("SELECT * FROM userInfo WHERE username = " + "\"" + superUser + "\""
                    + ";" );

            dbRs = dbStm.executeQuery();

            while (dbRs.next()){
                String username = "";
                String password = "";
                String email = "";
                String age = "";
                String school = "";
                String city = "";
                String country = "";

                System.out.println("<----- Output from createUserInfoXML in login ----->");
                username = dbRs.getString("username");
                System.out.println(username);
                email = dbRs.getString("email");
                System.out.println(email);
                age = dbRs.getInt("age") + "";
                System.out.println(age);
                school = dbRs.getString("school");
                System.out.println(school);
                city = dbRs.getString("city");
                System.out.println(city);
                country = dbRs.getString("country");
                System.out.println(country);
                System.out.println("<---------->");

                xmlCreator.createUserInfo(username, password, email, age, school, city, country);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Login() {}

}
