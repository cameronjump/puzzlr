package room323.puzzlr;

import android.util.Log;

import com.cloudinary.android.MediaManager;

import java.io.File;

public class Frisbee {

    private static void uploadFile(String path) {

        File file = new File(path);
        if (file.exists()) {
            String requestId = MediaManager.get().upload(path).dispatch();
            Log.d("Frisbee", requestId);
        } else {
            Log.d("Frisbee", path + "not found");
        }
    }
}
