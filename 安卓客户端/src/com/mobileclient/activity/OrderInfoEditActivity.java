package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class OrderInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_orderId;
	// 声明用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明车次信息下拉框
	private Spinner spinner_trainObj;
	private ArrayAdapter<String> trainObj_adapter;
	private static  String[] trainObj_ShowText  = null;
	private List<TrainInfo> trainInfoList = null;
	/*车次信息管理业务逻辑层*/
	private TrainInfoService trainInfoService = new TrainInfoService();
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
	// 声明座位信息输入框
	private EditText ET_seatInfo;
	// 声明总票价输入框
	private EditText ET_totalPrice;
	// 声明开车时间输入框
	private EditText ET_startTime;
	// 声明终到时间输入框
	private EditText ET_endTime;
	protected String carmera_path;
	/*要保存的订单信息信息*/
	OrderInfo orderInfo = new OrderInfo();
	/*订单信息管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑订单信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getRealName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		spinner_trainObj = (Spinner) findViewById(R.id.Spinner_trainObj);
		// 获取所有的车次信息
		try {
			trainInfoList = trainInfoService.QueryTrainInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int trainInfoCount = trainInfoList.size();
		trainObj_ShowText = new String[trainInfoCount];
		for(int i=0;i<trainInfoCount;i++) { 
			trainObj_ShowText[i] = trainInfoList.get(i).getSeatId();
		}
		// 将可选内容与ArrayAdapter连接起来
		trainObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, trainObj_ShowText);
		// 设置图书类别下拉列表的风格
		trainObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_trainObj.setAdapter(trainObj_adapter);
		// 添加事件Spinner事件监听
		spinner_trainObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setTrainObj(trainInfoList.get(arg2).getSeatId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_trainObj.setVisibility(View.VISIBLE);
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
		// 设置图书类别下拉列表的风格
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startStation.setAdapter(startStation_adapter);
		// 添加事件Spinner事件监听
		spinner_startStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setStartStation(stationInfoList.get(arg2).getStationId()); 
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
		// 设置图书类别下拉列表的风格
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endStation.setAdapter(endStation_adapter);
		// 添加事件Spinner事件监听
		spinner_endStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setEndStation(stationInfoList.get(arg2).getStationId()); 
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
		// 设置图书类别下拉列表的风格
		seatType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatType.setAdapter(seatType_adapter);
		// 添加事件Spinner事件监听
		spinner_seatType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setSeatType(seatTypeList.get(arg2).getSeatTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatType.setVisibility(View.VISIBLE);
		ET_seatInfo = (EditText) findViewById(R.id.ET_seatInfo);
		ET_totalPrice = (EditText) findViewById(R.id.ET_totalPrice);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		/*单击修改订单信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取车次*/ 
					if(ET_trainNumber.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "车次输入不能为空!", Toast.LENGTH_LONG).show();
						ET_trainNumber.setFocusable(true);
						ET_trainNumber.requestFocus();
						return;	
					}
					orderInfo.setTrainNumber(ET_trainNumber.getText().toString());
					/*获取出版日期*/
					Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
					orderInfo.setStartDate(new Timestamp(startDate.getTime()));
					/*验证获取座位信息*/ 
					if(ET_seatInfo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "座位信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_seatInfo.setFocusable(true);
						ET_seatInfo.requestFocus();
						return;	
					}
					orderInfo.setSeatInfo(ET_seatInfo.getText().toString());
					/*验证获取总票价*/ 
					if(ET_totalPrice.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "总票价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_totalPrice.setFocusable(true);
						ET_totalPrice.requestFocus();
						return;	
					}
					orderInfo.setTotalPrice(Float.parseFloat(ET_totalPrice.getText().toString()));
					/*验证获取开车时间*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "开车时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					orderInfo.setStartTime(ET_startTime.getText().toString());
					/*验证获取终到时间*/ 
					if(ET_endTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "终到时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_endTime.setFocusable(true);
						ET_endTime.requestFocus();
						return;	
					}
					orderInfo.setEndTime(ET_endTime.getText().toString());
					/*调用业务逻辑层上传订单信息信息*/
					OrderInfoEditActivity.this.setTitle("正在更新订单信息信息，稍等...");
					String result = orderInfoService.UpdateOrderInfo(orderInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderId);
		this.TV_orderId.setText(orderId+"");
		for (int i = 0; i < userInfoList.size(); i++) {
			if (orderInfo.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < trainInfoList.size(); i++) {
			if (orderInfo.getTrainObj() == trainInfoList.get(i).getSeatId()) {
				this.spinner_trainObj.setSelection(i);
				break;
			}
		}
		this.ET_trainNumber.setText(orderInfo.getTrainNumber());
		for (int i = 0; i < stationInfoList.size(); i++) {
			if (orderInfo.getStartStation() == stationInfoList.get(i).getStationId()) {
				this.spinner_startStation.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < stationInfoList.size(); i++) {
			if (orderInfo.getEndStation() == stationInfoList.get(i).getStationId()) {
				this.spinner_endStation.setSelection(i);
				break;
			}
		}
		Date startDate = new Date(orderInfo.getStartDate().getTime());
		this.dp_startDate.init(startDate.getYear() + 1900,startDate.getMonth(), startDate.getDate(), null);
		for (int i = 0; i < seatTypeList.size(); i++) {
			if (orderInfo.getSeatType() == seatTypeList.get(i).getSeatTypeId()) {
				this.spinner_seatType.setSelection(i);
				break;
			}
		}
		this.ET_seatInfo.setText(orderInfo.getSeatInfo());
		this.ET_totalPrice.setText(orderInfo.getTotalPrice() + "");
		this.ET_startTime.setText(orderInfo.getStartTime());
		this.ET_endTime.setText(orderInfo.getEndTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
