package ale.database;

import ale.Main;
import ale.utils.Format;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;

public class Interaction {

    Connection dbCon = null;

    public boolean connectToDB() {
        boolean estCon = false;
        try {
            if (dbCon == null ) {
                dbCon = DriverManager.getConnection("jdbc:mysql://192.168.1.6:3306/ale", "Root", "oqu#$XQgHFzDj@1MGg1G8");
                estCon = true;
            }

        }catch (SQLException sqlExcep) {
        System.out.println("<----- SQL Exception in Establish Database Connection ----->");
        sqlExcep.printStackTrace();
        System.out.println("<---------->\n");
        }

        return estCon;
    }

    public Connection getDBCon() {
        return dbCon;
    }

    public void closeDBCon(){
        try {
            dbCon.close();
            dbCon = null;
        }catch (SQLException sqlExcep){
            System.out.println("<----- SQL Exception in Close Database Connection ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public String getCourseServer(){
        String serverIP = "";

        return serverIP;
    }

    public String getChatServer(String chatroom){
        String serverIP = "Cannot Find Sever";

        connectToDB();
        Connection dbCon = getDBCon();

        String getChatServerSQL = "SELECT ip FROM serverinfo WHERE chatroom = "
                + "\'" + chatroom + "\';";

        try {

            PreparedStatement dbPs = dbCon.prepareStatement(getChatServerSQL);
            ResultSet dbRs = dbPs.executeQuery();

            while (dbRs.next()){
                serverIP = dbRs.getString("ip");
            }

        }catch (SQLException sqlExcep){
        System.out.println("<----- SQL Exception in Get Chat Server----->");
        sqlExcep.printStackTrace();
        System.out.println("<---------->\n");
        }


        return serverIP;
    }

    public String updateProfileInfo(String columnName, String stringValue, int intValue ,String superUser){
        String error = "";
        String updateProfileInfoSQL = "";
        connectToDB();
        Connection dbCon = getDBCon();
        if(stringValue == null){
            if (intValue > 0 && intValue < 100){
                updateProfileInfoSQL = "UPDATE userInfo" +
                        " SET " + columnName +" = " + intValue +
                        " WHERE username = " + "\'" + superUser + "\'" + ";";
                System.out.println(updateProfileInfoSQL);
            }else {
                error = "Enter valid age";
            }
        }else {
            updateProfileInfoSQL = "UPDATE userInfo" +
                " SET " + columnName +" = " + stringValue +
                " WHERE username = " + "\'" + superUser + "\'" + ";";
        }

        try{
            PreparedStatement psUpdateProfileInfo = dbCon.prepareStatement(updateProfileInfoSQL);
            psUpdateProfileInfo.executeUpdate();
        }catch (SQLException sqlExcep){
            System.out.println("<----- SQL Exception in Update Profile Info ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
            error = "Unable to update profile";
        }
        return error;
    }

    public void uploadProfilePicture(String superUser, Stage primaryStage){
        connectToDB();
        Connection dbCon = getDBCon();
        String updateProfilePictureSQL = "UPDATE userInfo" +
                " SET profilePicture = ?" +
                " WHERE username = " + "\'" + superUser + "\'" + ";";

        FileInputStream profilePictureFIS = null;

        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Picture");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg")
            );
            File file = fileChooser.showOpenDialog(primaryStage);
            Format format = new Format();
            if(file != null){
                File croppedImage =  format.resizeImage(file, 200, 200);
                profilePictureFIS = new FileInputStream(croppedImage);

                PreparedStatement psUpdateProfilePicture = dbCon.prepareStatement(updateProfilePictureSQL);
                psUpdateProfilePicture.setBinaryStream(1, profilePictureFIS, (int)file.length());
                System.out.println(psUpdateProfilePicture);
                psUpdateProfilePicture.executeUpdate();
                croppedImage.deleteOnExit();
            }

        }catch (SQLException sqlExcep){
            sqlExcep.printStackTrace();
        }catch (IOException ioExcep){
            ioExcep.printStackTrace();
        }finally {
            try{
                dbCon.close();
                closeDBCon();
                if(profilePictureFIS != null){
                    profilePictureFIS.close();
                }
            }catch (SQLException sqlExcep){
                System.out.println("<----- SQL Exception in Close Database Connection for Update Profile Picture " +
                        "----->");
                sqlExcep.printStackTrace();
                System.out.println("<---------->\n");
            }catch (IOException ioExcep){
                System.out.println("<----- IO Exception in Close File Input Stream for Profile Picture ----->");
                ioExcep.printStackTrace();
                System.out.println("<---------->\n");
            }
            Main main = new Main();
            main.setProfilePicture();
        }
    }

    public Interaction() {
    }

}