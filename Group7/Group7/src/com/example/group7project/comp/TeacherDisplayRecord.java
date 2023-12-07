package com.example.group7project.comp;

import java.io.FileReader;
import java.util.ArrayList;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TeacherDisplayRecord extends GridPane {

    public TeacherDisplayRecord(int studentIndex, int teacherIndex, String teacherAuthenFile, String studentRecordFile) {
        try {


            JSONParser parser = new JSONParser();

            // Parse the JSON string
            FileReader fileReader1 = new FileReader(teacherAuthenFile);
            JSONObject obj1 = (JSONObject) parser.parse(fileReader1);
            JSONArray subjectArray = (JSONArray) obj1.get("subjects");

            // Parse the JSON string
            FileReader fileReader2 = new FileReader(studentRecordFile);
            JSONObject obj2 = (JSONObject) parser.parse(fileReader2);
            JSONArray recordArray = (JSONArray) obj2.get("record");

            //
            // Get teacher's subject by index in JSONArray
            JSONArray sub = (JSONArray) subjectArray.get(teacherIndex);

            // Get student's all record by index in JSONArray
            JSONArray allRecord = (JSONArray) recordArray.get(studentIndex);

            // Append subjects allowed to edit in an Arraylist
            ArrayList<String> subjectsList = new ArrayList<String>();

            for (int i = 0; i <= sub.size() - 1; i++) {
                String item = (String) sub.get(i);
                subjectsList.add(item);
            }

            System.out.print(subjectsList);

            Label labelSub = new Label("Subject");
            labelSub.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 12));
            Label labelGrade = new Label("Grade");
            labelGrade.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 12));
            Label labelNote = new Label("Note");
            labelNote.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 12));


            // Create table
            addRow(0, labelSub, new Label("\t\t"), labelGrade, new Label("\t\t"), labelNote);

            //
            // For loop to check if subjects authorize to read and update
            for (int i = 0; i <= subjectsList.size() - 1; i++) {

                // check if subjects matches
                for (int j = 0; j <= allRecord.size() - 2; j += 2) {
                    // Typecast to String
                    String studentSubject = (String) allRecord.get(j);
                    String item = (String) subjectsList.get(i);

                    // if subject authorized to view and update
                    if (studentSubject.equals(item)) {
                        String subject = (String) allRecord.get(j);
                        String grade = (String) allRecord.get(j + 1);

                        addRow((i + 1), new Label(subject), new Label("\t\t"), new Label(grade), new Label("\t\t"), new Label(""));
                    }

                }
            }





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
