package com.example.demo;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Feedback {
    public void viewFeedbacks(Stage primaryStage) {
        Stage feedbackStage = new Stage();

        TextArea feedbackArea = new TextArea();
        feedbackArea.setEditable(false);
        StackPane stackPane = new StackPane(feedbackArea);

        try (BufferedReader reader = new BufferedReader(new FileReader("feedback.txt"))) {
            String line;
            StringBuilder feedbackText = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                feedbackText.append(line).append("\n");
            }

            feedbackArea.setText(feedbackText.toString());
        } catch (IOException e) {
            // Handle file reading exception or feedback file not found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to read feedbacks!");
            alert.showAndWait();
        }

        Scene scene = new Scene(stackPane);
        feedbackStage.setScene(scene);
        feedbackStage.setTitle("Feedbacks");
        feedbackStage.initOwner(primaryStage);

        // Update TextArea size on stage resize
        scene.widthProperty().addListener((obs, oldVal, newVal) -> feedbackArea.setPrefWidth((Double) newVal));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> feedbackArea.setPrefHeight((Double) newVal));

        feedbackStage.show();
    }
}
