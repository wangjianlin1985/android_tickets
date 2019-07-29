package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.TrainInfo;
import com.mobileclient.service.TrainInfoService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.SeatType;
import com.mobileclient.service.SeatTypeService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class TrainInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明车次输入框
	private EditText ET_trainNumber;
	// 声明始发站下拉框
	private Spinner spinner_startStation;
	private ArrayAdapter<String> startStation_adapter;
	private static  String[] startStation_ShowText  = null;
	private List<StationInfo> stationInfoList = null;
	/*始发站管理业务逻辑层*/
	private StationInfoService stationInfoService = new StationInfoService();
	// 声明终到站下拉框
	private Spinner spinner_endStation;
	private ArrayAdapter<String> endStation_adapter;
	private static  String[] endStation_ShowText  = null;
	// 出版开车日期控件
	private DatePicker dp_startDate;
	// 声明席别下拉框
	private Spinner spinner_seatType;
	private ArrayAdapter<String> seatType_adapter;
	private static  String[] seatType_ShowText  = null;
	private List<SeatType> seatTypeList = null;
	/*席别管理业务逻辑层*/
	private SeatTypeService seatTypeService = new SeatTypeService();
	// 声明票价输入框
	private EditText ET_price;
	// 声明总座位数输入框
	private EditText ET_seatNumber;
	// 声明剩余座位数输入框
	private EditText ET_leftSeatNumber;
	// 声明开车时间输入框
	private EditText ET_startTime;
	// 声明终到时间输入框
	private EditText ET_endTime;
	// 声明历时输入框
	private EditText ET_totalTime;
	protected String carmera_path;
	/*要保存的车次信息信息*/
	TrainInfo trainInfo = new TrainInfo();
	/*车次信息管理业务逻辑层*/
	private TrainInfoService trainInfoService = new TrainInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.traininfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加车次信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_trainNumber = (EditText) findViewById(R.id.ET_trainNumber);
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// 获取所有的始发站
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int stationInfoCount = stationInfoList.size();
		startStation_ShowText = new String[stationInfoCount];
		for(int i=0;i<stationInfoCount;i++) { 
			startStation_ShowText[i] = stationInfoList.get(i).getStationName();
		}
		// 将可选内容与ArrayAdapter连接起来
		startStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startStation_ShowText);
		// 设置下拉列表的风格
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startStation.setAdapter(startStation_adapter);
		// 添加事件Spinner事件监听
		spinner_startStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				trainInfo.setStartStation(stationInfoList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startStation.setVisibility(View.VISIBLE);
		spinner_endStation = (Spinner) findViewById(R.id.Spinner_endStation);
		endStation_ShowText = new String[stationInfoCount];
		for(int i=0;i<stationInfoCount;i++) { 
			endStation_ShowText[i] = stationInfoList.get(i).getStationName();
		}
		// 将可选内容与ArrayAdapter连接起来
		endStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endStation_ShowText);
		// 设置下拉列表的风格
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endStation.setAdapter(endStation_adapter);
		// 添加事件Spinner事件监听
		spinner_endStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				trainInfo.setEndStation(stationInfoList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endStation.setVisibility(View.VISIBLE);
		dp_startDate = (DatePicker)this.findViewById(R.id.dp_startDate);
		spinner_seatType = (Spinner) findViewById(R.id.Spinner_seatType);
		// 获取所有的席别
		try {
			seatTypeList = seatTypeService.QuerySeatType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int seatTypeCount = seatTypeList.size();
		seatType_ShowText = new String[seatTypeCount];
		for(int i=0;i<seatTypeCount;i++) { 
			seatType_ShowText[i] = seatTypeList.get(i).getSeatTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		seatType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatType_ShowText);
		// 设置下拉列表的风格
		seatType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatType.setAdapter(seatType_adapter);
		// 添加事件Spinner事件监听
		spinner_seatType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				trainInfo.setSeatType(seatTypeList.get(arg2).getSeatTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatType.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_seatNumber = (EditText) findViewById(R.id.ET_seatNumber);
		ET_leftSeatNumber = (EditText) findViewById(R.id.ET_leftSeatNumber);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		ET_totalTime = (EditText) findViewById(R.id.ET_totalTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加车次信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取车次*/ 
					if(ET_trainNumber.getText().toString().equals("")) {
						Toast.makeText(TrainInfoAddActivity.this, "车次输入不能为空!", Toast.LENGTH_LONG).show();
						ET_trainNumber.setFocusable(true);
						ET_trainNumber.requestFocus();
						return;	
					}
					trainInfo.setTrainNumber(ET_trainNumber.getText().toString());
					/*获取开车日期*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					trainInfo.setStartDate(new Timestamp(startDate.getTime()));
					/*验证获取票价*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(TrainInfoAddActivity.this, "票价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					trainInfo.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取总座位数*/ 
					if(ET_seatNumber.getText().toString().equals("")) {
						Toast.makeText(TrainInfoAddActivity.this, "总座位数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_seatNumber.setFocusable(true);
						ET_seatNumber.requestFocus();
						return;	
					}
					trainInfo.setSeatNumber(Integer.parseInt(ET_seatNumber.getText().toString()));
					/*验证获取剩余座位数*/ 
					if(ET_leftSeatNumber.getText().toString().equals("")) {
						Toast.makeText(TrainInfoAddActivity.this, "剩余座位数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leftSeatNumber.setFocusable(true);
						ET_leftSeatNumber.requestFocus();
						return;	
					}
					trainInfo.setLeftSeatNumber(Integer.parseInt(ET_leftSeatNumber.getText().toString()));
					/*验证获取开车时间*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(TrainInfoAddActivity.this, "开车时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					trainInfo.setStartTime(ET_startTime.getText().toString());
					/*验证获取终到时间*/ 
					if(ET_endTime.getText().toString().equals("")) {
						Toast.makeText(TrainInfoAddActivity.this, "终到时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_endTime.setFocusable(true);
						ET_endTime.requestFocus();
						return;	
					}
					trainInfo.setEndTime(ET_endTime.getText().toString());
					/*验证获取历时*/ 
					if(ET_totalTime.getText().toString().equals("")) {
						Toast.makeText(TrainInfoAddActivity.this, "历时输入不能为空!", Toast.LENGTH_LONG).show();
						ET_totalTime.setFocusable(true);
						ET_totalTime.requestFocus();
						return;	
					}
					trainInfo.setTotalTime(ET_totalTime.getText().toString());
					/*调用业务逻辑层上传车次信息信息*/
					TrainInfoAddActivity.this.setTitle("正在上传车次信息信息，稍等...");
					String result = trainInfoService.AddTrainInfo(trainInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
