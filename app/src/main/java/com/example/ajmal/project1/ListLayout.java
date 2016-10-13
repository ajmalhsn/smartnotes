package com.example.ajmal.project1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

public class ListLayout extends AppCompatActivity {

    DataHandler db;
    Spinner filter;
    ArrayList<String> lsv1;
    ArrayAdapter<String> adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        db = db.getsInstance();
        Cursor li = db.getAllDefn();
        lsv1 = new ArrayList<>();
        if (li.moveToFirst()) {
            do {
                lsv1.add(li.getString(li.getColumnIndex("term")));
            } while (li.moveToNext());
        }
        lsv1 = new ArrayList<String>(new LinkedHashSet<String>(lsv1));


        /*
                try {
              for(Definition element:li) {
                Log.i("TAG", element.getTerm() + "   " + element.getDefn() + "   " + element.getSet());
                lsv1.add(element.getTerm());
            }*/
        //              Log.i("TAG", String.valueOf(lsv1.size()));
        final ListView lsv = (ListView) findViewById(R.id.view1);
        filter = (Spinner) findViewById(R.id.sort);
        ArrayAdapter arrayadapter = ArrayAdapter.createFromResource(this, R.array.set, android.R.layout.simple_spinner_item);
        filter.setAdapter(arrayadapter);

            /*Bundle data = getIntent().getExtras();
            String[] viewdata = data.getStringArray("view1");
             Log.i("TAG",viewdata[0]);*/

        adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lsv1);
        //ListAdapter listadapt = new ListAdapter(this,R.layout.support_simple_spinner_dropdown_item,li);
        lsv.setAdapter(adapt);
        //lsv.deferNotifyDataSetChanged();


        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                try {
                    String setName = filter.getSelectedItem().toString();
                    Log.i("TAG", setName);
                    Cursor c = db.getDefnBySet(setName);
                    Log.i("TAG", String.valueOf(c.moveToFirst()));
                    c.moveToFirst();
                    lsv1.clear();
                    if (c.moveToFirst()) {
                        do {
                            lsv1.add(c.getString(c.getColumnIndex("term")));
                            Log.i("TAG", c.getString(c.getColumnIndex("term")));
                        } while (c.moveToNext());
                    } else {
                        lsv1.clear();
                    }
                    lsv1 = new ArrayList<String>(new LinkedHashSet<String>(lsv1));
                    adapt.clear();
                    adapt.addAll(lsv1);
                    adapt.notifyDataSetChanged();
                } catch (Exception e) {

                    Log.i("TAG", e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        lsv.setClickable(true);

        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                try {
                    final String[] term1 = new String[3];
                    String term = (String) lsv.getItemAtPosition(position);
                    Log.i("TAG", term);
                    Cursor c = db.getDefn(term);
                    c.moveToFirst();
                    Log.i("TAG", String.valueOf(c.moveToFirst()));
                    Log.i("TAG", c.getString(c.getColumnIndex("defn")) + "  " + c.getCount());
                    if (c.moveToFirst()) {
                        //c.moveToPosition(0);
                        term1[0] = c.getString(c.getColumnIndex("term"));
                        term1[1] = c.getString(c.getColumnIndex("defn"));
                        term1[2] = c.getString(c.getColumnIndex("studyset"));
                        update(term1);
                    }
                } catch (Exception e) {
                    Log.i("TAG", e.toString());
                }

            }
        });
        lsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String term= (String) lsv.getItemAtPosition(position);
                new AlertDialog.Builder(ListLayout.this)
                        .setMessage("Do you want to delete this record permanently from the database")
                        .setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int which) {

                                        db.deleteone(term);
                                        Toast.makeText(getBaseContext(), "your term has been deleted",
                                                Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        adapt.notifyDataSetChanged();
                                    }
                                }).show();

                return true;
            }
        });
    }



    public void update(String[] term1) {
        Intent i = new Intent(this,listitem.class);
        i.putExtra("term",term1);
        startActivity(i);

    }
}


