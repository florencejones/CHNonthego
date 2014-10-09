package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.grameenfoundation.chnonthego.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OtherBaseAdapter extends BaseAdapter{
	 private Context mContext;
	 private final ArrayList<String> otherCategory;
	 private final ArrayList<String> otherNumber;
	 private final ArrayList<String> otherPeriod;
	 private final ArrayList<String> id;
	
	 public OtherBaseAdapter(Context c,ArrayList<String> otherCategory ,ArrayList<String> otherNumber,ArrayList<String> otherPeriod,ArrayList<String> id) {
     mContext = c;
     this.otherCategory = otherCategory;
     this.otherNumber = otherNumber;
     this.otherPeriod = otherPeriod;
     this.id=id;
 }
	@Override
	public int getCount() {
	
		return otherCategory.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] item=null;
		item=new String[]{otherCategory.get(position),otherNumber.get(position),otherPeriod.get(position)};
		return item;
	}

	@Override
	public long getItemId(int position) {
		
		return Integer.valueOf(id.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View list;	
	          if (convertView == null) {
	        	  LayoutInflater inflater = (LayoutInflater) mContext
	        		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	  list = new View(mContext);
	        	  list = inflater.inflate(R.layout.other_listview_single, null);
	       
	          } else {
	        	  list = (View) convertView;
	          }
	          TextView textView2 = (TextView) list.findViewById(R.id.textView_otherCategory);
	          TextView textView3 = (TextView) list.findViewById(R.id.textView_otherNumber);
	          TextView textView4 = (TextView) list.findViewById(R.id.textView_otherPeriod);
	        	  textView2.setText(otherCategory.get(position));
	        	  textView3.setText(otherNumber.get(position));
	        	  textView4.setText(otherPeriod.get(position));
	        	  
	            Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		          	      "fonts/Roboto-Thin.ttf");
		            textView2.setTypeface(custom_font);
		            textView3.setTypeface(custom_font);
		            textView4.setTypeface(custom_font);
	      return list;
	    }
		
	}


