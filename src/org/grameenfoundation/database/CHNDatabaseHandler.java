package org.grameenfoundation.database;

import java.util.ArrayList;

import org.grameenfoundation.database.CHNDataClass.CHNDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CHNDatabaseHandler {

	Context context;	
	CHNDatabaseHelper mDbHelper = new CHNDatabaseHelper(context);
	private SQLiteDatabase database;
	
		public CHNDatabaseHandler(Context context) {
		mDbHelper = new CHNDatabaseHelper(context);
		}
		

		public boolean insertUser(String firstname, String lastname, String username, String password, String sync_status){
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(CHNDatabase.COL_FIRSTNAME,firstname);
			values.put(CHNDatabase.COL_LASTNAME,lastname);
			values.put(CHNDatabase.COL_USERNAME,username);
			values.put(CHNDatabase.COL_PASSWORD,password);
			values.put(CHNDatabase.COL_SYNC_STATUS,sync_status);
			
			long newRowId;
			newRowId = db.insert(
					CHNDatabase.USER_TABLE, null, values);
			return true;
		}
		
		public boolean insertEventCategory(String event_name, String sync_status){
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(CHNDatabase.COL_EVENT_NAME,event_name);
			values.put(CHNDatabase.COL_SYNC_STATUS,sync_status);
			
			long newRowId;
			newRowId = db.insert(
					CHNDatabase.EVENTS_CATEGORY_TABLE, null, values);
			return true;
		}
		
		public boolean insertEventSet(String event_name, String event_period, String event_number, String sync_status){
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(CHNDatabase.COL_EVENT_NAME,event_name);
			values.put(CHNDatabase.COL_EVENT_PERIOD,event_period);
			values.put(CHNDatabase.COL_SYNC_STATUS,sync_status);
			values.put(CHNDatabase.COL_EVENT_NUMBER,event_number);
			
			long newRowId;
			newRowId = db.insert(
					CHNDatabase.EVENTS_SET_TABLE, null, values);
			return true;
		}
		
		public boolean insertCoverage(String category_name, String category_detail, String sync_status){
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(CHNDatabase.COL_CATEGORY_NAME,category_name);
			values.put(CHNDatabase.COL_CATEGORY_DETAIL,category_detail);
			values.put(CHNDatabase.COL_SYNC_STATUS,sync_status);
			
			long newRowId;
			newRowId = db.insert(
					CHNDatabase.COVERAGE_CATEGORY_TABLE, null, values);
			return true;
		}
		
		public boolean insertCoverageSet(String category_name, String category_detail,String coverage_period, String coverage_number, String sync_status){
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(CHNDatabase.COL_COVERAGE_SET_CATEGORY_NAME,category_name);
			values.put(CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL,category_detail);
			values.put(CHNDatabase.COL_COVERAGE_SET_PERIOD,coverage_period);
			values.put(CHNDatabase.COL_COVERAGE_NUMBER,coverage_number);
			values.put(CHNDatabase.COL_SYNC_STATUS,sync_status);
			
			long newRowId;
			newRowId = db.insert(
					CHNDatabase.COVERAGE_SET_TABLE, null, values);
			return true;
		}
		
		public boolean insertLearning(String learning_category, String learning_description, String sync_status){
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(CHNDatabase.COL_LEARNING_CATEGORY,learning_category);
			values.put(CHNDatabase.COL_LEARNING_DESCRIPTION,learning_description);
			values.put(CHNDatabase.COL_SYNC_STATUS,sync_status);
			
			long newRowId;
			newRowId = db.insert(
					CHNDatabase.LEARNING_TABLE, null, values);
			return true;
		}
		
		public boolean insertOther(String other_category,String other_number,String other_period, String sync_status){
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(CHNDatabase.COL_OTHER_CATEGORY,other_category);
			values.put(CHNDatabase.COL_OTHER_NUMBER,other_number);
			values.put(CHNDatabase.COL_OTHER_PERIOD,other_period);
			values.put(CHNDatabase.COL_SYNC_STATUS,sync_status);
			
			long newRowId;
			newRowId = db.insert(
					CHNDatabase.OTHER_TABLE, null, values);
			return true;
		}
		
		public boolean insertLoginActivity(String date,String time,String username,String password,String sync_status){
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(CHNDatabase.COL_DATE_LOGGED_IN,date);
			values.put(CHNDatabase.COL_TIME_LOGGED_IN,time);
			values.put(CHNDatabase.COL_USERNAME,username);
			values.put(CHNDatabase.COL_PASSWORD,password);
			values.put(CHNDatabase.COL_SYNC_STATUS,sync_status);
			
			long newRowId;
			newRowId = db.insert(
					CHNDatabase.LOGIN_ACTIVITY_TABLE_NAME, null, values);
			return true;
		}
		public ArrayList<String> verifyLogin(String username, String password){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_USERNAME
	                 +"," +CHNDatabase.COL_PASSWORD
	                 +","+CHNDatabase.COL_FIRSTNAME
	                 +" from "+CHNDatabase.USER_TABLE
	                 +" where "+CHNDatabase.COL_USERNAME+" = \""+username+"\""
	                 + " and "+CHNDatabase.COL_PASSWORD+ " = \""+password+"\"";
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_USERNAME)));
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_PASSWORD)));
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_FIRSTNAME)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public int getAllLoginActivity(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			int count=0;
			 String strQuery="select * "	
	                 +" from "+CHNDatabase.LOGIN_ACTIVITY_TABLE_NAME;
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			count=c.getCount();																						
			c.close();
			return count;
			
		}
		public ArrayList<String> getAllEventCategory(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_EVENT_NAME
	                 +" from "+CHNDatabase.EVENTS_CATEGORY_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_EVENT_NAME)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		public ArrayList<String> getAllCoverageCategory(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_CATEGORY_NAME
	                 +" from "+CHNDatabase.COVERAGE_CATEGORY_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_CATEGORY_NAME)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		public ArrayList<String> getAllEventName(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_EVENT_SET_NAME
	                 +" from "+CHNDatabase.EVENTS_SET_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_EVENT_SET_NAME)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllEventNumber(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_EVENT_NUMBER
	                 +" from "+CHNDatabase.EVENTS_SET_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_EVENT_NUMBER)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		
		public ArrayList<String> getAllEventID(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_EVENT_NAME
	                 +" from "+CHNDatabase.EVENTS_SET_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
			list.add(c.getString(c.getColumnIndex(CHNDatabase._ID)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllCoverageImmunizationsTarget(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_CATEGORY_NAME
	                 +" from "+CHNDatabase.COVERAGE_SET_TABLE	
	                 +" where "+ CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL
	                 +" = '"+"Immunization"+"'";	
			 
			System.out.println(strQuery);			
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_COVERAGE_SET_CATEGORY_NAME)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllCoverageImmunizationsNumber(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_COVERAGE_NUMBER
	                 +" from "+CHNDatabase.COVERAGE_SET_TABLE	
	                 +" where "+ CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL
	                 +" = '"+"Immunization"+"'";	
			 
			System.out.println(strQuery);			
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_COVERAGE_NUMBER)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllCoverageImmunizationsPeriod(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_COVERAGE_SET_PERIOD
	                 +" from "+CHNDatabase.COVERAGE_SET_TABLE	
	                 +" where "+ CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL
	                 +" = '"+"Immunization"+"'";	
			 
			System.out.println(strQuery);			
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_COVERAGE_SET_PERIOD)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllCoverageImmunizationsId(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +" from "+CHNDatabase.COVERAGE_SET_TABLE
	                 +" where "+ CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL
	                 +" = '"+"Immunization"+"'";	
			 
			System.out.println(strQuery);			
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase._ID)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		public ArrayList<String> getAllCoveragePeopleTarget(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_COVERAGE_SET_CATEGORY_NAME
	                 +" from "+CHNDatabase.COVERAGE_SET_TABLE
	                 +" where "+ CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL
	                 +" = '"+"People"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_COVERAGE_SET_CATEGORY_NAME)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllCoveragePeopleNumber(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_COVERAGE_NUMBER
	                 +" from "+CHNDatabase.COVERAGE_SET_TABLE
	                 +" where "+ CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL
	                 +" = '"+"People"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_COVERAGE_NUMBER)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllCoveragePeoplePeriod(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_COVERAGE_SET_PERIOD
	                 +" from "+CHNDatabase.COVERAGE_SET_TABLE
	                 +" where "+ CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL
	                 +" = '"+"People"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_COVERAGE_SET_PERIOD)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		
		public ArrayList<String> getAllCoveragePeopleId(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +" from "+CHNDatabase.COVERAGE_SET_TABLE
	                 +" where "+ CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL
	                 +" = '"+"People"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase._ID)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllLearningCategory(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_LEARNING_CATEGORY
	                 +" from "+CHNDatabase.LEARNING_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_LEARNING_DESCRIPTION)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllLearningAntenatalCare(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_LEARNING_DESCRIPTION
	                 +" from "+CHNDatabase.LEARNING_TABLE
	                 +" where "+ CHNDatabase.COL_LEARNING_CATEGORY
	                 +" = '"+"Antenatal Care"+"'";	
			 
			System.out.println("1"+strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_LEARNING_DESCRIPTION)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllLearningPostnatalCare(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
					 +","+CHNDatabase.COL_LEARNING_DESCRIPTION
	                 +" from "+CHNDatabase.LEARNING_TABLE
	                 +" where "+ CHNDatabase.COL_LEARNING_CATEGORY
	                 +" = '"+"Postnatal Care"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_LEARNING_DESCRIPTION)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllLearningFamilyPlanning(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
					 +","+CHNDatabase.COL_LEARNING_DESCRIPTION
	                 +" from "+CHNDatabase.LEARNING_TABLE
	                 +" where "+ CHNDatabase.COL_LEARNING_CATEGORY
	                 +" = '"+"Family Planning"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_LEARNING_DESCRIPTION)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllLearningChildHealth(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
					 +","+CHNDatabase.COL_LEARNING_DESCRIPTION
	                 +" from "+CHNDatabase.LEARNING_TABLE
	                 +" where "+ CHNDatabase.COL_LEARNING_CATEGORY
	                 +" = '"+"Child Health"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_LEARNING_DESCRIPTION)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllLearningGeneral(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
					 +","+CHNDatabase.COL_LEARNING_DESCRIPTION
	                 +" from "+CHNDatabase.LEARNING_TABLE
	                 +" where "+ CHNDatabase.COL_LEARNING_CATEGORY
	                 +" = '"+"General"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_LEARNING_DESCRIPTION)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		public ArrayList<String> getAllLearningOther(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
					 +","+CHNDatabase.COL_LEARNING_DESCRIPTION
	                 +" from "+CHNDatabase.LEARNING_TABLE
	                 +" where "+ CHNDatabase.COL_LEARNING_CATEGORY
	                 +" = '"+"Other"+"'";	
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_LEARNING_DESCRIPTION)));
				System.out.println(list);
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		public ArrayList<String> getAllLearningId(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_LEARNING_CATEGORY
	                 +" from "+CHNDatabase.LEARNING_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase._ID)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllOtherCategory(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_OTHER_CATEGORY
	                 +" from "+CHNDatabase.OTHER_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_OTHER_CATEGORY)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllOtherNumber(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_OTHER_NUMBER
	                 +" from "+CHNDatabase.OTHER_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_OTHER_NUMBER)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		public ArrayList<String> getAllOtherPeriod(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_OTHER_PERIOD
	                 +" from "+CHNDatabase.OTHER_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_OTHER_PERIOD)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllOthersId(){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_OTHER_CATEGORY
	                 +" from "+CHNDatabase.OTHER_TABLE;
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase._ID)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllEventsForMonth(String month){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_EVENT_SET_NAME
	                 +" from "+CHNDatabase.EVENTS_SET_TABLE
	                 +" where "+CHNDatabase.COL_EVENT_PERIOD+" = \""+month+"\"";
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_EVENT_SET_NAME)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public ArrayList<String> getAllEventsNumberForMonth(String month){
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			ArrayList<String> list=new ArrayList<String>();
			 String strQuery="select "+CHNDatabase._ID
	                 +","+CHNDatabase.COL_EVENT_SET_NAME
	                 +","+CHNDatabase.COL_EVENT_NUMBER
	                 +" from "+CHNDatabase.EVENTS_SET_TABLE
	                 +" where "+CHNDatabase.COL_EVENT_PERIOD+" = \""+month+"\"";
			 
			System.out.println(strQuery);
			Cursor c=db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.add(c.getString(c.getColumnIndex(CHNDatabase.COL_EVENT_NUMBER)));
				c.moveToNext();						
			}
			c.close();
			return list;
		}
		
		public boolean editEventCategory(String event_category,String event_number,String event_period, long id){
			 SQLiteDatabase database = mDbHelper.getWritableDatabase();    
		        String updateQuery = "Update "+CHNDatabase.EVENTS_SET_TABLE+" set "+
		        					CHNDatabase.COL_EVENT_NAME+" = '"+ event_category +"'"+
		        					","+CHNDatabase.COL_EVENT_NUMBER+" = '"+event_number+"'"+
		        					","+CHNDatabase.COL_EVENT_PERIOD+" = '"+event_period+"'"+
		        					" where "+CHNDatabase._ID+" = "+id;
		        database.execSQL(updateQuery);
		        database.close();
			return true;
			
		}
		
		public boolean deleteEventCategory(long id){
			 SQLiteDatabase database = mDbHelper.getWritableDatabase();    
			 String deleteQuery="Delete from "+CHNDatabase.EVENTS_SET_TABLE+" where "+
					 			CHNDatabase._ID+" = "+ id;
			 System.out.println(deleteQuery);
		        database.execSQL(deleteQuery);
		        database.close();
			return true;
			
		}
		
		public boolean editCoverage(String coverage_category,String coverage_number,String coverage_period, long id){
			 SQLiteDatabase database = mDbHelper.getWritableDatabase();    
		        String updateQuery = "Update "+CHNDatabase.COVERAGE_SET_TABLE+" set "+
		        					CHNDatabase.COL_COVERAGE_SET_CATEGORY_NAME+" = '"+ coverage_category +"'"+
		        					","+CHNDatabase.COL_COVERAGE_NUMBER+" = '"+coverage_number+"'"+
		        					","+CHNDatabase.COL_COVERAGE_SET_PERIOD+" = '"+coverage_period+"'"+
		        					" where "+CHNDatabase._ID+" = "+id;
		        database.execSQL(updateQuery);
		        database.close();
			return true;
			
		}
		
		public boolean editLearning(String learning_category,String learning_description, long id){
			 SQLiteDatabase database = mDbHelper.getWritableDatabase();    
		        String updateQuery = "Update "+CHNDatabase.LEARNING_TABLE+" set "+
		        					CHNDatabase.COL_LEARNING_CATEGORY+" = '"+ learning_category +"'"+
		        					","+CHNDatabase.COL_LEARNING_DESCRIPTION+" = '"+learning_description+"'"+
		        					" where "+CHNDatabase._ID+" = "+id;
		        database.execSQL(updateQuery);
		        database.close();
			return true;
		}
		
		public boolean editOther(String other_category,String other_number,String other_period, long id){
			 SQLiteDatabase database = mDbHelper.getWritableDatabase();    
		        String updateQuery = "Update "+CHNDatabase.OTHER_TABLE+" set "+
		        					CHNDatabase.COL_OTHER_CATEGORY+" = '"+ other_category +"'"+
		        					","+CHNDatabase.COL_OTHER_NUMBER+" = '"+ other_number +"'"+
		        					","+CHNDatabase.COL_OTHER_PERIOD+" = '"+other_period+"'"+
		        					" where "+CHNDatabase._ID+" = "+id;
		        database.execSQL(updateQuery);
		        database.close();
			return true;
			
		}
}
