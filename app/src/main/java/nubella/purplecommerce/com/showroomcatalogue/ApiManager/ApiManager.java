package nubella.purplecommerce.com.showroomcatalogue.ApiManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;

import nubella.purplecommerce.com.showroomcatalogue.AppConstants.AppUtils;
import nubella.purplecommerce.com.showroomcatalogue.AppUtitlity.Utility;
import nubella.purplecommerce.com.showroomcatalogue.R;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

/**
 * Created by purplecommerce on 04/10/17.
 */

public class ApiManager {

    public Context context;
    String url;
    HashMap map;
    GsonBuilder gsonBuilder;
    Gson gson;
    APIFETCHER apifetcher;
    public static OkHttpClient okHttpClient ;
    Dialog progress_dialog ;
    private static final String TAG = "APIExecution";


//    OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
//        @Override
//        public Request authenticate(Route route, Response response) throws IOException {
//
//            String credential = Credentials.basic("0ab63a19-d325-4439-90ba-a845611bacbe" , "");
//
//
//            return response.request().newBuilder().header("Authorization" , credential).build();
//        }
//    }).build();



    public ApiManager(Context ctc , APIFETCHER apifetcher ) {
        this.context = ctc;
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        map = new HashMap();
        this.apifetcher = apifetcher;


        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1 = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(context);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v1);
        Utility.clearParentsBackgrounds(v1);

    }


    @SuppressLint("LongLogTag")
    public void execution_method_post(final String tag, String url , HashMap<String , String > bodyparameter) {
        Log.d("** Executing API (POST) ", "Hashparameters => " +bodyparameter);
        Log.d("** Executing API (POST) ", "Name => " + tag + "  " + "URL => " + url);
        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STARTED);
        progress_dialog.show();
        AndroidNetworking.post(AppUtils.BASE_URL+url)
                .addBodyParameter(bodyparameter)
                .setTag(this)
                .setOkHttpClient(okHttpClient)
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        Log.d("*** RESPONSE ", "" + jsonObject);
                        progress_dialog.dismiss();
                        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                        apifetcher.onFetchComplete(""+jsonObject, tag , gson);
                    }

                    @Override
                    public void onError(ANError anError) {
                        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                        apifetcher.onFetchFailed(anError);
                        progress_dialog.dismiss();
                        Log.e("errror", "" + anError.getErrorBody());
                        Log.e("errror", "" + anError.getErrorDetail());
                        Log.e("errror", "" + anError.getMessage());
                        Log.e("error", "" + anError.getStackTrace());
                        Log.e("error", "" + anError.getCause());
                        if (anError.getErrorDetail().equals("connectionError")){
                            Toast.makeText(context, "Check Your Internet !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void execution_method_get(final String tag, String url) {

        Log.d("** Executing API ", "Name => " + tag + "  " + "URL => " + url);

        progress_dialog.show();
        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STARTED);
        AndroidNetworking.get(AppUtils.BASE_URL+url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                }).getAsString(new StringRequestListener() {


            @Override
            public void onResponse(String response) {
                Log.d("*** RESPONSE ", "" + response);
                progress_dialog.dismiss();
                apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                apifetcher.onFetchComplete(response, tag, gson);
            }

            @Override
            public void onError(ANError anError) {
                progress_dialog.dismiss();
                apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                apifetcher.onFetchFailed(anError);
                Log.e("errror", "" + anError.getErrorBody());
                Log.e("errror", "" + anError.getErrorDetail());
                Log.e("errror", "" + anError.getMessage());
                Log.e("error", "" + anError.getStackTrace());
                Log.e("error", "" + anError.getCause());
                if (anError.getErrorDetail().equals("connectionError")){
                    Toast.makeText(context, "Check Your Internet !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void Execution_method_get(final String tag, String url) {

        Log.d("** Executing API ", "Name => " + tag + "  " + "URL => " + url);

        //    progress_dialog.show();


        apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STARTED);
        AndroidNetworking.get(url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                }).getAsString(new StringRequestListener() {


            @Override
            public void onResponse(String response) {
                Log.d("*** RESPONSE ", "" + response);
                progress_dialog.dismiss();
                apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                apifetcher.onFetchComplete(response, tag, gson);
            }

            @Override
            public void onError(ANError anError) {
                progress_dialog.dismiss();
                apifetcher.onAPIRunningState(APIFETCHER.KEY_API_IS_STOPPED);
                apifetcher.onFetchFailed(anError);
                Log.e("errror", "" + anError.getErrorBody());
                Log.e("errror", "" + anError.getErrorDetail());
                Log.e("errror", "" + anError.getMessage());
                Log.e("error", "" + anError.getStackTrace());
                Log.e("error", "" + anError.getCause());
                if (anError.getErrorDetail().equals("connectionError")){
                    Toast.makeText(context, "Check Your Internet !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public interface APIFETCHER {

        public static int KEY_API_IS_STARTED = 0;
        public static int KEY_API_IS_RUNNING = 1;
        public static int KEY_API_IS_STOPPED = 2;
        public static int KEY_API_IS_ERRORED = 3;

        void onAPIRunningState(int a);  // state - API Starts(0) , API Running(1) , API Stops(2)  API Error(3)

        void onFetchProgress(int progress);  // This Will useful when downloading file

        void onFetchComplete(String script, String APINAME, Gson gson); // This will give the full script

        void onFetchFailed(ANError error);  // This will give the cause of error if occurred

        void WhichApi(String APINAME);
    }
}