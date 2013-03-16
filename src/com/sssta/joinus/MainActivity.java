package com.sssta.joinus;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sssta.model.Activities;
import com.sssta.model.Club;

public class MainActivity extends Activity {
	// ViewPager��google SDk���Դ���һ�����Ӱ���һ���࣬��������ʵ����Ļ����л���
	// android-support-v4.jar
	private Button activity_recent;
	private Button activity_search;
	private Button myActivity;
	private ViewPager mPager;// ҳ������
	private List<View> listViews = null; // Tabҳ���б�
	private List<Activities> activities = null;
	private ImageView cursor;// ����ͼƬ
	private TextView t1, t2, t3; // ҳ��ͷ��
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	private int count, photoCount;
	private int temp_number = 0;
	private TextView textView_name;
	private TextView textView_shetuan;
	private TextView textView_time;
	private TextView textView_place;
	private View v;
	// private Set<Activities> activities_set=null;
	private joinus_init ini;
	private View layout_temp = null;
	private String text_activity;
	private String shetuan;
	private String text_time;
	private String text_place;
	private String test;
	private ImageView posterImageView;
	private Button refresh;
	private View account_View;
	private Builder account;
	private String username1;
	private String password1;
	private EditText user;
	private EditText pass;
	private Boolean enter;
	private SharedPreferences use;
	private joinus_init init;
	private List<Bitmap> list_photoBitmap;
	private Bitmap photoBitmap;
	Runnable runableCreateDialog = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			initDialog();
		}

	};
	Runnable loadactivities = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			activities = Activities.GetAllActivities(1, 7);
		}

	};
	public void onCreate(Bundle savedInstanceState) {

	//	if (Float.parseFloat(android.os.Build.VERSION.RELEASE.substring(0,3))>2.3)
				
	/*	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // or
																		// .detectAll()
																		// for
																		// all
																		// detectable
																		// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
				}*/

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init = (joinus_init) getApplication();
		LayoutInflater inflater = getLayoutInflater();
		account_View = inflater.inflate(R.layout.dialog_for_account,
				(ViewGroup) findViewById(R.id.dialog));

		/*
		 * String string="select * from activities;"; Log.i("start", "success");
		 * //string=cinScanner.nextLine(); try { String
		 * m=SocketClient.Execute(string); System.out.println(m); Log.e("x", m);
		 * } catch (Exception e) { // TODO: handle exception Log.e("x",
		 * "failed"); }
		 */
		account = new AlertDialog.Builder(this).setTitle("�˻���֤")
				.setView(account_View).setPositiveButton("ȷ��", null)
				.setNegativeButton("ȡ��", null);
		activities = new ArrayList<Activities>();
		Log.i("getall", "success2");
		try {
			//activities = Activities.GetAllActivities(1, 7);
			InitViewPager();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "�޷����ӷ�����",
					Toast.LENGTH_LONG).show();
		}
		loadtask task= new loadtask();
		//task.execute();
		loadactivities.run();
		InitViewPager();
	//	Log.e("asnytask", "success1");

		Log.w("activity", String.valueOf(activities.size()));
		// Log.w("activity_set", String.valueOf(activities_set.size()));
		if (activities.size() >= 8)
			count = 8;
		else
			count = activities.size();
		Log.d("count", String.valueOf(count));
		activity_search = (Button) findViewById(R.id.activity_search);
		// refresh = (Button) findViewById(R.id.refresh);
		// refresh.setVisibility(View.GONE);
		// activity_recent=(Button)findViewById(0x7f080002);
		myActivity = (Button) findViewById(R.id.myActivity);
		
		/*
		 * refresh.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub // activities.clear(); // listViews.clear(); // mPager.n //
		 * InitViewPager(); } });
		 */
		myActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// account.show();
				if (init.isLogin() == true) {
					Intent intent = new Intent();
					/* ָ��intentҪ�������� */
					intent.setClass(MainActivity.this, myActivity.class);
					/* ����һ���µ�Activity */
					startActivity(intent);
				} else {
					Handler handler = new Handler();
					handler.post(runableCreateDialog);
					// Intent intent = new Intent();
					/* ָ��intentҪ�������� */
					// intent.setClass(MainActivity.this, activity_login.class);
					/* ����һ���µ�Activity */
					// startActivity(intent);
				}

			}
		});
		activity_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(MainActivity.this, activity_search.class);
				/* ����һ���µ�Activity */
				startActivity(intent);

			}
		});
				
		// test=Activities.GetImage(1);
		// Log.i("photo", test);

	}

	private void CreateLoginAlert() {
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle("�˺ŵ�½");
		ad.setView(account_View);

	}

	/**
	 * ��ʼ��ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		count = 0;
		listViews = new ArrayList<View>();
		// ��ȡͼƬ
		list_photoBitmap = new ArrayList<Bitmap>();
		for (int n = 1; n <= activities.size(); n++) {
			String path_photoString = Activities.GetImage(n);
			Log.e("pathphoto", path_photoString);
			photoBitmap = BitmapFactory.decodeFile(path_photoString);
			list_photoBitmap.add(photoBitmap);
		}
		LayoutInflater mInflater = getLayoutInflater();

		for (Activities act : activities) {
			Log.d("tempnumber", String.valueOf(temp_number));
			Log.d("count", String.valueOf(count));
			if (temp_number > 7)
				break;
			Club tempclub;
			temp_number++;
			tempclub=Club.GetClub(act.getPublisher_id());
			text_activity = act.getName();
			shetuan = tempclub.getName();
			text_time = act.getTime();
			text_place = act.getPosition();
			layout_temp = mInflater.inflate(R.layout.list_for_viewpager_new,
					null);
			posterImageView = (ImageView) layout_temp
					.findViewById(R.id.poster_new);
			textView_name = (TextView) layout_temp.findViewById(R.id.name_new);
			textView_shetuan = (TextView) layout_temp
					.findViewById(R.id.shetuan_new1);
			textView_time = (TextView) layout_temp.findViewById(R.id.time_new);
			textView_place = (TextView) layout_temp
					.findViewById(R.id.place_new);
			textView_name.setText(text_activity);
			textView_shetuan.setText(shetuan);
			textView_place.setText(text_place);
			textView_time.setText(text_time);
			// ��������ͼƬ����
			posterImageView.setImageBitmap(list_photoBitmap
					.get(temp_number - 1));
			listViews.add(layout_temp);
		}

		// Log.e("test", testTextView.getText().toString());
		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		// mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * ViewPager������
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int position) {
			ImageView img = (ImageView) mListViews.get(position).findViewById(
					R.id.poster_new);
			final Intent intent5 = new Intent();
			intent5.setClass(MainActivity.this, activity_detail.class);
			switch (position) {
			case 0:
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						intent5.putExtra("fromMain", activities.get(0));
				
						startActivity(intent5);
					}
				});
				break;
			case 1:
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						intent5.putExtra("fromMain", activities.get(1));
				
						startActivity(intent5);
					}
				});
				break;
			case 2:
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						intent5.putExtra("fromMain", activities.get(2));
				
						startActivity(intent5);
					}
				});
				break;
			case 3:
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						intent5.putExtra("fromMain", activities.get(3));
					
						startActivity(intent5);
					}
				});
				break;
			case 4:
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						intent5.putExtra("fromMain", activities.get(4));
					
						startActivity(intent5);
					}
				});
				break;
			case 5:
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						intent5.putExtra("fromMain", activities.get(5));
				
						startActivity(intent5);
					}
				});
				break;
			case 6:
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						intent5.putExtra("fromMain", activities.get(6));
				
						startActivity(intent5);
					}
				});
				break;
			case 7:
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						intent5.putExtra("fromMain", activities.get(7));
						startActivity(intent5);
					}
				});
				break;
			default:
				break;
			}
			((ViewPager) arg0).addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

	// dialog ��ʼ�� -- �����

	public void initDialog() {
		init = (joinus_init) getApplication();
		LayoutInflater factory = LayoutInflater.from(MainActivity.this);
		// �õ��Զ���Ի���
		final View DialogView = factory.inflate(R.layout.dialog_for_account,
				null);
		pass = (EditText) DialogView.findViewById(R.id.password);
		user = (EditText) DialogView.findViewById(R.id.account);
		use = getApplicationContext()
				.getSharedPreferences("user", MODE_PRIVATE);
		username1 = use.getString("user1", "");
		password1 = use.getString("pass1", "");
		user.setText(username1);
		pass.setText(password1);
		// �����Ի���
		new AlertDialog.Builder(MainActivity.this).setTitle("�û���¼")
				.setView(DialogView)
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						password1 = pass.getText().toString();
						username1 = user.getText().toString();

						Log.i("username", username1);
						Log.i("password", password1);
						enter = Club.AccountValid(username1, password1);
						Log.i("enter", String.valueOf(enter));
						if (enter == true) {
							Intent intent3 = new Intent();
							/* ָ��intentҪ�������� */
							intent3.setClass(MainActivity.this,
									myActivity.class);
							intent3.putExtra("club", username1);
							Editor editor_user = use.edit();
							// Editor editor_pass=pas.edit();
							editor_user.putString("user1", username1);
							editor_user.putString("pass1", password1);
							editor_user.commit();
							init.setLogin(true);
							init.setClub(username1);
							// editor_pass.commit();
							/* ����һ���µ�Activity */
							startActivity(intent3);

						}
					}
				}).show();

	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("ȷ���˳�Join Us��")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// �����ȷ�ϡ���Ĳ���
						System.exit(0);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// ��������ء���Ĳ���,���ﲻ����û���κβ���
					}
				}).show();
		// super.onBackPressed();

	}
 class loadtask extends AsyncTask
 {
	 @Override  
     protected void onPreExecute() {  
         //��һ��ִ�з���  
         super.onPreExecute();  
     }  
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		try {
			
			activities = Activities.GetAllActivities(1, 7);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "�޷����ӷ�����",
					Toast.LENGTH_LONG).show();
		}
	
		return null;
	}
	
	
 }
}
