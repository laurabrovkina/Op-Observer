package com.example.admin.opobserver;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,Serializable {

    Button btnActTwo;
    Button picDate;
    private TextView dataView;
    private EditText editText,editText2,editText4,editText5,editText6;
    private String room, teacher, observer, numStud, curDate, duration;
    int day,month,year,hour,minute;
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;
    private final static String FILE_NAME = "results.txt";
    private String s;
    private int seconds;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnActTwo = (Button) findViewById(R.id.btnActTwo);
        btnActTwo.setOnClickListener(this);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);
        picDate = (Button) findViewById(R.id.picDate);
        dataView = (TextView) findViewById(R.id.dataView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


    picDate.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view)
        {
            Calendar c = Calendar.getInstance();
            year=c.get(Calendar.YEAR);
            month=c.get(Calendar.MONTH);
            day=c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,MainActivity.this,year,month,day);
            datePickerDialog.show();
        }
    });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActTwo:
                intialize(); // call validation when button 'Start' is clicked
                if (!validate()) {
                    Toast.makeText(this, "Empty fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    //Save
                    FileOutputStream fos = null;
                    s = "Information: ";
                    try {

                        s += " Classroom No:" + editText.getText() + " Number of Students:" + editText2.getText() + " Date and Time:" + dataView.getText() +
                                " Teacher:" + editText4.getText() +
                                " Observer:" + editText5.getText() +
                                " Seconds:" + editText6.getText();


                        File file = new File(
                                Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_DOWNLOADS),
                                "results.txt"
                        );
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fos = new FileOutputStream(file);
                        fos.write(s.getBytes());
                        fos.close();

                    } catch (IOException ex) {

                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    } finally {
                        try {
                            if (fos != null)
                                fos.close();

                            //Start Second Activity
                            seconds = Integer.parseInt(editText6.getText().toString());
                            Intent intent = new Intent(this, ActivityTwo.class);
                            intent.putExtra("seconds", seconds);
                            startActivity(intent);
                        } catch (IOException ex) {

                            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                break;
            default:
                break;

        }
    }


    public void intialize(){
        room = editText.getText().toString().trim();
        teacher = editText4.getText().toString().trim();
        observer = editText5.getText().toString().trim();
        numStud = editText2.getText().toString().trim();
        curDate = dataView.getText().toString().trim();
        duration = editText6.getText().toString().trim();

    }

    public boolean validate(){
        boolean valid = true;
        if (room.isEmpty() || room.length()>32){
            editText.setError("Please enter room name");
            valid = false;
        }
        if (teacher.isEmpty() || teacher.length()>32){
            editText4.setError("Please enter teacher's name");
            valid = false;
        }
        if (observer.isEmpty() || observer.length()>32){
            editText5.setError("Please enter observer's name");
            valid = false;
        }
        if (numStud.isEmpty()){
            editText2.setError("Please enter number of students");
            valid = false;
        }
        if (duration.isEmpty()){
            editText6.setError("Please enter countdown duration");
            valid = false;
        }
        if (curDate.isEmpty()){
            dataView.setError(dataView.getHint() + " is required");
            valid = false;
        }
        return valid;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1+1;
        dayFinal = i2;
        Calendar c = Calendar.getInstance();
        hour=c.get(Calendar.HOUR_OF_DAY);
        minute=c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,MainActivity.this,
                hour,minute, android.text.format.DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal=i;
        minuteFinal=i1;
        dataView.setText(yearFinal +"/"+ monthFinal +"/"+ dayFinal+ " " + hourFinal +":"+ minuteFinal);
    }
}
