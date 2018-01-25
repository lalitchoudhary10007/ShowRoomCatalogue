package nubella.purplecommerce.com.showroomcatalogue.Database;

import io.realm.RealmObject;

/**
 * Created by purplecommerce on 22/01/18.
 */

public class CategorySortOrder extends RealmObject {


    private String categoryID ;
    private String sortOrder ;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
