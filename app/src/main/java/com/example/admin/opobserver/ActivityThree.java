package com.example.admin.opobserver;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ActivityThree extends AppCompatActivity {
    public int ta,sa;
    public  float fta, fsa,tcount;
    public ArrayList<String> finalResult = new ArrayList<String>();
    public TextView dataView;
    public EditText feedback;
    public Button saveResult;
    final String LOG_TAG = "myLogs";
    private final static String FILE_NAME = "results.txt";
    private String s;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        saveResult = (Button) findViewById(R.id.saveResult);
        feedback = (EditText)findViewById(R.id.feedback);
        setSupportActionBar(toolbar);
        ta = getIntent().getExtras().getInt("ta");
        sa = getIntent().getExtras().getInt("sa");
        finalResult = getIntent().getExtras().getStringArrayList("finalResult");
        fta=ta+sa;
        fsa=ta+sa;
        tcount=ta+sa;

        fta=ta/fta;
        fsa=sa/fsa;

        fta=fta*100;
        fsa=fsa*100;

        String r="";
        for (String s : finalResult){
            r += s + "\t";
        }


        HashMap<String,Integer> hm = new HashMap<String, Integer>();
        Integer am;
        for (String s : finalResult) {
            am = hm.get(s);

            hm.put(s,am == null ? 1 : am + 1);
        }

        System.out.println();


        int i=1;
        Iterator<HashMap.Entry<String, Integer>> entries = hm.entrySet().iterator();
        while (entries.hasNext()) {
            HashMap.Entry<String, Integer> entry = entries.next();
            Log.d(LOG_TAG, "Key = " + entry.getKey() + ", Value = " + entry.getValue());
            float per = entry.getValue()/tcount;
            //Log.d(LOG_TAG, "PER: "+per);
            per=per*100;
            //Log.d(LOG_TAG, "FINAL PER: "+per);
            addRow(entry.getKey(),entry.getValue(),per);
            i++;


        }

        Log.d(LOG_TAG, "FINAL fta="+fta +" "+ "fsa="+fsa+"Count=" + tcount);
        dataView = (TextView)findViewById(R.id.dataView);
        //dataView.setText("finalResult: " + r);
        dataView.setText("Teacher's Activities:"+String.format("%.2f", fta)+"% Students' Activities:"+String.format("%.2f", fsa)+"%");

        saveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.saveResult:
                        //Save
                        FileOutputStream fos = null;
                        s=" Results: ";
                        try {
                            TableLayout table = (TableLayout)findViewById(R.id.table);
                            for (int i = 0; i < table.getChildCount(); i++){
                                TableRow row = (TableRow)table.getChildAt(i);
                                TextView et0=(TextView)row.getChildAt(0);
                                TextView et1=(TextView)row.getChildAt(1);
                                TextView et2=(TextView)row.getChildAt(2);
                                //TextView et3=(TextView)row.getChildAt(3);
                                s+= et0.getText().toString()+" "+et1.getText().toString()+" "+et2.getText().toString()+" ";//+et3.getText().toString()+" ";
                            }

                            s+=" Feedback:"+feedback.getText();

                            File file = new File(
                                    Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_DOWNLOADS),
                                    "results.txt"
                            );

                            fos = new FileOutputStream(file,true);
                            fos.write(s.getBytes());

                            fos.close();
                            Toast.makeText(ActivityThree.this, "File results.txt saved", Toast.LENGTH_SHORT).show();
                        }
                        catch(IOException ex) {

                            Toast.makeText(ActivityThree.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        finally{
                            try{
                                if(fos!=null)
                                    fos.close();
                            }
                            catch(IOException ex){

                                Toast.makeText(ActivityThree.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        break;
                    default:
                        break;
                }

            }


        });
        verifyStoragePermissions(this);
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public void addRow( String c2,int c3,float c4) {
    TableLayout tableLayout = (TableLayout)findViewById(R.id.table);
    LayoutInflater inflater = LayoutInflater.from(this);
    //Create row using template /res/layout/table_row.xml
    TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, null);

    TextView tv = (TextView) tr.findViewById(R.id.col2);

    tv.setMaxLines(5); tv.setMinLines(1); tv.setSingleLine(false);
    tv.setText(c2);

    tv = (TextView) tr.findViewById(R.id.col3);
    tv.setText(Integer.toString(c3));

    tv = (TextView) tr.findViewById(R.id.col4);
    tv.setText(String.format("%.2f", c4));

    tableLayout.addView(tr); //add row to the table
}




}
