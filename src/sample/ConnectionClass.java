package sample;

import java.sql.*;

public class ConnectionClass {
    public Connection connection;
    public Connection getConnection(){
        String dbname = "myquizdb";
        String username = "root";
        String password = "root";

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname+"?useSSL=false",username,password);
        }
        catch(Exception e){
            e.printStackTrace();

        }
        return connection;
    }
}
