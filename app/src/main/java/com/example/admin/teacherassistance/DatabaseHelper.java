package com.example.admin.teacherassistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TeacherAssistance.db";
    public static final String TABLE_NAME = "subject_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SUBJECTNAME";
    public static final String COL_3 = "SUBJECTCODE";

    public static final String TABLE_STUDENT = "student_table";
    public static final String TABLE_STUDENT_COL_ID = "ID";
    public static  final  String TABLE_STUDENT_COL_ROLLNO = "ROLLNO";
    public static final String TABLE_STUDENT_COL_NAME = "NAME";
    public static final String TABLE_STUDENT_COL_SUBJECT = "SUBJECT";
    public static final String TABLE_STUDENT_COL_EMAIL = "EMAIL";
    public static final String TABLE_STUDENT_COL_ELEARNINGID= "ELEARNINGID";
    public static final String TABLE_STUDENT_COL_ELEARNINGEMAIL = "ELEARNINGEMAIL";
    public static final String TABLE_STUDENT_COL_PHONENUMBER = "PHONENUMBER";


    public static final String TABLE_GROUP = "group_table";
    public static final String TABLE_GROUP_COL_ID = "ID";
    public static final String TABLE_GROUP_COL_GPNUM = "GROUPNUM";
    public static final String TABLE_GROUP_COL_STUDENTID = "STUID";
    public static final String TABLE_GROUP_COL_SUBNAME = "SUBJECT";


    public static final String TABLE_ROLLCALL = "rollcall_table";
    public static final String COL_ROLL_NO = "ROLL_NO";
    public static final String COL_SUBJECT = "SUBJECT";
    public static final String COL_DAY= "DAY";
    public static final String COL_MONTH = "MONTH";
    public static final String COL_YEAR = "YEAR";
    public static final String COL_STATUS = "STATUS";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,SUBJECTNAME TEXT,SUBJECTCODE TEXT )");
        db.execSQL("create table " + TABLE_STUDENT + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ROLLNO TEXT,NAME TEXT,EMAIL TEXT,ELEARNINGID TEXT,ELEARNINGEMAIL TEXT,PHONENUMBER TEXT,SUBJECT TEXT )");
        db.execSQL("create table " + TABLE_GROUP + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,GROUPNUM TEXT,STUID INTEGER,SUBJECT TEXT )");
        db.execSQL("create table " + TABLE_ROLLCALL + " (ROLL_NO STRING,SUBJECT TEXT,DAY TEXT,MONTH TEXT,YEAR TEXT,STATUS TEXT)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //ForSubjectTable
    public boolean insertData(String subjectname,String subjectcode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,subjectname);
        contentValues.put(COL_3,subjectcode);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }

    public Cursor getSubject(String subject){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+TABLE_NAME+" where "+COL_2 +" = "+"'"+subject+"'",null);
        return res;
    }

    public boolean deleteSubject(String name)
    {   SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_2 + "=" + name, null) > 0;
    }

    //ForStudentTable
    public boolean insertStudent(String rollno,String name,String email,String elearningid,String elearningemail,String phonenumber,String subject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_STUDENT_COL_ROLLNO,rollno);
        contentValues.put(TABLE_STUDENT_COL_NAME,name);
        contentValues.put(TABLE_STUDENT_COL_EMAIL,email);
        contentValues.put(TABLE_STUDENT_COL_ELEARNINGID,elearningid);
        contentValues.put(TABLE_STUDENT_COL_ELEARNINGEMAIL,elearningemail);
        contentValues.put(TABLE_STUDENT_COL_PHONENUMBER,phonenumber);

        contentValues.put(TABLE_STUDENT_COL_SUBJECT,subject);

        long result = db.insert(TABLE_STUDENT,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getStudent(String subject){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+TABLE_STUDENT+" where "+TABLE_STUDENT_COL_SUBJECT +" = "+"'"+subject+"'",null);
        return res;

    }

    public Cursor getIndividualStudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+TABLE_STUDENT+" where "+TABLE_STUDENT_COL_ID +" = "+"'"+id+"'",null);
        return res;
    }

    //forviewRollcall
    public Cursor getRollCall(String subj){
        SQLiteDatabase db = this.getWritableDatabase();
        //String select_query=
        //String select_query = "SELECT DISTINCT t2.NAME from rollcall_table t1 INNER JOIN student_table t2 ON t1.ROLL_NO=t2.ROLLNO" ;
        Cursor res  = db.rawQuery("select * from "+TABLE_ROLLCALL+" where "+COL_SUBJECT +" = "+"'"+subj+"'",null);
        return res;

    }


    public Cursor getName(String subj){
        SQLiteDatabase db = this.getWritableDatabase();
        //String select_query=
        //String select_query = "SELECT DISTINCT t2.NAME from rollcall_table t1 INNER JOIN student_table t2 ON t1.ROLL_NO=t2.ROLLNO" ;
        Cursor res  = db.rawQuery("select * from "+TABLE_STUDENT+" where "+TABLE_STUDENT_COL_SUBJECT +" = "+"'"+subj+"'",null);
        return res;

    }


    public Cursor getAllDay(String rollnum,String subj){
        SQLiteDatabase db = this.getWritableDatabase();
        //String select_query="select * from "+TABLE_ROLLCALL+" where "+COL_ROLL_NO+" = "+"'"+ String.valueOf(i)+"'"
        //String select_query = "select * from rollcall_table where ROLL_NO='"+  String.valueOf(i) +"' and SUBJECT='"+subj+"'";
        Cursor cursor = db.rawQuery("select * from "+TABLE_ROLLCALL+" where "+COL_ROLL_NO+" = "+"'"+ rollnum+"' and "+COL_SUBJECT+" = "+"'"+ subj+"'",null);
        /*double count = 0;
        if(null != cursor)
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        cursor.close();


        db.close();*/
        return cursor;
    }

    public Cursor getPresentDay(String rollnum,String subj){
        SQLiteDatabase db = this.getWritableDatabase();
        //String select_query = "select * from rollcall_table where ROLL_NO='"+ String.valueOf(i)+"'and SUBJECT='"+subj+"' and STATUS='P'";
        Cursor cursor = db.rawQuery("select * from "+TABLE_ROLLCALL+" where "+COL_ROLL_NO+" = "+"'"+ rollnum+"' and "+COL_SUBJECT+" = "+"'"+ subj+"' and "+COL_STATUS+" = "+"'P'",null);
        /*double count = 0;
        if(null != cursor)
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        cursor.close();


        db.close();*/
        return cursor;
    }

    public Cursor getAllDayMonth(String rollnum,String subj,String month,String year){
        SQLiteDatabase db = this.getWritableDatabase();
        String select_query = "select * from rollcall_table where ROLL_NO='"+  rollnum +"' and SUBJECT='"+subj+"'and MONTH='"+month+"' and YEAR='"+year+"'";
        Cursor cursor = db.rawQuery(select_query,null);

        return cursor;
    }


    public Cursor getPresentDayMonth(String rollnum,String subj,String month,String year){
        SQLiteDatabase db = this.getWritableDatabase();
        String select_query = "select * from rollcall_table where ROLL_NO='"+ rollnum +"'and SUBJECT='"+subj+"' and STATUS='P' and MONTH='"+month+"' and YEAR='"+year+"'";
        Cursor cursor = db.rawQuery(select_query,null);

        return cursor;
    }


    //ForRollCall
    public boolean insertRollCall(String rollno,String subject,String day,String month,String year,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ROLL_NO,rollno);
        contentValues.put(COL_SUBJECT,subject);
        contentValues.put(COL_DAY,day);
        contentValues.put(COL_MONTH,month);
        contentValues.put(COL_YEAR,year);
        contentValues.put(COL_STATUS,status);

        long result = db.insert(TABLE_ROLLCALL,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }




    //ForGroupTable

    public boolean insertGroup(String gpnum,int stuid,String subject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_GROUP_COL_GPNUM,gpnum);
        contentValues.put(TABLE_GROUP_COL_STUDENTID,stuid);
        contentValues.put(TABLE_GROUP_COL_SUBNAME,subject);

        long result = db.insert(TABLE_GROUP,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    //toCheckGroupingOrNotForThatSubject
     public Cursor getSubjectGroup(String subject){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+TABLE_GROUP+" where "+TABLE_GROUP_COL_SUBNAME+" = "+"'"+subject+"'",null);
        return res;

    }

    //ToDIsplayGroup
    public Cursor getIndividualSubjectGroup(String subject,String gpnum){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  = db.rawQuery("select * from "+TABLE_GROUP+" where "+TABLE_GROUP_COL_SUBNAME+" = "+"'"+subject+"'"+" and "+TABLE_GROUP_COL_GPNUM+" = "+"'"+gpnum+"'",null);
        return res;

    }
  /*  public boolean updateData(String id,String name,String surname,String mark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        //contentValues.put(COL_4,mark);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[] {id});
        return true;
    }*/
}