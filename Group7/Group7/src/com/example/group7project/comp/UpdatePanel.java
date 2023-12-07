package com.example.group7project.comp;

import java.io.FileReader;
import java.util.ArrayList;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class UpdatePanel extends GridPane {

    public UpdatePanel(int teacherIndex, int studentIndex, String JSON_FILE_PATH_TEACHER_AUTHEN, String JSON_FILE_PATH_ACADEMIC_RECORD) {

        String allGrade[] = { "-", "A+", "A", "A-", "B+", "B", "B-", "C", "D", "Fail" };

        ComboBox<String> leftDropDown = new ComboBox<>();
        ComboBox<String> rightDropDown = new ComboBox<>();

        try {

            JSONParser parser = new JSONParser();

            // Parse the JSON string
            FileReader fileReader1 = new FileReader(JSON_FILE_PATH_TEACHER_AUTHEN);
            JSONObject obj1 = (JSONObject) parser.parse(fileReader1);
            JSONArray allSubjects = (JSONArray) obj1.get("subjects");

            // Parse the JSON string
            FileReader fileReader2 = new FileReader(JSON_FILE_PATH_ACADEMIC_RECORD);
            JSONObject obj = (JSONObject) parser.parse(fileReader2);
            JSONArray recordArray = (JSONArray) obj.get("record");

            // Get item by index
            JSONArray subjects = (JSONArray) allSubjects.get(teacherIndex);

            // Get item by index
            JSONArray records = (JSONArray) recordArray.get(studentIndex);


            //
            // Append subjects allowed to edit in an Arraylist
            ArrayList<String> subjectsList = new ArrayList<String>();

            for (int i = 0; i <= subjects.size() - 1; i++) {
                String item = (String) subjects.get(i);
                subjectsList.add(item);
            }

            //
            // Add subjects allowed to edit to drop-down list
            for (int i = 0; i <= subjectsList.size() - 1; i++) {
                for (int j = 0; j <= records.size() - 1; j+=2) {
                    String subject = (String) subjectsList.get(i);
                    String item = (String) records.get(j);

                    if (subject.equals(item)) {
                        leftDropDown.getItems().add(subject);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Grades drop-down list
        for (int i = 0; i < allGrade.length; i++) {
            String eachGrade = allGrade[i];
            rightDropDown.getItems().add(eachGrade);
        }


        addRow(0, new Label(" "));

        Label title = new Label("   Select Subject :  ");
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 12));

        Label title2 = new Label("    Select Grade :  ");
        title2.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 12));

        addRow(1, title, leftDropDown);
        addRow(2,  title2, rightDropDown);





    }

}