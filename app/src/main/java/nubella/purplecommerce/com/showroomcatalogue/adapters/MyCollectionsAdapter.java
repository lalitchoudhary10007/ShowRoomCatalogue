package nubella.purplecommerce.com.showroomcatalogue.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nubella.purplecommerce.com.showroomcatalogue.R;

/**
 * Created by purplecommerce on 15/01/18.
 */

public class MyCollectionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ItemClickListener mClickListener;

    public MyCollectionsAdapter() {

    }

    public class ViewHolder0 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView catalogName;
        ImageView catalogImage ;
        ViewHolder0(View itemView) {
            super(itemView);
            catalogName = (TextView) itemView.findViewById(R.id.tv_catalog);
            catalogImage = (ImageView) itemView.findViewById(R.id.img_catalog);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onCollectionClick(view, getAdapterPosition() , "1");
        }
    }
    public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView catalogName;
        ImageView catalogImage ;
        ViewHolder1(View itemView) {
            super(itemView);
            catalogName = (TextView) itemView.findViewById(R.id.tv_catalog);
            catalogImage = (ImageView) itemView.findViewById(R.id.img_catalog);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onCollectionClick(view, getAdapterPosition() , "2");

        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous

        Log.e("position" , ""+position);

        if (5 == position){
            return 2 ;
        }else {
            return 0 ;
        }

    }

    @Override
    public int getItemCount() {

        return 6;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collections_recycler_view, parent, false);
                return new ViewHolder0(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_collection_layout, parent, false);
                return new ViewHolder1(view);

        }
        return null ;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0)holder;

                break;

            case 2:
                ViewHolder1 viewHolder2 = (ViewHolder1)holder;

                break;
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onCollectionClick(View view, int position , String type);
    }




}
