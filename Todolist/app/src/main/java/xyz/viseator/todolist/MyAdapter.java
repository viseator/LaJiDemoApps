package xyz.viseator.todolist;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by viseator on 2016/11/1.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] dataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textView;
        public ViewHolder(View v){
            super(v);
            cardView = (CardView) v.findViewById(R.id.cv_item);
            textView = (TextView) v.findViewById(R.id.text_view);
        }
    }

    public MyAdapter(String[] dataset,Context context){
        this.dataset = dataset;
        this.context = context;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.listtext, viewGroup, false);
        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        int pos = position;
        holder.textView.setText(dataset[pos]);
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }


}