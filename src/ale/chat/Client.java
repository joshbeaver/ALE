package ale.chat;

import ale.database.Interaction;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class Client {

    private Socket sock;
    protected BufferedReader reader;
    PrintWriter writer;
    Boolean isConnected = false;

    //GUI Components
    TextArea msgArea;
    TextArea msgInputArea;
    String userName;

    public void connect(TextArea messageArea, TextArea messageInputArea, String username, String chatRoom){
        if (isConnected == false) {

            msgArea = messageArea;
            msgInputArea = messageInputArea;
            userName = username;

            Interaction dbInteraction = new Interaction();

            messageArea.setText("");

            //Gets Server IP
            String ip = dbInteraction.getChatServer(chatRoom);
            System.out.println("Server IP = " + ip);

            if(ip.equals("Cannot Find Sever")){
                messageArea.appendText("Cannot Find Server\n");
            }else {
                try {
                    sock = new Socket(ip, 6129);
                    InputStreamReader streamreader
                            = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(sock.getOutputStream(), true);

                    //Connect Message
                    writer.println("<message>" + "<type>" + "connect" + "</type>"
                            + "<room>" + chatRoom + "</room>"
                            + "<username>" + username + "</username>"
                            + "<content>" + " Has Connected" + "</content>"
                            + "</message>");

                    isConnected = true;

                    //Listen Thread
                    ClientReceive chatClientReceive = new ClientReceive(this);
                    Thread listenThread = new Thread(chatClientReceive);
                    listenThread.start();


                }catch (IOException ioExcep) {
                    System.out.println("<----- IO Exception in Set Profile Picture ----->");
                    ioExcep.printStackTrace();
                    System.out.println("<---------->\n");

                    messageArea.appendText("Cannot Connect! Try Again\n");
                }
            }
        } else if (isConnected == true) {
            messageArea.appendText("You are connected\n");
        }

    }

    public void send(TextArea messageArea, TextArea messageInputArea, String username, String chatRoom){
        if ((messageInputArea.getText()).equals("")) {
            messageInputArea.setText("");
            messageInputArea.requestFocus();
        } else {
            if(isConnected == true){
                try {

                    //Message
                    writer.println("<message>" + "<type>" + "message" + "</type>"
                            + "<room>" + chatRoom + "</room>"
                            + "<username>" + username + "</username>"
                            + "<content>" + messageInputArea.getText() + "</content>"
                            + "</message>");

                } catch (Exception ex) {
                    messageArea.appendText("Message was not sent\n");
                }
            }else{
                messageArea.appendText("Not Connected");
            }

            messageInputArea.setText("");
            messageInputArea.requestFocus();
        }

    }

    public void disconnect(TextArea messageArea, TextArea messageInputArea, String username, String chatRoom){
        try {

            //Disconnect Message
            writer.println("<message>" + "<type>" + "disconnect" + "</type>"
                    + "<room>" + chatRoom + "</room>"
                    + "<username>" + username + "</username>"
                    + "<content>" + " Has Disconnected" + "</content>"
                    + "</message>");

            writer.close();
            reader.close();
            isConnected = false;

        } catch (Exception ex) {
            messageArea.appendText("Cannot disconnect\n");
        }
    }

    public void test(TextArea messageArea, TextArea messageInputArea){
        messageArea.appendText("Test");
        messageInputArea.appendText("test");
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(Boolean state){ this.isConnected = state; }

    public Client() {
    }
}

