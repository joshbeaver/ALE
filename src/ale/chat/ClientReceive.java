package ale.chat;

import ale.xml.XMLParser;

import java.io.IOException;

public class ClientReceive implements Runnable {

    private Client chatClient = new Client();

    private static XMLParser xmlParser = new XMLParser();

    public ClientReceive(Client chatClient) {
        this.chatClient = chatClient;
    }

    public static String returnTypeXML(){
        String type = xmlParser.getType();
        return type;
    }

    public static String returnUserXML(){
        String username = xmlParser.getUsername();
        return username;
    }

    public static String returnMsgXML(){
        String content = xmlParser.getContent();
        return content;
    }

    public static String returnRoomXML(){
        String room = xmlParser.getRoom();
        return room;
    }

    @Override
    public void run() {

        System.out.println("Listen thread started");

        while (chatClient.isConnected) {

            String message = "";
            try {
                message = chatClient.reader.readLine();
                xmlParser.parseMessage(message);
                if(message != null){
                    System.out.println(message);
                }

            } catch (IOException ex) {
                chatClient.msgArea.appendText("Cannot Connect! Try Again\n");
            }

            if (returnTypeXML().equals("message")) {
                chatClient.msgArea.appendText("\n" + returnUserXML() +
                        " - " + returnMsgXML());
                System.out.println("\n" + returnUserXML() +
                        " - " + returnMsgXML());
            }

            if (returnTypeXML().equals("connect")) {
                System.out.println(message);
                chatClient.msgArea.appendText("\n" + returnUserXML() +
                        " - " + returnMsgXML());
                System.out.println("\n" + returnUserXML() +
                        " - " + returnMsgXML());
            }

            if (returnTypeXML().equals("disconnect")) {
                System.out.println(message);
                chatClient.msgArea.appendText("\n" + returnUserXML() +
                        " - " + returnMsgXML());
            }

        }

    }

    public ClientReceive(){

    }

}
