package softs.org.uk.marvelcomics.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import softs.org.uk.marvelcomics.R;
import softs.org.uk.marvelcomics.activity.base.BaseActivity;
import softs.org.uk.marvelcomics.adapter.ComicsListAdapter;
import softs.org.uk.marvelcomics.api.CustomRetrofit;
import softs.org.uk.marvelcomics.api.MarvelAPI;
import softs.org.uk.marvelcomics.model.api.ComicsRequestData;
import softs.org.uk.marvelcomics.model.object.ComicBookData;
import softs.org.uk.marvelcomics.utils.ConnectionUtils;

public class MainActivity extends BaseActivity implements Callback<ComicsRequestData> {

    public static final String LAST_TIME_STAMP = "LAST_TIME_STAMP";
    public static final String LAST_HASH = "LAST_HASH";
    private static final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private ProgressBar mProgressBar;
    private FrameLayout mContentLinearLayout;
    private FloatingActionButton mBudgetFab;
    private Button mRetryButton;
    private RelativeLayout mEmptyResult;
    private LinearLayoutManager mLayoutManager;
    private List<ComicBookData> mItems = new ArrayList<>();
    private ComicsListAdapter mAdapter;
    private RecyclerView mComicsRecyclerView;
    private long mTimestamp;

    public static Intent getNewIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        setViewReferences();
        setListeners();
        loadContent();
    }

    private void setListeners() {
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContent();
            }
        });
    }

    private void setViewReferences() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mContentLinearLayout = (FrameLayout) findViewById(R.id.main_container_frame_layout);
        mEmptyResult = (RelativeLayout) findViewById(R.id.empty_result_relative_layout);
        mComicsRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mBudgetFab = (FloatingActionButton) findViewById(R.id.main_fab);
        mRetryButton = (Button) findViewById(R.id.retry_button);
    }

    public void setLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mContentLinearLayout.setVisibility(View.GONE);
        mEmptyResult.setVisibility(View.GONE);
        mBudgetFab.setVisibility(View.GONE);
    }

    public void showEmptyResult() {
        mEmptyResult.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mContentLinearLayout.setVisibility(View.GONE);
        mBudgetFab.setVisibility(View.GONE);
    }

    public void showContent() {
        mContentLinearLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mEmptyResult.setVisibility(View.GONE);
        mBudgetFab.setVisibility(View.VISIBLE);
    }

    private void loadContent() {
        setLoading();
        mTimestamp = System.currentTimeMillis();
        retrieveComicsData();
    }

    private void retrieveComicsData() {
        final int creator = 30;                             //Stan Lee
        final int limit = 100;                              //100 Comics

        final String apiKey = getString(R.string.marvel_public_key);
        final String hash = ConnectionUtils.generateMD5Hash(this, mTimestamp);

        Retrofit retrofit = CustomRetrofit.getNewInstance(this);

        MarvelAPI marvelAPI = retrofit.create(MarvelAPI.class);

        Call<ComicsRequestData> call = marvelAPI.loadComics(creator, limit, apiKey, hash, mTimestamp);

        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<ComicsRequestData> call, Response<ComicsRequestData> response) {
        if (response.body() != null) {
            Log.d(TAG, "Response: " + response.body().data.results.size());
            if (ConnectionUtils.isNetworkConnectionAvailable(this)) {
                mSharedPreferences.edit().putLong(LAST_TIME_STAMP, mTimestamp).commit();
                mSharedPreferences.edit().putString(LAST_HASH, ConnectionUtils.generateMD5Hash(this, mTimestamp)).commit();
            }
            setupRecycleView(response.body().data.results);
            showContent();
        } else {
            long lastWorkingTimeStamp = mSharedPreferences.getLong(LAST_TIME_STAMP, 0);
            if (lastWorkingTimeStamp > 0 && lastWorkingTimeStamp != mTimestamp) {
                Log.i(TAG, "Warning: Retrieving cached data");
                mTimestamp = lastWorkingTimeStamp;
                retrieveComicsData();
            } else {
                Log.d(TAG, "Error: Empty comics result");
                showEmptyResult();
            }
        }
    }

    @Override
    public void onFailure(Call<ComicsRequestData> call, Throwable t) {
        long lastWorkingTimeStamp = mSharedPreferences.getLong(LAST_TIME_STAMP, 0);
        if (lastWorkingTimeStamp > 0 && lastWorkingTimeStamp != mTimestamp) {
            Log.i(TAG, "Warning: Retrieving cached data");
            mTimestamp = lastWorkingTimeStamp;
            retrieveComicsData();
        } else {
            Log.d(TAG, "Error: " + t.getMessage());
            showEmptyResult();
        }
    }

    private void setupRecycleView(List<ComicBookData> comicBookList) {
        showContent();
        mLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mComicsRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ComicsListAdapter(comicBookList);
        mComicsRecyclerView.setAdapter(mAdapter);
    }


}


