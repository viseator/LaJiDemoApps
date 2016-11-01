package com.example.viseator.listviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by viseator on 16-10-28.
 */

public class Adap extends ArrayAdapter {
    private int resourceId;

    public Adap(Context context, int resourceId, List<Name> objects) {
        super(context,resourceId,objects);
        this.resourceId = resourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Name name = (Name) getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder=new ViewHolder();
            viewHolder.theImage = (ImageView) view.findViewById(R.id.name_image);
            viewHolder.theName = (TextView) view.findViewById(R.id.name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.theImage.setImageResource(name.getImageid());
        viewHolder.theName.setText(name.getName());
        return view;
    }

    class ViewHolder {
        ImageView theImage;
        TextView theName;
    }
}
