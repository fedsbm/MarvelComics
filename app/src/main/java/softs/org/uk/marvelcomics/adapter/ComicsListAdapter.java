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
 * Created by Fernando Bonet on 30/10/2016.
 */
public class ComicsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_COMIC = 0;

    private List<ComicBookData> mItems;

    public int lastPosition = -1;

    public ComicsListAdapter(List<ComicBookData> items) {
        this.mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_COMIC;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case VIEW_TYPE_COMIC:
            default:
                View v1 = inflater.inflate(R.layout.row_comic_book, viewGroup, false);
                viewHolder = new ComicBookHolder(v1);
                break;
        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        setAnimation(viewHolder.itemView, position);
        ComicBookHolder movieHeaderItemHolder = (ComicBookHolder) viewHolder;
        if (movieHeaderItemHolder != null) {
            movieHeaderItemHolder.setView(mItems.get(position));
        }
    }


    private void setAnimation(View viewToAnimate, int position) {
        int duration;
        if (isEven(position)) {
            duration = 200;
        } else {
            duration = 500;
        }
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(duration);
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ((ComicBookHolder) holder).clearAnimation();

    }

    private boolean isEven(int num) {
        return ((num % 2) == 0);
    }
}