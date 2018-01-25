package nubella.purplecommerce.com.showroomcatalogue.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.utils.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.realm.RealmResults;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import me.zhanghai.android.materialratingbar.MaterialRatingDrawable;
import nubella.purplecommerce.com.showroomcatalogue.AppConstants.AppUtils;
import nubella.purplecommerce.com.showroomcatalogue.Database.ProductsTable;
import nubella.purplecommerce.com.showroomcatalogue.R;

/**
 * Created by purplecommerce on 12/01/18.
 */

public class ProductListingAdapter extends RecyclerView.Adapter<ProductListingAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context ctc ;
    RealmResults<ProductsTable> products ;


    public ProductListingAdapter(Context context, RealmResults<ProductsTable> allProducts) {
        this.mInflater = LayoutInflater.from(context);
        this.ctc = context ;
        this.products = allProducts ;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_recycleritem_layout, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // holder.productImage.setImageDrawable(imgs.get(position));
        holder.ratingBar.setIsIndicator(true);
        holder.ratingBar.setRating(5);
        Log.e("PRoduct" , "Base"+AppUtils.BASE_URL+products.get(position).getProduct_base_image());
                        Glide
                        .with(ctc)
                        .load(AppUtils.IMAGE_BASE_URL+products.get(position).getProduct_base_image())
                        .fitCenter()
                        .crossFade()
                        .error(R.drawable.no_image_available)
                        .into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialRatingBar ratingBar;
        MaterialRatingDrawable ratingDrawable ;

        ImageView productImage ;
        ViewHolder(View itemView) {
            super(itemView);
            ratingBar = (MaterialRatingBar) itemView.findViewById(R.id.rating_bar);
            productImage = (ImageView) itemView.findViewById(R.id.img_product);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onProductClick(view, getAdapterPosition());
        }
    }

//    String getItem(int id) {
//        return mData[id];
//    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onProductClick(View view, int position);
    }


}
