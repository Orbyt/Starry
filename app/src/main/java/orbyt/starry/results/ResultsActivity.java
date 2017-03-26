package orbyt.starry.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import orbyt.starry.R;
import orbyt.starry.models.Response;
import orbyt.starry.models.ResponseWrapper;

/**
 * Created by calebchiesa on 3/24/17.
 */

public class ResultsActivity extends AppCompatActivity {

    public static final String TAG = "ResultsActivity";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        ResponseWrapper one = (ResponseWrapper) bundle.getSerializable("firstItem");
        ResponseWrapper two = (ResponseWrapper) bundle.getSerializable("secondItem");

        compareStars(one, two);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new ViewPagerAdapter(getFragmentManager());

        ResultFragment resultFragmentOne = new ResultFragment();
        Bundle bundleOne = new Bundle();
        bundleOne.putSerializable("item", one);
        resultFragmentOne.setArguments(bundleOne);

        ResultFragment resultFragmentTwo = new ResultFragment();
        Bundle bundleTwo = new Bundle();
        bundleTwo.putSerializable("item", two);
        resultFragmentTwo.setArguments(bundleTwo);

        mAdapter.addItems(resultFragmentOne, one.getResponses().get(0).getOwner().getLogin());
        mAdapter.addItems(resultFragmentTwo, two.getResponses().get(0).getOwner().getLogin());

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void compareStars(ResponseWrapper firstContestant, ResponseWrapper secondContestant) {

        int numStars1 = 0;
        for (Response res : firstContestant.getResponses()) {
            numStars1 += res.getStargazers_count();
        }

        int numStars2 = 0;
        for (Response res: secondContestant.getResponses()) {
            numStars2 += res.getStargazers_count();
        }

        if (numStars1 > numStars2) {
            WinnerDialog dialog = new WinnerDialog(firstContestant.getResponses().get(0).getOwner().getLogin(), numStars1);
            dialog.show(getFragmentManager(), "WinnerDialog");
        } else if (numStars2 > numStars1) {
            WinnerDialog dialog = new WinnerDialog(secondContestant.getResponses().get(0).getOwner().getLogin(), numStars2);
            dialog.show(getFragmentManager(), "WinnerDialog");
        } else {
            //tie
            Toast.makeText(this, "It was a tie!", Toast.LENGTH_SHORT).show();
        }

    }
}
