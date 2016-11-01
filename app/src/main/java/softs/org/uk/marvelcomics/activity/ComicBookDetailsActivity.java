package softs.org.uk.marvelcomics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import softs.org.uk.marvelcomics.R;
import softs.org.uk.marvelcomics.activity.base.BaseActivity;
import softs.org.uk.marvelcomics.model.object.ComicBookData;
import softs.org.uk.marvelcomics.model.object.Item;

/**
 * Created by Fernando Bonet on 29/10/2016.
 */
public class ComicBookDetailsActivity extends BaseActivity {

    public static final String COMIC_DATA = "comic_data";

    private ImageView mCoverImageView;
    private TextView mTitleTextView;
    private TextView mPriceTextView;
    private TextView mPagesTextView;
    private TextView mAuthorsTextView;
    private TextView mDescriptionTextView;

    public static Intent getNewIntent(Context context, ComicBookData comicBookData) {
        Intent intent = new Intent(context, ComicBookDetailsActivity.class);
        intent.putExtra(COMIC_DATA, comicBookData);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_comic_details;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarHomeEnable(true);
        setViewReferences();
        loadValues();
    }

    private void setViewReferences() {
        mCoverImageView = (ImageView) findViewById(R.id.comic_detail_cover_image_view);
        mTitleTextView = (TextView) findViewById(R.id.comic_detail_title_text_view);
        mPriceTextView = (TextView) findViewById(R.id.comic_detail_price_text_view);
        mPagesTextView = (TextView) findViewById(R.id.comic_detail_pages_text_view);
        mAuthorsTextView = (TextView) findViewById(R.id.comic_detail_authors_text_view);
        mDescriptionTextView = (TextView) findViewById(R.id.comic_detail_description_text_view);
    }

    private void loadValues() {
        ComicBookData comicBookData = getIntent().getParcelableExtra(COMIC_DATA);
        Glide
                .with(this)
                .load(comicBookData.getThumbnailUrl(this))
                .asBitmap()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mCoverImageView);
        getSupportActionBar().setTitle(getString(R.string.comic_details_toolbar, comicBookData.id));
        mTitleTextView.setText(comicBookData.title);
        String price = String.format("%.2f", comicBookData.prices.get(0).price);
        mPriceTextView.setText(getString(R.string.comic_details_price, price));
        mPagesTextView.setText(getString(R.string.comic_details_pages, comicBookData.pageCount));
        String authors = getAuthors(comicBookData.creators.items);
        mAuthorsTextView.setText(getString(R.string.comic_details_authors, authors));
        mDescriptionTextView.setText(comicBookData.description);

    }

    private String getAuthors(ArrayList<Item> items) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (Item item : items) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(item.name);
        }
        return sb.toString();
    }


}
