package nubella.purplecommerce.com.showroomcatalogue.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import me.zhanghai.android.materialratingbar.MaterialRatingDrawable;
import nubella.purplecommerce.com.showroomcatalogue.R;

/**
 * Created by purplecommerce on 18/01/18.
 */

public class CollectionProductsAdapter extends RecyclerView.Adapter<CollectionProductsAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context ctc ;
    ArrayList<Float> ratings ;
    ArrayList<Drawable> imgs ;
    int removeIcons ;


    public CollectionProductsAdapter(Context context, ArrayList<Drawable> product_images,
                                     ArrayList<Float> product_ratings,
                                     int removedIcon) {
        this.mInflater = LayoutInflater.from(context);
        this.ctc = context ;
        this.ratings = product_ratings ;
        this.imgs = product_images ;
        this.removeIcons = removedIcon ;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_recycleritem_layout, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productImage.setImageDrawable(imgs.get(position));
        holder.ratingBar.setIsIndicator(true);
        holder.ratingBar.setRating(ratings.get(position));


    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialRatingBar ratingBar;

        ImageView productImage , delete ;
        ViewHolder(View itemView) {
            super(itemView);
            ratingBar = (MaterialRatingBar) itemView.findViewById(R.id.rating_bar);
            productImage = (ImageView) itemView.findViewById(R.id.img_product);
            delete = (ImageView)itemView.findViewById(R.id.remove_img);

            if (removeIcons == 0){
                delete.setVisibility(View.VISIBLE);
            }else {
                delete.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onProductClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onProductClick(View view, int position);
    }



}
