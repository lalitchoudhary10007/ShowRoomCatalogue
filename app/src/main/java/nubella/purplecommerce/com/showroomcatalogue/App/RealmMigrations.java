package nubella.purplecommerce.com.showroomcatalogue.App;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by purplecommerce on 04/12/17.
 */

public class RealmMigrations implements RealmMigration {


    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

        if (oldVersion == 1) {
//            final RealmObjectSchema userSchema = schema.get("UserData");
//            userSchema.addField("age", int.class);

  //          RealmObjectSchema realmObjectSchema = schema.get("AddressesTable");


        }
    }




}
