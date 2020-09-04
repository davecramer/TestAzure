package org.postgresql;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class TestAzure {
  public static void main(String args[]) {
    File file = null;

    if (args.length > 1){
      file = new File(args[1]);
    } else {
      file = new File("local.properties");
    }

    String url="jdbc:postgresql:";
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(file));
      url += properties.getProperty("host") + '/';
      Connection connection = DriverManager.getConnection(url, properties);
      try (Statement statement = connection.createStatement() ){
        try (ResultSet rs = statement.executeQuery("select version()") ) {
          if (rs.next()) {
            System.err.println("Version: " + rs.getString(1));
          } else {
            System.err.println("Unable to read version");
          }
        }
      }
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
  }
}

