package com.awok.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.R;
import com.awok.model.NavigationOption;
import com.awok.utils.Constants;


import java.util.List;


/**
 * Created by dilip on 23/1/18.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>{

    private List<NavigationOption> mNavigationOptions;
    public OnItemClickListener mOnItemClickListner;
    private int lastItemSelected = 1;


   public interface OnItemClickListener {
        void onItemClickListener(int position, String navigationOption);
    }


    public void setOnItemSelectedListener(OnItemClickListener listener ){
        mOnItemClickListner = listener;
    }

    public NavigationDrawerAdapter(List<NavigationOption> navigationOptions) {
        mNavigationOptions = navigationOptions;
        //set default selected fragment
        mNavigationOptions.get(0).setSelected(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.navigation_drawer_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivIcon.setImageResource(mNavigationOptions.get(position).getIconResource());
        holder.tvTitle.setText(mNavigationOptions.get(position).getTitle());

        if(mNavigationOptions.get(position).isSelected()){
            //holder.drawerItem.setBackgroundResource(R.color.colorAccent);
        }else {
            holder.drawerItem.setBackground(null);
        }

        if(mNavigationOptions.get(position).getTitle().equals(Constants.POPULAR)){
            //set Badge Visible
            holder.tvBadge.setVisibility(View.INVISIBLE);
            holder.tvBadge.setText("");
        }else {
            //invisible
            holder.tvBadge.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mNavigationOptions != null ? mNavigationOptions.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout drawerItem;
        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvBadge;
        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBadge = itemView.findViewById(R.id.tvBadge);
            drawerItem = itemView.findViewById(R.id.drawerItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListner != null){
                        mOnItemClickListner.onItemClickListener(getAdapterPosition(),mNavigationOptions.get(getAdapterPosition()).getTitle());
                    }
                    setItemSelection(getAdapterPosition());

                }
            });
        }
    }

    public void setItemSelection( int position) {

        mNavigationOptions.get(lastItemSelected).setSelected(false);
        mNavigationOptions.get(position).setSelected(true);
        lastItemSelected = position;
        notifyDataSetChanged();
    }
}