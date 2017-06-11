package softs.org.uk.marvelcomics.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import softs.org.uk.marvelcomics.R;

/**
 * Created by Fernando Bonet on 30/10/2016.
 */
public class ConnectionUtils {

    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnectedOrConnecting();
    }

    public static String generateMD5Hash(Context context) {
        String rawHash = getTimestamp() + context.getString(R.string.marvel_private_key) + context.getString(R.string.marvel_public_key);
        return stringToMD5(rawHash);
    }

    private static String stringToMD5(String string) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(string.getBytes(), 0, string.length());
        String hash = new BigInteger(1, messageDigest.digest()).toString(16);
        return hash;
    }

    public static String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
