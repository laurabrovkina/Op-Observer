package com.example.admin.opobserver;

import android.support.annotation.LayoutRes;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

public class ParentNavigationActivity extends AppCompatActivity {
    public NavigationLayout navigationLayout;
    public RelativeLayout left_drawer;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupMenu();
    }

    public void setupMenu()
    {
        left_drawer=(RelativeLayout) findViewById(R.id.left_drawer);
        navigationLayout=new NavigationLayout(getApplicationContext(),left_drawer);
        left_drawer.addView(navigationLayout);

    }


}
