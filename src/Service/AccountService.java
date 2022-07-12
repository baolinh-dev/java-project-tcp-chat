/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Model.Account;
import Server.DashBoard;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Linh Tran Bao;
 */
public class AccountService {

    static Connection con = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;

    public static boolean checkAccount(String name, String pass) {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-DU2NQCV\\SQLEXPRESS:1433;databaseName=ChatMessageClient", "sa", "linh0707");
            pst = con.prepareStatement("SELECT * FROM Account");
            rs = pst.executeQuery();

            while (rs.next()) {
                if (name.equals(rs.getString("NameUser")) && pass.equals(rs.getString("Pass"))) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    } 
    
    public static ArrayList<Account> getListUser() {
        ArrayList<Account> list = new ArrayList<>();
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-DU2NQCV\\SQLEXPRESS:1433;databaseName=ChatMessageClient", "sa", "linh0707");
            pst = con.prepareStatement("SELECT * FROM Account");
            rs = pst.executeQuery();

            while (rs.next()) {
                Account s1= new Account(rs.getString("NameUser"), rs.getString("Pass"));
                list.add(s1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
