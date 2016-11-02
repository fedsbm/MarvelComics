package softs.org.uk.marvelcomics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import softs.org.uk.marvelcomics.R;
import softs.org.uk.marvelcomics.activity.base.BaseActivity;
import softs.org.uk.marvelcomics.adapter.ComicListHorizontalAdapter;
import softs.org.uk.marvelcomics.adapter.ComicsListAdapter;
import softs.org.uk.marvelcomics.model.object.ComicBookData;

/**
 * Created by Fernando Bonet on 01/11/2016.
 */
public class BudgetActivity extends BaseActivity {
    private static final String COMIC_DATA = "comic_data";
    public static final String MONEY_DATA = "money_data";

    private LinearLayoutManager mLayoutManager;
    private ComicsListAdapter mAdapter;
    private RecyclerView mComicsRecyclerView;
    private TextView mMoneyTextView;
    private TextView mComicsTextView;
    private TextView mPagesTextView;
    private TextView mTotalCostTextView;


    public static Intent getNewIntent(Context context, float budget, ArrayList<ComicBookData> comicBookData) {
        Intent intent = new Intent(context, BudgetActivity.class);
        intent.putExtra(COMIC_DATA, comicBookData);
        intent.putExtra(MONEY_DATA, budget);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_budget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarHomeEnable(true);
        setViewReferences();
        setupUI();
    }

    private void setupUI() {
        float budget = getIntent().getFloatExtra(MONEY_DATA, 0);
        String money = String.format("%.2f", budget);
        mMoneyTextView.setText(getString(R.string.budget_money, money));
        ArrayList<ComicBookData> comicsAffordable = getIntent().getParcelableArrayListExtra(COMIC_DATA);
        if (comicsAffordable != null) {
            setupRecycleView(comicsAffordable);
        }
    }

    private void setViewReferences() {
        mComicsRecyclerView = (RecyclerView) findViewById(R.id.budget_recycler_view);
        mMoneyTextView = (TextView) findViewById(R.id.budget_money_text_view);
        mComicsTextView = (TextView) findViewById(R.id.budget_comics_text_view);
        mPagesTextView = (TextView) findViewById(R.id.budget_pages_text_view);
        mTotalCostTextView = (TextView) findViewById(R.id.budget_total_cost_text_view);
    }

    private void setupRecycleView(ArrayList<ComicBookData> comicBookList) {
        setTexts(comicBookList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mComicsRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ComicListHorizontalAdapter(comicBookList);
        mComicsRecyclerView.setAdapter(mAdapter);
    }

    public void setTexts(ArrayList<ComicBookData> comicBookList) {
        mComicsTextView.setText(getString(R.string.budget_comics_number, comicBookList.size()));
        int pages = 0;
        float totalCost = 0;
        for (ComicBookData comicBookData : comicBookList) {
            pages = pages + comicBookData.pageCount;
            totalCost = totalCost + comicBookData.prices.get(0).price;
        }
        mPagesTextView.setText(getString(R.string.budget_pages, pages));
        String money = String.format("%.2f", totalCost);
        mTotalCostTextView.setText(getString(R.string.budget_comics_total_cost, money));
    }
}
