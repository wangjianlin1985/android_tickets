package com.mobileclient.activity;

import java.util.Date;
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
public class SeatTypeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_seatTypeId;
	// 声明席别名称控件
	private TextView TV_seatTypeName;
	/* 要保存的座位席别信息 */
	SeatType seatType = new SeatType(); 
	/* 座位席别管理业务逻辑层 */
	private SeatTypeService seatTypeService = new SeatTypeService();
	private int seatTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.seattype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看座位席别详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_seatTypeId = (TextView) findViewById(R.id.TV_seatTypeId);
		TV_seatTypeName = (TextView) findViewById(R.id.TV_seatTypeName);
		Bundle extras = this.getIntent().getExtras();
		seatTypeId = extras.getInt("seatTypeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeatTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    seatType = seatTypeService.GetSeatType(seatTypeId); 
		this.TV_seatTypeId.setText(seatType.getSeatTypeId() + "");
		this.TV_seatTypeName.setText(seatType.getSeatTypeName());
	} 
}
