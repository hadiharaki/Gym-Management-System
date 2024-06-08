package com.example.demo;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddClasses {
    private final TableView<Classes> table = new TableView<Classes>();
    private final ObservableList<Classes> data = FXCollections.observableArrayList(
            new Classes("Jacob", "7:00", "jacoovjv",2 ));
    final HBox hb = new HBox();

    ObservableList<Classes> list1 = new ObservableList<Classes>() {
        @Override
        public void addListener(InvalidationListener invalidationListener) {

        }

        @Override
        public void removeListener(InvalidationListener invalidationListener) {

        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Classes> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Classes classes) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Classes> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Classes> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Classes get(int index) {
            return null;
        }

        @Override
        public Classes set(int index, Classes element) {
            return null;
        }

        @Override
        public void add(int index, Classes element) {

        }

        @Override
        public Classes remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Classes> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Classes> listIterator(int index) {
            return null;
        }

        @Override
        public List<Classes> subList(int fromIndex, int toIndex) {
            return null;
        }

        @Override
        public void addListener(ListChangeListener<? super Classes> listChangeListener) {

        }

        @Override
        public void removeListener(ListChangeListener<? super Classes> listChangeListener) {

        }

        @Override
        public boolean addAll(Classes... classes) {
            return false;
        }

        @Override
        public boolean setAll(Classes... classes) {
            return false;
        }

        @Override
        public boolean setAll(Collection<? extends Classes> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Classes... classes) {
            return false;
        }

        @Override
        public boolean retainAll(Classes... classes) {
            return false;
        }

        @Override
        public void remove(int i, int i1) {

        }
    };

    ObservableList<Classes> dataList;

    TableColumn<Classes,String> classNameCol;
    TableColumn<Classes,String> classTimeCol;
    TableColumn<Classes,String> classCoachCol;
    TableColumn<Classes,Integer>classRoomCol;


    private TextField addClassName  ;
    private TextField addClassTime ;
    private TextField addCoachName ;
    private TextField addClassNumber;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_classes (){
        conn = MysqlCon.ConnectDb();
        String sql = "insert into Classes (class_Name, class_Time, class_Coach, class_Room)values(?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, addClassName.getText());
            pst.setString(2, addClassTime.getText());
            pst.setString(3, addCoachName.getText());
            pst.setString(4, addClassNumber.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Class: " + addClassName.getText() + " Added");
            dataList = MysqlCon.GetClasses();
            table.setItems(dataList);
            addClassName.clear();
            addClassTime.clear();
            addCoachName.clear();
            addClassNumber.clear();
        } catch (Exception e) {
            System.out.println("not working!");
        }
    }
    public void Edit (){
        try {
            conn = MysqlCon.ConnectDb();
            String value1 = addClassName.getText();
            String value2 = addClassTime.getText();
            String value3 = addCoachName.getText();
            Integer value4 = Integer.parseInt(addClassNumber.getText());
            String sql = "update Classes set class_Name= '"+value1+"',class_Time= '"+value2+"',class_Coach= '"+
                    value3+"',class_Room= '"+value4+"' where class_Name='"+value1+"'";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Class: " + addClassName.getText() + " Updated");
            list1 = MysqlCon.GetClasses();
            table.setItems(list1);
            addClassName.clear();
            addClassTime.clear();
            addCoachName.clear();
            addClassNumber.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
    public void Search (){
        try {
            ObservableList<Classes> list = FXCollections.observableArrayList();
            conn = MysqlCon.ConnectDb();
            String value1 = addClassName.getText();;
            String sql = "Select * from Classes where class_Name like '%" +value1+ "%'";
            pst= conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                list.add(new Classes(rs.getString("class_Name"), rs.getString("class_Time"), rs.getString("class_Coach"), Integer.parseInt(rs.getString("class_Room"))));
            }
            table.setItems(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
    public void Delete(){
        conn = MysqlCon.ConnectDb();
        String sql = "delete from Classes where class_Name = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, addClassName.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Class: " + addClassName.getText() + " Deleted");
            list1 = MysqlCon.GetClasses();
            table.setItems(list1);
            addClassName.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    public Scene addClasses1() {

        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle2 = new Text("Welcome to your gym: ");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid2.add(scenetitle2, 0, 0, 2, 1);


        Scene scene2 = new Scene(new Group());
        final Label label = new Label("Classes Book");
        label.setFont(new Font("Arial", 20));

        @SuppressWarnings("rawtypes")
        TableColumn classNameCol = new TableColumn("Class Name");
        classNameCol.setMinWidth(100);
        classNameCol.setCellValueFactory(new PropertyValueFactory<Classes, String>("className"));

        TableColumn classTimeCol = new TableColumn("Class Time");
        classTimeCol.setMinWidth(100);
        classTimeCol.setCellValueFactory(new PropertyValueFactory<Classes, String>("classTime"));

        TableColumn classCoachCol = new TableColumn("Class Coach");
        classCoachCol.setMinWidth(200);
        classCoachCol.setCellValueFactory(new PropertyValueFactory<Classes, String>("classCoach"));

        TableColumn classRoomCol = new TableColumn("Class Room");
        classRoomCol.setMinWidth(200);
        classRoomCol.setCellValueFactory(new PropertyValueFactory<Classes, Integer>("classRoom"));

        dataList = MysqlCon.GetClasses();
        System.out.println(dataList);
        table.setItems(dataList);
        table.getColumns().addAll(classNameCol, classTimeCol, classCoachCol,classRoomCol );

        addClassName = new TextField();
        addClassName.setPromptText("Class Name");
        addClassName.setMaxWidth(classNameCol.getPrefWidth());

        addClassTime = new TextField();
        addClassTime.setMaxWidth(classTimeCol.getPrefWidth());
        addClassTime.setPromptText("Class Time");

        addCoachName = new TextField();
        addCoachName.setMaxWidth(classCoachCol.getPrefWidth());
        addCoachName.setPromptText("Coach Name");

        addClassNumber = new TextField();
        addClassNumber.setMaxWidth(classRoomCol.getPrefWidth());
        addClassNumber.setPromptText("Class Room");

        final Button addButton1 = new Button("Add");
        final Button updateButton1 = new Button("Update");
        final Button deleteButton1 = new Button("Delete");
        final Button searchButton1 = new Button("Search");
        addButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Add_classes();
            }
        });
        updateButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Edit();
            }
        });
        searchButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Search();
            }
        });
        deleteButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Delete();
            }
        });
//        backBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//
//                addClassName.clear();
//                addClassTime.clear();
//                addCoachName.clear();
//                addClassNumber.clear();
//            }
//        });
//        addButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                int classNumber = Integer.parseInt(addClassNumber.getText());
//                data.add(new Classes(addClassName.getText(), addClassTime.getText(), addCoachName.getText() ,classNumber ));
//                addClassName.clear();
//                addClassTime.clear();
//                addCoachName.clear();
//                addClassNumber.clear();
//            }
//        });

        hb.getChildren().addAll(addClassName, addClassTime, addCoachName,addClassNumber, addButton1,updateButton1,deleteButton1,searchButton1 );
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        ((Group) scene2.getRoot()).getChildren().addAll(vbox);

        return scene2;

    }
}