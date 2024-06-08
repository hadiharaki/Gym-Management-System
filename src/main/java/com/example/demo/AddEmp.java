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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import javafx.scene.Node;
public class AddEmp{
    private TableView<Employee> table1 = new TableView<>();
    private final ObservableList<Employee> data1 = FXCollections.observableArrayList(
            new Employee("Jacob", "Smith", "Reception", 22788, 22));
    final HBox hb1 = new HBox();

    ObservableList<Employee> listM = new ObservableList<Employee>() {
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
        public Iterator<Employee> iterator() {
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
        public boolean add(Employee employee) {
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
        public boolean addAll(Collection<? extends Employee> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Employee> c) {
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
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public Employee get(int index) {
            return null;
        }

        @Override
        public Employee set(int index, Employee element) {
            return null;
        }

        @Override
        public void add(int index, Employee element) {

        }

        @Override
        public Employee remove(int index) {
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
        public ListIterator<Employee> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Employee> listIterator(int index) {
            return null;
        }

        @Override
        public List<Employee> subList(int fromIndex, int toIndex) {
            return null;
        }

        @Override
        public void addListener(ListChangeListener<? super Employee> listChangeListener) {

        }

        @Override
        public void removeListener(ListChangeListener<? super Employee> listChangeListener) {

        }

        @Override
        public boolean addAll(Employee... employees) {
            return false;
        }

        @Override
        public boolean setAll(Employee... employees) {
            return false;
        }

        @Override
        public boolean setAll(Collection<? extends Employee> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Employee... employees) {
            return false;
        }

        @Override
        public boolean retainAll(Employee... employees) {
            return false;
        }

        @Override
        public void remove(int i, int i1) {

        }
    };
    ObservableList<Employee> dataList;

    TableColumn<Employee,String> firstNameCol1;
    TableColumn<Employee,String> lastNameCol1;
    TableColumn<Employee,String> positionCol;
    TableColumn<Employee,Integer> salaryCol;
    TableColumn<Employee,Integer>ageCol;

    private TextField addFirstName1;
    private TextField addLastName1;
    private TextField addPosition;
    private TextField addSalary;
    private TextField addAge;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_users (){
        conn = MysqlCon.ConnectDb();
        String sql = "insert into Employee (first_name, last_name, position, salary, age)values(?,?,?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, addFirstName1.getText());
            pst.setString(2, addLastName1.getText());
            pst.setString(3, addPosition.getText());
            pst.setString(4, addSalary.getText());
            pst.setString(5, addAge.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Employee: " + addFirstName1.getText() + " Added");
            dataList = MysqlCon.GetEmployees();
            table1.setItems(dataList);
            addFirstName1.clear();
            addLastName1.clear();
            addPosition.clear();
            addAge.clear();
            addSalary.clear();
        } catch (Exception e) {
            System.out.println("not working!");
        }
    }

    public void Edit (){
        try {
            conn = MysqlCon.ConnectDb();
            String value1 = addFirstName1.getText();
            String value2 = addLastName1.getText();
            String value3 = addPosition.getText();
            Integer value4 = Integer.parseInt(addSalary.getText());
            Integer value5 = Integer.parseInt(addAge.getText());
            String sql = "update Employee set first_name= '"+value1+"',last_name= '"+value2+"',position= '"+
                    value3+"',salary= '"+value4+"',age= '"+value5+"' where first_name='"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Employee: " + addFirstName1.getText() + " Updated");
            listM = MysqlCon.GetEmployees();
            table1.setItems(listM);
            addFirstName1.clear();
            addLastName1.clear();
            addPosition.clear();
            addAge.clear();
            addSalary.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void Search (){
        try {
            ObservableList<Employee> list = FXCollections.observableArrayList();
            conn = MysqlCon.ConnectDb();
            String value1 = addFirstName1.getText();;
            String sql = "Select * from Employee where first_name like '%" +value1+ "%'";
            pst= conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                list.add(new Employee(rs.getString("first_name"), rs.getString("last_name"), rs.getString("position"), Integer.parseInt(rs.getString("salary")), Integer.parseInt(rs.getString("age"))));
            }
            table1.setItems(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void Delete(){
        conn = MysqlCon.ConnectDb();
        String sql = "delete from Employee where first_name = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, addFirstName1.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Employee: " + addFirstName1.getText() + " Deleted");
            listM = MysqlCon.GetEmployees();
            table1.setItems(listM);
            addFirstName1.clear();
            addLastName1.clear();
            addPosition.clear();
            addAge.clear();
            addSalary.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void UpdateTable(){
        firstNameCol1.setCellValueFactory(new PropertyValueFactory<Employee,String>("firstName"));
        lastNameCol1.setCellValueFactory(new PropertyValueFactory<Employee,String>("lastName"));
        positionCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("Position"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("Salary"));
        ageCol.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("age"));

        listM = MysqlCon.GetEmployees();
        table1.setItems(listM);
    }

    public Scene addEmp1() {

        GridPane grid = new GridPane();
        Scene scene1 = new Scene(grid, 450, 325);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        scene1 = new Scene(new Group());
        final Label label3 = new Label("Employee Book");
        label3.setFont(new Font("Arial", 20));
        //table1.setEditable(true);

        @SuppressWarnings("rawtypes")
        TableColumn firstNameCol1 = new TableColumn("First Name");
        firstNameCol1.setMinWidth(100);
        firstNameCol1.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));

        TableColumn lastNameCol1 = new TableColumn("Last Name");
        lastNameCol1.setMinWidth(100);
        lastNameCol1.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));

        TableColumn positionCol = new TableColumn("Position");
        positionCol.setMinWidth(200);
        positionCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("Position"));

        TableColumn salaryCol = new TableColumn("Salary");
        salaryCol.setMinWidth(100);
        salaryCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("Salary"));

        TableColumn ageCol = new TableColumn("Age");
        ageCol.setMinWidth(100);
        ageCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("age"));

        dataList = MysqlCon.GetEmployees();
        System.out.println(dataList);
        table1.setItems(dataList);
        table1.getColumns().addAll(firstNameCol1, lastNameCol1,positionCol, salaryCol,ageCol);

        addFirstName1 = new TextField();
        addFirstName1.setPromptText("First Name");
        addFirstName1.setMaxWidth(firstNameCol1.getPrefWidth());
        addLastName1 = new TextField();
        addLastName1.setMaxWidth(lastNameCol1.getPrefWidth());
        addLastName1.setPromptText("Last Name");
        addPosition = new TextField();
        addPosition.setMaxWidth(lastNameCol1.getPrefWidth());
        addPosition.setPromptText("Position");
        addAge = new TextField();
        addAge.setMaxWidth(lastNameCol1.getPrefWidth());
        addAge.setPromptText("Age");
        addSalary = new TextField();
        addSalary.setMaxWidth(lastNameCol1.getPrefWidth());
        addSalary.setPromptText("Salary");


        final Button addButton1 = new Button("Add");
        final Button updateButton1 = new Button("Update");
        final Button deleteButton1 = new Button("Delete");
        final Button searchButton1 = new Button("Search");
        //final Button backBtn1 = new Button("Back");
       /* backBtn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close()
                addFirstName1.clear();
                addLastName1.clear();
                addPosition.clear();
                addAge.clear();
                addSalary.clear();
            }
        });*/
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

        deleteButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Delete();
            }
        });

        searchButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Search();
            }
        });

        hb1.getChildren().addAll(addFirstName1, addLastName1, addPosition,addSalary,addAge, addButton1,updateButton1, deleteButton1, searchButton1);
        hb1.setSpacing(3);
        final VBox vbox1 = new VBox();
        vbox1.setSpacing(5);
        vbox1.setPadding(new Insets(10, 0, 0, 10));
        vbox1.getChildren().addAll(label3, table1, hb1);
        ((Group) scene1.getRoot()).getChildren().addAll(vbox1);
        table1.setEditable(true);

        return scene1;

    }
}