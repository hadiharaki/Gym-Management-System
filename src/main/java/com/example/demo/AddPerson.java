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

public class AddPerson {
    private final TableView<Person> table = new TableView<Person>();
    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com"));
    final HBox hb = new HBox();
    ObservableList<Person> list1 = new ObservableList<Person>() {
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
        public Iterator<Person> iterator() {
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
        public boolean add(Person person) {
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
        public boolean addAll(Collection<? extends Person> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Person> c) {
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
        public Person get(int index) {
            return null;
        }

        @Override
        public Person set(int index, Person element) {
            return null;
        }

        @Override
        public void add(int index, Person element) {

        }

        @Override
        public Person remove(int index) {
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
        public ListIterator<Person> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Person> listIterator(int index) {
            return null;
        }

        @Override
        public List<Person> subList(int fromIndex, int toIndex) {
            return null;
        }

        @Override
        public void addListener(ListChangeListener<? super Person> listChangeListener) {

        }

        @Override
        public void removeListener(ListChangeListener<? super Person> listChangeListener) {

        }

        @Override
        public boolean addAll(Person... people) {
            return false;
        }

        @Override
        public boolean setAll(Person... people) {
            return false;
        }

        @Override
        public boolean setAll(Collection<? extends Person> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Person... people) {
            return false;
        }

        @Override
        public boolean retainAll(Person... people) {
            return false;
        }

        @Override
        public void remove(int i, int i1) {

        }
    };

        ObservableList<Person> dataList;

    TableColumn<Person,String> firstNameCol1;
    TableColumn<Person,String> lastNameCol1;
    TableColumn<Person,String> emailCol1;


    private TextField addFirstName1  ;
    private TextField addLastName1 ;
    private TextField addEmail1 ;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_users (){
        conn = MysqlCon.ConnectDb();
        String sql = "insert into Person (fname, lname, email)values(?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, addFirstName1.getText());
            pst.setString(2, addLastName1.getText());
            pst.setString(3, addEmail1.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Member: " + addFirstName1.getText() + " added");
            dataList = MysqlCon.GetPerson();
            table.setItems(dataList);
            addFirstName1.clear();
            addLastName1.clear();
            addEmail1.clear();
        } catch (Exception e) {
            System.out.println("not working!");
            //System.out.println(e.getMessage());
        }
    }

    public void Edit () {
        try {
            conn = MysqlCon.ConnectDb();
            String value1 = addFirstName1.getText();
            String value2 = addLastName1.getText();
            String value3 = addEmail1.getText();
            String sql = "update Person set fname= '"+value1+"',lname= '"+value2+"',email= '"+
                    value3+"' where fname='"+value1+"' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Member: " + addFirstName1.getText() + " Updated");
            list1 = MysqlCon.GetPerson();
            table.setItems(list1);
            addFirstName1.clear();
            addLastName1.clear();
            addEmail1.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void Search (){
        try {
            ObservableList<Person> list = FXCollections.observableArrayList();
            conn = MysqlCon.ConnectDb();
            String value1 = addFirstName1.getText();;
            String sql = "Select * from Person where fname like '%" +value1+ "%'";
            pst= conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                list.add(new Person(rs.getString("fname"), rs.getString("lname"), rs.getString("email")));
            }
            table.setItems(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void Delete(){
        conn = MysqlCon.ConnectDb();
        String sql = "delete from Person where fname = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, addFirstName1.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Member: " + addFirstName1.getText() + " Deleted");
            list1 = MysqlCon.GetPerson();
            table.setItems(list1);
            addFirstName1.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
        public Scene addPerson() {

        GridPane grid2 = new GridPane();

        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle2 = new Text("Welcome to your gym: ");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid2.add(scenetitle2, 0, 0, 2, 1);

        Scene scene2 = new Scene(new Group());

        final Label label = new Label("Members Book");
        label.setFont(new Font("Arial", 20));
        @SuppressWarnings("rawtypes")
        TableColumn firstNameCol1 = new TableColumn("First Name");
        firstNameCol1.setMinWidth(100);
        firstNameCol1.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));
        TableColumn lastNameCol1 = new TableColumn("Last Name");
        lastNameCol1.setMinWidth(100);
        lastNameCol1.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        TableColumn emailCol1 = new TableColumn("Email");
        emailCol1.setMinWidth(200);
        emailCol1.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

            dataList = MysqlCon.GetPerson();
            System.out.println(dataList);
            table.setItems(dataList);
            table.getColumns().addAll(firstNameCol1, lastNameCol1,emailCol1);

            addFirstName1 = new TextField();
            addFirstName1.setPromptText("First Name");
            addFirstName1.setMaxWidth(firstNameCol1.getPrefWidth());

        addLastName1 = new TextField();
        addLastName1.setMaxWidth(lastNameCol1.getPrefWidth());
        addLastName1.setPromptText("Last Name");

        addEmail1 = new TextField();
        addEmail1.setMaxWidth(emailCol1.getPrefWidth());
        addEmail1.setPromptText("Email");

            final Button addButton1 = new Button("Add");
            final Button updateButton1 = new Button("Update");
            final Button deleteButton1 = new Button("Delete");
            final Button searchButton1 = new Button("Search");
//        backBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//
//                addFirstName1.clear();
//                addLastName1.clear();
//                addEmail1.clear();
//            }
//        });

            addButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Add_users();
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


            hb.getChildren().addAll(addFirstName1, addLastName1, addEmail1, addButton1 ,updateButton1,deleteButton1 , searchButton1 );
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);


        ((Group) scene2.getRoot()).getChildren().addAll(vbox);

        return scene2;

    }


}
