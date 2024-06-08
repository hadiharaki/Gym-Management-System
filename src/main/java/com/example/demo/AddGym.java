package com.example.demo;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.demo.MysqlCon.ConnectDb;

public class AddGym {
    private TableView<Gym> table1 = new TableView<>();
    final HBox hb1 = new HBox();

    ObservableList<Gym> listM = new ObservableList<Gym>() {

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
        public Iterator<Gym> iterator() {
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
        public boolean add(Gym gym) {
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
        public boolean addAll(Collection<? extends Gym> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Gym> c) {
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
        public Gym get(int index) {
            return null;
        }

        @Override
        public Gym set(int index, Gym element) {
            return null;
        }

        @Override
        public void add(int index, Gym element) {

        }

        @Override
        public Gym remove(int index) {
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
        public ListIterator<Gym> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Gym> listIterator(int index) {
            return null;
        }

        @Override
        public List<Gym> subList(int fromIndex, int toIndex) {
            return null;
        }

        @Override
        public void addListener(ListChangeListener<? super Gym> listChangeListener) {

        }

        @Override
        public void removeListener(ListChangeListener<? super Gym> listChangeListener) {

        }

        @Override
        public boolean addAll(Gym... gyms) {
            return false;
        }

        @Override
        public boolean setAll(Gym... gyms) {
            return false;
        }

        @Override
        public boolean setAll(Collection<? extends Gym> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Gym... gyms) {
            return false;
        }

        @Override
        public boolean retainAll(Gym... gyms) {
            return false;
        }

        @Override
        public void remove(int i, int i1) {

        }
    };

        ObservableList<Gym> dataList;

        TableColumn<Gym, String> usernameCol;
        TableColumn<Gym, String> emailCol;
        TableColumn<Gym, String> passwordCol;
        TableColumn<Gym, Integer> password2;


        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;


        public Scene addGym() {

            GridPane grid = new GridPane();
            Scene scene1 = new Scene(grid, 450, 325);
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            scene1 = new Scene(new Group());
            final Label label3 = new Label("Gym accounts Book");
            label3.setFont(new Font("Arial", 20));
            //table1.setEditable(true);

            @SuppressWarnings("rawtypes")
            TableColumn usernameCol = new TableColumn("Username");
            usernameCol.setMinWidth(100);
            usernameCol.setCellValueFactory(new PropertyValueFactory<Gym, String>("username"));

            TableColumn emailCol = new TableColumn("Email");
            emailCol.setMinWidth(100);
            emailCol.setCellValueFactory(new PropertyValueFactory<Gym, String>("gym_email"));

            TableColumn passwordCol = new TableColumn("Password");
            passwordCol.setMinWidth(200);
            passwordCol.setCellValueFactory(new PropertyValueFactory<Gym, String>("password1"));

            dataList = MysqlCon.GetGym();
            System.out.println(dataList);
            table1.setItems(dataList);
            table1.getColumns().addAll(usernameCol, emailCol, passwordCol);
            final VBox vbox1 = new VBox();
            vbox1.setSpacing(5);
            vbox1.setPadding(new Insets(10, 0, 0, 10));
            vbox1.getChildren().addAll(label3, table1);
            ((Group) scene1.getRoot()).getChildren().addAll(vbox1);
            table1.setEditable(true);

            return scene1;
        }
    }
