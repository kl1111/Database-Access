package com.fdmgroup.dataaccess;
import java.sql.*;

public class TestOracle {

       public static void main(String args[]) {

              try {
                     DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

              } catch (SQLException e) {
                     System.out.println("ORACLE driver failed to load-have you loaded the JAR file?");
                     return;
              }

              Connection connection = null;
              try {

                     connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
                     Statement statement = connection.createStatement();
                     ResultSet resultset = statement.executeQuery("SELECT Name, Employee_ID, Boss_ID FROM Employees WHERE employee_id=5");

                     while (resultset.next()) {
                           String name = resultset.getString("name");
                           int employee_id = resultset.getInt("employee_id");
                           int boss_id = resultset.getInt("boss_id");
                           System.out.println("Employee ID: " + employee_id + ", " + "Name: " + name + ", " + "Boss ID: " + boss_id);
                     }
                     connection.close();

              } catch (SQLException e) {
                     e.printStackTrace();
              }
       }
}
