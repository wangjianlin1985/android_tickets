package com.mobileclient.activity;

import java.util.Date;
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
public class TrainInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_seatId;
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
	// 声明票价控件
	private TextView TV_price;
	// 声明总座位数控件
	private TextView TV_seatNumber;
	// 声明剩余座位数控件
	private TextView TV_leftSeatNumber;
	// 声明开车时间控件
	private TextView TV_startTime;
	// 声明终到时间控件
	private TextView TV_endTime;
	// 声明历时控件
	private TextView TV_totalTime;
	/* 要保存的车次信息信息 */
	TrainInfo trainInfo = new TrainInfo(); 
	/* 车次信息管理业务逻辑层 */
	private TrainInfoService trainInfoService = new TrainInfoService();
	private StationInfoService stationInfoService = new StationInfoService();
	private SeatTypeService seatTypeService = new SeatTypeService();
	private int seatId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.traininfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看车次信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_seatId = (TextView) findViewById(R.id.TV_seatId);
		TV_trainNumber = (TextView) findViewById(R.id.TV_trainNumber);
		TV_startStation = (TextView) findViewById(R.id.TV_startStation);
		TV_endStation = (TextView) findViewById(R.id.TV_endStation);
		TV_startDate = (TextView) findViewById(R.id.TV_startDate);
		TV_seatType = (TextView) findViewById(R.id.TV_seatType);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_seatNumber = (TextView) findViewById(R.id.TV_seatNumber);
		TV_leftSeatNumber = (TextView) findViewById(R.id.TV_leftSeatNumber);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_endTime = (TextView) findViewById(R.id.TV_endTime);
		TV_totalTime = (TextView) findViewById(R.id.TV_totalTime);
		Bundle extras = this.getIntent().getExtras();
		seatId = extras.getInt("seatId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TrainInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    trainInfo = trainInfoService.GetTrainInfo(seatId); 
		this.TV_seatId.setText(trainInfo.getSeatId() + "");
		this.TV_trainNumber.setText(trainInfo.getTrainNumber());
		StationInfo startStation = stationInfoService.GetStationInfo(trainInfo.getStartStation());
		this.TV_startStation.setText(startStation.getStationName());
		StationInfo endStation = stationInfoService.GetStationInfo(trainInfo.getEndStation());
		this.TV_endStation.setText(endStation.getStationName());
		Date startDate = new Date(trainInfo.getStartDate().getTime());
		String startDateStr = (startDate.getYear() + 1900) + "-" + (startDate.getMonth()+1) + "-" + startDate.getDate();
		this.TV_startDate.setText(startDateStr);
		SeatType seatType = seatTypeService.GetSeatType(trainInfo.getSeatType());
		this.TV_seatType.setText(seatType.getSeatTypeName());
		this.TV_price.setText(trainInfo.getPrice() + "");
		this.TV_seatNumber.setText(trainInfo.getSeatNumber() + "");
		this.TV_leftSeatNumber.setText(trainInfo.getLeftSeatNumber() + "");
		this.TV_startTime.setText(trainInfo.getStartTime());
		this.TV_endTime.setText(trainInfo.getEndTime());
		this.TV_totalTime.setText(trainInfo.getTotalTime());
	} 
}
