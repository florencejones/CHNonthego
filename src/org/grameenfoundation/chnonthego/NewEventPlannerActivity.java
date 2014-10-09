package org.grameenfoundation.chnonthego;

import java.util.ArrayList;
import java.util.Locale;

import org.grameenfoundation.adapters.CoverageListAdapter;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.adapters.LearningBaseAdapter;
import org.grameenfoundation.adapters.OtherBaseAdapter;
import org.grameenfoundation.database.CHNDatabaseHandler;
import org.grameenfoundation.utils.TypefaceUtil;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.ActionMode;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class NewEventPlannerActivity extends FragmentActivity implements ActionBar.TabListener{

	SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_new_event_planner);
	    final ActionBar actionBar =getActionBar();
	    final PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_header);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(Color.rgb(83,171,32));
        
       //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("Planner");
     
        // Create the adapter that will return a fragment for each of the four
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        /*
        mViewPager
        .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                }
        });

        // For each of the sections in the app, add a tab to the action bar.
        
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                // Create a tab with text corresponding to the page title defined by
                // the adapter. Also specify this Activity object, which implements
                // the TabListener interface, as the callback (listener) for when
                // this tab is selected.
        	
                actionBar.addTab(actionBar.newTab()
                                .setText(mSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
        }
        */
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

   
	 public class SectionsPagerAdapter extends FragmentPagerAdapter {

         public SectionsPagerAdapter(FragmentManager fm) {
                 super(fm);
         }

         @Override
         public Fragment getItem(int position) {
                 Fragment fragment = null;
                 if(position==0 ){
                        fragment= new EventsActivity();
                 }else if(position==1){
                	 fragment= new CoverageActivity();
                 }else if(position==2){
                	 fragment= new LearningActivity();
                 }else if(position==3){
                	 fragment= new OtherActivity();
                 }
                 return fragment;
         }

         @Override
         public int getCount() {
                 return 4;
         }

         @Override
         public CharSequence getPageTitle(int position) {
                 Locale l = Locale.getDefault();
                 switch (position) {
                         case 0:
                                 return "Events";
                         case 1:
                                 return "Coverage";
                         case 2: 
                        	 	return "Learning";
                         case 3:
                        	 return "Other";
                 
                 }
                 return null;
         }
 }
		
	 public static class EventsActivity extends Fragment implements OnItemClickListener{

			private Context mContext;															
			private TextView textview_status;
			private ListView listView_events;
			private ArrayList<String> eventName;
			private ArrayList<String> eventNumber;
			private ArrayList<String> eventsId;
			private CHNDatabaseHandler db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private EventBaseAdapter events_adapter;
			private String[] selected_items;
			int selected_position;

			 public EventsActivity(){

            }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_events,null,false);
			    mContext=getActivity().getApplicationContext();
			    //TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
			    db=new CHNDatabaseHandler(getActivity());
			    textview_status=(TextView) rootView.findViewById(R.id.textView_eventsStatus);
			    listView_events=(ListView) rootView.findViewById(R.id.listView_events);
			    listView_events.setOnItemClickListener(this);
			    //registerForContextMenu(listView_events);
			    eventName=db.getAllEventName();
			    eventNumber=db.getAllEventNumber();
			    eventsId=db.getAllEventID();
			    events_adapter=new EventBaseAdapter(mContext,eventName,eventNumber,eventsId);
			    events_adapter.notifyDataSetChanged();
			    listView_events.setAdapter(events_adapter);	
			    if(listView_events.getCount()>0){
			    	textview_status.setText(" ");	
			    }else if (listView_events.getCount()==0){
			    	textview_status.setText("You have not entered any events!");
			    }
			    listView_events.setOnItemClickListener(this);
			    listView_events.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		          
			    listView_events.setMultiChoiceModeListener(new MultiChoiceModeListener() {
		              
		            private int nr = 0;
		              
		            @Override
		            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		                
		                return false;
		            }
		              
		            @Override
		            public void onDestroyActionMode(ActionMode mode) {
		                
		            	events_adapter.clearSelection();
		            }
		              
		            @Override
		            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		            
		                  
		                nr = 0;
		                MenuInflater inflater = getActivity().getMenuInflater();
		                inflater.inflate(R.menu.context_menu, menu);
		                return true;
		            }
		              
		            @Override
		            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		                switch (item.getItemId()) {
		                    case R.id.option1:
		                        nr = 0;
		                    System.out.println(selected_position);
		                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		            				getActivity());
		             
		            			// set title
		            			alertDialogBuilder.setTitle("Delete event?");
		             
		            			// set dialog message
		            			alertDialogBuilder
		            				.setMessage("You are about to delete an event. Proceed?")
		            				.setCancelable(false)
		            				.setIcon(R.drawable.ic_error)
		            				.setPositiveButton("No",new DialogInterface.OnClickListener() {
		            					public void onClick(DialogInterface dialog,int id) {
		            						// if this button is clicked, close
		            						// current activity
		            						dialog.cancel();
		            					}
		            				  })
		            				.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
		            					public void onClick(DialogInterface dialog,int id) {
		            						// if this button is clicked, just close
		            						// the dialog box and do nothing
		            						if(db.deleteEventCategory(selected_position)==true){
		    		    		        		
		       	    		        		 Toast.makeText(getActivity().getApplicationContext(), "Deleted!",
		       									         Toast.LENGTH_LONG).show();
		       		    		        	}
		       		    		        	
		            						events_adapter.clearSelection();
		       		                      
		            					}
		            				});
		             
		            				// create alert dialog
		            				AlertDialog alertDialog = alertDialogBuilder.create();
		             
		            				// show it
		            				alertDialog.show();
		            				  mode.finish();
		                }
		                return false;
		            }
		              
		            @Override	
		            public void onItemCheckedStateChanged(ActionMode mode, int position,
		                    long id, boolean checked) {
		                // TODO Auto-generated method stub
		                 if (checked) {
		                        nr++;
		                        selected_position=(int) id;
		                        
		                        events_adapter.setNewSelection(position, checked);                    
		                    } else {
		                        nr--;
		                        events_adapter.removeSelection(position);                 
		                    }
		                    mode.setTitle(nr + " selected");
		                    
		            }
		        });
		          
		      listView_events.setOnItemLongClickListener(new OnItemLongClickListener() {
		  
		            @Override
		            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		                    int position, long arg3) {
		                // TODO Auto-generated method stub
		                  
		            	listView_events.setItemChecked(position, !events_adapter.isPositionChecked(position));
		            	
		                return false;
		            }
		        });
		    
			   Button b = (Button) rootView.findViewById(R.id.button_addEvent);
			    b.setOnClickListener(new OnClickListener() {
			    	@Override
				       public void onClick(View v) {
			    		final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.event_set_dialog);
						dialog.setTitle("Set Event Target");
						String[] items={"Daily","Monthly","Weekly","Yearly"};
						final Spinner spinner_event_period=(Spinner) dialog.findViewById(R.id.spinner_dialogEventPeriod);
						final Spinner spinner_event_name=(Spinner) dialog.findViewById(R.id.spinner_eventName);
						
						ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
						spinner_event_period.setAdapter(adapter);
						ArrayList<String> list=db.getAllEventCategory();
						ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
						spinner_event_name.setAdapter(adapter2);
						
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogSetEVent);
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								EditText editText_event_period=(EditText) dialog.findViewById(R.id.editText_dialogEventPeriodNumber);
								String event_period=spinner_event_period.getSelectedItem().toString();
								String event_name=spinner_event_name.getSelectedItem().toString();
								String event_period_number=editText_event_period.getText().toString();
							    if(db.insertEventSet(event_name, event_period, event_period_number, "new_record") ==true){
							    	getActivity().runOnUiThread(new Runnable() {
							            @Override
							            public void run() {
							            	
							            	events_adapter.notifyDataSetChanged();
							            	 listView_events.setAdapter(events_adapter);	
							            }
							        });
							    
							    	 Toast.makeText(getActivity().getApplicationContext(), "Event set successfully!",
									         Toast.LENGTH_LONG).show();
							    }else{
							    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
									         Toast.LENGTH_LONG).show();
							    }
							}
						});
			 				dialog.show();
					}

			    });
			return rootView;
				   
			}
			 
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					final long id) {
				selected_items=events_adapter.getItem(position);
				System.out.println(selected_items[0]+" "+selected_items[1]);
				final Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.event_set_dialog);
				dialog.setTitle("Edit Event");
				final EditText editText_eventNumber=(EditText) dialog.findViewById(R.id.editText_dialogEventPeriodNumber);
				String[] items={"January","February","March","April","May","June","July","August","September","October","November","December"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				final Spinner spinner_event_name=(Spinner) dialog.findViewById(R.id.spinner_eventName);
				final Spinner spinner_eventPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogEventPeriod);
				spinner_eventPeriod.setAdapter(adapter);
				
				ArrayList<String> list=db.getAllEventCategory();
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
				spinner_event_name.setAdapter(adapter2);
				int spinner_position=adapter2.getPosition(selected_items[0]);
				spinner_event_name.setSelection(spinner_position);
				editText_eventNumber.setText(selected_items[1]);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogSetEVent);
				dialogButton.setText("Edit");
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
						String event_name=spinner_event_name.getSelectedItem().toString();
						String event_number=editText_eventNumber.getText().toString();
						String event_period=spinner_eventPeriod.getSelectedItem().toString();
					    if(db.editEventCategory(event_name, event_number, event_period, id)==true){
					    	EventBaseAdapter adapter=new EventBaseAdapter(getActivity(),eventName,eventNumber,eventsId);
					    	adapter.notifyDataSetChanged();
						    listView_events.setAdapter(adapter);	
					    	 Toast.makeText(getActivity().getApplicationContext(), "Event edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
				
	 				dialog.show();
		}
	 
	 }
	 public static class CoverageActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			private ArrayList<String> coveragePeopleTarget;
			private ArrayList<String> coveragePeopleNumber;
			private ArrayList<String> coveragePeoplePeriod;
			private ArrayList<String> coverageImmunzationTarget;
			private ArrayList<String> coverageImmunzationNumber;
			private ArrayList<String> coverageImmunzationPeriod;
			private ArrayList<String> coveragePeopleId;
			private ArrayList<String> coverageImmunizationId;
			private CHNDatabaseHandler db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private ExpandableListView listView_coverage;
			private TextView textStatus;
			private String[] group={"People","Immunizations"};
			private int[] imageId={R.drawable.ic_people,R.drawable.ic_syringe};
			private CoverageListAdapter adapter;
			private String[] selected_items;
			 public CoverageActivity(){

         }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_coverage,null,false);
			    mContext=getActivity().getApplicationContext();
			    TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
			    db=new CHNDatabaseHandler(getActivity());
			    coveragePeopleTarget=db.getAllCoveragePeopleTarget();
			    coveragePeopleNumber=db.getAllCoveragePeopleNumber();
			    coveragePeoplePeriod=db.getAllCoveragePeoplePeriod();
			    
			    coverageImmunzationTarget=db.getAllCoverageImmunizationsTarget();
			    coverageImmunzationNumber=db.getAllCoverageImmunizationsNumber();
			    coverageImmunzationPeriod=db.getAllCoverageImmunizationsPeriod();
			    coveragePeopleId=db.getAllCoveragePeopleId();
			    coverageImmunizationId=db.getAllCoverageImmunizationsId();
			    listView_coverage=(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
			    listView_coverage.setOnChildClickListener(this);
			    //registerForContextMenu(listView_coverage);
			    textStatus=(TextView) rootView.findViewById(R.id.textView_coverageStatus);
			    adapter=new CoverageListAdapter(getActivity(),group,coveragePeopleTarget,coveragePeopleNumber,
			    																		coveragePeoplePeriod,coverageImmunzationTarget,
			    																		coverageImmunzationNumber,coverageImmunzationPeriod,
			    																		coveragePeopleId,coverageImmunizationId,
			    																		imageId,listView_coverage);
		    	adapter.notifyDataSetChanged();
		    	listView_coverage.setAdapter(adapter);	
			    if(listView_coverage.getChildCount()>0){
			    	textStatus.setText(" ");
			    	
			    }else if (listView_coverage.getChildCount()==0){
			    	textStatus.setText("You have not entered any events!");
			    }
			    
			    Button b = (Button) rootView.findViewById(R.id.button_addCoverage);
			    b.setOnClickListener(new OnClickListener() {

			       @Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.coverage_add_dialog);
						dialog.setTitle("Add Coverage");
						String[] items={"People","Immunization"};
						String[] items2={"Daily","Monthly","Weekly"};
						ArrayList<String> list=db.getAllCoverageCategory();
						final Spinner spinner_coverageName=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageName);
						final Spinner spinner_coverageCategory=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageCategory);
						final Spinner spinner_coveragePeriod=(Spinner) dialog.findViewById(R.id.spinner_coveragePeriod);
						ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
						spinner_coverageCategory.setAdapter(adapter);
						ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
						spinner_coveragePeriod.setAdapter(adapter2);
						ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
						spinner_coverageName.setAdapter(adapter3);
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddCoverage);
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								EditText editText_coverageNumber=(EditText) dialog.findViewById(R.id.editText_dialogCoverageNumber);
								String coverage_detail=spinner_coverageCategory.getSelectedItem().toString();//people or immunizations
								String coverage_name=spinner_coverageName.getSelectedItem().toString();
								String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
								String coverage_number=editText_coverageNumber.getText().toString();
							    if(db.insertCoverageSet(coverage_name, coverage_detail, coverage_period, coverage_number, "new_record") ==true){
							    	 Toast.makeText(getActivity().getApplicationContext(), "Coverage added successfully!",
									         Toast.LENGTH_LONG).show();
							    }else{
							    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
									         Toast.LENGTH_LONG).show();
							    }
							}
						});
			 				dialog.show();
			       }
			    });
			return rootView;
				   
			}
			
			

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, final long id) {
				final Dialog dialog = new Dialog(getActivity());
				selected_items=adapter.getChild(groupPosition,childPosition);
				dialog.setContentView(R.layout.coverage_add_dialog);
				final LinearLayout coverageCategory=(LinearLayout) dialog.findViewById(R.id.LinearLayout_dialogCoverageCatgeory);
				coverageCategory.setVisibility(View.GONE);
				dialog.setTitle("Edit Coverage");
				String[] items2={"Daily","Monthly","Weekly"};
				ArrayList<String> list=db.getAllCoverageCategory();
				final Spinner spinner_coverageName=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageName);
				final Spinner spinner_coveragePeriod=(Spinner) dialog.findViewById(R.id.spinner_coveragePeriod);
				ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
				spinner_coveragePeriod.setAdapter(spinner_adapter);
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
				spinner_coverageName.setAdapter(adapter2);
				final EditText editText_coverageNumber=(EditText) dialog.findViewById(R.id.editText_dialogCoverageNumber);
				int spinner_position=adapter2.getPosition(selected_items[0]);
				spinner_coverageName.setSelection(spinner_position);
				editText_coverageNumber.setText(selected_items[1]);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddCoverage);
				dialogButton.setText("Edit");
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
						String coverage_name=spinner_coverageName.getSelectedItem().toString();
						String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
						String coverage_number=editText_coverageNumber.getText().toString();
					    if(db.editCoverage(coverage_name, coverage_number, coverage_period, id) ==true){
					    	 Toast.makeText(getActivity().getApplicationContext(), "Coverage edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	 				dialog.show();
				return true;
			}
			
	 }
	 
	 public static class LearningActivity extends Fragment implements OnChildClickListener{

			private Context mContext;															
			
			 public ArrayList<String> AntenatalCare;
			 public ArrayList<String> PostnatalCare;
			 public ArrayList<String> FamilyPlanning;
			 public ArrayList<String> ChildHealth;
			 public ArrayList<String> General;
			 public ArrayList<String> Other;
			 public ExpandableListView learningList;
			 private CHNDatabaseHandler db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			 View rootView;
			private TextView textStatus;

			private LearningBaseAdapter adapter;

			private String selected_item;
			
			 public LearningActivity(){

      }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				rootView=inflater.inflate(R.layout.activity_learning,null,false);
			    mContext=getActivity().getApplicationContext();
			    TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
			    db=new CHNDatabaseHandler(getActivity());
			    learningList=(ExpandableListView) rootView.findViewById(R.id.expandableListView_learningCategory);
			    AntenatalCare=db.getAllLearningAntenatalCare();
			    PostnatalCare=db.getAllLearningPostnatalCare();
			    FamilyPlanning=db.getAllLearningFamilyPlanning();
			    ChildHealth=db.getAllLearningChildHealth();
			    General=db.getAllLearningGeneral();
			    Other=db.getAllLearningOther();
			    textStatus=(TextView) rootView.findViewById(R.id.textView_learningStatus);
			   String[] groupItem={"Antenatal Care","Postnatal Care","Family Planning","Child Health","General","Other"};
			   int[] imageId={R.drawable.ic_antenatal,R.drawable.ic_postnatal,
				   		R.drawable.ic_family,R.drawable.ic_child_health,
				   		R.drawable.ic_general,R.drawable.ic_others};
		   
		   adapter=new LearningBaseAdapter(getActivity(),groupItem,AntenatalCare,
				   													PostnatalCare,
				   													FamilyPlanning,
				   													ChildHealth,
				   													General,
				   													Other,imageId,learningList);
		   adapter.notifyDataSetChanged();
	    	learningList.setAdapter(adapter);	
	    	learningList.setOnChildClickListener(this);
		   	if(learningList.getChildCount()>0){
		    	textStatus.setText(" ");
		    }else if (learningList.getChildCount()==0){
		    	textStatus.setText("No learning categories found");
		    }
			    //registerForContextMenu(learningList);
			    
			    textStatus=(TextView) rootView.findViewById(R.id.textView_learningStatus);
			    
			    Button b = (Button) rootView.findViewById(R.id.button_addLearning);
			    b.setOnClickListener(new OnClickListener() {

			       @Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.learning_add_dialog);
						dialog.setTitle("Add Learning Category");
						final Spinner spinner_learningCatagory=(Spinner) dialog.findViewById(R.id.spinner_learningHeader);
						String[] items={"Antenatal Care","Postnatal Care","Family Planning","Child Health","General","Other"};
						ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
						spinner_learningCatagory.setAdapter(adapter);
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddLearning);
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								EditText editText_learningDescription=(EditText) dialog.findViewById(R.id.editText_learningDescription);
								String learning_category=spinner_learningCatagory.getSelectedItem().toString();
								String learning_description=editText_learningDescription.getText().toString();
							    if(db.insertLearning(learning_category, learning_description, "new_record") ==true){
							    	 Toast.makeText(getActivity().getApplicationContext(), "Added successfully!",
									         Toast.LENGTH_LONG).show();
							    }else{
							    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
									         Toast.LENGTH_LONG).show();
							    }
							}
						});
			 				dialog.show();
			       }
			    });
			return rootView;
				   
			 }			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, final long id) {
					selected_item=adapter.getChild(groupPosition, childPosition);
				 	final Dialog dialog = new Dialog(getActivity());
					dialog.setContentView(R.layout.learning_add_dialog);
					dialog.setTitle("Edit Learning Category");
					final Spinner spinner_learningCatagory=(Spinner) dialog.findViewById(R.id.spinner_learningHeader);
					String[] items={"Antenatal Care","Postnatal Care","Family Planning","Child Health","General","Other"};
					ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
					spinner_learningCatagory.setAdapter(adapter);
					final EditText editText_learningDescription=(EditText) dialog.findViewById(R.id.editText_learningDescription);
					editText_learningDescription.setText(selected_item);
					Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddLearning);
					dialogButton.setText("Edit");
					dialogButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							
							String learning_category=spinner_learningCatagory.getSelectedItem().toString();
							String learning_description=editText_learningDescription.getText().toString();
						    if(db.editLearning(learning_category, learning_description, id) ==true){
						    	 Toast.makeText(getActivity().getApplicationContext(), "Edited successfully!",
								         Toast.LENGTH_LONG).show();
						    }else{
						    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
								         Toast.LENGTH_LONG).show();
						    }
						}
					});
		 				dialog.show();
				return true;
			}
			
	 }
	 
	 public static class OtherActivity extends Fragment implements OnItemClickListener{

			private Context mContext;															
			private ArrayList<String> otherCategory;
			 private ArrayList<String> otherNumber;
			 private ArrayList<String> otherPeriod;
			private ArrayList<String> otherId;
			private CHNDatabaseHandler db;
			 public static final String ARG_SECTION_NUMBER = "section_number";       
			View rootView;
			private ListView listView_other;
			private TextView textStatus;
			private OtherBaseAdapter adapter;
			
			 public OtherActivity(){

   }
			 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				 rootView=inflater.inflate(R.layout.activity_other,null,false);
			    mContext=getActivity().getApplicationContext();
			    TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
			    db=new CHNDatabaseHandler(getActivity());
			    otherCategory=db.getAllOtherCategory();
			    otherNumber=db.getAllOtherNumber();
			    otherPeriod=db.getAllOtherPeriod();
			    otherId=db.getAllOthersId();
			    listView_other=(ListView) rootView.findViewById(R.id.listView_other);
			    listView_other.setOnItemClickListener(this);
			
			   textStatus=(TextView) rootView.findViewById(R.id.textView_otherStatus);
			   listViewPopulate();
			    
			    Button b = (Button) rootView.findViewById(R.id.button_addOther);
			    b.setOnClickListener(new OnClickListener() {

			       @Override
			       public void onClick(View v) {
			    	   final Dialog dialog = new Dialog(getActivity());
						dialog.setContentView(R.layout.event_add_dialog);
						dialog.setTitle("Add other Category");
						final EditText editText_otherCategory=(EditText) dialog.findViewById(R.id.editText_dialogOtherName);
						final Spinner spinner_otherPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogOtherPeriod);
						String[] items={"Daily","Monthly","Weekly","Yearly"};
						ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
						spinner_otherPeriod.setAdapter(adapter);
						final EditText editText_otherNumber=(EditText) dialog.findViewById(R.id.editText_dialogOtherNumber);
						Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddEvent);
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								
								String other_category=editText_otherCategory.getText().toString();
								String other_number=editText_otherNumber.getText().toString();
								String other_period=spinner_otherPeriod.getSelectedItem().toString();
							    if(db.insertOther(other_category,other_number,other_period, "new_record") ==true){
							    	 Toast.makeText(getActivity().getApplicationContext(), "Added successfully!",
									         Toast.LENGTH_LONG).show();
							    }else{
							    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
									         Toast.LENGTH_LONG).show();
							    }
							}
						});
			 				dialog.show();
			       }
			    });
			return rootView;
				   
			}
			
			public boolean listViewPopulate(){
				 if(otherCategory.size()>0){
				    	textStatus.setText(" ");
				    	adapter=new OtherBaseAdapter(getActivity(),otherCategory,otherNumber,otherPeriod,otherId);
				    	adapter.notifyDataSetChanged();
				    	listView_other.setAdapter(adapter);	
				    }else if (otherCategory.size()==0){
				    	textStatus.setText("No other categories found!");
				    }
				    
				
				return true;
			}
			
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, final long id) {
				String[] selected_items=adapter.getItem(position);
				final Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.event_add_dialog);
				dialog.setTitle("Edit other Category");
				final EditText editText_otherCategory=(EditText) dialog.findViewById(R.id.editText_dialogOtherName);
				editText_otherCategory.setText(selected_items[0]);
				final Spinner spinner_otherPeriod=(Spinner) dialog.findViewById(R.id.spinner_dialogOtherPeriod);
				String[] items={"Daily","Monthly","Weekly","Yearly"};
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
				spinner_otherPeriod.setAdapter(adapter);
				int spinner_position=adapter.getPosition(selected_items[2]);
				spinner_otherPeriod.setSelection(spinner_position);
				final EditText editText_otherNumber=(EditText) dialog.findViewById(R.id.editText_dialogOtherNumber);
				editText_otherNumber.setText(selected_items[1]);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddEvent);
				dialogButton.setText("Edit");
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
						String other_category=editText_otherCategory.getText().toString();
						String other_number=editText_otherNumber.getText().toString();
						String other_period=spinner_otherPeriod.getSelectedItem().toString();
					    if(db.editOther(other_category, other_number, other_period, id) ==true){
					    	 Toast.makeText(getActivity().getApplicationContext(), "Edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	 				dialog.show();
				
			}
			
	 }

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		   mViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		
	}


}
