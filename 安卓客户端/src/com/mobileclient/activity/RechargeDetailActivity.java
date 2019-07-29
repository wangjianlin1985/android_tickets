package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Recharge;
import com.mobileclient.service.RechargeService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class RechargeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_id;
	// 声明充值用户控件
	private TextView TV_userObj;
	// 声明充值金额控件
	private TextView TV_money;
	// 声明充值时间控件
	private TextView TV_chargeTime;
	/* 要保存的充值信息信息 */
	Recharge recharge = new Recharge(); 
	/* 充值信息管理业务逻辑层 */
	private RechargeService rechargeService = new RechargeService();
	private UserInfoService userInfoService = new UserInfoService();
	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.recharge_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看充值信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_id = (TextView) findViewById(R.id.TV_id);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_money = (TextView) findViewById(R.id.TV_money);
		TV_chargeTime = (TextView) findViewById(R.id.TV_chargeTime);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RechargeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    recharge = rechargeService.GetRecharge(id); 
		this.TV_id.setText(recharge.getId() + "");
		UserInfo userObj = userInfoService.GetUserInfo(recharge.getUserObj());
		this.TV_userObj.setText(userObj.getRealName());
		this.TV_money.setText(recharge.getMoney() + "");
		this.TV_chargeTime.setText(recharge.getChargeTime());
	} 
}
