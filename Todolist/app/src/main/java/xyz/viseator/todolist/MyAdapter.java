package xyz.viseator.todolist;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by viseator on 2016/11/1.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private OperateData db;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textViewTitle;
        TextView textViewContext;
        TextView textViewcreTime;
        TextView textViewendTime;
        TextView textViewPri;
        CheckBox checkBox;
        public ViewHolder(View v){
            super(v);
            cardView = (CardView) v.findViewById(R.id.cv_item);
            textViewTitle = (TextView) v.findViewById(R.id.text_view_title);
            textViewContext = (TextView) v.findViewById(R.id.text_view_context);
            textViewcreTime = (TextView) v.findViewById(R.id.text_view_creTime);
            textViewendTime = (TextView) v.findViewById(R.id.text_view_endTime);
            textViewPri = (TextView) v.findViewById(R.id.primer);
            checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        }
    }

    public MyAdapter(OperateData db,Context context){
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
        int pos = position;
        holder.textViewTitle.setText(db.getTitle(pos+1));
        holder.textViewContext.setText(db.getContext(pos+1));
        holder.textViewcreTime.setText(db.getCreTime(pos+1));
        holder.textViewendTime.setText(db.getEndTime(pos+1));
        holder.textViewPri.setText("Important");
        holder.checkBox.setChecked(db.getCheck(pos+1));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    db.setCheck(position + 1, 1);
                } else {
                    db.setCheck(position + 1, 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return db.count();
    }

    public static interface OnItemClickListener{
        void onItemClick(View view);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v);
    }

    public void removeItem(int positon) {

    }


}