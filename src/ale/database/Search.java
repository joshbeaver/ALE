package ale.database;

import ale.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.sql.*;

public class Search {

    Interaction dbInteract = new Interaction();

    Connection dbCon = null;

    public ResultSet search(String searchRequest, String filter){
        dbInteract.connectToDB();
        ResultSet dbRs = null;
        try{
            Connection dbCon = dbInteract.getDBCon();
            String searchSQL = "SELECT username FROM userInfo " +
                    "WHERE username LIKE \'" + searchRequest + "%\';" ;
            System.out.println("SQL = " + searchSQL);
            PreparedStatement dbStm = dbCon.prepareStatement(searchSQL);
            dbRs = dbStm.executeQuery();
        }catch (SQLException sqlExcep){
            System.out.println("<----- SQL Exception in Search ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
        return dbRs;
    }

    public VBox searchResultsBox(String searchRequest, String filter){
        VBox searchResultBox = new VBox(20);
        searchResultBox.getStyleClass().add("rootPane");

        ResultSet searchRs = search(searchRequest, filter);
        Label noResultsLbl = new Label("No Results Found");
        noResultsLbl.getStyleClass().add("lbl");
        noResultsLbl.setAlignment(Pos.TOP_CENTER);

        searchResultBox.getChildren().add(noResultsLbl);

        try{
            while(searchRs.next()){
                searchResultBox.getChildren().removeAll(noResultsLbl);
                SearchResultItem searchResultItem = new SearchResultItem(searchRs.getString("username"));
                searchResultItem.setTitle(searchRs.getString("username"));
                searchResultBox.getChildren().add(searchResultItem);
            }
        }catch (SQLException sqlExcep){
            System.out.println("<----- SQL Exception in Search Box ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

        return searchResultBox;
    }

}
