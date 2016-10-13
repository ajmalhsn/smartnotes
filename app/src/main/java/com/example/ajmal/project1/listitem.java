package com.example.ajmal.project1;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

public class listitem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem);
        TextView txt=(TextView)findViewById(R.id.text1);
        Intent i=getIntent();
        String [] term1=i.getStringArrayExtra("term");
        Log.i("TAG","list item"+term1);
        txt.setText(term1[1]);

    }
}
