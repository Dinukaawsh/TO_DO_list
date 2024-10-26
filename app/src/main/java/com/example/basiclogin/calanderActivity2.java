package com.example.basiclogin;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class calanderActivity2 extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText editText;
    private String stringDateSelected;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander2);

        calendarView = findViewById(R.id.calendarView);
        editText = findViewById(R.id.editText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                stringDateSelected = Integer.toString(i) + Integer.toString(i1+1) + Integer.toString(i2);
                calendarClicked();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");
    }

    private void calendarClicked(){
        databaseReference.child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.getValue() != null){
                    editText.setText(snapshot.getValue().toString());
                }else {
                    editText.setText("null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void buttonSaveEvent(View view){
        databaseReference.child(stringDateSelected).setValue(editText.getText().toString());
    }
}