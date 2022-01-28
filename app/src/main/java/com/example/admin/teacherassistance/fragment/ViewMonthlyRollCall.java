package com.example.admin.teacherassistance.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.teacherassistance.MonthYearPickerDialog;
import com.example.admin.teacherassistance.R;
import com.example.admin.teacherassistance.DatabaseHelper;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.admin.teacherassistance.R;

import static android.content.Context.MODE_PRIVATE;


public class ViewMonthlyRollCall extends Fragment {
    SharedPreferences prf;
    DatabaseHelper myDb;
    TextView click_me;
    public static String monthYearStr;
    public static String yearInStr;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    String subj ;
    public static String monthInString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_view_monthly_roll_call, container, false);

        myDb = new DatabaseHelper(getActivity());

        prf = getActivity().getSharedPreferences("user_detail",MODE_PRIVATE);
        subj=prf.getString("subname","Your Name");

        click_me =view.findViewById(R.id.click_me);

        click_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        monthYearStr = year + "-" + (month + 1) + "-" + i2;
                        monthInString=String.valueOf(month);
                        yearInStr=String.valueOf(year);

                        click_me.setText(formatMonthYear(monthYearStr));




                        LinearLayout linear=(LinearLayout)view.findViewById(R.id.LinearForTable);
                        linear.removeAllViews();

                        TableLayout stk=new TableLayout(getActivity());

                        stk.setColumnStretchable(1,true);

                        linear.addView(stk);

                        // month = spMonth.getSelectedItem().toString();
                        //TableLayout stk = (TableLayout) view.findViewById(R.id.table_main);
                        TableRow tbrow0 = new TableRow(getActivity());

                        TextView tv0 = new TextView(getActivity());
                        tv0.setText(" Roll No. ");
                        tv0.setTextColor(Color.BLACK);
                        tv0.setBackgroundColor(Color.WHITE);
                        tv0.setBackgroundResource(R.drawable.border);
                        tbrow0.addView(tv0);
                        TextView tv1 = new TextView(getActivity());
                        tv1.setText(" Name ");
                        tv1.setTextColor(Color.BLACK);
                        tv1.setBackgroundColor(Color.WHITE);
                        tv1.setBackgroundResource(R.drawable.border);
                        tbrow0.addView(tv1);
                        TextView tv2 = new TextView(getActivity());
                        tv2.setText(" Percentage ");
                        tv2.setTextColor(Color.BLACK);
                        tv2.setBackgroundColor(Color.WHITE);
                        tv2.setBackgroundResource(R.drawable.border);
                        tbrow0.addView(tv2);
                        stk.addView(tbrow0);
                        Cursor rollcall=myDb.getRollCall(subj);
                        if(rollcall.getCount()!=0) {
                            Cursor name = myDb.getName(subj);//to get all student names from two tables

                            while (name.moveToNext()) {
                                TableRow tbrow = new TableRow(getActivity());

                                TextView t1v = new TextView(getActivity());
                                t1v.setText(name.getString(1));//setting roll no.
                                t1v.setTextColor(Color.BLACK);
                                t1v.setBackgroundColor(Color.WHITE);
                                t1v.setBackgroundResource(R.drawable.border);
                                tbrow.addView(t1v);

                                TextView t2v = new TextView(getActivity());
                                t2v.setText(name.getString(2));//setting name
                                t2v.setTextColor(Color.BLACK);
                                t2v.setBackgroundColor(Color.WHITE);
                                t2v.setBackgroundResource(R.drawable.border);
                                tbrow.addView(t2v);

                                TextView t3v = new TextView(getActivity());
                                System.out.print("MONTH IS " + month);
                                //calculating percentage for each student
                                Cursor allDay = myDb.getAllDayMonth(name.getString(1), subj, String.valueOf(month), String.valueOf(year));//to get all days
                                Cursor presentDay = myDb.getPresentDayMonth(name.getString(1), subj, String.valueOf(month), String.valueOf(year));//to get present days
                                int allDayCount = allDay.getCount();
                                int presentDayCount = presentDay.getCount();
                                int percentage = Math.round((presentDayCount * 100) / allDayCount);

                                t3v.setText(String.valueOf(percentage));//setting percentage
                                t3v.setTextColor(Color.BLACK);
                                t3v.setBackgroundColor(Color.WHITE);
                                t3v.setBackgroundResource(R.drawable.border);
                                tbrow.addView(t3v);

                                stk.addView(tbrow);

                            }

                        }




                    }
                });
                pickerDialog.show(getActivity().getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });





        // String[] name = {"January", " February", "March", " April", " May", "Jone", "July", "August", "September", "October", "November", "December"};
        // Data Bind to Name Spinner
        // ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, name);
        // spMonth = (Spinner) view.findViewById(R.id.);
        final FloatingActionButton btnCurrentSpread = (FloatingActionButton) view.findViewById(R.id.btnMonthlySpread);




        if (shouldAskPermissions()) {
            askPermissions();
        }

        btnCurrentSpread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Cursor rollcall=myDb.getRollCall(subj);
                if(rollcall.getCount()!=0) {
                    Date time = new java.util.Date(System.currentTimeMillis());
                    String s = String.valueOf(new SimpleDateFormat("HH_mm_ss").format(time));
                    String excelfile = monthInString + "_" + s + ".xls";
                    createExcelFile(excelfile, monthInString, yearInStr);
                }
            }
        });


        return view;
    }//on create view

    String formatMonthYear(String str) {
        Date date = null;
        try {
            date = input.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    /***    Create Excel File   ***/
    public void createExcelFile(String fileName,String month,String year) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("Sheet No 1");

        // HSSFSheet secondSheet = workbook.createSheet("Sheet No 2");
        HSSFRow row = firstSheet.createRow(0);

        HSSFCell cellA = row.createCell(0);
        cellA.setCellValue(new HSSFRichTextString("Roll Number"));
        HSSFCell cellB = row.createCell(1);
        cellB.setCellValue(new HSSFRichTextString("Name"));
        HSSFCell cellC = row.createCell(2);
        cellC.setCellValue(new HSSFRichTextString("Percentage"));
        Cursor name = myDb.getName(subj);
        int i = 1;

        while (name.moveToNext()) {
            row = firstSheet.createRow(i);
            cellA = row.createCell(0);
            cellA.setCellValue(name.getString(1));//for roll number

            cellB = row.createCell(1);
            cellB.setCellValue(name.getString(2));//for name

            /*Calculating percentage for each student*/
            Cursor allDay = myDb.getAllDayMonth(name.getString(1), subj, String.valueOf(month), String.valueOf(year));//to get all days
            Cursor presentDay = myDb.getPresentDayMonth(name.getString(1), subj, String.valueOf(month), String.valueOf(year));//to get present days
            int allDayCount = allDay.getCount();
            int presentDayCount = presentDay.getCount();
            int percentage = Math.round((presentDayCount * 100) / allDayCount);

            cellC = row.createCell(2);
            cellC.setCellValue(String.valueOf(percentage));//setting percentage
            i++;
        }

        // HSSFRow rowB = secondSheet.createRow(0);
        // HSSFCell cellB = rowB.createCell(0);
        // cellB.setCellValue(new HSSFRichTextString("Sheet two"));

        FileOutputStream fos = null;
        try {
            String folderName = "RollCall";
            String str_path = Environment.getExternalStorageDirectory()
                    + File.separator + folderName;
            Log.i("INFO", str_path);

            File file = new File(str_path);
            if (!file.exists()) {
                if (!file.mkdirs()) //folder creation
                    file.mkdirs();
            }
            file = new File(str_path, fileName);
            if (file.exists()) {
                file.delete();
                file = new File(str_path, fileName);
            }
            fos = new FileOutputStream(file, true);
            Log.i("INFO", "FILEOUTPUTSTREAM");
            workbook.write(fos);

        } catch (IOException e) {

            Log.i("INFO", "Exception in CATCH" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(getActivity(), "Excel Sheet Generated", Toast.LENGTH_SHORT).show();
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
                final String[] selectionArgs = new String[]{
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


}


