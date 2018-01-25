package nubella.purplecommerce.com.showroomcatalogue.Database;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by purplecommerce on 19/09/17.
 */

public class ProductsTable extends RealmObject implements Serializable {

    private String product_id ;
    private String isVarient ;
    private String product_sku ;
    private String category_sort_array ;
    private String name ;
    private String product_base_image ;
    private String price ;
    private String special_price ;
    private String price_symbol ;
    private String stock_level ;
    private boolean availablity ;
    private String short_description ;
    private String UpsellProducts ;
    private String MatchingProducts ;
    private String rating ;
    private RealmList<ImageGallery> gallery_images ;



    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory_sort_array() {
        return category_sort_array;
    }

    public void setCategory_sort_array(String category_sort_array) {
        this.category_sort_array = category_sort_array;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(String special_price) {
        this.special_price = special_price;
    }


    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getIsVarient() {
        return isVarient;
    }

    public void setIsVarient(String isVarient) {
        this.isVarient = isVarient;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(String product_sku) {
        this.product_sku = product_sku;
    }

    public String getProduct_base_image() {
        return product_base_image;
    }

    public void setProduct_base_image(String product_base_image) {
        this.product_base_image = product_base_image;
    }

    public String getPrice_symbol() {
        return price_symbol;
    }

    public void setPrice_symbol(String price_symbol) {
        this.price_symbol = price_symbol;
    }

    public String getStock_level() {
        return stock_level;
    }

    public void setStock_level(String stock_level) {
        this.stock_level = stock_level;
    }

    public boolean getAvailablity() {
        return availablity;
    }

    public void setAvailablity(boolean availablity) {
        this.availablity = availablity;
    }


    public RealmList<ImageGallery> getGallery_images() {
        return gallery_images;
    }

    public void setGallery_images(RealmList<ImageGallery> gallery_images) {
        this.gallery_images = gallery_images;
    }

    public String getUpsellProducts() {
        return UpsellProducts;
    }

    public void setUpsellProducts(String upsellProducts) {
        UpsellProducts = upsellProducts;
    }

    public String getMatchingProducts() {
        return MatchingProducts;
    }

    public void setMatchingProducts(String matchingProducts) {
        MatchingProducts = matchingProducts;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}





