package softs.org.uk.marvelcomics.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.util.List;

import softs.org.uk.marvelcomics.R;
import softs.org.uk.marvelcomics.model.object.ComicBookData;
import softs.org.uk.marvelcomics.viewholder.ComicBookHolder;

/**
 * Created by Fernando Bonet on 01/11/2016.
 */
public class ComicListHorizontalAdapter extends ComicsListAdapter {

    public ComicListHorizontalAdapter(List<ComicBookData> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case VIEW_TYPE_COMIC:
            default:
                View v1 = inflater.inflate(R.layout.row_comic_book_horizontal, viewGroup, false);
                viewHolder = new ComicBookHolder(v1);
                break;
        }

        return viewHolder;
    }

    @Override
    protected void setAnimation(View viewToAnimate, int position) {
        int duration = 300;
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(duration);
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }
}
