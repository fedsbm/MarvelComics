package softs.org.uk.marvelcomics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import softs.org.uk.marvelcomics.R;
import softs.org.uk.marvelcomics.activity.BudgetActivity;
import softs.org.uk.marvelcomics.model.object.ComicBookData;

/**
 * Created by Fernando Bonet on 01/11/2016.
 */
public class BudgetDialogFragment extends AppCompatDialogFragment {
    private static final String COMIC_DATA = "comic_data";
    private static final String TAG = BudgetDialogFragment.class.getSimpleName();

    private ArrayList<ComicBookData> mAllComicsData;
    private ArrayList<ComicBookData> mAffordableComicsData;
    private EditText mBudgetEditText;
    private Button mOkButton;

    public static BudgetDialogFragment create(ArrayList<ComicBookData> comicBookData) {
        Bundle b = new Bundle();
        b.putParcelableArrayList(COMIC_DATA, comicBookData);
        BudgetDialogFragment fragment = new BudgetDialogFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_dialog, container, false);
        setViewReferences(view);
        setListeners();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSortedComics();
    }

    private void setViewReferences(View view) {
        mOkButton = (Button) view.findViewById(R.id.budget_dialog_ok_button);
        mBudgetEditText = (EditText) view.findViewById(R.id.budget_dialog_budget_edit_text);
    }

    private void setListeners() {

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAffordableComicsData = new ArrayList<>();
                calculateMaximumComics();
                Log.i(TAG, "Total: " + mAffordableComicsData.size());
                startActivity(BudgetActivity.getNewIntent(v.getContext(),
                        getBudget(),
                        mAffordableComicsData));
            }
        });
    }

    private float getBudget() {
        final float budget;
        if (mBudgetEditText.getText().toString().isEmpty()) {
            budget = 0;
        } else {
            budget = Float.valueOf(mBudgetEditText.getText().toString());
        }
        return budget;
    }

    private void calculateMaximumComics() {
        float budget = getBudget();
        float total = 0;

        for (ComicBookData comicBookData : mAllComicsData) {
            if (comicBookData.prices.get(0).price + total > budget) {
                return;
            } else {
                mAffordableComicsData.add(comicBookData);
                total = total + comicBookData.prices.get(0).price;
            }
        }
    }

    private void loadSortedComics() {
        Bundle args = getArguments();
        mAllComicsData = args.getParcelableArrayList(COMIC_DATA);
        Collections.sort(mAllComicsData, new Comparator<ComicBookData>() {
            @Override
            public int compare(ComicBookData cb1, ComicBookData cb2) {
                if (cb1.prices.get(0).price > cb2.prices.get(0).price) {
                    return 1;
                } else if (cb2.prices.get(0).price > cb1.prices.get(0).price) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }


}
