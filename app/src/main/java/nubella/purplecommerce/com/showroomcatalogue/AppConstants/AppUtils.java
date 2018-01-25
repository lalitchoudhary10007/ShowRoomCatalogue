package nubella.purplecommerce.com.showroomcatalogue.AppConstants;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by purplecommerce on 22/01/18.
 */

public class AppUtils {


 public static String BASE_URL = "http://52.66.128.247/showroomapp/store_iphone/mobile_api/" ;
 public static String IMAGE_BASE_URL = "http://52.66.128.247/showroomapp/store_iphone/" ;
 public static final String AllCategoriesUrl = BASE_URL+"allcategories.php";
 public static final String AllProductsUrl = BASE_URL+"allproducts.php?size=10&page=1" ;


 public static final String MEDIA_DIRECTORY = "/nubella/";
 public static final String MEDIA_CHARACTER= "temp/";
 public static final String MEDIA_COLLECTIONS= "tmp/.nomedia/";
 public static final String MEDIA_EXTENSION = ".jpeg";



 public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
  return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
 }

 private static String getBytesToMBString(long bytes){
  return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
 }


 public static String getRootDirPath(Context context) {
  if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
   File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
           null)[0];
   return file.getAbsolutePath();
  } else {
   return context.getApplicationContext().getFilesDir().getAbsolutePath();
  }
 }


 public static List<String> StringToArrayList(String s){

  List<String> items = Arrays.asList(s.split("\\s*,\\s*"));
  return items ;
 }





}
