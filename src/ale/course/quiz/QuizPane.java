package ale.course.quiz;

import ale.database.Interaction;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizPane extends Pagination{


    Interaction dbInteract = new Interaction();
    String[] questionId = new String[15];
    MultipleChoice[] questionsArr = new MultipleChoice[10];

    ResultsPage resultsPage;

    public QuizPane(int pageCount, int pageIndex, String subject, String superTopic, String topic ) {
        super(pageCount, pageIndex);

        getStyleClass().add("pagination");

        dbInteract.connectToDB();
        Connection dbCon = dbInteract.getDBCon();

        try {
            String questionSQL = "SELECT * FROM questionbank\n" +
                    "WHERE subject = \'" + subject + "\' AND superTopic = \'" +  superTopic + "\' " +
                    "AND topic = \'" + topic + "\' \n" +
                    "ORDER BY RAND()\n" +
                    "limit 0,10;\n";
            PreparedStatement dbPs = dbCon.prepareStatement(questionSQL);
            ResultSet dbRs = dbPs.executeQuery();

            int index = 0;

            while (dbRs.next()){
                questionId[index] = dbRs.getString("id");
                index++;
            }

        }catch (SQLException sqlExcep){
            System.out.println("<----- SQL Exception in Multiple Choice Question ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

        setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });


    }


    public VBox createPage(int pageIndex) {
        VBox page = null;
        if(pageIndex == 10){
            if(resultsPage == null){
                resultsPage = new ResultsPage(questionsArr);
                page = resultsPage;
            }else {
                page = resultsPage;
            }
        }else {
            if(questionsArr[pageIndex] == null){
                page = new MultipleChoice(questionId[pageIndex]);
                questionsArr[pageIndex] = (MultipleChoice)page;
            }else{
                page = questionsArr[pageIndex];
            }
        }
        return page;
    }

}
