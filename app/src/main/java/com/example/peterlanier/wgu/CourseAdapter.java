package com.example.peterlanier.wgu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by peterlanier on 2/18/18.
 */

public class CourseAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Course> mDataSource;

    public CourseAdapter(Context context, ArrayList<Course> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_course, parent, false);

        // Get row elements
        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.course_list_title);
        TextView startTextView =
                (TextView) rowView.findViewById(R.id.course_list_start);
        TextView endTextView =
                (TextView) rowView.findViewById(R.id.course_list_end);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Course course = (Course) getItem(position);


        titleTextView.setText(course.title);
        startTextView.setText(df.format(course.start));
        endTextView.setText(df.format(course.end));


        return rowView;
    }
}
