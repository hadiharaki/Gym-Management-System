package com.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;

public class MainClient {
    RegisterClass register = new RegisterClass();

    public Scene mainClient(String mail) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome to your gym: ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 5, 1); // Spanning 5 columns for the title

        Button registerButton = new Button("Register");
        Button removeClassButton = new Button("Remove Class");
        Button feedbackButton = new Button("Leave Feedback");
        Button equipmentButton = new Button("View Equipment");
        Button profileButton = new Button("Profile");
        Button toDoButton = new Button("To Do");

        // Add buttons to the grid in separate rows
        grid.add(registerButton, 0, 1);
        grid.add(removeClassButton, 0, 2);
        grid.add(feedbackButton, 0, 3);
        grid.add(equipmentButton, 0, 4);
        grid.add(profileButton, 0, 5);
        grid.add(toDoButton, 0, 6);

        // Set button actions (assuming these methods are correctly implemented)
        registerButton.setOnAction(e -> {
            register.registerClass1(mail);
        });

        removeClassButton.setOnAction(e -> {
            register.displayAndDeleteClasses(mail);
        });

        feedbackButton.setOnAction(e -> {
            register.openFeedbackScene(mail);
        });

        equipmentButton.setOnAction(e -> {
            register.displayEquipmentImages();
        });

        profileButton.setOnAction(e -> {
            register.profile(mail);
        });

        toDoButton.setOnAction(e -> {
            register.toDo(mail);
        });

        Scene scene1 = new Scene(grid, 450, 350);

        scene1.getStylesheets().add(getClass().getResource("/com/example/demo/styles.css").toExternalForm());
        grid.setStyle("-fx-background-image: url('file:///C:/Users/user/Desktop/oopProj/proj1.jpg'); " +
                "-fx-background-size: cover;");

        grid.getStyleClass().add("background-grid");
        registerButton.getStyleClass().add("white-button");
        removeClassButton.getStyleClass().add("white-button");
        feedbackButton.getStyleClass().add("white-button");
        equipmentButton.getStyleClass().add("white-button");
        profileButton.getStyleClass().add("white-button");
        toDoButton.getStyleClass().add("white-button");

        return scene1;
    }
}
