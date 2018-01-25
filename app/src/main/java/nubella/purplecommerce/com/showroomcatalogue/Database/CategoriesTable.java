package nubella.purplecommerce.com.showroomcatalogue.Database;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by purplecommerce on 19/09/17.
 */

public class CategoriesTable extends RealmObject implements Serializable {

    private int category_id ;
    private String category_image ;
    private boolean is_active ;
    private int level ;
    private String name ;
    private int parent_id ;
    private String SortOrder ;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public boolean is_active() {
        return is_active;
    }

    public String getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(String sortOrder) {
        SortOrder = sortOrder;
    }


}
