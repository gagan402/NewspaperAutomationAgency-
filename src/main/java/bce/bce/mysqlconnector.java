package bce.bce;


import java.sql.Connection;
import java.sql.DriverManager;


public class mysqlconnector {
    static Connection conn;
    public static Connection doConnect()
    {

        try
        {
            conn=DriverManager.getConnection("jdbc:mysql://localhost/papermaster","root","123Sarpanch@#$");
            System.out.println("Connection Established");
        }
        catch(Exception exp)
        {
            System.out.println(exp.toString());
        }

        return conn;
    }


}
