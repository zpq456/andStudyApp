package kr.ac.hansung.photogallery;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Owner on 2016-08-19.
 */
public class FlickrFetchr {//String으로 Url을 참조하는 연결 객체를 만들고 HttpURLConnection타입으로 캐스팅 해서 사용한다.
    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "07b560af986fec995938195594200816";

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public void fetchItems(){
        try{
            String url = Uri.parse("http://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photo.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG,"Received JSON: " + jsonString);
        }catch (IOException ioe){
            Log.e(TAG,"Failed to fetch items",ioe);
        }
    }
}
