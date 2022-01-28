package com.example.admin.teacherassistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StartAttendance extends AppCompatActivity {

    private Toolbar toolbar;
    SharedPreferences prf;


    //database
    DatabaseHelper dbhelper;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //CalenderPicker

    static String year1;
    static String month1;
    static String day1;
    TextView txtdate, txtcalendar;
    Button  btncalendarpicker;

    java.sql.Time timeValue;
    SimpleDateFormat format;
    Calendar c;
    int year, month, day;
    SimpleDateFormat formatter;


    private List<Student> studentList;

    private Button btnSelection;
    private Boolean selectDate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_attendance);


        c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);





        //txtcalendar = (TextView) findViewById(R.id.txtcalendar);
        btncalendarpicker = (Button) findViewById(R.id.btncalendarpicker);


        //calenderBtnOnClick

        btncalendarpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date

                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment();
                cdp.show(StartAttendance.this.getSupportFragmentManager(), "Material Calendar Example");
                cdp.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        try {

                            year1 = String.valueOf(year);
                            month1 = String.valueOf(monthOfYear+1);
                            day1 = String.valueOf(dayOfMonth);

                            selectDate=true;


                            formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            Date date = formatter.parse(dateInString);


                            // txtcalendar.setText(formatter.format(date).toString());



                            btncalendarpicker.setText( formatter.format(date).toString());

                            //   formatter = new SimpleDateFormat("dd-MM-yyyy");

                            // txtcalendar.setText(txtcalendar.getText().toString() + "\n" + formatter.format(date).toString());

                            formatter = new SimpleDateFormat("dd.MMM.yyyy");
                            //txtcalendar.setText(R.string.defString);

                           // txtcalendar.setText(txtcalendar.getText().toString() + "\n" + formatter.format(date).toString());
                        } catch (Exception ex) {
                            //txtdate.setText(ex.getMessage().toString());
                        }
                    }
                });
            }
        });




        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSelection = (Button) findViewById(R.id.btn_show);

        studentList = new ArrayList<Student>();


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Start Attendance");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        //db object
        dbhelper = new DatabaseHelper(this);

      /*  dbhelper = new DataBaseHelper(this);
        System.out.println("LLLLLLLL");
        Cursor cursor = dbhelper.getStudentData();
        if(cursor.getCount()==0){
            System.out.println("Nogthing Found");
        }


        System.out.println("mmmmmm");

        while(cursor.moveToNext()){

            System.out.println("klsjfaljfld");

            Student st = new Student(cursor.getString(1), cursor.getString(2), true);

        }

*/
        prf = getSharedPreferences("user_detail",MODE_PRIVATE);
        /*** Display student from database***/
        Cursor res=dbhelper.getStudent(prf.getString("subname","Your Name"));
        while (res.moveToNext()){
            Student st = new Student(res.getString(1), res.getString(2), true);
            studentList.add(st);
        }

       /* for (int i = 1; i <= 15; i++) {
            Student st = new Student("Student " + i, "androidstudent" + i
                    + "@gmail.com", true);

            studentList.add(st);
        }*/


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(studentList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        btnSelection.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(selectDate) {
                    String data = "";
                    List<Student> stList = ((CardViewDataAdapter) mAdapter)
                            .getStudentist();

                    for (int i = 0; i < stList.size(); i++) {
                        Student singleStudent = stList.get(i);
                        if (singleStudent.isSelected() == true) {

                            boolean res = dbhelper.insertRollCall(singleStudent.getRollNumber(), prf.getString("subname", "Your Name"), day1, month1, year1, "P");


                        } else {
                            boolean res = dbhelper.insertRollCall(singleStudent.getRollNumber(), prf.getString("subname", "Your Name"), day1, month1, year1, "A");

                        }


                    }


                    Toast.makeText(StartAttendance.this,
                            "Saved!: \n" + data, Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(StartAttendance.this);
                    builder.setMessage("Select Date First!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });

    }
}
