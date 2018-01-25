package nubella.purplecommerce.com.showroomcatalogue.AppUtitlity;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by purplecommerce on 12/01/18.
 */

public class Utility {

    public static int calculateNoOfColumns(Context context , int columnSize) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / columnSize);
        return noOfColumns;
    }

    public static void clearParentsBackgrounds(View view) {
        while (view != null) {
            final ViewParent parent = view.getParent();
            if (parent instanceof View) {
                view = (View) parent;
                view.setBackgroundResource(android.R.color.transparent);
            } else {
                view = null;
            }
        }
    }



}
