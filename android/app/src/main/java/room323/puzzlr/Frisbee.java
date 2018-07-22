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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Frisbee {

    private static final String TAG = "FRISBEE";

    public static String uploadFile(String path) {

        String name = "puzzlr" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        File file = new File(path);
        if (file.exists()) {
            String requestId = MediaManager.get().upload(path).option("public_id", name).dispatch();
            Log.d(TAG, name);
            return name;
        } else {
            Log.d(TAG, path + "not found");
            return "File not found";
        }
    }

    public static void uploadAndNotifyFlask(String path, int pieces, String type) {
        String id = uploadFile(path);
        try {
            JSONObject json = new JSONObject();
            json.put("id", id);
            if (type.equals("ref")) json.put("num_pieces", pieces);
            notifyFlask(json, getURL(type));
        }
        catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
    }

    private static String getURL(String type) {
        if (type.equals("piece")) return new URL("https://dry-falls-41246.herokuapp.com/process_image");
        else return "https://dry-falls-41246.herokuapp.com/upload_puzzle_board"
    }

    public static void notifyFlask(final JSONObject json, URL url) {
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
