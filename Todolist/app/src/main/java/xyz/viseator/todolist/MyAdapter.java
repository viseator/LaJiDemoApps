package xyz.viseator.todolist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.ParseException;

/**
 * Created by viseator on 2016/11/1.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private OperateData db;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textViewTitle;
        TextView textViewContext;
        TextView textViewendTime;
        TextView textViewPri;
        TextView textViewRemain;
        CheckBox checkBox;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.cv_item);
            textViewTitle = (TextView) v.findViewById(R.id.text_view_title);
            textViewContext = (TextView) v.findViewById(R.id.text_view_context);
            textViewendTime = (TextView) v.findViewById(R.id.text_view_endTime);
            textViewPri = (TextView) v.findViewById(R.id.primer);
            textViewRemain = (TextView) v.findViewById(R.id.remainTime);
            checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        }
    }

    public MyAdapter(OperateData db, Context context) {
        this.context = context;
        this.db = db;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listtext, viewGroup, false);
        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
        holder.textViewTitle.setText(db.getTitle(position));
        holder.textViewContext.setText(db.getContext(position));
        holder.textViewendTime.setText(db.getEndTime(position));
        holder.textViewPri.setText(db.getPrimer(position));
        try {
            holder.textViewRemain.setText(db.getRemain(position));
            if (db.getRemain(position) == "已过期") {
                holder.textViewTitle.setTextColor(Color.LTGRAY);
                holder.textViewContext.setTextColor(Color.LTGRAY);
                holder.textViewRemain.setTextColor(Color.RED);
                holder.itemView.setAlpha((float) 0.5);
            } else {
                holder.textViewTitle.setTextColor(Color.BLACK);
                holder.textViewRemain.setTextColor(Color.GRAY);
                holder.textViewContext.setTextColor(Color.BLACK);
                holder.itemView.setAlpha(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (db.getCheck(position))
            holder.textViewTitle.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        else
            holder.textViewTitle.getPaint().setFlags(0);

        holder.checkBox.setChecked(db.getCheck(position));
        holder.itemView.setTag(position);

        switch (db.getPrimerNum(position)) {
            case 1:
                holder.textViewPri.setTextColor(Color.parseColor("#A9A9A9"));
                break;
            case 2:
                holder.textViewPri.setTextColor(Color.BLACK);
                break;
            case 3:
                holder.textViewPri.setTextColor(Color.parseColor("#FFD700"));
                break;
            case 4:
                holder.textViewPri.setTextColor(Color.parseColor("#FF0000"));
                break;
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    db.setCheck(position, 1);
                    notifyItemChanged(position);
                } else {
                    db.setCheck(position, 0);
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return db.count();
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, (int) v.getTag());
    }


}