package com.example.admin.teacherassistance;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class StudentInfo extends AppCompatActivity {
    private Toolbar toolbar;
    SharedPreferences prf;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Student> studentList;

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        studentList = new ArrayList<Student>();


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Student Info");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        prf = getSharedPreferences("user_detail",MODE_PRIVATE);
        myDb = new DatabaseHelper(StudentInfo.this);
        Cursor res  = myDb.getStudent(prf.getString("subname","Your Name"));

        while (res.moveToNext()){
            Student st=new Student(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6));
            studentList.add(st);
            System.out.println("hi");

        }
      /*for (int i = 1; i <= 15; i++) {
            Student st = new Student(String.valueOf(i), "name" + i, "Email"+i+"@gmail.com","utycc12014010"+i,"Elearning"+i+"@gmail.com");

            System.out.println("hi");
            studentList.add(st);
        }*/



        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter
        mAdapter = new StudentCardViewAdapter(studentList,this);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

       /* List<Student> stList = ((StudentCardViewAdapter) mAdapter).getStudentist();
      /*  for (int i = 0; i < stList.size(); i++) {
            Student singleStudent = stList.get(i);
            if (singleStudent.isClicked() == true) {

                Uri phone = Uri.parse("tel:09401841526");
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, phone);
                startActivity(phoneIntent);

            }

        }*/




    }
    }