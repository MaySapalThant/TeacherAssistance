package com.example.admin.teacherassistance;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.valueOf;

public class GroupGenerate extends AppCompatActivity {


    SharedPreferences prf;
    String subject="";
    DatabaseHelper myDb;



    private RecyclerView recyclerView;
    private GroupCardAdapter adapter;
    private List<GroupCard> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_generate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Groups");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new GroupCardAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if((getIntent().getExtras().getCharSequence("NUM")).toString().equals("NotNew")){
            displayGroups();
        }
        else{
        int groupmem = Integer.parseInt((getIntent().getExtras().getCharSequence("NUM")).toString());
        prepareGroups(groupmem);}

        /*try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GroupGenerate.this, "Play !", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
  /*  private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }*/

    //DisplayGroupFromDatabase
    private void displayGroups(){
        String name="";
        String roll="";
       int gpcount=1;
        myDb = new DatabaseHelper(this);
        prf = getSharedPreferences("user_detail", MODE_PRIVATE);
        subject = prf.getString("subname", "Your Name");
        Cursor allGroup=myDb.getSubjectGroup(prf.getString("subname", "Your Name"));
       while (allGroup.moveToNext()) {
           name="";
           roll="";
            Cursor group = myDb.getIndividualSubjectGroup(prf.getString("subname", "Your Name"), valueOf(gpcount));
            if(group.getCount()==0)
            break;

            while (group.moveToNext()) {

                int stuid=group.getInt(2);
                String[] myString = new String[2];
                Cursor student=myDb.getIndividualStudent(stuid);
                while (student.moveToNext()){
                    myString[0]=student.getString(2);
                    myString[1]=student.getString(1);
                }
                name+=myString[0]+ "\n";
                roll += myString[1] + "\n";



            }
           GroupCard a = new GroupCard(name, roll, gpcount);
           albumList.add(a);
           gpcount++;
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * Adding few albums for testing
     */
    private void prepareGroups(int groupmem) {


        myDb = new DatabaseHelper(this);
        prf = getSharedPreferences("user_detail", MODE_PRIVATE);
        subject = prf.getString("subname", "Your Name");

        ArrayList<String> list = new ArrayList<String>();
        //ArrayList<String[]> newlist = new ArrayList<String[]>();

        Cursor res = myDb.getStudent(prf.getString("subname", "Your Name"));
        while (res.moveToNext()) {
            String s=valueOf(res.getInt(0));
            list.add(s);

        }

        Collections.shuffle(list);


        int total=res.getCount();
        int extraStuCount=total-((int)(total/groupmem)*groupmem);
        ArrayList<String> extralist=new ArrayList<String>(extraStuCount);

        if(extraStuCount==0) {

            int gpcount = 0;


            for (int i = 0; i < res.getCount(); i += groupmem) {

                gpcount++;
                for (int j = i; j < i + groupmem; j++) {
                    String myString = new String();
                    myString = list.get(j);
                    myDb.insertGroup(valueOf(gpcount),Integer.parseInt(myString),prf.getString("subname", "Your Name"));
                }
            }
        }


        if(extraStuCount>0){
            for(int i=extraStuCount;i>=1;i--) {
                extralist.add(list.get(list.size() - i));
            }
        }


        if(extraStuCount<Math.ceil(groupmem/2)) {

            int gpcount = 0;


            for (int i = 0; i < res.getCount()-extraStuCount; i += groupmem) {

                gpcount++;
                for (int j = i; j < i + groupmem; j++) {
                    String myString = new String();
                    myString = list.get(j);
                    myDb.insertGroup(valueOf(gpcount),Integer.parseInt(myString),prf.getString("subname", "Your Name"));
                }
            }

            gpcount=1;
            for(int j=0;j<extraStuCount;j++){

                String myString = new String();
                myString = extralist.get(j);
                myDb.insertGroup(valueOf(gpcount),Integer.parseInt(myString),prf.getString("subname", "Your Name"));
                gpcount++;
            }

        }



        if(extraStuCount>=Math.ceil(groupmem/2)){

            int gpcount = 0;

            for (int i = 0; i < res.getCount()-extraStuCount; i += groupmem) {

                gpcount++;
                for (int j = i; j < i + groupmem; j++) {
                    String myString = new String();
                    myString = list.get(j);
                    myDb.insertGroup(valueOf(gpcount),Integer.parseInt(myString),prf.getString("subname", "Your Name"));
                }
            }
            gpcount++;
            for(int i=0;i<extraStuCount;i++) {
               String extraStuString=new String();
                      extraStuString = extralist.get(i);
                myDb.insertGroup(valueOf(gpcount), Integer.parseInt(extraStuString), prf.getString("subname", "Your Name"));
            }

        }



        displayGroups();

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
}