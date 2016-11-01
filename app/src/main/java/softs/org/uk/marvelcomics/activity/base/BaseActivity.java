package softs.org.uk.marvelcomics.activity.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import softs.org.uk.marvelcomics.R;

/**
 * Created by Fernando Bonet on 31/10/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    protected abstract int getLayoutResource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        setupToolbar();
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setActionBarHomeEnable(false);
        }
    }

    public void setActionBarHomeEnable(boolean enable) {
        if (mToolbar != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(enable);
            actionBar.setDisplayShowHomeEnabled(enable);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }

}
