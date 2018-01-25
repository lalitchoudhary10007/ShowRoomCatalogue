package nubella.purplecommerce.com.showroomcatalogue.Database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by purplecommerce on 22/11/17.
 */

public class AttributesDBManager {


    public static Realm myRealm;
    public static Context con ;

    public AttributesDBManager(Context con){
        this.con = con ;
        myRealm = Realm.getDefaultInstance();
    }


    public void SaveNewAttribute(String product_id , String attrib_code , String attrib_name , String attrib_entry_type , String attrib_type ,
                               String attrib_compare  , String attrib_filter , String attrib_show , String attrib_value ,
                               String attrib_value_key , String attrib_value_ref){

//        if(AlreadySavedOrNot(product_id)){
//            UpdateInDatabase(product_id , isVarient , product_sku , category_id_array , product_name , product_base_image ,
//                    price , special_price , price_symbol , stock_level , availablity , availablity_status ,
//                    short_description);
//        }else {
            /////  create new row in database
            SaveInDatabase(product_id , attrib_code , attrib_name , attrib_entry_type , attrib_type , attrib_compare , attrib_filter ,
                    attrib_show , attrib_value , attrib_value_key , attrib_value_ref);
       // }

    }


    private void SaveInDatabase(String product_id , String attrib_code , String attrib_name ,String attrib_entry_type , String attrib_type ,
                                String attrib_compare  , String attrib_filter , String attrib_show , String attrib_value ,
                                String attrib_value_key , String attribref) {

        myRealm.beginTransaction();
        AttributesMasterTable pd = myRealm.createObject(AttributesMasterTable.class);
        pd.setProduct_id(product_id);
        pd.setAttribute_code(attrib_code);
        pd.setAttribute_name(attrib_name);
        pd.setAttribute_entry_type(attrib_entry_type);
        pd.setAttribute_type(attrib_type);
        pd.setAttribute_compare(attrib_compare);
        pd.setAttribute_filter(attrib_filter);
        pd.setAttribute_show(attrib_show);
        pd.setAttribute_value(attrib_value);
        pd.setAttribute_value_key(attrib_value_key);
        pd.setAttribute_value_ref(attribref);
        myRealm.commitTransaction();

    }

    public RealmResults<AttributesMasterTable> GetAttributesProduct(String productid){
        RealmResults<AttributesMasterTable> attributes = myRealm.where(AttributesMasterTable.class).equalTo("product_id" , productid).findAll();

        return attributes ;
    }




    public  void ClearAttributesTable(){
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(AttributesMasterTable.class);
            }
        });

    }




}
