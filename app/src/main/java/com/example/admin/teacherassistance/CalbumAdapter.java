package com.example.admin.teacherassistance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CalbumAdapter extends RecyclerView.Adapter<CalbumAdapter.MyViewHolder> {
    private Context mContext;
    private List<Calbum> calbumList;
    DatabaseHelper myDb;
    private Calbum album;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cname, language;
        public ImageView flag, overflow;
        public CardView cardView ;
        SharedPreferences pref;
        private Calbum album;

        Context mContext;

        public MyViewHolder(View view,Context context) {

            super(view);
            this.mContext = context;
            cname = (TextView) view.findViewById(R.id.title);
            language = (TextView) view.findViewById(R.id.count);
            flag = (ImageView) view.findViewById(R.id.thumbnail);
           // overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView =(CardView)view.findViewById(R.id.card_view);

            /*overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(overflow);
                }
            });*/

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pref = mContext.getSharedPreferences("user_detail",MODE_PRIVATE);
                    Intent intent = new Intent(mContext,Subject.class);
                    mContext.startActivity(intent);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("subname",album.getCountryname());

                    editor.commit();

                    //Toast.makeText(mContext, "Click on "+album.getCountryname(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bindData(Calbum album) {

            this.album = album;
            cname.setText(album.getCountryname());
            language.setText(album.getLanguage());
            //cardView.setCardBackgroundColor(Color.TRANSPARENT);


            // loading album cover using Glide library
            Glide.with(mContext).load(album.getFlag()).into(flag);
        }
    }

    public CalbumAdapter(Context mContext, List<Calbum> calbumList) {
        this.mContext = mContext;
        this.calbumList = calbumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_card, parent, false);

        return new MyViewHolder(itemView,mContext);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Calbum album = calbumList.get(position);
        holder.bindData(album);
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_subject, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {



        public MyMenuItemClickListener() {
        }



        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
           // myDb = new DatabaseHelper(mContext);
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                   // Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    //myDb.deleteSubject("Ai");
                    return true;
                case R.id.action_play_next:
                    //Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return calbumList.size();
    }

}
