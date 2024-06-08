package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import javax.swing.*;


public class SignInSignUp extends Application {

    Scene scene1 , scene2;
    Stage window;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private TextField userNameTextField =  new TextField();
    private TextField userTextField = new TextField();;
    private TextField pwBox = new TextField();;
    private TextField cpwBox = new TextField();;
    public void Add_users (String userName, String email, String password, String confirmPassword){
        conn = MysqlCon.ConnectDb();
        String sql = "insert into gym (username, gym_email, password1, password2)values(?,?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, confirmPassword);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Username: " + userName + " successfully signed up");
            userNameTextField.clear();
            userTextField.clear();
            pwBox.clear();
            cpwBox.clear();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    boolean logIn (String email, String password){
        conn = MysqlCon.ConnectDb();
        String sql = "select count(*) as count from gym where  gym_email = '" +email+ "' and password1 = '" + password + "'";
        try {
            int count = 0;
            System.out.println("Hadi sql " + sql);
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            // JOptionPane.showMessageDialog(null, "Email: " + email + " successfully signed up");
            while (rs.next()){
                count = rs.getInt("count");
            }
           //  System.out.println("charbel " + count);
            if(count == 0){
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    boolean logIn2 (String email, String password){
        conn = MysqlCon.ConnectDb();
        String sql = "select count(*) as count from person where email = '" +email+ "' and pass = '" + password + "'";
        try {
            int count = 0;
            System.out.println("Hadi sql " + sql);
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            // JOptionPane.showMessageDialog(null, "Email: " + email + " successfully signed up");
            while (rs.next()){
                count = rs.getInt("count");
            }
            //  System.out.println("charbel " + count);
            if(count == 0){
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        MainPage mainPage = new MainPage();
MainClient mainClient=new MainClient();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome to the gym industry: ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0 , 3, 2);

        Label userName1 = new Label("Username: ");
        grid.add(userName1, 0, 2);

        TextField userNameTextField = new TextField();
        grid.add(userNameTextField, 1, 2);

        Label userName = new Label("Email:");
        grid.add(userName, 0, 3);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 3);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 4);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 4);

        Label cpw = new Label("Confirm Password:");
        grid.add(cpw, 0,5);

        PasswordField cpwBox = new PasswordField();
        grid.add(cpwBox, 1, 5);

        Button signIn = new Button("Already have an account? Signin here");
        grid.add(signIn, 0, 6);

        signIn.setOnAction(e -> window.setScene(scene2));

        Button btn = new Button("Sign up");
        btn.setDefaultButton(true);
        btn.setOnAction(e-> mainPage.mainPage());
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                Add_users();
//            }
//        });


        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);

        Image logoImage = new Image("file:C:/Users/user/Desktop/oopProj/logo.png");
        ImageView imageView = new ImageView(logoImage);
        imageView.setFitWidth(100); // Adjust the width of the image as needed
        imageView.setFitHeight(100);

        HBox imageBox = new HBox(10); // Set spacing between image and components
        imageBox.getChildren().addAll(grid, imageView); // Add the image to the HBox
        imageBox.setAlignment(Pos.CENTER);

        scene1 = new Scene(imageBox, 450, 275);

        scene1.getStylesheets().add(getClass().getResource("/com/example/demo/styles.css").toExternalForm());
        btn.getStyleClass().add("red-button");
        signIn.getStyleClass().add("red-button");

        window.setScene(scene1);
        window.setTitle("Gym Management System");
        window.show();



        userNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            final Pattern VALID_USERNAME_REGEX =
                    Pattern.compile("^[a-zA-Z0-9]([.-](?![.-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_USERNAME_REGEX.matcher(newValue);

            if(matcher.matches() == false) {
                userNameTextField.setStyle("-fx-text-inner-color: red;");
            }
            else {
                userNameTextField.setStyle("-fx-text-inner-color: green;");
            }
            if(newValue.equalsIgnoreCase("")) {
                userNameTextField.setStyle("-fx-text-inner-color: black;");
            }

        });

        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            final Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(newValue);

            if(matcher.matches() == false) {
                userTextField.setStyle("-fx-text-inner-color: red;");
            }
            else {
                userTextField.setStyle("-fx-text-inner-color: green;");
            }
            if(newValue.equalsIgnoreCase("")) {
                userTextField.setStyle("-fx-text-inner-color: black;");
            }

        });

        pwBox.textProperty().addListener((observable, oldValue, newValue) -> {
            final Pattern VALID_PASSWORD_REGEX =
                    Pattern.compile("[0-9a-zA-Z]{8,}", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_PASSWORD_REGEX.matcher(newValue);
            if(matcher.matches() == false) {
                pwBox.setStyle("-fx-text-inner-color: red;");
            }
            else {
                pwBox.setStyle("-fx-text-inner-color: green;");
            }
            if(newValue.equalsIgnoreCase("")) {
                pwBox.setStyle("-fx-text-inner-color: black;");
            }

        });

        cpwBox.textProperty().addListener((observable, oldValue, newValue) -> {
            final Pattern VALID_PASSWORD_REGEX =
                    Pattern.compile("[0-9a-zA-Z]{8,}", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_PASSWORD_REGEX.matcher(newValue);
            if(matcher.matches() == false) {
                cpwBox.setStyle("-fx-text-inner-color: red;");
            }
            else {
                cpwBox.setStyle("-fx-text-inner-color: green;");
            }
            if(newValue.equalsIgnoreCase("")) {
                cpwBox.setStyle("-fx-text-inner-color: black;");
            }
            if(!newValue.equals(pwBox.getText())){
                cpwBox.setStyle("-fx-text-inner-color: red;");

            }
            else{
                cpwBox.setStyle("-fx-text-inner-color: green;");

            }
            if(newValue.equalsIgnoreCase("")) {
                cpwBox.setStyle("-fx-text-inner-color: black;");
            }


        });


        btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String cPassword = cpwBox.getText();
                String password = pwBox.getText();
                final Pattern VALID_EMAIL_ADDRESS_REGEX =
                        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcherUsr = VALID_EMAIL_ADDRESS_REGEX.matcher(userTextField.getText());
                final Pattern VALID_PASSWORD_REGEX =
                        Pattern.compile("[0-9a-zA-Z]{8,}", Pattern.CASE_INSENSITIVE);
                Matcher matcherPwd = VALID_PASSWORD_REGEX.matcher(pwBox.getText());

                if(matcherUsr.matches() == false || matcherPwd.matches() == false || !cPassword.equals(password)) {
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("Please use valid email address or password");
                }else {
                    actiontarget.setFill(Color.GREEN);
                    actiontarget.setText("SignUp successful!");
                    Add_users(userNameTextField.getText(), userTextField.getText(), pwBox.getText(), cpwBox.getText());
                    window.setScene(mainPage.mainPage());
                }
                if( !cPassword.equals(password)) {
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("Password and Confirm password don't match");
                }

            }
        });



        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle2 = new Text("Welcome to your gym: ");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid2.add(scenetitle2, 0, 0, 2, 1);


        Label userName2 = new Label("Email:");
        grid2.add(userName2, 0, 2);

        TextField userTextField2 = new TextField();
        grid2.add(userTextField2, 1, 2);

        Label pw2 = new Label("Password:");
        grid2.add(pw2, 0, 4);

        PasswordField pwBox2 = new PasswordField();
        grid2.add(pwBox2, 1, 4);

        Button signUp2 = new Button("Don't have an account? Sign UP here!");
        grid2.add(signUp2, 0, 7);

        signUp2.setOnAction(e -> window.setScene(scene1));

        Button btn2 = new Button("Sign in");
        btn2.setDefaultButton(true);
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);
        grid2.add(hbBtn2, 1, 7);

        final Text actiontarget2 = new Text();
        grid2.add(actiontarget2, 1, 8);

        Image logoImage2 = new Image("file:C:/Users/user/Desktop/oopProj/logo.png");
        ImageView imageView2 = new ImageView(logoImage2);
        imageView2.setFitWidth(100); // Adjust the width of the image as needed
        imageView2.setFitHeight(100);

        HBox imageBox2 = new HBox(10); // Set spacing between image and components
        imageBox2.getChildren().addAll(grid2, imageView2); // Add the image to the HBox
        imageBox2.setAlignment(Pos.CENTER);

        scene2 = new Scene(imageBox2, 450, 275);

        scene2.getStylesheets().add(getClass().getResource("/com/example/demo/styles.css").toExternalForm());
        btn2.getStyleClass().add("red-button");
        signUp2.getStyleClass().add("red-button");

        userTextField2.textProperty().addListener((observable, oldValue, newValue) -> {
            final Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(newValue);

            if(matcher.matches() == false) {
                userTextField2.setStyle("-fx-text-inner-color: red;");
            }
            else {
                userTextField2.setStyle("-fx-text-inner-color: green;");
            }
            if(newValue.equalsIgnoreCase("")) {
                userTextField2.setStyle("-fx-text-inner-color: black;");
            }

        });

        btn2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                final Pattern VALID_EMAIL_ADDRESS_REGEX =
                        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcherUsr = VALID_EMAIL_ADDRESS_REGEX.matcher(userTextField2.getText());
                final Pattern VALID_PASSWORD_REGEX =
                        Pattern.compile("[0-9a-zA-Z]{8,}", Pattern.CASE_INSENSITIVE);
                Matcher matcherPwd = VALID_PASSWORD_REGEX.matcher(pwBox2.getText());

                if(matcherUsr.matches() == false || matcherPwd.matches() == false) {
                    actiontarget2.setFill(Color.RED);
                    actiontarget2.setText("Please use valid email address or password");
                }else if( logIn(userTextField2.getText(), pwBox2.getText())){
                    actiontarget2.setFill(Color.GREEN);
                    actiontarget2.setText("SignIn successful!");
                    window.setScene(mainPage.mainPage());
                }else if( logIn2(userTextField2.getText(), pwBox2.getText())){
                    actiontarget2.setFill(Color.GREEN);
                    actiontarget2.setText("SignIn as client successful!");
                    window.setScene(mainClient.mainClient(userTextField2.getText()));
                }

                else{
                    actiontarget2.setFill(Color.RED);
                    actiontarget2.setText("Username or password incorrect");
                }


            }
        });

        pwBox2.textProperty().addListener((observable, oldValue, newValue) -> {
            final Pattern VALID_PASSWORD_REGEX =
                    Pattern.compile("[0-9a-zA-Z]{8,}", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_PASSWORD_REGEX.matcher(newValue);
            if(matcher.matches() == false) {
                pwBox2.setStyle("-fx-text-inner-color: red;");
            }
            else {
                pwBox2.setStyle("-fx-text-inner-color: green;");
            }
            if(newValue.equalsIgnoreCase("")) {
                pwBox2.setStyle("-fx-text-inner-color: black;");
            }

        });



    }

    public static void main(String[] args) {
        launch(args);
    }
}