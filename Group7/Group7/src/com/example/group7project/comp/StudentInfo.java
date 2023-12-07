package com.example.group7project.comp;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StudentInfo extends GridPane {

    public StudentInfo(int studentIndex, String JSON_FILE_PATH_PERSONAL_INFO) {

        try {
            // Parse the JSON string
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(JSON_FILE_PATH_PERSONAL_INFO);
            JSONObject obj = (JSONObject) parser.parse(fileReader);

            JSONArray userIDArray = (JSONArray) obj.get("userID");
            JSONArray nameArray = (JSONArray) obj.get("name");
            JSONArray genderArray = (JSONArray) obj.get("gender");
            JSONArray emailAddressArray = (JSONArray) obj.get("email");
            JSONArray phonenumberArray = (JSONArray) obj.get("phonenumber");

            Label userIDLabel = new Label("Student ID:   ");
            Label nameLabel = new Label("Name: ");
            Label genderLabel = new Label("Gender:   ");
            Label emailAddressLabel = new Label("Email Address:   ");
            Label phonenumberLabel = new Label("Phone Number:   ");

            // Typecast
            String id = (String) userIDArray.get(studentIndex);
            String name = (String) nameArray.get(studentIndex);
            String gender = (String) genderArray.get(studentIndex);
            String emailAddress = (String) emailAddressArray.get(studentIndex);
            String phonenumber = (String) phonenumberArray.get(studentIndex);

            // Add rows
            addRow(0, userIDLabel, new Label(id));
            addRow(1, nameLabel, new Label(name));
            addRow(2, genderLabel, new Label(gender));
            addRow(3, emailAddressLabel, new Label(emailAddress));
            addRow(4, phonenumberLabel, new Label(phonenumber));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}