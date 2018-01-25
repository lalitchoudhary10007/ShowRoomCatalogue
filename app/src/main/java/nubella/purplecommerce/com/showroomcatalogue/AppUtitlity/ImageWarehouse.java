package nubella.purplecommerce.com.showroomcatalogue.AppUtitlity;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nubella.purplecommerce.com.showroomcatalogue.AppConstants.AppUtils;

/**
 * Created by purplecommerce on 17/11/17.
 */

public class ImageWarehouse implements Callback {

    private static final String TAG = "ImageWarehouse";

    private String mDirectory;
    private String mFileName;
    private Bitmap mContainer;
    Context ctc ;
//    @Inject
//    App mApplication;


    public ImageWarehouse(Context c , String fileName, Bitmap container, String directory) {
        this.mFileName = fileName;
        this.mContainer = container;
        this.mDirectory = directory;
        this.ctc = c ;
      //  this.getStorageDir();
    }

    @Override
    public void onSuccess() {
        if (this.isExternalStorageWritable()) {
            final Bitmap bitmap = mContainer;
            new AsyncTask<Void, Void, File>() {
                @Override
                protected File doInBackground(Void... params) {
//                    File file = null;
//                    try {
//                        file = new File(ImageWarehouse.this.getStorageDir().getPath().concat("/").concat(mFileName));
//                        file.createNewFile();
//                        FileOutputStream ostream = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
//                        ostream.close();
//                    } catch (Exception e) {
//                        Log.e(TAG, "External Storage is not available");
//                    }
//                    return file;

                  //  File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);



                    ContextWrapper cw = new ContextWrapper(ctc);
                    // path to /data/data/yourapp/app_data/imageDir
                    File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    // Create imageDir
                    File mypath=new File(directory,mDirectory);

                    if (!mypath.exists()) {
                        if (!mypath.mkdir()) {
                            Log.e("***ERROR", "Cannot create a directory!");
                        } else {
                            mypath.mkdir();
                            Log.e("***SUCCESS", "directory created");
                        }
                    }

                    File file = new File(mypath , mFileName);

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        // Use the compress method on the BitMap object to write image to the OutputStream
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        Log.e(TAG , "compressed");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG , "not compressed"+e);
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG , "fos close"+e);
                        }
                    }

                    Log.e(TAG , "directory : -"+directory.getAbsolutePath());

                   return mypath ;
                }
            }.execute();
        } else {
            Log.e(TAG, "External Storage is not available");
        }
    }

    @Override
    public void onError() {

    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
         return false;
    }

    public File getStorageDir() {
        File file = new File(Environment.getExternalStorageDirectory(), AppUtils.MEDIA_DIRECTORY.concat(this.mDirectory));
        if (!file.mkdirs()) {
        }
        return file;
    }





}
