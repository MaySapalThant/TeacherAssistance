package com.example.admin.teacherassistance.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.example.admin.teacherassistance.Calbum;
import com.example.admin.teacherassistance.CalbumAdapter;
import com.example.admin.teacherassistance.DatabaseHelper;
import com.example.admin.teacherassistance.R;
import com.example.admin.teacherassistance.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private CalbumAdapter adapter;
    private List<Calbum> albumList;
    DatabaseHelper myDb;

    public HomeFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        myDb = new DatabaseHelper(getActivity());



        //initCollapsingToolbar();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new CalbumAdapter(getActivity(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        viewAll(myDb);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Play !", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogview = inflater.inflate(R.layout.subjectdialogcustom, null);
                builder.setView(dialogview)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText subjectname = (EditText) dialogview.findViewById(R.id.subject);
                                String sname = subjectname.getText().toString();
                                EditText stdclassname = (EditText) dialogview.findViewById(R.id.stclass);
                                String cname = stdclassname.getText().toString();
                                if((sname.equals(""))) {
                                    Snackbar snackbar=Snackbar.make(view,"Enter Subject Name!",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                                    else if ((isThereSubject(sname))) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Subject with the same name exists !");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
                                    }

                                    else {
                                    Adddata(sname, cname);
                                    prepareAlbums(sname, cname);
                                    }



                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                builder.show();


            }
        });
        return view;
    }

    public void viewAll(DatabaseHelper myDb){
        System.out.println("viewall");


                Cursor res  = myDb.getAllData();
                if(res.getCount()==0)
                { //showMessage("error","Nothing found");
                  //  return;
                    }
               // StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                   /* buffer.append("Id: " + res.getString(0)+"\n");
                    buffer.append("Name: " + res.getString(1)+"\n");
                    buffer.append("Surname: " + res.getString(2)+"\n");
                    buffer.append("Mark: " + res.getString(3)+"\n\n");*/
                   prepareAlbums(res.getString(1),res.getString(2));

                }
                //showMessage("Data",buffer.toString());

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

    private void prepareAlbums(String sname,String cname) {
        int[] covers = new int[]{
                R.drawable.idea,
                R.drawable.archives,
                R.drawable.teacher,
                R.drawable.teacher1,
                R.drawable.teacher2,
        };

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<covers.length;i++) {
            list.add(covers[i]);
        }

        Collections.shuffle(list);

        Calbum a = new Calbum(sname,cname,list.get(0));
        albumList.add(a);


        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void Adddata(String subject,String code){
                boolean isInserted = myDb.insertData(subject,code);
                if(isInserted==true)
                    Toast.makeText(getActivity(),"Data Insrted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(),"Data not Insrted",Toast.LENGTH_LONG).show();




    }

    public boolean isThereSubject(String subject){
        Cursor res=myDb.getSubject(subject);
        if(res.getCount()==0){
            return false;
        }
        return true;
    }


}
