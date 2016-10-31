package softs.org.uk.marvelcomics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import softs.org.uk.marvelcomics.R;

/**
 * Created by Fernando Bonet on 29/10/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {

    public static final int DELAY_MILLIS = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startDelayed(MainActivity.getNewIntent(this));
    }


    private void startDelayed(final Intent intent) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, DELAY_MILLIS);
    }
}
