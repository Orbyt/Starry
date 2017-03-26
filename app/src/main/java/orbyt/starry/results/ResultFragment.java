package orbyt.starry.results;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import orbyt.starry.R;
import orbyt.starry.models.Item;
import orbyt.starry.models.ResponseWrapper;
import rx.Observable;

/**
 * Created by calebchiesa on 3/24/17.
 */

public class ResultFragment extends Fragment {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter mAdapter;
    private ResponseWrapper mResponseWrapper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        mResponseWrapper = (ResponseWrapper) bundle.getSerializable("item");

        mAdapter = new RecyclerViewAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        Observable.from(mResponseWrapper.getResponses())
                .map(response -> {
                    Item item = new Item();
                    item.setName(response.getFull_name());
                    item.setStars(response.getStargazers_count().toString());
                    item.setDescription(response.getDescription());
                    item.setUrl(response.getHtml_url());
                    item.setProfileImg(response.getOwner().getAvatar_url());
                    item.setProfileUrl(response.getOwner().getHtml_url());
                    return item;
                })
                .subscribe(item -> {
                    mAdapter.addItem(item);
                });


        return view;

    }
}
