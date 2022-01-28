package com.example.admin.teacherassistance;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Subject extends AppCompatActivity {
    SharedPreferences prf;
    CardView cardAddStudent,cardGroupGenerate,cardStartAttendance,cardViewRollCall,cardStudentInfo;
    TextView tvSubname;
    public static final int REQUEST_CODE = 1;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (shouldAskPermissions()) {
            askPermissions();
        }
        myDb = new DatabaseHelper(Subject.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        prf = getSharedPreferences("user_detail",MODE_PRIVATE);
       // Toast.makeText(Subject.this, "Click on "+prf.getString("subname","Your Name"), Toast.LENGTH_SHORT).show();
        tvSubname=(TextView)findViewById(R.id.tvSubname);
       tvSubname.setText( prf.getString("subname","Your Name"));

       //setupToolbar();


       cardAddStudent = (CardView)findViewById(R.id.cardAddStudent);
       cardAddStudent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!studentAdded(prf.getString("subname","Your Name"))) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(Subject.this);
                   builder.setTitle("Add Student");
                   builder.setMessage("Import Student Data Excel Sheet!");
                   builder.setPositiveButton("import", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                           intent.setType("*/*");

                           startActivityForResult(intent, REQUEST_CODE);

                       }
                   });
                   builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });
                   builder.show();
               }

               else{
                   AlertDialog.Builder builder = new AlertDialog.Builder(Subject.this);
                   builder.setMessage("Data already imported");
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


       //GroupGenerator

        cardGroupGenerate = (CardView) findViewById(R.id.cardGroupGenerator);
        cardGroupGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Cursor res  = myDb.getSubjectGroup(prf.getString("subname","Your Name"));
                if(res.getCount()==0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Subject.this);
                    LayoutInflater inflater = Subject.this.getLayoutInflater();
                    final View dialogview = inflater.inflate(R.layout.dialog_custom, null);
                    builder1.setView(dialogview)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    EditText etNum = (EditText) dialogview.findViewById(R.id.etNum);
                                    Intent intent = new Intent(Subject.this, GroupGenerate.class);
                                    intent.putExtra("NUM", etNum.getText().toString());
                                    startActivity(intent);
                                }

                            });


                    builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder1.show();
                }
                else {
                    Intent intent = new Intent(Subject.this, GroupGenerate.class);
                    intent.putExtra("NUM", "NotNew");
                    startActivity(intent);
                }
        }
        });





        cardStartAttendance = (CardView) findViewById(R.id.cardStartAttendance);
        cardStartAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subject.this, StartAttendance.class);
                startActivity(intent);
            }
        });

        //view rollcall
        cardViewRollCall = (CardView)findViewById(R.id.cardViewRollCall);
        cardViewRollCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subject.this,ViewRollcall.class);
                startActivity(intent);
            }
        });

        //view student info
        cardStudentInfo = (CardView)findViewById(R.id.card_student_info);
        cardStudentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subject.this,StudentInfo.class);
                startActivity(intent);
            }
        });






    }


    private boolean studentAdded(String subject){
        Cursor res  = myDb.getStudent(subject);
        if(res.getCount()==0) {
            return false;
        }
        return true;
    }



    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(prf.getString("subname","Your Name"));
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String realPath = "";
        Uri uri = data.getData();
        realPath = getPathFromUri(this, uri);
        Log.i("INFO", "URI: " + uri);
        Log.i("INFO", "REAL_PATH: " + realPath);

        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Log.i("INFO", "PATH:" + realPath);
                readExcelFile(realPath);
               // Toast.makeText(Subject.this, "EXCEL FILE IS IMPORTED", Toast.LENGTH_SHORT).show();

            }

        }
    }


    /***    Read Excel File     ***/
    public void readExcelFile(String fileName) {
        //  fileName = Environment.getExternalStorageDirectory()+ File.separator + fileName;
        String name="";
        String roll_no="";
        try (FileInputStream file = new FileInputStream(new File(fileName))) {

            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                System.out.println(row.getCell(0));
                row.getCell(0);
                System.out.println("a"+row.getCell(1));
                DataFormatter formatter = new DataFormatter();

                addStudent(formatter.formatCellValue(row.getCell(0)),formatter.formatCellValue(row.getCell(1)),formatter.formatCellValue(row.getCell(2)),formatter.formatCellValue(row.getCell(3)),formatter.formatCellValue(row.getCell(4)),formatter.formatCellValue(row.getCell(5)),prf.getString("subname","Your Name"));




               /* String str = "";
                for (Cell cell : row) {
                    //name= String.valueOf(row.getCell(0));
                    //roll_no=String.valueOf(row.getCell(1));
                    // str += cell.getStringCellValue();
                    Log.i("INFO", cell.getStringCellValue());

                }*/


            }

        } catch (IOException ioException) {
            Log.e("INFO", "IOEXCEPTION");
            ioException.printStackTrace();
        }
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    /*** GET REAL Path form all types of devices ****/
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void addStudent(String rollno,String name,String email,String elearningid,String elearningemail,String phonenumber,String subject){
        boolean isInserted = myDb.insertStudent(rollno,name,email,elearningid,elearningemail,phonenumber,subject);
        if(isInserted==true) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Student Datas Imported", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            Snackbar snackbar1 = Snackbar.make(findViewById(android.R.id.content), "Error Importing Student!.", Snackbar.LENGTH_LONG);
            snackbar1.show();
        }


    }

}
