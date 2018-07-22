package room323.puzzlr;

import android.util.Log;

import com.cloudinary.android.MediaManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Frisbee {

    private static final String TAG = "FRISBEE";

    public static String uploadFile(String path) {

        File file = new File(path);
        if (file.exists()) {
            String requestId = MediaManager.get().upload(path).dispatch();
            Log.d(TAG, requestId);
            return requestId;
        } else {
            Log.d(TAG, path + "not found");
            return "File not found";
        }
    }

    public static void uploadAndNotifyFlask(String path, int pieces) {
        String id = uploadFile(path);
        try {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("pieces", pieces);
            notifyFlask(json);
        }
        catch (JSONException e) {
            ;
        }
    }

    public static void notifyFlask(final JSONObject json) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://dry-falls-41246.herokuapp.com/process_image");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(json.toString());

                    os.flush();
                    os.close();

                    Log.d(TAG, String.valueOf(conn.getResponseCode()));
                    Log.d(TAG, conn.getResponseMessage());
                    InputStream is = conn.getInputStream();
                    Scanner s = new Scanner(is).useDelimiter("\\A");
                    String result = s.hasNext() ? s.next() : "";
                    Log.d(TAG, result);

                    conn.disconnect();
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }
        });
        thread.start();
    }

}
