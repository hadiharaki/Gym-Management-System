package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
class MysqlCon{
    Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/oop2project","root","Hadi7076");
            return conn;
        } catch (Exception e) {
            return null;
        }
    }

    public static ObservableList<Employee> GetEmployees(){
        Connection conn = ConnectDb();
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Employee");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Employee(rs.getString("first_name"), rs.getString("last_name"), rs.getString("position"), Integer.parseInt(rs.getString("salary")), Integer.parseInt(rs.getString("age"))));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<Gym> GetGym() {
        Connection conn = ConnectDb();
        ObservableList<Gym> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Gym");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Gym(rs.getString("username"), rs.getString("gym_email"), rs.getString("password1"), rs.getString("password2")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<Person> GetPerson() {
        Connection conn = ConnectDb();
        ObservableList<Person> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Person");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Person(rs.getString("fname"), rs.getString("lname"), rs.getString("email")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<Classes> GetClasses() {
        Connection conn = ConnectDb();
        ObservableList<Classes> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Classes");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Classes(rs.getString("class_Name"), rs.getString("class_Time"), rs.getString("class_Coach"),Integer.parseInt(rs.getString("class_Room"))));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void main(String[] args){
        //System.out.println(GetEmployees());

    }

}
