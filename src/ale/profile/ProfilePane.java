package ale.profile;

import ale.database.Interaction;
import ale.xml.XMLParser;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfilePane {

    Interaction interaction = new Interaction();
    XMLParser xmlParser = new XMLParser();

    String superUser = "";

    ScrollPane profilePane;
    GridPane profileGridPane;
    Label usernameContentLbl;
    Label ageContentLbl;
    Label emailContentLbl;
    Label schoolContentLbl;
    Button profilePictureBtn;

    public void setProfilePane(String superUser, double prefWidth, double prefHeight, BorderPane parentPane,
                               Stage primaryStage){

        if(profilePane == null){
            this.superUser = superUser;

            profilePictureBtn = new Button();
            profilePictureBtn.getStyleClass().add("profilePictureBtn");
            profilePictureBtn.setMaxSize(200, 200);
            profilePictureBtn.setOnAction(e -> {
                interaction.uploadProfilePicture(superUser, primaryStage);
            });

            FileOutputStream profilePictureFOS = null;

            Image ppImage = null;

            setProfilePicture();

            String profileUserName = xmlParser.getSuperUser();

            String profileEmail = xmlParser.getEmail();
            String profileAge = xmlParser.getAge();
            String profileSchool = xmlParser.getSchool();
            String profileCountry = "";
            String profileCity = "";

            Label usernameLbl = new Label("Username: ");
            usernameLbl.getStyleClass().add("profileInfoLbl");

            usernameContentLbl = new Label(profileUserName);
            usernameContentLbl.getStyleClass().add("profileInfoContentLbl");

            HBox usernameBox = new HBox();
            usernameBox.getChildren().addAll(usernameLbl, usernameContentLbl);

            Label emailLbl = new Label("Email: ");
            emailLbl.getStyleClass().add("profileInfoLbl");

            emailContentLbl = new Label(profileEmail);
            emailContentLbl.getStyleClass().add("profileInfoContentLbl");
            emailContentLbl.setOnMouseClicked(e -> {

            });

            HBox emailBox = new HBox();
            emailBox.getChildren().addAll(emailLbl, emailContentLbl);

            Label ageLbl = new Label("Age: ");
            ageLbl.getStyleClass().add("profileInfoLbl");

            ageContentLbl = new Label();
            if(Integer.parseInt(profileAge) > 0){
                ageContentLbl.setText(profileAge);
            }else{
                ageContentLbl.setText("Set");
            }
            ageContentLbl.getStyleClass().add("profileInfoContentLbl");
            ageContentLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY){

                    }
                }
            });

            HBox ageBox = new HBox();
            ageBox.getChildren().addAll(ageLbl, ageContentLbl);

            Label schoolLbl = new Label("School: ");
            schoolLbl.getStyleClass().add("profileInfoLbl");

            schoolContentLbl = new Label();
            if (profileSchool.length() > 0){
                schoolContentLbl.setText(profileSchool);
            }else {
                schoolContentLbl.setText("Set");
            }
            schoolContentLbl.getStyleClass().add("profileInfoContentLbl");

            HBox schoolBox = new HBox();
            schoolBox.getChildren().addAll(schoolLbl, schoolContentLbl);

            profileGridPane = new GridPane();
            profileGridPane.getStyleClass().add("gridPane");
            profileGridPane.setVgap(5);
            profileGridPane.setHgap(5);
            profileGridPane.setGridLinesVisible(false);
            profileGridPane.setPrefWidth(prefWidth);
            profileGridPane.setPrefHeight(prefHeight);
            profileGridPane.setAlignment(Pos.TOP_LEFT);
            profileGridPane.setPadding(new Insets(10, 0, 0, 6));

            profileGridPane.setRowIndex(profilePictureBtn, 0);
            profileGridPane.setColumnIndex(profilePictureBtn, 0);
            profileGridPane.setRowSpan(profilePictureBtn, 6);
            profileGridPane.setRowIndex(usernameBox, 0);
            profileGridPane.setColumnIndex(usernameBox, 1);
            profileGridPane.setRowIndex(emailBox, 1);
            profileGridPane.setColumnIndex(emailBox, 1);
            profileGridPane.setRowIndex(ageBox, 2);
            profileGridPane.setColumnIndex(ageBox, 1);
            profileGridPane.setRowIndex(schoolBox, 3);
            profileGridPane.setColumnIndex(schoolBox, 1);

            profileGridPane.getChildren().addAll(profilePictureBtn, usernameBox, emailBox, ageBox, schoolBox);

            profilePane = new ScrollPane();
            profilePane.getStyleClass().add("scrollPane");
            profilePane.setContent(profileGridPane);
        }

        parentPane.setCenter(profilePane);

    }



    public void setProfilePicture() {

        FileOutputStream profilePictureFOS = null;
        Image ppImage = null;

        try {
            interaction.connectToDB();
            Connection dbCon = interaction.getDBCon();

            String selectProfilePictureSQL = "SELECT profilePicture" +
                    " FROM userInfo" +
                    " WHERE username = " + "\'" + superUser + "\'" + ";";
            PreparedStatement psSelectProfilePicture = dbCon.prepareStatement(selectProfilePictureSQL);
            ResultSet rsSelectProfilePicture = psSelectProfilePicture.executeQuery();

            File profilePictureFile = new File("profilePicture.jpg");
            profilePictureFOS = new FileOutputStream(profilePictureFile);

            while (rsSelectProfilePicture.next()) {
                byte[] buffer = new byte[1];
                InputStream profilePictureIS = rsSelectProfilePicture.getBinaryStream(1);
                if (profilePictureIS != null) {
                    while (profilePictureIS.read(buffer) > 0) {
                        profilePictureFOS.write(buffer);

                        if (profilePictureFile != null) {
                            //profilePictureBtn.getStyleClass().remove("profilePictureBtn");
                            profilePictureBtn.setStyle("-fx-graphic: url(\"" + profilePictureFile.toURI().toURL() + "\"); " +
                                    "-fx-background-color: transparent;" +
                                    "-fx-border-radius: 0em;" +
                                    "-fx-background-radius: 0em;" +
                                    "-fx-graphic-height: 100px;");
                            if (profileGridPane != null && profilePictureBtn != null) {
                                profileGridPane.getChildren().removeAll(profilePictureBtn);
                                profileGridPane.getChildren().addAll(profilePictureBtn);
                            }

                            profilePictureFile.deleteOnExit();

                            /*
                            final ImageView profilePictureImageView = new ImageView();
                            final Image profilePictureImage = new Image(Main.class.getResourceAsStream("profilePicture.jpg"));
                            profilePictureImageView.setImage(profilePictureImage);

                            profilePictureBtn.setGraphic(profilePictureImageView);
                            */
                        }
                    }
                }
            }

            profilePictureFOS.close();
            dbCon.close();
        } catch (SQLException sqlExcep) {
            System.out.println("<----- SQL Exception in Set Profile Picture ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
        } catch (MalformedURLException urlExcep) {
            System.out.println("<----- Malformed URL Exception in Set Profile Picture ----->");
            urlExcep.printStackTrace();
            System.out.println("<---------->\n");
        } catch (IOException ioExcep) {
            System.out.println("<----- IO Exception in Set Profile Picture ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        } finally {
            interaction.closeDBCon();
        }

    }

}
