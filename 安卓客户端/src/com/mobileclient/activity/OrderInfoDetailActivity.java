package com.mobileclient.activity;

import java.util.Date;
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
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class OrderInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_orderId;
	// 声明用户控件
	private TextView TV_userObj;
	// 声明车次信息控件
	private TextView TV_trainObj;
	// 声明车次控件
	private TextView TV_trainNumber;
	// 声明始发站控件
	private TextView TV_startStation;
	// 声明终到站控件
	private TextView TV_endStation;
	// 声明开车日期控件
	private TextView TV_startDate;
	// 声明席别控件
	private TextView TV_seatType;
	// 声明座位信息控件
	private TextView TV_seatInfo;
	// 声明总票价控件
	private TextView TV_totalPrice;
	// 声明开车时间控件
	private TextView TV_startTime;
	// 声明终到时间控件
	private TextView TV_endTime;
	/* 要保存的订单信息信息 */
	OrderInfo orderInfo = new OrderInfo(); 
	/* 订单信息管理业务逻辑层 */
	private OrderInfoService orderInfoService = new OrderInfoService();
	private UserInfoService userInfoService = new UserInfoService();
	private TrainInfoService trainInfoService = new TrainInfoService();
	private StationInfoService stationInfoService = new StationInfoService();
	private SeatTypeService seatTypeService = new SeatTypeService();
	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看订单信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_trainObj = (TextView) findViewById(R.id.TV_trainObj);
		TV_trainNumber = (TextView) findViewById(R.id.TV_trainNumber);
		TV_startStation = (TextView) findViewById(R.id.TV_startStation);
		TV_endStation = (TextView) findViewById(R.id.TV_endStation);
		TV_startDate = (TextView) findViewById(R.id.TV_startDate);
		TV_seatType = (TextView) findViewById(R.id.TV_seatType);
		TV_seatInfo = (TextView) findViewById(R.id.TV_seatInfo);
		TV_totalPrice = (TextView) findViewById(R.id.TV_totalPrice);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_endTime = (TextView) findViewById(R.id.TV_endTime);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderId); 
		this.TV_orderId.setText(orderInfo.getOrderId() + "");
		UserInfo userObj = userInfoService.GetUserInfo(orderInfo.getUserObj());
		this.TV_userObj.setText(userObj.getRealName());
		TrainInfo trainObj = trainInfoService.GetTrainInfo(orderInfo.getTrainObj());
		this.TV_trainObj.setText(trainObj.getSeatId());
		this.TV_trainNumber.setText(orderInfo.getTrainNumber());
		StationInfo startStation = stationInfoService.GetStationInfo(orderInfo.getStartStation());
		this.TV_startStation.setText(startStation.getStationName());
		StationInfo endStation = stationInfoService.GetStationInfo(orderInfo.getEndStation());
		this.TV_endStation.setText(endStation.getStationName());
		Date startDate = new Date(orderInfo.getStartDate().getTime());
		String startDateStr = (startDate.getYear() + 1900) + "-" + (startDate.getMonth()+1) + "-" + startDate.getDate();
		this.TV_startDate.setText(startDateStr);
		SeatType seatType = seatTypeService.GetSeatType(orderInfo.getSeatType());
		this.TV_seatType.setText(seatType.getSeatTypeName());
		this.TV_seatInfo.setText(orderInfo.getSeatInfo());
		this.TV_totalPrice.setText(orderInfo.getTotalPrice() + "");
		this.TV_startTime.setText(orderInfo.getStartTime());
		this.TV_endTime.setText(orderInfo.getEndTime());
	} 
}
