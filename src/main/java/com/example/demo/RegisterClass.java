package com.example.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.ImageView;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;


public class RegisterClass {
    Stage window = new Stage();
    Connection conn = MysqlCon.ConnectDb(); // Your database connection

    public void registerClass1(String userEmail) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Register for a Class");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        ObservableList<String> classOptions = FXCollections.observableArrayList();

        try {
            String selectQuery = "SELECT class_Name, class_Time, class_Coach, class_Room, class_Room FROM classes";
            PreparedStatement pst = conn.prepareStatement(selectQuery);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String className = rs.getString("class_Name");
                String classTime = rs.getString("class_Time");
                String classCoach = rs.getString("class_Coach");
                int classNB = rs.getInt("class_Room");
                // Add class details to the ComboBox
                classOptions.add(className + " - " + classTime + " - " + classCoach + " - " +classNB);
            }

            pst.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any SQL exception
        }

        ComboBox<String> classSelection = new ComboBox<>(classOptions);
        grid.add(classSelection, 0, 1);

        Button registerButton = new Button("Register");
        grid.add(registerButton, 0, 2);

        registerButton.setOnAction(e -> {
            String selectedClass = classSelection.getValue();
            if (selectedClass != null) {
                try {
                    String[] classInfo = selectedClass.split(" - ");
                    String className = classInfo[0];
                    String classTime = classInfo[1];
                    String classCoach = classInfo[2];
                    int classNB= Integer.parseInt(classInfo[3]);
                    // Search for user with the given email
                    String searchUserQuery = "SELECT fname, lname FROM person WHERE email = ?";
                    PreparedStatement searchUserPst = conn.prepareStatement(searchUserQuery);
                    searchUserPst.setString(1, userEmail);
                    ResultSet userRs = searchUserPst.executeQuery();

                    if (userRs.next()) {
                        String firstName = userRs.getString("fname");
                        String lastName = userRs.getString("lname");

                        // Insert user's credentials and selected class information into person_classes table
                        String insertQuery = "INSERT INTO person_classes (fname, lname, class_Name, class_Time, class_Coach, class_Room, mail) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement insertPst = conn.prepareStatement(insertQuery);
                        insertPst.setString(1, firstName);
                        insertPst.setString(2, lastName);
                        insertPst.setString(3, className);
                        insertPst.setString(4, classTime);
                        insertPst.setString(5, classCoach);
                        insertPst.setInt(6, classNB); // Room number example
                        insertPst.setString(7, userEmail);
                        insertPst.executeUpdate();
                        insertPst.close();

                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("User with email " + userEmail + " not found.");
                    }

                    userRs.close();
                    searchUserPst.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle any SQL exception
                }
            } else {
                System.out.println("Please select a class to register.");
            }
        });

        Scene registerScene = new Scene(grid, 450, 200);
        window.setScene(registerScene);
        window.setTitle("Register for Class");
        window.show();
    }
    public List<String> getClientClasses(String userEmail) {
        List<String> clientClasses = new ArrayList<>();

        try {
            String selectQuery = "SELECT class_Name, class_Time, class_Coach, class_Room FROM person_classes WHERE mail = ?";
            PreparedStatement pst = conn.prepareStatement(selectQuery);
            pst.setString(1, userEmail);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String className = rs.getString("class_Name");
                String classTime = rs.getString("class_Time");
                String classCoach = rs.getString("class_Coach");
                int classNB=rs.getInt("class_Room");
                clientClasses.add(className + " - " + classTime + " - " + classCoach + " - " +classNB);
            }

            pst.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any SQL exception
        }

        return clientClasses;
    }
    public void deleteClass(String userEmail, String selectedClass) {
        try {
            // Split the selected class to retrieve class details
            String[] classInfo = selectedClass.split(" - ");
            String className = classInfo[0];
            String classTime = classInfo[1];
            String classCoach = classInfo[2];

            // Delete the selected class for the given user
            String deleteQuery = "DELETE FROM person_classes WHERE class_Name = ? AND class_Time = ? AND class_Coach = ? AND mail = ?";
            PreparedStatement pst = conn.prepareStatement(deleteQuery);

            pst.setString(1, className);
            pst.setString(2, classTime);
            pst.setString(3, classCoach);
            pst.setString(4, userEmail);
            pst.executeUpdate();

            pst.close();
            System.out.println("Class deleted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any SQL exception
        }
    }
    public void displayAndDeleteClasses(String userEmail) {
        List<String> clientClasses = getClientClasses(userEmail);

        // Create a GridPane for the layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add ComboBox to display client classes
        ComboBox<String> classSelection = new ComboBox<>();
        classSelection.getItems().addAll(clientClasses);
        grid.add(classSelection, 0, 0);

        // Add Delete button
        Button deleteButton = new Button("Delete Class");
        grid.add(deleteButton, 0, 1);

        // Handle delete action
        deleteButton.setOnAction(event -> {
            String selectedClass = classSelection.getValue();
            if (selectedClass != null) {
                deleteClass(userEmail, selectedClass);
                // Optionally, you can refresh the displayed classes after deletion
                classSelection.getItems().clear();
                classSelection.getItems().addAll(getClientClasses(userEmail));
            } else {
                System.out.println("Please select a class to delete.");
            }
        });

        // Create a new window to display the classes and delete button
        Stage displayStage = new Stage();
        displayStage.setTitle("Your Classes");
        Scene scene = new Scene(grid, 300, 200);
        displayStage.setScene(scene);
        displayStage.show();
    }

    public void openFeedbackScene(String userEmail) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Leave Feedback");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        TextArea feedbackTextArea = new TextArea();
        feedbackTextArea.setPromptText("Write your feedback here...");
        grid.add(feedbackTextArea, 0, 1);

        Button submitButton = new Button("Submit Feedback");
        grid.add(submitButton, 0, 2);

        Stage feedbackStage = new Stage();
        feedbackStage.setTitle("Feedback");
        Scene scene = new Scene(grid, 400, 300);
        feedbackStage.setScene(scene);
        feedbackStage.show();

        submitButton.setOnAction(e -> {
            String feedback = feedbackTextArea.getText();
            if (!feedback.isEmpty()) {
                saveFeedback(userEmail, feedback);
                feedbackStage.close();
            } else {
                // Handle empty feedback submission
                System.out.println("Please enter your feedback before submitting.");
            }
        });
    }

    private void saveFeedback(String userEmail, String feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            writer.write("Client Email: " + userEmail);
            writer.newLine();
            writer.write("Feedback:");
            writer.newLine();
            writer.write(feedback);
            writer.newLine();
            writer.write("----------------------------------");
            writer.newLine();
            System.out.println("Feedback saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file writing exception
        }
    }

    private ImageView[] equipmentImages = {
            new ImageView("C:/Users/user/Desktop/oopProj/treadmill.jpg"),
            new ImageView("C:/Users/user/Desktop/oopProj/spinning bike.jpg"),
            new ImageView("C:/Users/user/Desktop/oopProj/dumbbells.jpg"),
            new ImageView("C:/Users/user/Desktop/oopProj/bench press.jpg"),
            new ImageView("C:/Users/user/Desktop/oopProj/squats.jpg"),
    };


    public void displayEquipmentImages() {
        Stage equipmentStage = new Stage();
        equipmentStage.setTitle("Gym Equipment Images");

        ComboBox<String> equipmentComboBox = new ComboBox<>();
        // Add equipment options to the combo box
        ObservableList<String> equipmentOptions = FXCollections.observableArrayList("Treadmill", "Spinning Bike", "dumbbells", "bench press", "squats");
        equipmentComboBox.setItems(equipmentOptions);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(equipmentComboBox, 0, 0);

        ImageView selectedImageView = new ImageView();
        selectedImageView.setFitWidth(380);
        selectedImageView.setFitHeight(250);
        gridPane.add(selectedImageView, 0, 1);

        equipmentComboBox.setOnAction(e -> {
            int selectedIndex = equipmentComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < equipmentImages.length) {

                Image selectedImage = equipmentImages[selectedIndex].getImage();
                selectedImageView.setImage(selectedImage);
                selectedImageView.setFitWidth(380);
                selectedImageView.setFitHeight(250);
            }
        });

        Scene equipmentScene = new Scene(gridPane, 400, 300);
        equipmentStage.setScene(equipmentScene);
        equipmentStage.show();
    }

    public void profile(String userEmail) {
        Stage profileStage = new Stage();
        profileStage.setTitle("User Profile");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));

        try {
            String selectQuery = "SELECT fname, lname, pass FROM person WHERE email = ?";
            PreparedStatement pst = conn.prepareStatement(selectQuery);
            pst.setString(1, userEmail);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("fname");
                String lastName = rs.getString("lname");
                String password = rs.getString("pass");

                Label fNameLabel = new Label("First Name:");
                Label lNameLabel = new Label("Last Name:");
                Label passLabel = new Label("Password:");

                TextField fNameField = new TextField(firstName);
                TextField lNameField = new TextField(lastName);
                PasswordField passField = new PasswordField();
                passField.setText(password);

                Button updateButton = new Button("Update");

                updateButton.setOnAction(e -> {
                    try {
                        String updateQuery = "UPDATE person SET fname = ?, lname = ?, pass = ? WHERE email = ?";
                        PreparedStatement updatePst = conn.prepareStatement(updateQuery);
                        updatePst.setString(1, fNameField.getText());
                        updatePst.setString(2, lNameField.getText());
                        updatePst.setString(3, passField.getText());
                        updatePst.setString(4, userEmail);
                        updatePst.executeUpdate();

                        System.out.println("Profile updated successfully!");
                        updatePst.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });

                gridPane.add(fNameLabel, 0, 0);
                gridPane.add(fNameField, 1, 0);
                gridPane.add(lNameLabel, 0, 1);
                gridPane.add(lNameField, 1, 1);
                gridPane.add(passLabel, 0, 2);
                gridPane.add(passField, 1, 2);
                gridPane.add(updateButton, 1, 3);
            }

            pst.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Scene profileScene = new Scene(gridPane, 400, 200);
        profileStage.setScene(profileScene);
        profileStage.show();
    }

    public void toDo(String mail) {
        List<String> classNames = new ArrayList<>();
        Map<String, String> classStatus = new HashMap<>();

        try {
            // Fetch class names and statuses for the given mail from person_classes and class_status tables
            String selectQuery = "SELECT pc.class_Name, cs.status FROM person_classes pc LEFT JOIN class_status cs ON pc.class_Name = cs.class_Name WHERE pc.mail = ?";
            PreparedStatement pst = conn.prepareStatement(selectQuery);
            pst.setString(1, mail);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String className = rs.getString("class_Name");
                String status = rs.getString("status");

                classNames.add(className);
                classStatus.put(className, status);
            }

            pst.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any SQL exception
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        int rowIndex = 0;

        for (String className : classNames) {
            CheckBox checkBox = new CheckBox(className);
            checkBox.setSelected("to do".equals(classStatus.getOrDefault(className, "not to do")));
            checkBox.setOnAction(event -> {
                String newStatus = checkBox.isSelected() ? "to do" : "not to do";
                updateClassStatus(className, newStatus);
            });
            grid.add(checkBox, 0, rowIndex);
            rowIndex++;
        }

        Scene scene = new Scene(grid, 400, 300);
        Stage stage = new Stage();
        stage.setTitle("To Do List");
        stage.setScene(scene);
        stage.show();
    }

    private void updateClassStatus(String className, String newStatus) {
        try {
            String updateQuery = "INSERT INTO class_status (class_Name, status) VALUES (?, ?) ON DUPLICATE KEY UPDATE status = ?";
            PreparedStatement updatePst = conn.prepareStatement(updateQuery);
            updatePst.setString(1, className);
            updatePst.setString(2, newStatus);
            updatePst.setString(3, newStatus);
            updatePst.executeUpdate();

            System.out.println("Status updated successfully!");
            updatePst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any SQL exception
        }
    }

}
