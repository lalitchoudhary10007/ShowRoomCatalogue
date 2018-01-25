package nubella.purplecommerce.com.showroomcatalogue.Database;

import io.realm.RealmObject;

/**
 * Created by purplecommerce on 22/11/17.
 */

public class ImageGallery extends RealmObject {

    private int image_key ;
    private String image_url ;


    public int getImage_key() {
        return image_key;
    }

    public void setImage_key(int image_key) {
        this.image_key = image_key;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }



}
