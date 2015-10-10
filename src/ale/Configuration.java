package ale;


import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Configuration {

    Boolean loggedIn = false;

    public void loadInfo(Stage primaryStage){
        Login login = new Login();
        try{
            Scanner scanner = new Scanner(new File("config/config.txt"));
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                if(line.substring(0, 8).equals("remember")){
                    System.out.println(line.substring(0, 8));
                    if(line.substring(9, line.indexOf(" ")).equals("true")){
                        System.out.println(line.substring(9, line.indexOf(" ")));
                        String username = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
                        System.out.println(username);
                        String password = line.substring(line.lastIndexOf(" ") + 1, line.length() - 1);
                        System.out.println(password);
                        loggedIn = login.login(primaryStage, username, password, true);
                    }
                }
            }
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Load Info ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public void createInfo(String username, String password){
        Login login = new Login();
        try{
            File configFile= new File("config.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.txt"));
            String line= "";
            while (bufferedReader.readLine() != null){
                line += bufferedReader.readLine();
                System.out.println("Line = " + line);
                if(line.substring(0, 8).equals("remember")){
                    if (line.substring(9, line.indexOf(" ")).equals("false")){
                        line.replace(line.substring(9, line.indexOf(" ")), "true");
                    }
                    line.replace(line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" ")), username);
                    line.replace(line.substring(line.lastIndexOf(" ") + 1, line.length() - 1), password);

                    FileOutputStream outputStream = new FileOutputStream(configFile);
                    String newline = "hello";
                    //outputStream.write(line.getBytes());
                    outputStream.close();
                }
            }
            bufferedReader.close();
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Create Info ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public String getStyle(){
        String style = "";
        return style;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }
}
