package com.example.admin.teacherassistance;

import android.content.Context;
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

public class GroupCardAdapter extends RecyclerView.Adapter<GroupCardAdapter.MyViewHolder> {

    private Context mContext;
    private List<GroupCard> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,rollnum,gpnum;
        // public ImageView thumbnail, overflow;

        private GroupCard album;

        public MyViewHolder(View view) {
            super(view);
            gpnum=(TextView)view.findViewById(R.id.tvGpnum);
            title = (TextView) view.findViewById(R.id.title);
            rollnum=(TextView)view.findViewById(R.id.tvRollnum);
            //count = (TextView) view.findViewById(R.id.count);
            //thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);

            /*overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(overflow);
                }
            });*/

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "Click on "+album.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bindData(GroupCard album) {

            this.album = album;
            title.setText(album.getName());
            rollnum.setText(album.getRollnum());
            gpnum.setText("GROUP "+album.getGpnum());

            // loading album cover using Glide library
            //Glide.with(mContext).load(album.getThumbnail()).into(thumbnail);
        }
    }

    public GroupCardAdapter(Context mContext, List<GroupCard> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        GroupCard album = albumList.get(position);
        holder.bindData(album);
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_group, popup.getMenu());
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
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}