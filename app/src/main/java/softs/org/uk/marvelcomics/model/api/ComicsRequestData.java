package softs.org.uk.marvelcomics.model.api;

import java.util.ArrayList;

import softs.org.uk.marvelcomics.model.object.ComicBookData;

/**
 * Created by Fernando Bonet on 30/10/2016.
 */
public class ComicsRequestData {
    public int code;
    public String status;
    public Data data;

    public class Data {
        public int total;
        public ArrayList<ComicBookData> results;
    }
}
