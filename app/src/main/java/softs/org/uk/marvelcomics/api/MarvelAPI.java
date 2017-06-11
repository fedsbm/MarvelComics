package softs.org.uk.marvelcomics.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import softs.org.uk.marvelcomics.model.api.ComicsRequestData;

/**
 * Created by Fernando Bonet on 30/10/2016.
 */
public interface MarvelAPI {

    @GET("v1/public/comics")
    Call<ComicsRequestData> loadComics(@Query("creators") int creators,
                                       @Query("limit") int limit,
                                       @Query("apikey") String apikey,
                                       @Query("hash") String hash,
                                       @Query("ts") String ts);
}
