package orbyt.starry.results;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import orbyt.starry.R;
import orbyt.starry.models.Item;

/**
 * Created by calebchiesa on 3/24/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Item> mItems;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repository, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mName.setText(mItems.get(position).getName());
        holder.mDescription.setText(mItems.get(position).getDescription());
        holder.mStars.setText(mItems.get(position).getStars());
        Glide.with(mContext).load(mItems.get(position).getProfileImg()).into(holder.mProfileImg);

        holder.mProfileImg.setOnClickListener(view -> mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mItems.get(position).getProfileUrl()))));
        holder.itemView.setOnClickListener(view -> mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mItems.get(position).getUrl()))));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name)
        TextView mName;
        @Bind(R.id.description)
        TextView mDescription;
        @Bind(R.id.stars)
        TextView mStars;
        @Bind(R.id.profileImg)
        ImageView mProfileImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addItems(List<Item> items) {
        mItems = items;
        sort();
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        mItems.add(item);
        sort();
        notifyDataSetChanged();
    }

    public void sort() {
        Collections.sort(mItems, (lhs, rhs) -> Integer.parseInt(rhs.getStars()) - Integer.parseInt(lhs.getStars()));
        notifyDataSetChanged();
    }
}
