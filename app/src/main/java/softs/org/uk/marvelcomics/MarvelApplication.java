package softs.org.uk.marvelcomics;

import android.app.Application;

import softs.org.uk.marvelcomics.api.MarvelRestClient;

/**
 * Created by Fernando on 11/06/2017.
 */

public class MarvelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MarvelRestClient.init(this);
    }
}
