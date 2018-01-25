package nubella.purplecommerce.com.showroomcatalogue.Database;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by purplecommerce on 23/01/18.
 */

public class Categories2 extends RealmObject implements Serializable {


    /**
     * categoryId : 37
     * name : Test1
     * parentId : 0
     * isActive : true
     * CategoryLevel : 0
     * SortOrder : 0
     * Category_icon :
     */
    @PrimaryKey
    private String categoryId;
    private String name;
    private String parentId;
    private boolean isActive;
    private int CategoryLevel;
    private String SortOrder;
    private String Category_icon;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getCategoryLevel() {
        return CategoryLevel;
    }

    public void setCategoryLevel(int CategoryLevel) {
        this.CategoryLevel = CategoryLevel;
    }

    public String getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(String SortOrder) {
        this.SortOrder = SortOrder;
    }

    public String getCategory_icon() {
        return Category_icon;
    }

    public void setCategory_icon(String Category_icon) {
        this.Category_icon = Category_icon;
    }
}
