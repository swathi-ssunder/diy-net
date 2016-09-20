package diy.net.menzap.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import diy.net.menzap.R;
import diy.net.menzap.model.Event;

public class EventAdapter extends ArrayAdapter {

    private Context context;
    private Event events[];
    private int resource;

    public EventAdapter(Context context, int resource, Event[] events) {
        super(context, resource, events);

        this.context = context;
        this.resource = resource;
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
            row = inflater.inflate(this.resource, parent, false);
        }
        TextView eventName = (TextView)row.findViewById(R.id.eventName);
        eventName.setText(events[position].getName());

        return row;
    }
}
