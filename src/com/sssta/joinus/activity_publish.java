package com.sssta.joinus;

import java.io.Console;
import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sssta.model.Activities;

public class activity_publish extends Activity {
	private TextView textView_shetuanmingcheng;
	// private EditText editText_name;
	private String shetuanmingcheng;
	private String name_Activity;
	private String photo_string, clubid;
	// private TextView textView_shetuanmingcheng;
	private TextView name_shetuan;
	private EditText name_EditText;
	private EditText place_EditText;
	private EditText detail_EditText;
	//private EditText contact_EditText;
	private Button time_select;
	private Button date_select;
	private Button photo_chooser;
	private Button submit;
	private String name, place, detail, contact, time, date, clubname;
	private Activities activities;
	private int tempid=-1;
	private boolean sub_photo = false;
	private Bitmap poster;
	private ImageView poster_image;
	private int mHour, mMinute, year, monthOfYear, dayOfMonth;
	final static int TIME_DIALOG = 0;
	final static int DATE_DIALOG = 1;
	private String sendString;
	private File sdcardTempFile;
	private AlertDialog dialog;
	private joinus_init init;
	private int cropx = 210, cropy = 297, crop = 180;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish);
		textView_shetuanmingcheng = (TextView) findViewById(R.id.shetuanmingcheng);
		name_EditText = (EditText) findViewById(R.id.name_EditText);
		place_EditText = (EditText) findViewById(R.id.place_EditText);
		detail_EditText = (EditText) findViewById(R.id.detail_EditTexts);
	//	contact_EditText = (EditText) findViewById(R.id.contact_EditText);
		photo_chooser = (Button) findViewById(R.id.photo_chooser);
		init = (joinus_init) getApplication();
		poster_image = (ImageView) findViewById(R.id.poster);
		activities = new Activities();
		time_select = (Button) findViewById(R.id.time_select);
		date_select = (Button) findViewById(R.id.date_select);
		Intent intent2 = getIntent();
		clubname = intent2.getStringExtra("clubname");
		clubid = intent2.getStringExtra("clubid");
		Log.i("clubname", clubname);
		name_shetuan = (TextView) findViewById(R.id.name_shetuan);
		name_shetuan.setText(clubname);
		submit = (Button) findViewById(R.id.submit);
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		sdcardTempFile = new File("/mnt/sdcard/", "tmp_pic_" + clubid + ".jpg");
		time_select.setOnClickListener(clickListener);
		photo_chooser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		date_select.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG);

			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent3 = new Intent();
				intent3.setClass(activity_publish.this, myActivity.class);
				name = name_EditText.getText().toString();
				place = place_EditText.getText().toString();
				detail = detail_EditText.getText().toString();
				time = time_select.getText().toString();
				date = date_select.getText().toString();
				Log.i("time", time);
				Log.i("date", date);
				Log.i("detail", detail);
				activities.setContent(detail);
				activities.setName(name);
				activities.setPosition(place);
				activities.setTime(date + " " + time);
				activities.setPublisher_id("sssta");
				Log.e("success", "success");
				
				if (name_EditText.getText().length() != 0
						&& place_EditText.getText().length() != 0
						&& detail_EditText.getText().length() != 0
						&& date.compareTo("选择日期")!=0 && time.compareTo("选择时间")!=0 ) {
					{
						Log.i("date", date);
						Log.i("time", time);
						Log.i("path", sdcardTempFile.getAbsolutePath());

						tempid = Activities.AddActivities(activities);
						sub_photo = Activities.UploadPic(tempid,sdcardTempFile.getAbsolutePath());
					    
						// sub_photo=Activities.UploadPic(1,
						// sdcardTempFile.getAbsolutePath());
			
						Log.i("sub_photo", String.valueOf(sub_photo));
						Log.i("tempid",String.valueOf(tempid));
					}

					if (tempid>0 && sub_photo == true) {
						Toast.makeText(getApplicationContext(), "添加成功",
								Toast.LENGTH_SHORT).show();
						init.setFirstboot_my(false);
						// startActivity(intent);
						Intent intent4= new Intent();
						intent4.setClass(activity_publish.this, myActivity.class);
						startActivity(intent4);
						finish();
						
					}

					else {
						Toast.makeText(getApplicationContext(), "添加失败",
								Toast.LENGTH_SHORT).show();

					}
				} else
					Toast.makeText(getApplicationContext(), "请完善信息",
							Toast.LENGTH_SHORT).show();
			}

		});
		photo_chooser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.PICK");
				intent.setDataAndType(
						MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
				intent.putExtra("output", Uri.fromFile(sdcardTempFile));
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 3);// 裁剪框比例
				intent.putExtra("aspectY", 4);
				intent.putExtra("outputX", cropx);// 输出图片大小
				intent.putExtra("outputY", cropy);
				startActivityForResult(intent, 100);
				// TODO Auto-generated method stub
				/*
				 * if (dialog == null) { dialog = new
				 * AlertDialog.Builder(activity_publish.this).setItems(new
				 * String[] { "相机", "相册" }, new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { if (which == 0) { Intent intent = new
				 * Intent("android.media.action.IMAGE_CAPTURE");
				 * intent.putExtra("output", Uri.fromFile(sdcardTempFile));
				 * intent.putExtra("crop", "true"); intent.putExtra("aspectX",
				 * 3);// 裁剪框比例 intent.putExtra("aspectY", 4);
				 * intent.putExtra("outputX", cropx);// 输出图片大小
				 * intent.putExtra("outputY", cropy);
				 * startActivityForResult(intent, 101); } else { Intent intent =
				 * new Intent("android.intent.action.PICK");
				 * intent.setDataAndType
				 * (MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
				 * intent.putExtra("output", Uri.fromFile(sdcardTempFile));
				 * intent.putExtra("crop", "true"); intent.putExtra("aspectX",
				 * 3);// 裁剪框比例 intent.putExtra("aspectY", 4);
				 * intent.putExtra("outputX", cropx);// 输出图片大小
				 * intent.putExtra("outputY", cropy);
				 * startActivityForResult(intent, 100); } } }).create(); } if
				 * (!dialog.isShowing()) { dialog.show(); }
				 */
			}
		});
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog(TIME_DIALOG);
		}
	};

	private void updateDisplay() {
		// TODO Auto-generated method stub
		time_select.setText(new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute)).append(":00"));
	}

	private String pad(int c) {
		// TODO Auto-generated method stub
		if (c >= 10) {
			return String.valueOf(c);
		} else {
			return "0" + String.valueOf(c);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case TIME_DIALOG:

			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
		case DATE_DIALOG:
			return new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					date_select.setText(year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth);
				}
			}, year, monthOfYear, dayOfMonth);

		}
		return null;
	}

	private OnTimeSetListener mTimeSetListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			mHour = hourOfDay;
			mMinute = minute;
			updateDisplay();
		}

	};

	public void onClick(View v) {
		if (v == poster_image) {

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == RESULT_OK) {
			Bitmap bmp = BitmapFactory.decodeFile(sdcardTempFile
					.getAbsolutePath());
			// Log.i("path",sdcardTempFile.getAbsolutePath());
			poster_image.setImageBitmap(bmp);
		}
	}
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(activity_publish.this, myActivity.class);
		startActivity(intent);
		finish();
	}
		// super.onBackPressed();
	
}
