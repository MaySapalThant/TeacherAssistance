package com.example.admin.teacherassistance;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.teacherassistance.fragment.SettingFragment;

public class Setting extends AppCompatActivity {
    TextView tvName;
    Button btnEdit;
    TextView username;
    SharedPreferences prf;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Setting");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        tvName=(TextView)findViewById(R.id.tvName);
        btnEdit=(Button)findViewById(R.id.btnEdit);

        username = (TextView)findViewById(R.id.txtViewName);
        //tvName.setText(username.getText().toString());

        prf = getSharedPreferences("user_detail",MODE_PRIVATE);
        tvName.setText(prf.getString("username","Your Name"));


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                LayoutInflater inflater = Setting.this.getLayoutInflater();
                final View dialogview = inflater.inflate(R.layout.custom_dialog,null);
                builder.setView(dialogview);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText text = (EditText)dialogview.findViewById(R.id.editText);
                        if(!(text.getText().toString()).equals("")) {

                            tvName.setText(text.getText().toString());
                            SharedPreferences.Editor editor = prf.edit();
                            editor.putString("username", text.getText().toString());
                            editor.commit();
                        }
                        // username.setText(text.getText().toString());
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

    }
}
