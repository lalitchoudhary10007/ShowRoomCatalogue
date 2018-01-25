package nubella.purplecommerce.com.showroomcatalogue.POJO_Models;

import java.util.List;

/**
 * Created by purplecommerce on 21/11/17.
 */

public class All_CategoriesResponse {


    /**
     * code : 0
     * msg : Changelog Lists Success !
     * model : [{"categoryId":"37","name":"Test1","parentId":"0","isActive":true,"CategoryLevel":0,"SortOrder":"0","Category_icon":""},{"categoryId":"22","name":"Washing Machines","parentId":"0","isActive":true,"CategoryLevel":0,"SortOrder":"4","Category_icon":""},{"categoryId":"25","name":"Televisions","parentId":"0","isActive":true,"CategoryLevel":0,"SortOrder":"7","Category_icon":""},{"categoryId":"23","name":"Iphones","parentId":"0","isActive":true,"CategoryLevel":0,"SortOrder":"5","Category_icon":""},{"categoryId":"10","name":"Laptops","parentId":"0","isActive":true,"CategoryLevel":0,"SortOrder":"3","Category_icon":"images/Category_10/banner6.jpg"},{"categoryId":"24","name":"Air Conditioners","parentId":"0","isActive":true,"CategoryLevel":0,"SortOrder":"6","Category_icon":""},{"categoryId":"3","name":"Mobiles","parentId":"0","isActive":true,"CategoryLevel":0,"SortOrder":"2","Category_icon":"images/Category_3/banner-1140x380.jpg"},{"categoryId":"6","name":"Samsung","parentId":"3","isActive":true,"CategoryLevel":1,"SortOrder":"2","Category_icon":""},{"categoryId":"8","name":"LG Nexus","parentId":"3","isActive":true,"CategoryLevel":1,"SortOrder":"4","Category_icon":""},{"categoryId":"5","name":"Lenovo","parentId":"3","isActive":true,"CategoryLevel":1,"SortOrder":"1","Category_icon":""},{"categoryId":"4","name":"Motorola","parentId":"3","isActive":true,"CategoryLevel":1,"SortOrder":"0","Category_icon":""},{"categoryId":"7","name":"Mi","parentId":"3","isActive":true,"CategoryLevel":1,"SortOrder":"3","Category_icon":""},{"categoryId":"9","name":"Apple","parentId":"3","isActive":true,"CategoryLevel":1,"SortOrder":"5","Category_icon":""},{"categoryId":"26","name":"K5 Note","parentId":"5","isActive":true,"CategoryLevel":2,"SortOrder":"0","Category_icon":""},{"categoryId":"12","name":"Dell","parentId":"10","isActive":true,"CategoryLevel":1,"SortOrder":"1","Category_icon":""},{"categoryId":"14","name":"Asus","parentId":"10","isActive":true,"CategoryLevel":1,"SortOrder":"3","Category_icon":""},{"categoryId":"11","name":"HP","parentId":"10","isActive":true,"CategoryLevel":1,"SortOrder":"0","Category_icon":""},{"categoryId":"13","name":"Lenovo","parentId":"10","isActive":true,"CategoryLevel":1,"SortOrder":"2","Category_icon":""},{"categoryId":"15","name":"Acer","parentId":"10","isActive":true,"CategoryLevel":1,"SortOrder":"4","Category_icon":""},{"categoryId":"39","name":"Test2","parentId":"37","isActive":true,"CategoryLevel":1,"SortOrder":"8","Category_icon":""}]
     */

    private String code;
    private String msg;
    private List<ModelBean> model;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ModelBean> getModel() {
        return model;
    }

    public void setModel(List<ModelBean> model) {
        this.model = model;
    }

    public static class ModelBean {
        /**
         * categoryId : 37
         * name : Test1
         * parentId : 0
         * isActive : true
         * CategoryLevel : 0
         * SortOrder : 0
         * Category_icon :
         */

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
}
