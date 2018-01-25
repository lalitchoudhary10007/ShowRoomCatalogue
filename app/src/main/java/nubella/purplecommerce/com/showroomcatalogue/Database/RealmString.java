package nubella.purplecommerce.com.showroomcatalogue.Database;

import io.realm.RealmObject;

/**
 * Created by purplecommerce on 22/01/18.
 */

public class RealmString extends RealmObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

