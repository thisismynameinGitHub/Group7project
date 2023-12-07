package com.example.group7project.comp;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MyInfo extends GridPane {

    public MyInfo(int studentIndex, String JSON_FILE_PATH_PERSONAL_INFO, String JSON_FILE_PATH_STUDENT_AUTHEN) {



        try {
            // Parse the JSON Object
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(JSON_FILE_PATH_PERSONAL_INFO);
            JSONObject obj = (JSONObject) parser.parse(fileReader);

            JSONArray userIDArray = (JSONArray) obj.get("userID");
            JSONArray nameArray = (JSONArray) obj.get("name");
            JSONArray genderArray = (JSONArray) obj.get("gender");
            JSONArray emailAddressArray = (JSONArray) obj.get("email");
            JSONArray phonenumberArray = (JSONArray) obj.get("phonenumber");

            FileReader fileReader2 = new FileReader(JSON_FILE_PATH_STUDENT_AUTHEN);
            JSONObject obj2 = (JSONObject) parser.parse(fileReader2);
            JSONArray passwordArray = (JSONArray) obj2.get("password");

            // Add rows to the GridPane
            String studentId = (String) userIDArray.get(studentIndex);
            String name = (String) nameArray.get(studentIndex);
            String gender = (String) genderArray.get(studentIndex);
            String emailAddress = (String) emailAddressArray.get(studentIndex);
            String phonenumber = (String) phonenumberArray.get(studentIndex);
            String password = (String) passwordArray.get(studentIndex);

            //
            //
            // Create Item Labels, set disabled

            Label studentIdLabel = new Label("Student ID:   ");
            Label nameLabel = new Label("Name: ");
            Label genderLabel = new Label("Gender:   ");
            Label emailAddressLabel = new Label("Email Address:   ");
            Label phonenumberLabel = new Label("Phone Number:   ");
            Label passwordLabel = new Label("Password:   ");

            //
            // Create Textfields
            TextField plhdstudentId = new TextField(studentId);
            plhdstudentId.setDisable(true);

            TextField plhdname = new TextField(name);
            plhdname.setDisable(true);

            TextField plhdgender = new TextField(gender);
            plhdgender.setDisable(true);

            TextField plhdemailAddress = new TextField(emailAddress);
            plhdemailAddress.setDisable(true);

            TextField plhdphonenumber = new TextField(phonenumber);
            plhdphonenumber.setDisable(true);

            TextField plhdpassword = new TextField(password);
            plhdpassword.setDisable(true);

            //
            // Add rows to the GridPane

            // "Edit" Button to Enable textfields
            Button updateButton = new Button("Edit");
            updateButton.setStyle(
                    "-fx-font-size: 13; -fx-background-color: transparent; -fx-background-radius: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-text-fill: black;");
            updateButton.setOnAction(event -> {
                plhdemailAddress.setDisable(false);
                plhdphonenumber.setDisable(false);
                plhdpassword.setDisable(false);
            });


            addRow(0, studentIdLabel, plhdstudentId);
            addRow(1, nameLabel, plhdname);
            addRow(2, genderLabel, plhdgender);
            addRow(3, emailAddressLabel, plhdemailAddress); // 7
            addRow(4, phonenumberLabel, plhdphonenumber); // 9
            addRow(5, passwordLabel, plhdpassword); // 11

            add(updateButton, 3, 5); // 13


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}