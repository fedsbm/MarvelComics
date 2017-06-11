package softs.org.uk.marvelcomics.api;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import softs.org.uk.marvelcomics.R;
import softs.org.uk.marvelcomics.utils.ConnectionUtils;

/**
 * Created by Fernando Bonet on 30/10/2016.
 */
public class MarvelRestClient {
    private static final String TAG = MarvelRestClient.class.getSimpleName();
    private static final String CACHE_CONTROL = "Cache-Control";
    private static MarvelAPI sMarvelAPI;

    public static void init(Context context) {
        sMarvelAPI = getNewInstance(context).create(MarvelAPI.class);
    }

    private static Retrofit getNewInstance(Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient(context))
                .build();
        return retrofit;
    }

    public static MarvelAPI getApiService() {
        return sMarvelAPI;
    }

    private static OkHttpClient getClient(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(provideCache(context))
                .addInterceptor(provideCacheInterceptor(context))
                .addInterceptor(httpLoggingInterceptor);

        return builder.build();
    }

    private static Cache provideCache(Context context) {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
            Log.i(TAG, "Cache created");
        } catch (Exception e) {
            Log.e(TAG, "Could not create Cache");
        }
        return cache;
    }

    public static Interceptor provideCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                if (!ConnectionUtils.isNetworkConnectionAvailable(context)) {
                    Request request = chain.request();

                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();

                    return chain.proceed(request);
                } else {
                    Response response = chain.proceed(chain.request());

                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxAge(1, TimeUnit.HOURS)
                            .build();

                    return response.newBuilder()
                            .header(CACHE_CONTROL, cacheControl.toString())
                            .build();
                }
            }
        };
    }
}
