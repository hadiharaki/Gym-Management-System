package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class MainPage {

    Stage window = new Stage();
    Feedback feedback = new Feedback();
    public Scene mainPage() {
        Scene  scene2, scene3,  scene5, scene6, scene7, scene8;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome to your gym: ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);


        Button addBtn = new Button("Manage members");
        Button addEmp = new Button("Manage employees");
        Button addClasses = new Button("Manage Classes");
        Button displayBtn = new Button("Display gym accounts");
        Button updateBtn = new Button("Update Information");
        Button exitBtn = new Button("Exit and Logout");

        Button viewFeedbacksBtn = new Button("View Feedbacks");
        grid.add(viewFeedbacksBtn, 0, 6);

        viewFeedbacksBtn.setOnAction(e -> {
            feedback.viewFeedbacks(window);
        });



        grid.add(addBtn, 0, 1);
        grid.add(addEmp, 0, 2);
        grid.add(addClasses,0,3);
        grid.add(displayBtn, 0, 4);
        grid.add(exitBtn, 0, 5);

        Image logoImage = new Image("file:C:/Users/user/Desktop/oopProj/logo.png");
        ImageView imageView = new ImageView(logoImage);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        HBox imageBox = new HBox(10);
        imageBox.getChildren().addAll(grid, imageView);
        imageBox.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(imageBox, 450, 350);



        AddEmp addEmp1 = new AddEmp();
        AddPerson addPerson = new AddPerson();
        AddClasses addClasses1 = new AddClasses();
        AddGym addGym = new AddGym();

        addBtn.setOnAction(e -> {window.setScene(addPerson.addPerson());
            window.show();});
        addEmp.setOnAction(e -> {window.setScene(addEmp1.addEmp1());
            window.show();});

        addClasses.setOnAction(e -> {window.setScene(addClasses1.addClasses1());
            window.show();});
        exitBtn.setOnAction(e -> System.exit(0));

        displayBtn.setOnAction(e -> {window.setScene(addGym.addGym());
            window.show();});

        //deleteBtn.setOnAction(e -> window.setScene(scene6));
        //updateBtn.setOnAction(e -> window.setScene(scene7));
        scene1.getStylesheets().add(getClass().getResource("/com/example/demo/styles.css").toExternalForm());

        addBtn.getStyleClass().add("red-button");
        addEmp.getStyleClass().add("red-button");
        addClasses.getStyleClass().add("red-button");
        displayBtn.getStyleClass().add("red-button");
        exitBtn.getStyleClass().add("red-button");
        viewFeedbacksBtn.getStyleClass().add("red-button");



        return scene1;
    }



}