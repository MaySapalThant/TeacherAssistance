package com.example.admin.teacherassistance;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class StudentCardViewAdapter extends
        RecyclerView.Adapter<StudentCardViewAdapter.ViewHolder> {
    private List<Student> stList;
    Context mcontext;

    public StudentCardViewAdapter(List<Student> students,Context mcontext) {
        this.stList = students;
        this.mcontext=mcontext;
    }

    // Create new views
    @Override
    public StudentCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.student_info_card_view, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.roll.setText(stList.get(position).getRollNumber());
        viewHolder.tvName.setText(stList.get(position).getName());

        //viewHolder.tvEmailId.setText(stList.get(position).getEmailId());
        viewHolder.elemail.setText(stList.get(position).getElearningemail());
        viewHolder.elid.setText(stList.get(position).getElearningid());

       final String ph = stList.get(position).getPhonenumber();
        viewHolder.btnClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ph1="tel:"+ph;
                Uri phone = Uri.parse(ph1);
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, phone);
                mcontext.startActivity(phoneIntent);



            }
        });

        final String email=stList.get(position).getEmailId();
        viewHolder.gmailClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] emailAddress = {email};
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.setType("text/plain");
                mailIntent.putExtra(Intent.EXTRA_EMAIL  ,emailAddress);
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                mailIntent.putExtra(Intent.EXTRA_TEXT   , "Hello");
                mcontext.startActivity(Intent.createChooser(mailIntent, "Choose Mail App"));



            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;

        public TextView roll;
        public TextView elid;
        public TextView elemail;


        public ImageButton btnClick,gmailClick;

        public Student singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);

            //tvEmailId = (TextView) itemLayoutView.findViewById(R.id.tvEmailId);

            roll = (TextView)itemLayoutView.findViewById(R.id.tvroll);
            elid = (TextView)itemLayoutView.findViewById(R.id.elid);
            elemail = (TextView)itemLayoutView.findViewById(R.id.elemail);
            btnClick = (ImageButton)itemLayoutView.findViewById(R.id.btnClick);
            gmailClick = (ImageButton)itemLayoutView.findViewById(R.id.btngmail);


        }

    }

    // method to access in activity after updating selection
    public List<Student> getStudentist() {
        return stList;
    }
}
