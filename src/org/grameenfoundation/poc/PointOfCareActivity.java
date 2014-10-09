package org.grameenfoundation.poc;

import org.grameenfoundation.adapters.PointOfCareBaseAdapter;
import org.grameenfoundation.chnonthego.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PointOfCareActivity extends Activity implements OnItemClickListener {

	private ListView listView_menu;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_point_of_care);
	    mContext=PointOfCareActivity.this;
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Point of Care");
	    listView_menu=(ListView) findViewById(R.id.listView_pocMenu);
	    listView_menu.setOnItemClickListener(this);
	    int[] imageIds={R.drawable.ic_antenatal,R.drawable.ic_family,R.drawable.ic_postnatal};
	    String[] category={"Antenatal Care","Family Planning","Postnatal Care"};
	    PointOfCareBaseAdapter adapter=new PointOfCareBaseAdapter(mContext,imageIds,category);
	    listView_menu.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(mContext, AntenatalCareActivity.class);
			startActivity(intent);
			break;
		}
		
	}

}
