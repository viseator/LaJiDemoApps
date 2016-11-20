package xyz.viseator.v2ex.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import xyz.viseator.v2ex.R;
import xyz.viseator.v2ex.data.DetailContent;

/**
 * Created by viseator on 2016/11/19.
 */

public class DetailRecyclerViewAdapter extends
        RecyclerView.Adapter<DetailRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<DetailContent> detailContents;

    public DetailRecyclerViewAdapter(Context context,List<DetailContent> detailContents) {
        this.context = context;
        this.detailContents = detailContents;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView usernameTextView;
        TextView timeTextView;
        TextView contentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.detail_recyclerview_avatar);
            usernameTextView = (TextView) itemView.findViewById(R.id.detail_user);
            timeTextView = (TextView) itemView.findViewById(R.id.detail_time);
            contentTextView = (TextView) itemView.findViewById(R.id.detail_content);

        }
    }

    @Override
    public DetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.detail_recyclerview_content_layout, parent, false);
        return new DetailRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.contentTextView.setText(detailContents.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return detailContents.size();
    }
}
