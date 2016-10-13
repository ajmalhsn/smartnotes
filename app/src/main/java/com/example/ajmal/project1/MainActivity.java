package com.example.ajmal.project1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText term,defn;
    Context context;
    TextView test;
    ListView lview;
    Button add,view;
    Spinner spinner;
    DataHandler db;


    String Total[]=new String[3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        term=(EditText)findViewById(R.id.term);
        defn=(EditText)findViewById(R.id.defn);
        add=(Button)findViewById(R.id.btnAdd);
        view=(Button)findViewById(R.id.btnView);
        spinner=(Spinner)findViewById(R.id.set_menu);
        ArrayAdapter arrayadapter=ArrayAdapter.createFromResource(this,R.array.set,android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayadapter);
        db.setInstance(new DataHandler(this));
        db=db.getsInstance();
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    /*@Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView text=(TextView) view;

        Total[2]=adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this,"You selected"+text.getText(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/

     public void AddToDatabase(View view){
        Log.i("TAG","add button is clicked");
         Total[0]= term.getText().toString();
         Total[1]= defn.getText().toString();
         Total[2]=(String)spinner.getSelectedItem();

         Definition def1=new Definition(Total[0],Total[1],Total[2]);
         Log.i("TAG",Total[0]);
         Log.i("TAG",Total[1]);
         Log.i("TAG",Total[2]);
         if(Total[0]!=null&&Total[1]!=null&&Total[2]!=null) {
             Log.i("TAG", "not null");
             try {
                 db.AddTerm(def1);
             } catch (Exception e) {

                 Log.i("TAG", e.toString());
             }
         }

        defn.setText("");
        term.setText("");
     }
    public void deletedata(View view){

        db.DeleteTerm();
        try {
            Intent i = new Intent(this, ListLayout.class);
            startActivity(i);
        }catch(Exception e){
            Log.i("TAG",e.toString()+"main activity");
        }


    }
    public void ViewOfData(View view) {

        try{
        /*List<Definition> defs = db.getAllDefn();
                try {
                for (Definition element : defs) {
                    int count=0;
                    dbvalue = element.getTerm() + " \n" + element.getDefn();

                }*/
                  //  Log.i("TAG",String.valueOf(defs.size())+" main activity");
        } catch (Exception e) {
            Log.i("TAG", e.toString() + " lm ");

        }


        //Log.i("TAG",String.valueOf(.getDefn()));
        try {
            Intent i = new Intent(this, ListLayout.class);
            Slide s=new Slide();
            s.setDuration(1000);
            startActivity(i);
        }catch(Exception e){
            Log.i("TAG",e.toString()+"main activity");
        }

    }


}
