package ale.log;

import java.io.File;

public class Log {

    public void log(LogType logType, String logTitle, String logMessage){

        File logFile = new File("log.txt");

        switch (logType){

            case ERR:

                break;

            case STD:
                break;

        }
    }

}
