import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class test {

    public static void main(String[] args) {
        
        ArrayList<String> city = new ArrayList<>();
        city.add("london");
        city.add("cairo");
        city.add("alex");
        city.add("paris");

//        System.out.println(generatePassword());
//        sendEmail(generatePassword().toString());
//        insertCityName(city);
//        getCityName();

    }

    public static ArrayList<String> getCityName(){

        ArrayList<String> cityName =new ArrayList<>();
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cities","root","");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM city");
            while(rs.next()){
                cityName.add(rs.getString("cityName"));
            }
            con.close();
            System.out.println(cityName);

        }catch(Exception e){
            e.printStackTrace();
        }
        return cityName;
    }

    public static void insertCityName( ArrayList<String> cityName){

        try{
            int i =0;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cities","root","");
            PreparedStatement ps=con.prepareStatement("INSERT INTO city (cityName) VALUES (?)");
            for(String values : cityName) {
                ps.setString(1,values);
                ps.addBatch();
                i++;
                if (i % 1000 == 0 || i == cityName.size()) {
                    ps.executeBatch();
                }

            }
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static char[] generatePassword() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[12];

        for(int i = 0; i< 12 ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }

    public static void sendEmail(String password){
        String to = "Yehia@gmail.com";
        String from = "Karam@gmail.com";
        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("This is your Password");
            message.setText(password);
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}


