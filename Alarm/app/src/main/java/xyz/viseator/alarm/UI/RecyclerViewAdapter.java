package xyz.viseator.alarm.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.viseator.alarm.Activities.ChangeDataActivity;
import xyz.viseator.alarm.DataBase.DataBaseManager;
import xyz.viseator.alarm.R;

/**
 * Created by viseator on 2016/11/9.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private DataBaseManager db;
    Context context;

    public RecyclerViewAdapter(DataBaseManager db, Context context) {
        this.db = db;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_content_layout, parent, false);
        return new RecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.textViewForTime.setText(getHour(position) + ":" + getMin(position));
        holder.swithForOpen.setChecked(db.getIsOn(position));
        holder.swithForOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.setIsOn(position, holder.swithForOpen.isChecked());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChangeDataActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return db.count();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewForTime;
        SwitchCompat swithForOpen;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewForTime = (TextView) itemView.findViewById(R.id.time_recyclerview);
            swithForOpen = (SwitchCompat) itemView.findViewById(R.id.switch_recyclerview);
        }
    }

    private String getHour(int position) {
        if (db.getHour(position) < 10) return "0" + String.valueOf(db.getHour(position));
        else return String.valueOf(db.getHour(position));
    }
    private String getMin(int position) {
        if (db.getMin(position) < 10) return "0" + String.valueOf(db.getMin(position));
        else return String.valueOf(db.getMin(position));
    }

}
