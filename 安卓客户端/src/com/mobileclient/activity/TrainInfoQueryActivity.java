package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.TrainInfo;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.domain.SeatType;
import com.mobileclient.service.SeatTypeService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class TrainInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明车次输入框
	private EditText ET_trainNumber;
	// 声明始发站下拉框
	private Spinner spinner_startStation;
	private ArrayAdapter<String> startStation_adapter;
	private static  String[] startStation_ShowText  = null;
	private List<StationInfo> stationInfoList = null; 
	/*站点信息管理业务逻辑层*/
	private StationInfoService stationInfoService = new StationInfoService();
	// 声明终到站下拉框
	private Spinner spinner_endStation;
	private ArrayAdapter<String> endStation_adapter;
	private static  String[] endStation_ShowText  = null;
	// 开车日期控件
	private DatePicker dp_startDate;
	private CheckBox cb_startDate;
	// 声明席别下拉框
	private Spinner spinner_seatType;
	private ArrayAdapter<String> seatType_adapter;
	private static  String[] seatType_ShowText  = null;
	private List<SeatType> seatTypeList = null; 
	/*座位席别管理业务逻辑层*/
	private SeatTypeService seatTypeService = new SeatTypeService();
	/*查询过滤条件保存到这个对象中*/
	private TrainInfo queryConditionTrainInfo = new TrainInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.traininfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置车次信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_trainNumber = (EditText) findViewById(R.id.ET_trainNumber);
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// 获取所有的站点信息
		try {
			stationInfoList = stationInfoService.QueryStationInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int stationInfoCount = stationInfoList.size();
		startStation_ShowText = new String[stationInfoCount+1];
		startStation_ShowText[0] = "不限制";
		for(int i=1;i<=stationInfoCount;i++) { 
			startStation_ShowText[i] = stationInfoList.get(i-1).getStationName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		startStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startStation_ShowText);
		// 设置始发站下拉列表的风格
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startStation.setAdapter(startStation_adapter);
		// 添加事件Spinner事件监听
		spinner_startStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionTrainInfo.setStartStation(stationInfoList.get(arg2-1).getStationId()); 
				else
					queryConditionTrainInfo.setStartStation(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startStation.setVisibility(View.VISIBLE);
		spinner_endStation = (Spinner) findViewById(R.id.Spinner_endStation);
		endStation_ShowText = new String[stationInfoCount+1];
		endStation_ShowText[0] = "不限制";
		for(int i=1;i<=stationInfoCount;i++) { 
			endStation_ShowText[i] = stationInfoList.get(i-1).getStationName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		endStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endStation_ShowText);
		// 设置终到站下拉列表的风格
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endStation.setAdapter(endStation_adapter);
		// 添加事件Spinner事件监听
		spinner_endStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionTrainInfo.setEndStation(stationInfoList.get(arg2-1).getStationId()); 
				else
					queryConditionTrainInfo.setEndStation(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endStation.setVisibility(View.VISIBLE);
		dp_startDate = (DatePicker) findViewById(R.id.dp_startDate);
		cb_startDate = (CheckBox) findViewById(R.id.cb_startDate);
		spinner_seatType = (Spinner) findViewById(R.id.Spinner_seatType);
		// 获取所有的座位席别
		try {
			seatTypeList = seatTypeService.QuerySeatType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int seatTypeCount = seatTypeList.size();
		seatType_ShowText = new String[seatTypeCount+1];
		seatType_ShowText[0] = "不限制";
		for(int i=1;i<=seatTypeCount;i++) { 
			seatType_ShowText[i] = seatTypeList.get(i-1).getSeatTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		seatType_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatType_ShowText);
		// 设置席别下拉列表的风格
		seatType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatType.setAdapter(seatType_adapter);
		// 添加事件Spinner事件监听
		spinner_seatType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionTrainInfo.setSeatType(seatTypeList.get(arg2-1).getSeatTypeId()); 
				else
					queryConditionTrainInfo.setSeatType(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatType.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionTrainInfo.setTrainNumber(ET_trainNumber.getText().toString());
					if(cb_startDate.isChecked()) {
						/*获取开车日期*/
						Date startDate = new Date(dp_startDate.getYear()-1900,dp_startDate.getMonth(),dp_startDate.getDayOfMonth());
						queryConditionTrainInfo.setStartDate(new Timestamp(startDate.getTime()));
					} else {
						queryConditionTrainInfo.setStartDate(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionTrainInfo", queryConditionTrainInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
