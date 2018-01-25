package nubella.purplecommerce.com.showroomcatalogue.Database;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by purplecommerce on 19/09/17.
 */

public class CategoryDBManager {

    public static Realm myRealm;
    public static Context con ;

    public CategoryDBManager(Context con){
        this.con = con ;
        myRealm = Realm.getDefaultInstance();
    }

    public void SaveNewCategory(final JSONObject json){

//        if(AlreadySavedOrNot(category_id)){
//            UpdateInDatabase(category_id , category_image , is_active , level , category_name , parent_id);
//        }else {
            /////  create new row in database
          //  SaveInDatabase(category_id , category_image , is_active , level , category_name , parent_id , SortOrder);
      //  }

        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(Categories2.class, json);
            }
        });


    }

    private void SaveInDatabase(int category_id , String category_image ,  boolean is_active , int level ,
                                String category_name  , int parent_id , String SortOrder) {

        myRealm.beginTransaction();
        CategoriesTable pd = myRealm.createObject(CategoriesTable.class);
        pd.setCategory_id(category_id);
        pd.setCategory_image(category_image);
        pd.setIs_active(is_active);
        pd.setLevel(level);
        pd.setName(category_name);
        pd.setParent_id(parent_id);
        pd.setSortOrder(SortOrder);
        myRealm.commitTransaction();

    }


    private  boolean AlreadySavedOrNot(int categoryid){
        boolean value  = false;
        if(myRealm.where(CategoriesTable.class)
                .equalTo("category_id", categoryid)
                .count()==0)
        {
            value = false ;
        }else {
            value = true ;
        }

        return  value ;

    }


    public void UpdateInDatabase(int category_id , String category_image ,  boolean is_active , int level ,
                                  String category_name  , int parent_id , String SortOrder) {


        CategoriesTable tobechangedelement =
                myRealm.where(CategoriesTable.class)
                        .equalTo("category_id", category_id)
                        .findFirst();

        myRealm.beginTransaction();
        tobechangedelement.setCategory_image(category_image);
        tobechangedelement.setIs_active(is_active);
        tobechangedelement.setLevel(level);
        tobechangedelement.setName(category_name);
        tobechangedelement.setParent_id(parent_id);
        tobechangedelement.setSortOrder(SortOrder);
        myRealm.commitTransaction();
    }


    public boolean CategoryTableIsEmpty(){
        RealmResults<Categories2> results = myRealm.where(Categories2.class).findAll();

        if (results.isEmpty()){
            return true ;
        }else {
            return false ;
        }

    }

    public RealmResults<Categories2> GetAllCategories(){
        RealmResults<Categories2> results = myRealm.where(Categories2.class).findAll();
        return  results ;
    }

    public RealmResults<Categories2> GetOnlyActiveCategoris(){
        RealmResults<Categories2> results = myRealm.where(Categories2.class).equalTo("isActive" , true).findAll();

        return results ;
    }


    public void DeleteCategoryAccordingId(int categoryid){

        CategoriesTable ct = myRealm.where(CategoriesTable.class).equalTo("category_id" , categoryid).findFirst();
        if (ct != null){
            ct.deleteFromRealm();
        }else {
            Toast.makeText(con, "Category not found in DB", Toast.LENGTH_SHORT).show();
        }

    }





    public  void ClearCategoriesTable(){
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Categories2.class);
            }
        });

    }




}
