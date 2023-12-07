package com.example.group7project.comp;

import java.io.FileReader;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class AcademicRecord extends GridPane {

    public AcademicRecord(int studentIndex, String studentRecordFile) {
        try {
            // Parse the JSON string
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(studentRecordFile);
            JSONObject obj = (JSONObject) parser.parse(fileReader);

            // Get record by JSONObject key "record"
            JSONArray record = (JSONArray) obj.get("record");

            // Get student's record by index in JSONArray
            JSONArray array = (JSONArray) record.get(studentIndex);

            Label labelSub = new Label("Subject");
            labelSub.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 12));
            Label labelGrade = new Label("Grade");
            labelGrade.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 12));
            Label labelNote = new Label("Note");
            labelNote.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 12));

            // Create table
            addRow(0, new Label(" "));
            addRow(1, labelSub, new Label("\t\t"), labelGrade, new Label("\t\t"), labelNote);
            addRow(2, new Label(" "));

            // Iterate items
            for (int i = 0; i < array.size(); i += 2) {
                String subject = (String) array.get(i);
                String grade = (String) array.get(i + 1);
                addRow((i + 4), new Label(subject), new Label("\t\t"), new Label(grade), new Label("\t\t"),
                        new Label(""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
