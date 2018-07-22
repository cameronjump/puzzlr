package room323.puzzlr;

import android.content.Context;
import android.util.Log;

import com.cloudinary.android.MediaManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static room323.puzzlr.PieceMatchActivity.pieceid;
import static room323.puzzlr.PieceMatchActivity.pieces;
import static room323.puzzlr.PieceMatchActivity.refid;

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

    public static void notifyFlask(PieceMatchHeatmapActivity context, String refid, String pieceid, int pieces) {
        try {
            JSONObject json = new JSONObject();
            Log.d(TAG, pieceid);
            Log.d(TAG, refid);
            Log.d(TAG, String.valueOf(pieces));
            json.put("id", pieceid);
            json.put("puzzle_id", refid);
            json.put("num_pieces", pieces);
            Log.d(TAG, json.toString());
            notifyFlask(context, json);
        }
        catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
    }

    public static void notifyFlask(final PieceMatchHeatmapActivity context, final JSONObject json) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    URL url = new URL("https://intense-ocean-43816.herokuapp.com/process_image?id="+pieceid+"&puzzle_id="+refid+"&num_pieces="+pieces);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    //DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(json.toString()));
                    //os.writeBytes(json.toString());
                    //os.write("hey man lets go to bed soon");

                    //os.flush();
                    //os.close();

                    Log.d(TAG, String.valueOf(conn.getResponseCode()));
                    Log.d(TAG, conn.getResponseMessage());
                    InputStream is = conn.getInputStream();
                    Scanner s = new Scanner(is).useDelimiter("\\A");
                    String result = s.hasNext() ? s.next() : "";
                    Log.d(TAG, result);

                    double[][] d = MyUtils.johnsMethod(result);
                    context.runThread(d);

                    conn.disconnect();
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }
        });
        thread.start();
    }

}
