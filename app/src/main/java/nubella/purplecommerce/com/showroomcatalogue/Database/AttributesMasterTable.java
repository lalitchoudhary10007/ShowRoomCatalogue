package nubella.purplecommerce.com.showroomcatalogue.Database;

import io.realm.RealmObject;

/**
 * Created by purplecommerce on 22/09/17.
 */

public class AttributesMasterTable extends RealmObject {

    private String product_id ;
    private String attribute_code ;
    private String attribute_name ;
    private String attribute_entry_type ;
    private String attribute_type ;
    private String attribute_compare ;
    private String attribute_filter ;
    private String attribute_show ;
    private String attribute_value ;
    private String attribute_value_key ;
    private String attribute_value_ref ;



    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAttribute_code() {
        return attribute_code;
    }

    public void setAttribute_code(String attribute_code) {
        this.attribute_code = attribute_code;
    }

    public String getAttribute_name() {
        return attribute_name;
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
    }

    public String getAttribute_type() {
        return attribute_type;
    }

    public void setAttribute_type(String attribute_type) {
        this.attribute_type = attribute_type;
    }

    public String getAttribute_compare() {
        return attribute_compare;
    }

    public void setAttribute_compare(String attribute_compare) {
        this.attribute_compare = attribute_compare;
    }

    public String getAttribute_filter() {
        return attribute_filter;
    }

    public void setAttribute_filter(String attribute_filter) {
        this.attribute_filter = attribute_filter;
    }

    public String getAttribute_show() {
        return attribute_show;
    }

    public void setAttribute_show(String attribute_show) {
        this.attribute_show = attribute_show;
    }

    public String getAttribute_value() {
        return attribute_value;
    }

    public void setAttribute_value(String attribute_value) {
        this.attribute_value = attribute_value;
    }

    public String getAttribute_value_key() {
        return attribute_value_key;
    }

    public void setAttribute_value_key(String attribute_value_key) {
        this.attribute_value_key = attribute_value_key;
    }

    public String getAttribute_value_ref() {
        return attribute_value_ref;
    }

    public void setAttribute_value_ref(String attribute_value_ref) {
        this.attribute_value_ref = attribute_value_ref;
    }

    public String getAttribute_entry_type() {
        return attribute_entry_type;
    }

    public void setAttribute_entry_type(String attribute_entry_type) {
        this.attribute_entry_type = attribute_entry_type;
    }
}
