package com.example.admin.opobserver;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class NavigationLayout extends RelativeLayout
{

    public NavigationLayout(Context context, RelativeLayout parent)
    {
        super(context);
        initView(context,parent);
    }

    public void initView(final Context context,RelativeLayout parent)
    {

        View view= LayoutInflater.from(context).inflate(R.layout.view_drawer_layout,parent,true);


    }
}

