package com.example.admin.teacherassistance.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.teacherassistance.R;


public class SettingFragment extends Fragment {

TextView tvName;
Button btnEdit;
TextView username;
SharedPreferences sharedPreferences;

    public SettingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        tvName=(TextView)view.findViewById(R.id.tvName);
        btnEdit=(Button)view.findViewById(R.id.btnEdit);

        username = (TextView)view.findViewById(R.id.txtViewName);
        //tvName.setText(username.getText().toString());


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = SettingFragment.this.getLayoutInflater();
                final View dialogview = inflater.inflate(R.layout.custom_dialog,null);
                builder.setView(dialogview);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText text = (EditText)dialogview.findViewById(R.id.editText);

                        tvName.setText(text.getText().toString());
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


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
