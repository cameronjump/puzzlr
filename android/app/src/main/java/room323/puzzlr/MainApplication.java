package room323.puzzlr;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Map config = new HashMap();
        config.put("cloud_name", "puzzlr");
        config.put("api_key", "842712465463678");
        config.put("api_secret", "jbTSoQXh8f_Phg3de2PoWL1WPBg");
        MediaManager.init(this, config);
    }
}
