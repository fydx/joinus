package com.sssta.joinus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sssta.model.Activities;
import com.sssta.model.Club;

public class activity_search extends Activity {
	private TextView textView;
	private EditText editText;
	private ListView list;
	private List<Activities> activities = null;
	private String findString;
	private Button search;
	private String name;
	private SimpleAdapter mSchedule;
	private List<Map<String, Object>> mylist;
	private Button showall;
	private List<Bitmap> list_photoBitmaps;
	private Bitmap photoBitmap_1;
	private int temp_number=0;
	private Club tempClub;
	public class ImageSimpleAdapter extends SimpleAdapter {
			private int[] mTo;
			private String[] mFrom;
			private ViewBinder mViewBinder;
			private List<? extends Map<String, ?>> mData;
			private int mResource;
			private int mDropDownResource;
			private LayoutInflater mInflater;

			public ImageSimpleAdapter(Context context,
					List<? extends Map<String, ?>> data, int resource, String[] from,
					int[] to) {
				super(context, data, resource, from, to);
				mTo = to;
				mFrom = from;
				mData = data;
				mResource = mDropDownResource = resource;
				mInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				return createViewFromResource(position, convertView, parent, mResource);
			}

			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				return createViewFromResource(position, convertView, parent,
						mDropDownResource);
			}

			private View createViewFromResource(int position, View convertView,
					ViewGroup parent, int resource) {
				View v;
				if (convertView == null) {
					v = mInflater.inflate(resource, parent, false);
				} else {
					v = convertView;
				}

				bindView(position, v);

				return v;
			}

			private void bindView(int position, View view) {
				final Map dataSet = mData.get(position);
				if (dataSet == null) {
					return;
				}

				final ViewBinder binder = mViewBinder;
				final String[] from = mFrom;
				final int[] to = mTo;
				final int count = to.length;

				for (int i = 0; i < count; i++) {
					final View v = view.findViewById(to[i]);
					if (v != null) {
						final Object data = dataSet.get(from[i]);
						String text = data == null ? "" : data.toString();
						if (text == null) {
							text = "";
						}

						boolean bound = false;
						if (binder != null) {
							bound = binder.setViewValue(v, data, text);
						}

						if (!bound) {
							if (v instanceof Checkable) {
								if (data instanceof Boolean) {
									((Checkable) v).setChecked((Boolean) data);
								} else if (v instanceof TextView) {
									// Note: keep the instanceof TextView check at the
									// bottom of these
									// ifs since a lot of views are TextViews (e.g.
									// CheckBoxes).
									setViewText((TextView) v, text);
								} else {
									throw new IllegalStateException(v.getClass()
											.getName()
											+ " should be bound to a Boolean, not a "
											+ (data == null ? "<unknown type>"
													: data.getClass()));
								}
							} else if (v instanceof TextView) {
								// Note: keep the instanceof TextView check at the
								// bottom of these
								// ifs since a lot of views are TextViews (e.g.
								// CheckBoxes).
								setViewText((TextView) v, text);
							} else if (v instanceof ImageView) {
								if (data instanceof Integer) {
									setViewImage((ImageView) v, (Integer) data);
								} else if (data instanceof Bitmap) {// 仅仅添加这一步
									setViewImage((ImageView) v, (Bitmap) data);
								} else {
									setViewImage((ImageView) v, text);
								}
							} else {
								throw new IllegalStateException(
										v.getClass().getName()
												+ " is not a view that can be bounds by this SimpleAdapter");
							}
						}
					}
				}
			}

			/**
			 * 添加这个方法来处理Bitmap类型参数
			 * 
			 * @param v
			 * @param bitmap
			 */
			public void setViewImage(ImageView v, Bitmap bitmap) {
				v.setImageBitmap(bitmap);
			}

	 }
	// private List<Map<String, Object>> mData; // listview数据文件
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		// mData = null;
		// mData = new ArrayList<Map<String, Object>>();
		ListView list = (ListView) findViewById(R.id.listView1);
		editText = (EditText) findViewById(R.id.text_search);
		search = (Button) findViewById(R.id.search);
		showall = (Button) findViewById(R.id.showall);
		activities = new ArrayList<Activities>();
		Log.d("activities_search", "success");
		// activities=Activities.FindActivitiesByName("这是活动名称");
		activities = Activities.GetAllActivities();
		Log.d("activities_search", String.valueOf(activities.size()));
		mylist = new ArrayList<Map<String, Object>>();
		list_photoBitmaps = new ArrayList<Bitmap>();
		for(int n=1;n<=activities.size();n++)
		{
			String path_photoString = Activities.GetImage(n);
			Log.e("pathphoto", path_photoString);
			photoBitmap_1 = BitmapFactory.decodeFile(path_photoString);
			list_photoBitmaps.add(photoBitmap_1);
		}
		for (Activities act : activities) {
			
			tempClub= Club.GetClub(act.getPublisher_id());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Activity_name", act.getName());
			map.put("Activity_shetuan", tempClub.getName());
			map.put("Activity_time", act.getTime());
			map.put("Activity_place", act.getPosition());
			map.put("Activity_poster",list_photoBitmaps.get(temp_number));
			temp_number ++ ;
			Log.i("activities_id", String.valueOf(act.getId()));
			mylist.add(map);
		}

		mSchedule = new ImageSimpleAdapter(this, // 没什么解释
				mylist,// 数据来源
				R.layout.listitem_small_new,// ListItem的XML实现

				// 动态数组与ListItem对应的子项
				new String[] { "Activity_name", "Activity_shetuan",
						"Activity_time", "Activity_place","Activity_poster" },

				// ListItem的XML文件里面的两个TextView ID
				new int[] { R.id.name_small_new, R.id.shetuan_small_new,
						R.id.time_small_new, R.id.place_small_new ,R.id.poster_small_new});
		// 添加并且显示
		list.setDividerHeight(0);
		list.setAdapter(mSchedule);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {  
		        Intent intent = new Intent(getApplicationContext(), activity_detail.class); 
		        intent.putExtra("fromMain", activities.get(position));
		        startActivity(intent);   
		     }  
		
		});
			
	
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = editText.getText().toString();
				activities.clear();
				mylist.clear();
				activities = Activities.FindActivitiesByName(name);
				Log.i("activities_id_size", String.valueOf(activities.size()));

				if (activities != null) {
					for (Activities act : activities) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("Activity_name", act.getName());
						map.put("Activity_shetuan", act.getPublisher_id());
						map.put("Activity_time", act.getTime());
						map.put("Activity_place", act.getPosition());
						map.put("Activity_poster",list_photoBitmaps.get(act.getId()-1));
						Log.i("activities_id", String.valueOf(act.getId()));
						mylist.add(map);
						// Log.i("activities_id", String.valueOf(act.getId()));

					}
					mSchedule.notifyDataSetChanged();
				}
			}
		});
		showall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activities.clear();
				mylist.clear();
				activities = Activities.GetAllActivities();
				temp_number = 0;
				Log.i("activities_id_size", String.valueOf(activities.size()));

				for (Activities act : activities) {
					tempClub=Club.GetClub(act.getPublisher_id());
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("Activity_name", act.getName());
					map.put("Activity_shetuan", tempClub.getName());
					map.put("Activity_time", act.getTime());
					map.put("Activity_place", act.getPosition());
					map.put("Activity_poster",list_photoBitmaps.get(temp_number));
					temp_number ++ ;
					// Log.i("activities_id", String.valueOf(act.getId()));
					mylist.add(map);
					// Log.i("activities_id", String.valueOf(act.getId()));

				}
				mSchedule.notifyDataSetChanged();
			}
		});
	}
	 
	
}
