package group7;

import javafx.application.Application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.chart.Axis;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.example.group7project.comp.AcademicRecord;
import com.example.group7project.comp.MyInfo;
import com.example.group7project.comp.StudentInfo;
import com.example.group7project.comp.TeacherDisplayRecord;
import com.example.group7project.comp.UpdatePanel;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import static javafx.scene.text.Font.*;
import static javafx.scene.text.FontWeight.*;

public class App extends Application {

    // Replace ${local path} to local file path of "StudentUser.json" file
    public static String JSON_FILE_PATH_STUDENT_AUTHEN = "C:/Users/user/Documents/NetBeansProjects/Group7/src/group7/StudentUser.json";

    // Replace ${local path} to local file path of "TeacherUser.json" file
    public static String JSON_FILE_PATH_TEACHER_AUTHEN = "C:/Users/user/Documents/NetBeansProjects/Group7/src/group7/TeacherUser.json";

    // Replace ${local path} to local file path of "PersonalRecord.json" file
    public static String JSON_FILE_PATH_PERSONAL_INFO = "C:/Users/user/Documents/NetBeansProjects/Group7/src/group7/PersonalInfo.json";

    // Replace ${local path} to local file path of "AcademicRecord.json" file
    public static String JSON_FILE_PATH_ACADEMIC_RECORD = "C:/Users/user/Documents/NetBeansProjects/Group7/src/group7/AcademicRecord.json";

    // User index in database "StudentUsers.json" file
    private static int userStudentIndex = -1;

    // User index in database "TeachersUsers.json" file
    private static int userTeacherIndex = -1;

    public static void main(String[] args) {
        launch(args);
    }

    //
    //
    // 1 Main
    @Override
    public void start(Stage primaryStage) {

        // Login username text field
        Label usernameLabel = new Label("Username: ");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        // Login password text field
        Label passwordLabel = new Label("Password: ");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.matches("^[s]\\d{8}$") && password.matches("[a-zA-Z0-9/!@_$^&*]+$") ||
                    username.matches("^[t]\\d{3}$") && password.matches("[a-zA-Z0-9/!@_$^&*]+$")) {
                System.out.println("Pass: " + "username: "  + username + "password: " + password);
                // pass input username and password to authenticate
                authenticateUser(primaryStage, username, password);
            }

            else if (!username.matches("^[s]\\d{8}$") || !username.matches("^[t]\\d{3}$") ||
                    !password.matches("[a-zA-Z0-9/!@_$^&*]+$") ) {
                System.out.println("\tInvalid: " + " username: " + username + "password: " + password);
                showErrorDialog(primaryStage);
            }

        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(usernameLabel, 0, 1); gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2); gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 2, 2);

        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setTitle("Log In");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Login authentication
    public void authenticateUser(Stage primaryStage, String username, String password) {

        // Student attempt login
        if (username.charAt(0) == 's') {
            try {
                File file_studentUser = new File(JSON_FILE_PATH_STUDENT_AUTHEN);
                if (!file_studentUser.exists()) {
                    System.err.println("Users.json file does not exist.");
                }
                FileReader fileReader = new FileReader(file_studentUser);
                StringBuilder jsonContent = new StringBuilder();
                int character;
                while ((character = fileReader.read()) != -1) {
                    jsonContent.append((char) character);
                }
                fileReader.close();

                JSONParser parser = new JSONParser();
                JSONObject usersJson = (JSONObject) parser.parse(jsonContent.toString());
                JSONArray studentID = (JSONArray) usersJson.get("userID");
                JSONArray studentPassword = (JSONArray) usersJson.get("password");

                for (int i = 0; i < studentID.size(); i++) {
                    String name = (String) studentID.get(i);
                    String pw = (String) studentPassword.get(i);

                    if (username.equals(name) && password.equals(pw)) {
                        // Update variable studentIndex
                        userStudentIndex = i;

                        // Logged In to Student Main()
                        showSuccessDialog("Student  " + username);
                        StudentMain(primaryStage, username, userStudentIndex, JSON_FILE_PATH_PERSONAL_INFO,
                                    JSON_FILE_PATH_ACADEMIC_RECORD, JSON_FILE_PATH_STUDENT_AUTHEN);

                    }

                }

                if (userStudentIndex == -1) {
                    showErrorDialog(primaryStage);
                }


            } catch (IOException | org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

        } //

        //
        //
        //
        // Teacher attempt login
        else if (username.charAt(0) == 't') {
            try {
                File file_studentUser = new File(JSON_FILE_PATH_TEACHER_AUTHEN);
                if (!file_studentUser.exists()) {
                    System.err.println("Users.json file does not exist.");
                }
                FileReader fileReader = new FileReader(file_studentUser);
                StringBuilder jsonContent = new StringBuilder();
                int character;
                while ((character = fileReader.read()) != -1) {
                    jsonContent.append((char) character);
                }
                fileReader.close();

                // Parse JSON Content
                JSONParser parser = new JSONParser();
                JSONObject usersJson = (JSONObject) parser.parse(jsonContent.toString());
                JSONArray teacherID = (JSONArray) usersJson.get("userID");
                JSONArray teacherPassword = (JSONArray) usersJson.get("password");

                for (int i = 0; i < teacherID.size(); i++) {
                    String name = (String) teacherID.get(i);
                    String pw = (String) teacherPassword.get(i);

                    if (username.equals(name) && password.equals(pw)) {
                        // Update teacherIndex
                        userTeacherIndex = i;

                        if (userTeacherIndex > -1) {
                            // Logged In to Teacher Main()
                            showSuccessDialog("Teacher  " + username);
                            TeacherMain(primaryStage, username, i , JSON_FILE_PATH_TEACHER_AUTHEN, JSON_FILE_PATH_ACADEMIC_RECORD);
                        }
                    }

                }

                if (userTeacherIndex == -1) {
                    showErrorDialog(primaryStage);
                }


            } catch (IOException | org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }
    }



    // Log In Success
    private void showSuccessDialog(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Authentication Successful");
        alert.setHeaderText("Login Successful");
        alert.setContentText("Welcome, " + username + "!");
        alert.showAndWait();
    }

    // Log In Failed
    private void showErrorDialog(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication Error");
        alert.setHeaderText("Login Failed");
        alert.setContentText(
                "Invalid Username or Password \n\nUsername starts with \"s\" or \"t\"   \nPassword: 6-8 digits ");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    // Back to Start()
                    App restart = new App();
                    restart.start(stage);
                });
    }

    //
    // Teacher Main
    private static void TeacherMain(Stage primaryStage, String username, int teacherIndex, String JSON_FILE_PATH_TEACHER_AUTHEN, String JSON_FILE_PATH_ACADEMIC_RECORD) {


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30, 30, 0, 120));
        Label title = new Label("Welcome, Teacher  " + username);
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));


        // Button: Update Students' Record
        Button buttonUpdate = new Button("Update Student's Record");
        buttonUpdate.setPadding(new Insets(10));
        buttonUpdate.setStyle(
                "-fx-background-color: transparent; -fx-background-radius: 10; -fx-border-color: brown; -fx-border-radius: 10; -fx-text-fill: brown;");
        buttonUpdate.setOnAction(event -> updateStudentRecords(primaryStage, username, teacherIndex,
                JSON_FILE_PATH_TEACHER_AUTHEN, JSON_FILE_PATH_ACADEMIC_RECORD));

        // Button: Logout
        Button buttonLogOut = new Button("Log Out");
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Return the User Log In Page
                App restart = new App();
                restart.start(primaryStage);
            }
        });

        //
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(140, 170, 0, 0));

        ObservableList list = vBox.getChildren();
        list.addAll(buttonUpdate);


        // Add item to Gridpane
        gridPane.add(title, 0, 0);
        gridPane.add(buttonLogOut, 3, 0);
        gridPane.add(vBox, 0, 1);


        Scene teacherScene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(teacherScene);
        primaryStage.setTitle("Teacher Main");

    }

    //
    // Student Main
    private static void StudentMain(Stage primaryStage, String username, int studentIndex,
                                    String JSON_FILE_PATH_PERSONAL_INFO, String JSON_FILE_PATH_ACADEMIC_RECORD,
                                    String JSON_FILE_PATH_STUDENT_AUTHEN) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30, 30, 0, 120));
        Label title = new Label("Welcome, Student  " + username);
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));

        // Button: ViewPersonalInfo
        Button buttonViewPersonalInfo = new Button("View / Edit Personal Info");
        buttonViewPersonalInfo.setPadding(new Insets(10));
        buttonViewPersonalInfo.setStyle(
                "-fx-background-color: transparent; -fx-background-radius: 30; -fx-border-color: brown; -fx-border-radius: 30; -fx-text-fill: brown;");

        buttonViewPersonalInfo
                .setOnAction(
                        event -> viewPersonalInfo(primaryStage, username, studentIndex, JSON_FILE_PATH_PERSONAL_INFO,
                                JSON_FILE_PATH_STUDENT_AUTHEN));

        // Button: ViewAcadRecord
        Button buttonViewAcadRecord = new Button("My Academic Record");
        buttonViewAcadRecord.setPadding(new Insets(10));
        buttonViewAcadRecord.setStyle(
                "-fx-background-color: transparent; -fx-background-radius: 30; -fx-border-color: black; -fx-border-radius: 30; -fx-text-fill: black;");

        buttonViewAcadRecord
                .setOnAction(event -> viewAcademicRecord(primaryStage, username, studentIndex,
                        JSON_FILE_PATH_ACADEMIC_RECORD));

        // Button: Logout
        Button buttonLogOut = new Button("Log Out");
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Navigate back to the start() method
                App restart = new App();
                restart.start(primaryStage);
            }
        });

        //
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(140, 170, 0, 0));
        vBox.setSpacing(50);

        ObservableList list = vBox.getChildren();
        list.addAll(buttonViewPersonalInfo,buttonViewAcadRecord);


        gridPane.add(title, 0, 0);
        gridPane.add(vBox, 0, 1);
        gridPane.add(buttonLogOut, 1, 0);

        Scene studentScene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(studentScene);
        primaryStage.setTitle("Student  " + username);
    }

    // Search Student Index
    private static int searchStudent(String studentID) {

        // Initialize target student index
        int searchStudent = -1;

        try {
            File file = new File(JSON_FILE_PATH_ACADEMIC_RECORD);
            if (!file.exists()) {
                System.err.println("Users.json file does not exist.");
            }
            FileReader fileReader = new FileReader(file);
            StringBuilder jsonContent = new StringBuilder();
            int character;
            while ((character = fileReader.read()) != -1) {
                jsonContent.append((char) character);
            }

            fileReader.close();

            JSONParser parser = new JSONParser();
            JSONObject usersJSON = (JSONObject) parser.parse(jsonContent.toString());
            JSONArray userID = (JSONArray) usersJSON.get("userID");

            for (int i = 0; i < userID.size(); i++) {
                String username = (String) userID.get(i);

                if (studentID.equals(username)) {
                    // update target student index
                    searchStudent = i;
                }
            }

        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        return searchStudent;
    }

    // Update Student's Records
    private static void updateStudentRecords(Stage primaryStage, String username, int teacherIndex,
                                      String JSON_FILE_PATH_TEACHER_AUTHEN, String JSON_FILE_PATH_ACADEMIC_RECORD) {

        GridPane gridPane = new GridPane();
        Label title = new Label("Search Student ID: ");
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
        title.setPadding(new Insets(30));

        // Textfiele to search
        TextField field1 = new TextField();
        field1.setPadding(new Insets(10));
        field1.setStyle("-fx-border-color: blue; -fx-padding: 5px; -fx-border-insets: 35px; -fx-background-insets: 35px;");

        // Button: Search
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {

            String studentIDInput = field1.getText();

            // RegExp: check if studentIDInput invalid
            if (studentIDInput.matches("^s\\d{8}$")) {
                // if studentIDInput is valid
                int studentIndex = searchStudent(studentIDInput);

                // Navigate to Student Academic Record
                displayAcadRecord(primaryStage, studentIDInput, studentIndex, username, teacherIndex,
                        JSON_FILE_PATH_TEACHER_AUTHEN, JSON_FILE_PATH_ACADEMIC_RECORD);

            }

            else {
                // Display an error dialog
                Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
                confirmationDialog.setTitle("Error");
                confirmationDialog.setHeaderText(
                        "Invalid Student ID ");
                confirmationDialog.setContentText(
                        " [ Hints:   First character : \"s\", followed by 8 digits.  ]  \n\n\nTry Again Please.  ");
                confirmationDialog.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            updateStudentRecords(primaryStage, username, teacherIndex, JSON_FILE_PATH_TEACHER_AUTHEN,
                                    JSON_FILE_PATH_ACADEMIC_RECORD);
                        });
            }

        });

        // Button: Logout
        Button buttonLogOut = new Button("Log Out");
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Return to User Log In Page
                App restart = new App();
                restart.start(primaryStage);
            }
        });

        // Add items to the Gridpane

        gridPane.getChildren().add(title);
        gridPane.add(buttonLogOut, 3, 0);
        gridPane.add(field1, 0, 1);
        gridPane.add(searchButton, 2, 1);

        Scene studentScene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(studentScene);
        primaryStage.setTitle("Search by Student ID");

    }

    // Student Academic Record
    public void studentAcademicRecord(Stage primaryStage, int teacherIndex, String username, String studentID,
                                      int studentIndex, String JSON_FILE_PATH_TEACHER_AUTHEN, String JSON_FILE_PATH_ACADEMIC_RECORD) {

        Label title = new Label("Student   " + studentID + "  Academic Record");
        title.setPadding(new Insets(80));
        GridPane gridpane = new GridPane();

        // Student Info Panel
        StudentInfo studentInfo = new StudentInfo(studentIndex, JSON_FILE_PATH_PERSONAL_INFO);

        // Academic Record Panel
        AcademicRecord academicRecord = new AcademicRecord(studentIndex, JSON_FILE_PATH_ACADEMIC_RECORD);
        academicRecord.setPadding(new Insets(0, 10,0,80));

        // Grade Select Panel
        UpdatePanel selector = new UpdatePanel(teacherIndex, studentIndex, JSON_FILE_PATH_TEACHER_AUTHEN, JSON_FILE_PATH_ACADEMIC_RECORD);

        // Logout Button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> {
            App restart = new App();
            restart.start(primaryStage);
        });

        // Update Button
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {

            ComboBox<String> readSubject = (ComboBox<String>) selector.getChildren().get(1);
            ComboBox<String> readGrade = (ComboBox<String>) selector.getChildren().get(3);

            // TextField input
            String subject = readSubject.getValue();
            String grade = readGrade.getValue();

            // Update local .json file
            updateSubjectGrade(studentIndex, subject, grade, JSON_FILE_PATH_PERSONAL_INFO);

            // Display a confirmation dialog
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Student's Record has been saved");
            confirmationDialog.setContentText("Back ?");
            confirmationDialog.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        // Back to Student Main()
                        System.out.println(" \t Back to Teacher Main == ");
                        TeacherMain(primaryStage, username, teacherIndex,  JSON_FILE_PATH_TEACHER_AUTHEN,
                                JSON_FILE_PATH_ACADEMIC_RECORD);
                    });
        });

        // Add items to Gridpane
        gridpane.getChildren().add(title);
        gridpane.add(logoutButton, 3, 0);
        gridpane.add(studentInfo, 1, 1);
        gridpane.add(academicRecord, 1, 2);
        gridpane.add(selector, 1, 3);
        gridpane.add(updateButton, 1, 4);

        Scene teacherScene = new Scene(gridpane, 900, 800);
        primaryStage.setScene(teacherScene);
        primaryStage.setTitle("Student Records \t" + studentID);
    }

    // Update Student's Grade
    public static void updateSubjectGrade(int studentIndex, String subject, String grade, String JSON_FILE_PATH_ACADEMIC_RECORD) {
        try {
            // Parse the JSON String
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(JSON_FILE_PATH_ACADEMIC_RECORD);
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

            // Get JSONArray from jsonObject
            JSONArray academicRecord = (JSONArray) jsonObject.get("record");
            JSONArray userID = (JSONArray) jsonObject.get("userID");

            // Get target student's JSONArray "record"
            JSONArray update = (JSONArray) academicRecord.get(studentIndex);

            // Iterate over the Item and add rows to the model
            for (int i = 0; i < update.size(); i += 2) {
                String item = (String) update.get(i);

                if (item.equals(subject)) {

                    // Append new grade with subject to JSONArray
                    update.add(subject);
                    update.add(grade);

                    // Remove old data in JSONArray
                    update.remove(i);
                    update.remove(i);
                }
            }

            // Append new record to old JSONArray
            academicRecord.add(studentIndex + 1, update);

            // Remove old student's academic record
            academicRecord.remove(studentIndex);

            // Save updated array to the JSON Object
            JSONObject updateRecords = new JSONObject();
            updateRecords.put("userID", userID);
            updateRecords.put("record", academicRecord);

            FileWriter fileWriter = new FileWriter(JSON_FILE_PATH_ACADEMIC_RECORD);
            fileWriter.write(updateRecords.toJSONString());
            fileWriter.flush();

            System.out.println(" \tFileWriter: JSON file saved successfully.");

            System.out.println("\n  - Changes has been saved. -  \n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    //
    // ----------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------
    // Personal Information

    // View Personal Info
    public static void viewPersonalInfo(Stage primaryStage, String username, int studentIndex,
                                        String JSON_FILE_PATH_PERSONAL_INFO, String JSON_FILE_PATH_STUDENT_AUTHEN) {

        GridPane gridpane = new GridPane();
        Label title = new Label("Personal Information " + username);
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
        title.setPadding(new Insets(30));

        // Student Info Panel
        MyInfo infoPane = new MyInfo(studentIndex, JSON_FILE_PATH_PERSONAL_INFO, JSON_FILE_PATH_STUDENT_AUTHEN);
        infoPane.setPadding(new Insets(30));

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setStyle(
                "-fx-font-size: 13; -fx-background-color: transparent; -fx-background-radius: 20; -fx-border-color: brown; -fx-border-radius: 20; -fx-text-fill: brown;");
        saveButton.setOnAction(event -> {
            System.out.println(" Save ");
            String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z0-9.-]+)*\\.[a-zA-Z]{2,}$";
            String patternTelNo = "^[0-9]{7,14}$";
            String patternPassword = "^[a-zA-Z0-9][0-9?/!@_$^&*]{5,7}$";

            // Textfields
            String tfnewEmail = ((TextField) infoPane.getChildren().get(7)).getText();
            String tfPhoneNumber = ((TextField) infoPane.getChildren().get(9)).getText();
            String tfnewPassword = ((TextField) infoPane.getChildren().get(11)).getText();

            if ( tfnewEmail.matches(patternEmail) &&
                    tfPhoneNumber.matches(patternTelNo) &&
                    tfnewPassword.matches("^[a-zA-Z0-9][0-9?/!@_$^&*]{5,7}$") ) {

                updateMyInfo(primaryStage, tfnewEmail, tfPhoneNumber, tfnewPassword, username, studentIndex,
                        JSON_FILE_PATH_PERSONAL_INFO, JSON_FILE_PATH_STUDENT_AUTHEN);
            }


            // Invalid Email Input
            else if ( !tfnewEmail.matches(patternEmail) ) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Invalid Email Address");
                alert.setContentText(
                        "Enter one or more alphanumeric characters or ,\nSymbol: @ + - _ . ");
                alert.showAndWait();
            }

            // Invalid Tel No Input
            else if ( !tfPhoneNumber.matches(patternTelNo)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Invalid Telephone Number");
                alert.setContentText(
                        "Enter valid characters: [ 0-9 - ]");
                alert.showAndWait();
            }

            // Invalid Password
            else if ( !tfnewPassword.matches(patternPassword)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Invalid New Password");
                alert.setContentText(
                        "Enter 6-8 Digits\nFirst Digit:  Alphabetical [a-zA-Z0-9] \nSymbol: ? / ! @ _ $ ^ & *  ");
                alert.showAndWait();
            }

        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            StudentMain(primaryStage, username, studentIndex, JSON_FILE_PATH_PERSONAL_INFO,
                    JSON_FILE_PATH_ACADEMIC_RECORD, JSON_FILE_PATH_STUDENT_AUTHEN);
        });

        gridpane.add(title, 0, 0);
        gridpane.add(infoPane, 0, 1);
        gridpane.add(saveButton, 1, 1);
        gridpane.add(backButton, 5, 0);

        Scene studentScene = new Scene(gridpane, 600, 500);
        primaryStage.setScene(studentScene);
        primaryStage.setTitle("My Personal Information");
    }

    // Update Personal Information
    public static void updateMyInfo(Stage primaryStage, String updatedEmail, String updatedPhoneNumber,
                                    String updatedPassword, String username, int studentIDIndex, String JSON_FILE_PATH_PERSONAL_INFO,
                                    String JSON_FILE_PATH_STUDENT_AUTHEN) {

        // Update JSON Files
        try {
            // Parse the JSON String
            JSONParser parser = new JSONParser();
            FileReader fileReader1 = new FileReader(JSON_FILE_PATH_PERSONAL_INFO);
            JSONObject data = (JSONObject) parser.parse(fileReader1);

            // Get JSONArray from JSONObject (PersonalRecord.json)
            JSONArray studentID = (JSONArray) data.get("userID");
            JSONArray name = (JSONArray) data.get("name");
            JSONArray gender = (JSONArray) data.get("gender");
            JSONArray phonenumber = (JSONArray) data.get("phonenumber");
            JSONArray email = (JSONArray) data.get("email");

            // Get JSONArray from JSONObject (studentUsers.json)
            FileReader fileReader2 = new FileReader(JSON_FILE_PATH_STUDENT_AUTHEN);
            JSONObject loginData = (JSONObject) parser.parse(fileReader2);
            JSONArray userID = (JSONArray) loginData.get("userID");
            JSONArray password = (JSONArray) loginData.get("password");

            // Append new array and remove old data
            email.add(studentIDIndex + 1, updatedEmail);
            email.remove(studentIDIndex);

            phonenumber.add(studentIDIndex + 1, updatedPhoneNumber);
            phonenumber.remove(studentIDIndex);

            password.add(studentIDIndex + 1, updatedPassword);
            password.remove(studentIDIndex);

            // Save updated JSONObject to the PersonalRecord.json file
            JSONObject updatedPersonalRecord = new JSONObject();
            updatedPersonalRecord.put("userID", studentID);
            updatedPersonalRecord.put("name", name);
            updatedPersonalRecord.put("gender", gender);
            updatedPersonalRecord.put("email", email);
            updatedPersonalRecord.put("phonenumber", phonenumber);

            // Save updated JSONObject to the StudentUsers.json file
            JSONObject updatedUserData = new JSONObject();
            updatedUserData.put("userID", userID);
            updatedUserData.put("password", password);

            // Replace old PersonalRecord.json, sava a new .json file
            try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH_PERSONAL_INFO)) {
                fileWriter.write(updatedPersonalRecord.toJSONString());
                fileWriter.flush();
                System.out.println("\tJSON file saved successfully.  1 ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Replace old StudentUsers.json, sava a new .json file
            try (FileWriter fileWriter = new FileWriter(JSON_FILE_PATH_STUDENT_AUTHEN)) {
                fileWriter.write(updatedUserData.toJSONString());
                fileWriter.flush();
                System.out.println("\tJSON file saved successfully.   2 ");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Display a confirmation dialog
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Change(s) has been saved");
        confirmationDialog.setContentText("Back ?");
        confirmationDialog.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    // Back to Student Main()
                    System.out.println(" \t Back to Main: ");
                    StudentMain(primaryStage, username, userStudentIndex, JSON_FILE_PATH_PERSONAL_INFO,
                            JSON_FILE_PATH_ACADEMIC_RECORD,
                            JSON_FILE_PATH_STUDENT_AUTHEN);
                });

    };

    // View Academic Record
    public static void viewAcademicRecord(Stage primaryStage, String username, int studentIndex,
                                          String JSON_FILE_PATH_ACADEMIC_RECORD) {

        GridPane gridPane = new GridPane();
        Label title = new Label("My Academic Record  " + username);
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
        title.setPadding(new Insets(30));

        // Record Panel
        AcademicRecord record = new AcademicRecord(studentIndex, JSON_FILE_PATH_ACADEMIC_RECORD);
        record.setPadding(new Insets(0, 10,0,80));

        // Back Button
        Button backButton = new Button("Back");

        backButton.setOnAction(event -> {
            StudentMain(primaryStage, username, studentIndex, JSON_FILE_PATH_PERSONAL_INFO,
                    JSON_FILE_PATH_ACADEMIC_RECORD, JSON_FILE_PATH_STUDENT_AUTHEN);
        });

        // Add items to the Gridpane
        gridPane.getChildren().add(title);
        gridPane.add(backButton, 3, 0);
        gridPane.add(record, 0, 1);

        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Academic Record  " + username);

    }

    public static void displayAcadRecord(Stage primaryStage, String studentID, int studentIndex, String username,
                                         int teacherIndex, String JSON_FILE_PATH_TEACHER_AUTHEN, String JSON_FILE_PATH_ACADEMIC_RECORD) {

        GridPane gridpane = new GridPane();
        Label title = new Label("Student Record  " + studentID);
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
        title.setPadding(new Insets(30));

        // Student Info Panel
        StudentInfo studentInfo = new StudentInfo(studentIndex, JSON_FILE_PATH_PERSONAL_INFO);
        studentInfo.setPadding(new Insets(0, 10,0,80));

        // Academic Record Panel
        TeacherDisplayRecord recordPanel = new TeacherDisplayRecord(studentIndex, teacherIndex,
                JSON_FILE_PATH_TEACHER_AUTHEN, JSON_FILE_PATH_ACADEMIC_RECORD);
        recordPanel.setPadding(new Insets(0, 10,0,80));


        // Grade Select Panel
        UpdatePanel selector = new UpdatePanel(teacherIndex, studentIndex, JSON_FILE_PATH_TEACHER_AUTHEN, JSON_FILE_PATH_ACADEMIC_RECORD);
        selector.setPadding(new Insets(0, 10,0,80));

        // Logout Button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> {
            App restart = new App();
            restart.start(primaryStage);
        });

        // Update Button
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {

            ComboBox<String> readSubject = (ComboBox<String>) selector.getChildren().get(2);
            ComboBox<String> readGrade = (ComboBox<String>) selector.getChildren().get(4);

            // TextField input
            String subject = readSubject.getValue();
            String grade = readGrade.getValue();

            if (!subject.equals("-") && !grade.equals("-")) {
                // Update local .json file
                updateSubjectGrade(studentIndex, subject, grade, JSON_FILE_PATH_ACADEMIC_RECORD);

                // Display a confirmation dialog
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirmation");
                confirmationDialog.setHeaderText("Student's Record has been saved");
                confirmationDialog.setContentText("Back ?");
                confirmationDialog.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            // Back to Student Main()
                            TeacherMain(primaryStage, username, teacherIndex, JSON_FILE_PATH_TEACHER_AUTHEN,
                                    JSON_FILE_PATH_ACADEMIC_RECORD);
                        });

            }

            else  {
                // Display an error dialog
                Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
                confirmationDialog.setTitle("Error");
                confirmationDialog.setHeaderText(
                        "Invalid Input of Grade ");
                confirmationDialog.setContentText(
                        " Please input a valid grade.");
                confirmationDialog.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            displayAcadRecord(primaryStage, studentID, studentIndex, username,
                            teacherIndex,JSON_FILE_PATH_TEACHER_AUTHEN, JSON_FILE_PATH_ACADEMIC_RECORD);
                        });
            }



        });



        // Back Button
        Button backButton = new Button("Back");

        backButton.setOnAction(event -> {
            TeacherMain(primaryStage, username, teacherIndex, JSON_FILE_PATH_TEACHER_AUTHEN,
                    JSON_FILE_PATH_ACADEMIC_RECORD);
        });

        // Add items to the Gridpane
        gridpane.getChildren().add(title);
        gridpane.add(backButton, 2, 0);
        gridpane.add(logoutButton, 3, 0);
        gridpane.add(studentInfo, 0, 1);
        gridpane.add(new Label(" "), 0, 2);
        gridpane.add(new Label(" "), 0, 3);
        gridpane.add(recordPanel, 0, 4);
        gridpane.add(selector, 0, 5);
        gridpane.add(updateButton, 1, 5);

        Scene scene = new Scene(gridpane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Record   " + studentID);

    }

}