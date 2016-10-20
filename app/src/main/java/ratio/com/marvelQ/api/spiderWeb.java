package ratio.com.marvelQ.api;

/**
 * Created by Fer on 3/5/15.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ratio.com.marvelQ.BuildConfig;
import ratio.com.marvelQ.Game.event.GameEvent;

public class spiderWeb extends AsyncTask<String, Void, String> {

    private static final String TAG = "SpiderWeb";
    private String dataField;
    //private Context context;
    private boolean success;
    private int type;
    private String method;
    private String URL;


    public AsyncResponse callback=null;

    public spiderWeb(AsyncResponse callback,String method) {
        this.callback = callback;
         success=true;
        this.method = method;
        URL = BuildConfig.backendurl;

    }


    @Override
    protected String doInBackground(String... arg0) {

        type =  Integer.parseInt    (arg0[0]);

            try{

                String link = (String)arg0[1];
                if(type != GameEvent.OPTIONS)link = URL+link;
                Log.d("link", link);
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //conn.setReadTimeout(500);
                //conn.setConnectTimeout(1000);
                conn.setRequestMethod(method);
                conn.setDoInput(true);

                if(method.equals("POST")){
                    String urlParameters = arg0[2];
                    byte[] postData = urlParameters.getBytes();
                    int    postDataLength = postData.length;
                    conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.flush();
                    wr.close();
                   /* try( OutputStreamWriter wr = new OutputStreamWriter( conn.getOutputStream())) {
                        wr.write( postData );
                    }*/
                }

                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader
                        (is, "UTF-8") );
                String data = null;
                String webPage = "";
                while ((data = reader.readLine()) != null){
                    webPage += data + "\n";
                }
                return webPage;
            }catch(Exception e){
                success = false;
                Log.d(TAG,"Exception: "+e.getMessage());
                return new String("Exception: " + e.getMessage());
            }


    }

    @Override
    protected void onPostExecute(String result){
        Log.d(TAG,"success:"+success);
        Log.d(TAG,result);
        callback.processFinish(result,type,success);
    }

    protected void onPreExecute(){

    }
}