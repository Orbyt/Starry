package orbyt.starry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import orbyt.starry.api.ApiService;
import orbyt.starry.models.Response;
import orbyt.starry.models.ResponseWrapper;
import orbyt.starry.results.ResultsActivity;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Bind(R.id.firstEditText)
    EditText mFirstContestant;
    @Bind(R.id.secondEditText)
    EditText mSecondContestant;

    private String mFirstContestantName;
    private String mSecondContestantName;

    private Subscription mFirstEditTextSubscription;
    private Subscription mSecondEditTextSubscription;

    private Retrofit mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mFirstEditTextSubscription =
                RxTextView.textChanges(mFirstContestant)
                        .subscribe(value -> {
                            mFirstContestantName = value.toString();
                        });

        mSecondEditTextSubscription =
                RxTextView.textChanges(mSecondContestant)
                        .subscribe(value -> {
                            mSecondContestantName = value.toString();
                        });
    }

    @OnClick(R.id.go)
    public void onGoClicked() {
        if (!TextUtils.isEmpty(mFirstContestantName) &&
                !TextUtils.isEmpty(mSecondContestantName)) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService service = mRetrofit.create(ApiService.class);

            Observable<List<Response>> firstCall = service.getRepositories(mFirstContestantName);
            Observable<List<Response>> secondsCall = service.getRepositories(mSecondContestantName);
            Observable.zip(firstCall, secondsCall, (responses, responses2) -> {
                List<List<Response>> combinedResponses = new ArrayList<List<Response>>();
                combinedResponses.add(responses);
                combinedResponses.add(responses2);
                return combinedResponses;
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<List<Response>>>() {
                @Override
                public void onCompleted() {
                    Log.d(TAG, "onCompleted: ");
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "There was an issue with the usernames you've used.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(List<List<Response>> lists) {

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent();

                    ResponseWrapper wrapper = new ResponseWrapper();
                    wrapper.setResponses(lists.get(0));

                    ResponseWrapper wrapper2 = new ResponseWrapper();
                    wrapper2.setResponses(lists.get(1));

                    bundle.putSerializable("firstItem", wrapper);
                    bundle.putSerializable("secondItem", wrapper2);

                    intent.putExtra("bundle", bundle);
                    intent.setClass(MainActivity.this, ResultsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFirstEditTextSubscription != null &&
                !mFirstEditTextSubscription.isUnsubscribed()) {
            mFirstEditTextSubscription.unsubscribe();
        }
        if (mSecondEditTextSubscription != null &&
                !mSecondEditTextSubscription.isUnsubscribed()) {
            mSecondEditTextSubscription.unsubscribe();
        }
    }
}
