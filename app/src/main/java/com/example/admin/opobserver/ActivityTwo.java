package com.example.admin.opobserver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Toast;
import com.example.admin.opobserver.CanvasView.OnTextUpdateListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ActivityTwo extends ParentNavigationActivity implements OnTextUpdateListener,Serializable {
    private CanvasView canvasView;
    public TextView textView1;
    public TextView textView2;
    public RelativeLayout left_drawer1;
    CountDownTimer timer;
    private static final String FORMAT = "%02d:%02d:%02d";
    int seconds , minutes;
    private ActionBarDrawerToggle mToggle;
    private String childPos;
    public int ta,sa;
    AdapterHelper ah;
    private Button buttonStart1;
    private Button buttonFinish1;
    SimpleExpandableListAdapter adapter;
    ExpandableListView expandable_list_view;
    final String LOG_TAG = "myLogs";
    public ArrayList<String> finalResult = new ArrayList<String>();

    String[] groups = new String[] {"Classroom"};

    //elements of menu
    String[] elements = new String[]{"Chair", "Table", "Blackboard", "Door"};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        canvasView = (CanvasView)findViewById(R.id.canvas);
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        left_drawer1 = (RelativeLayout)findViewById(R.id.left_drawer);
        buttonStart1 = (Button)findViewById(R.id.buttonStart1);
        buttonFinish1 = (Button)findViewById(R.id.buttonFinish1);
        buttonFinish1.setVisibility(View.INVISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        seconds = getIntent().getExtras().getInt("seconds");
        Log.d(LOG_TAG, "Sec in ac2="+seconds);

         buttonFinish1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonFinish1:
                        //Start Third Activity
                        ta=canvasView.ta;
                        sa=canvasView.sa;
                        finalResult=canvasView.finalResult;
                        Log.d(LOG_TAG, "Send to 3 activity ta="+ta +" "+ "sa="+sa);
                        saveCanvas();
                        Intent intent = new Intent(ActivityTwo.this, ActivityThree.class);
                        intent.putExtra("ta", ta);
                        intent.putExtra("sa", sa);
                        intent.putStringArrayListExtra("finalResult", finalResult);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

            }


        });
        buttonStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonStart1:
                        //Start
                        String[] groups = new String[] {"Teacher's activities","Students' activities"};

                        //elements of menu
                        String[][] elements1 = new String[2][8];
                        elements1[0][0] = "1";
                        elements1[0][1] = "CD";
                        elements1[0][2] = "GI";
                        elements1[0][3] = "Wh";
                        elements1[0][4] = "TQ";
                        elements1[0][5] = "BM";
                        elements1[0][6] = "L";
                        elements1[0][7] = "ILE";

                        elements1[1][0] = "One on one teacher individual student";
                        elements1[1][1] = "Class teaching demonstration";
                        elements1[1][2] = "Giving instructions";
                        elements1[1][3] = "Whiteboard writing";
                        elements1[1][4] = "Teacher asking questions";
                        elements1[1][5] = "Behaviour Management";
                        elements1[1][6] = "Tutor lecturing/PPT presentation";
                        elements1[1][7] = "Creating an inclusive learning environment";

                        String[][] elements2 = new String[2][7];
                        elements2[0][0] = "Wv";
                        elements2[0][1] = "PEL";
                        elements2[0][2] = "REF";
                        elements2[0][3] = "GF";
                        elements2[0][4] = "M";
                        elements2[0][5] = "SQ";
                        elements2[0][6] = "LSP";

                        elements2[1][0] = "Watching a video or watching student activity";
                        elements2[1][1] = "Engaged in practical experiential learning activity";
                        elements2[1][2] = "Reflective practice - Learners making meaning";
                        elements2[1][3] = "Giving feedback on learners ideas";
                        elements2[1][4] = "Moving around the learning setting";
                        elements2[1][5] = "Teacher responding to student questions";
                        elements2[1][6] = "Listening to student presentation/ideas";


                        ah = new AdapterHelper(ActivityTwo.this);
                        adapter = ah.getAdapter(groups,elements1,elements2);
                        expandable_list_view = (ExpandableListView) findViewById(R.id.expandable_list_view);
                        expandable_list_view.setAdapter(adapter);
                        childPos=null;
                        buttonStart1.setVisibility(View.INVISIBLE);
                        buttonFinish1.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });

        String[] groups = new String[] {"Classroom"};

        //elements of menu
        String[] elements = new String[]{"Chair", "Table", "Blackboard", "Door"};

        ah = new AdapterHelper(this);
        adapter = ah.getAdapter(groups,elements);
        expandable_list_view = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandable_list_view.setAdapter(adapter);

        // click on the element
        expandable_list_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                public boolean onChildClick(ExpandableListView parent, View v,
                int groupPosition,   int childPosition, long id) {
                    parent.clearChoices();

                    Log.d(LOG_TAG, "onChildClick groupPosition = " + groupPosition +
                            " childPosition = " + childPosition +
                            " id = " + id);
                    textView2.setText(ah.getChildText2(groupPosition,childPosition));

                    childPos=ah.getChildText(groupPosition,childPosition);
                    parent.setItemChecked(childPosition, true);

                    return false;
                }
            });

    }

    public void saveCanvas() {
        canvasView.setDrawingCacheEnabled(true);
        canvasView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = canvasView.getDrawingCache();
        File file = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),
                "screenshot.jpeg"
        );
        FileOutputStream ostream;
        try {
            file.createNewFile();
            ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.flush();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.undo_canvas:
                undoCanvas();
                break;
            case R.id.clear_canvas:
                canvasView.clearCanvas();
                textView1.setText("done!");
                ah = new AdapterHelper(this);
                adapter = ah.getAdapter(groups,elements);
                expandable_list_view = (ExpandableListView) findViewById(R.id.expandable_list_view);
                expandable_list_view.setAdapter(adapter);
                buttonStart1.setVisibility(View.VISIBLE);
                buttonFinish1.setVisibility(View.INVISIBLE);
                ta=0;
                sa=0;
                finalResult=null;
                textView2.setText("");
                childPos="";
                break;
                default:
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onTextUpdate(String text) {
        textView1.setText(text);
    }
    public String getTextValue() {
        return textView1.getText().toString();
    }
    public String getChildValue() {
        return childPos;
    }
    public int getSeconds() {
        return seconds;
    }


    public void clearCanvas(View v){
        canvasView.clearCanvas();
        textView1.setText("done!");
    }
    public void undoCanvas(){
        canvasView.undo();
        textView1.setText("done!");
    }

}

