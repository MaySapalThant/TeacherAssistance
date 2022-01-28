package com.example.admin.teacherassistance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btnName;
    EditText etName;
    String Name;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnName = (Button)findViewById(R.id.btnName);
        etName = (EditText)findViewById(R.id.etName);




        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pref = getSharedPreferences("user_detail",MODE_PRIVATE);

                Intent intent = new Intent(MainActivity.this,MainDrawer.class);
                intent.putExtra("Name", etName.getText().toString());


                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username",etName.getText().toString());

                    editor.commit();
                    if(!((etName.getText().toString()).equals(""))) {
                        startActivity(intent);
                    }
                }


        });




    }
}
