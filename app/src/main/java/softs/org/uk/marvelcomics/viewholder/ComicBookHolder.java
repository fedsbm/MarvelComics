package softs.org.uk.marvelcomics.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import softs.org.uk.marvelcomics.R;
import softs.org.uk.marvelcomics.model.object.ComicBookData;

/**
 * Created by Fernando Bonet on 30/10/2016.
 */
public class ComicBookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ComicBookData mComicBookData;
    private FrameLayout mComicFrameLayout;
    private TextView mComicTitleTextView;
    private ImageView mCoverImageView;
    private View mView;

    public ComicBookHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mComicFrameLayout = (FrameLayout) itemView.findViewById(R.id.row_comic_book_frame_layout);
        mCoverImageView = (ImageView) itemView.findViewById(R.id.row_comic_book_image_view);
        mComicTitleTextView = (TextView) itemView.findViewById(R.id.row_comic_title_text_view);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Clicked on Comic id: " + mComicBookData.id, Toast.LENGTH_SHORT).show();
    }

    public void setView(ComicBookData comicBookData) {
        mComicFrameLayout.setOnClickListener(this);
        mComicBookData = comicBookData;
        mComicTitleTextView.setText(comicBookData.title);
        Glide.clear(mCoverImageView);
        Glide
                .with(mView.getContext())
                .load(comicBookData.getThumbnailUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mCoverImageView);
    }

    public void clearAnimation() {
        mView.clearAnimation();
    }
}
