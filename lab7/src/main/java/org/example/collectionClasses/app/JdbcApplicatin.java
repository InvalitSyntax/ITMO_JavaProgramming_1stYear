package org.example.collectionClasses.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class JdbcApplicatin {
    public static void main(String[] args) throws Exception{
 	   Connection connection  = DriverManager.getConnection(
          	"jdbc:postgresql://localhost:5432/studs",
          	"s467433", "gtsNAsET0QoUHh3Q");

        Statement statement = connection.createStatement();
    	ResultSet results = statement.executeQuery("SELECT * FROM space_marines");

    	while (results.next()) {
        	String login = results.getString("login");
        	String pswd = results.getString("password_hash");
        	System.out.println(results.getRow() + ". " + login + "\t"+ pswd);
    	}
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++)
        {
                    String name = metaData.getColumnName(column);
                    String className = metaData.getColumnClassName(column);
                    String typeName = metaData.getColumnTypeName(column);
                    int type = metaData.getColumnType(column);

                    System.out.println(name + "\t" + className + "\t" + typeName + "\t" + type);
        }
    	connection.close();
    }
}
