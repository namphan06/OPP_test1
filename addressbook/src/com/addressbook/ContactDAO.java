/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addressbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author aman
 */

// Data Access Object for Contacts
public class ContactDAO {
    Connection conn = null;
    PreparedStatement prepStatement= null;
    Statement statement = null;
    ResultSet resultSet = null;

    public ContactDAO() {
        try {
            conn = new ConnectionFactory().getConn();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to add new custoemr
    public void addContactDAO(ContactDTO ContactDTO) {
        try {
            String query = "SELECT * FROM Contacts WHERE lastname='"
                    +ContactDTO.getlastname()
                    + "' AND location='"
                    +ContactDTO.getLocation()
                    + "' AND phone='"
                    +ContactDTO.getPhone()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Contact already exists.");
            else
                addFunction(ContactDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addFunction(ContactDTO ContactDTO) {
        try {
            String query = "INSERT INTO Contacts VALUES(?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, ContactDTO.getCustCode());
            prepStatement.setString(2, ContactDTO.getlastname());
            prepStatement.setString(3, ContactDTO.getLocation());
            prepStatement.setString(4, ContactDTO.getPhone());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New Contact has been added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Method to edit existing Contact details
    public  void editContactDAO(ContactDTO ContactDTO) {
        try {
            String query = "UPDATE Contacts SET lastname=?,location=?,phone=? WHERE firstName=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, ContactDTO.getlastname());
            prepStatement.setString(2, ContactDTO.getLocation());
            prepStatement.setString(3, ContactDTO.getPhone());
            prepStatement.setString(4, ContactDTO.getCustCode());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Contact details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete existing Contact
    public void deleteContactDAO(String custCode) {
        try {
            String query = "DELETE FROM Contacts WHERE firstName='" +custCode+ "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Contact removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve data set to be displayed
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT firstName,lastname,location,phone FROM Contacts";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to retrieve search data
    public ResultSet getContactSearch(String text) {
        try {
            String query = "SELECT firstName,lastname,location,phone FROM Contacts " +
                    "WHERE firstName LIKE '%"+text+"%' OR lastname LIKE '%"+text+"%' OR " +
                    "location LIKE '%"+text+"%' OR phone LIKE '%"+text+"%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getCustName(String custCode) {
        try {
            String query = "SELECT * FROM Contacts WHERE firstName='" +custCode+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProdName(String prodCode) {
        try {
            String query = "SELECT productname,currentstock.quantity FROM products " +
                    "INNER JOIN currentstock ON products.productcode=currentstock.productcode " +
                    "WHERE currentstock.productcode='" +prodCode+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to display data set in tabular form
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount; col++){
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

}
