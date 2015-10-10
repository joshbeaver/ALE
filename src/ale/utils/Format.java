package ale.utils;

import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Format {

    public String checkUsernameFormat(String username){
        /* Username requirements
        * Cannot be more than 15 characters
        * Cannot contain spaces i.e " " */

        String error = null;

        if(username.length() > 15) error = "Username is too long";

        if(username.contains(" ")) error = "Username cannot contain spaces";

        return error;
    }

    public String checkEmailFormat(String email){
        /* Email requirements
        * Must contain "@"
        * Must end with a .* e.g .com or .org */

        String error = null;

        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
            System.out.println("Email Is Valid");
        } catch (AddressException ex) {
            error = "Enter Valid Email Address";
            System.out.println("Email is not valid");
        }

        return error;
    }

    public String checkPasswordFormat(String password){
        /* Password requirements
        * Must be at least six character
        * Must contain at least one special character*/

        String error = null;

        if(password.length() < 6) error = "Password must be at least six characters";

        return error;
    }

    public File resizeImage(File image, int width, int height){
        File newImage = new File("resizedImage.jpg");

        try{
            BufferedImage originalImage = ImageIO.read(image);
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = compressImage(originalImage, type, width, height);
            ImageIO.write(resizeImageJpg, "jpg", newImage);

        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Resize Image ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

        return newImage;
    }

    /*
    private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        BufferedImage dest = new BufferedImage(rect.getWidth(), rect.getHeight(), BufferedImage.TYPE_ARGB_PRE);
        Graphics g = dest.getGraphics();
        g.drawImage(src, 0, 0, rect.getWidth(), rect.getHeight(), rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), null);
        g.dispose();
        return dest;
    }
    */

    private BufferedImage compressImage(BufferedImage originalImage, int type, int width, int height){
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

}
