/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.visualisation.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




 public class Connexion{

 public static Connection getConnexion() throws SQLException{
  try
  {
  Class.forName("com.mysql.jdbc.Driver");
  //System.out.println("Connexion réussite");
  return DriverManager.getConnection("jdbc:mysql://localhost:3306/assvisual","root","admin");
  }
  catch ( ClassNotFoundException e)
  {
  throw new SQLException(e.getMessage());
  }
  }
  }