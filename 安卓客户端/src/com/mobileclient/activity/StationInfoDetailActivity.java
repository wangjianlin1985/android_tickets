package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.StationInfo;
import com.mobileclient.service.StationInfoService;
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
public class StationInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_stationId;
	// 声明站点名称控件
	private TextView TV_stationName;
	// 声明联系人控件
	private TextView TV_connectPerson;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明邮编控件
	private TextView TV_postcode;
	// 声明通讯地址控件
	private TextView TV_address;
	/* 要保存的站点信息信息 */
	StationInfo stationInfo = new StationInfo(); 
	/* 站点信息管理业务逻辑层 */
	private StationInfoService stationInfoService = new StationInfoService();
	private int stationId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.stationinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看站点信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_stationId = (TextView) findViewById(R.id.TV_stationId);
		TV_stationName = (TextView) findViewById(R.id.TV_stationName);
		TV_connectPerson = (TextView) findViewById(R.id.TV_connectPerson);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_postcode = (TextView) findViewById(R.id.TV_postcode);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		stationId = extras.getInt("stationId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StationInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    stationInfo = stationInfoService.GetStationInfo(stationId); 
		this.TV_stationId.setText(stationInfo.getStationId() + "");
		this.TV_stationName.setText(stationInfo.getStationName());
		this.TV_connectPerson.setText(stationInfo.getConnectPerson());
		this.TV_telephone.setText(stationInfo.getTelephone());
		this.TV_postcode.setText(stationInfo.getPostcode());
		this.TV_address.setText(stationInfo.getAddress());
	} 
}
