package nubella.purplecommerce.com.showroomcatalogue.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import nubella.purplecommerce.com.showroomcatalogue.Database.Categories2;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoriesTable;
import nubella.purplecommerce.com.showroomcatalogue.R;

/**
 * Created by purplecommerce on 12/01/18.
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {



    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context ctc ;
    ArrayList<Categories2> Categories ;


    public CatalogAdapter(Context context, ArrayList<Categories2> topLevelCategories) {
        this.mInflater = LayoutInflater.from(context);
        this.ctc = context ;
        this.Categories = topLevelCategories ;

    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.catalog_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(ctc)
                .load(Categories.get(position).getCategory_icon())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.no_image_available)
                .into(holder.catalogImage);

        holder.catalogName.setText(Categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView catalogName;
        ImageView catalogImage ;
        ViewHolder(View itemView) {
            super(itemView);
            catalogName = (TextView) itemView.findViewById(R.id.tv_catalog);
            catalogImage = (ImageView) itemView.findViewById(R.id.img_catalog);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
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
        void onItemClick(View view, int position);
    }

}
