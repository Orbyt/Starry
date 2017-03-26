package orbyt.starry.results;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import orbyt.starry.R;

/**
 * Created by calebchiesa on 3/25/17.
 */

public class WinnerDialog extends DialogFragment {

    @Bind(R.id.winnerName)
    TextView mWinnerName;
    @Bind(R.id.starCount)
    TextView mStarCount;
    @Bind(R.id.resultsBtn)
    Button mResultsBtn;
    @Bind(R.id.starIcon)
    ImageView mStarIcon;

    private String mName;
    private int mStars;

    @SuppressLint("ValidFragment")
    public WinnerDialog(String name, int stars) {
        mName = name;
        mStars = stars;
    }

    public WinnerDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_winner, null);
        builder.setView(view);

        ButterKnife.bind(this, view);

        mWinnerName.setText(mName + "!");
        mStarCount.setText(String.valueOf(mStars));

        mResultsBtn.setOnClickListener(view1 -> dismiss());

        return builder.create();
    }
}
