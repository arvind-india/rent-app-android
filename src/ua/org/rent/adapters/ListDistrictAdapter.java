package ua.org.rent.adapters;

import java.util.ArrayList;

import ua.org.rent.library.DB;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import ua.org.rent.entities.District;

public class ListDistrictAdapter extends SimpleCursorAdapter {

	Cursor c;
	Context context;
	int layout;
	ArrayList<District> districts;

	public ListDistrictAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, ArrayList<District> districts) {
		super(context, layout, c, from, to);
		this.c = c;
		this.context = context;
		this.layout = layout;
		this.districts = districts;

		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, layout, null);
		}
		if (c.moveToPosition(position)) {
			String title = c.getString(c.getColumnIndexOrThrow(DB.TABLE_DISTRICT_TITLE));
			TextView district = (TextView) convertView.findViewById(android.R.id.text1);
			district.setText(title);
			int district_id = c.getInt(c.getColumnIndexOrThrow(DB.TABLE_DISTRICT_ID));
			if (this.districts.contains(new District(title, district_id))) {
				district.setBackgroundColor(Color.GRAY);
			} else {
				district.setBackgroundColor(Color.BLACK);
			}
		}
		return convertView;
	}
}
