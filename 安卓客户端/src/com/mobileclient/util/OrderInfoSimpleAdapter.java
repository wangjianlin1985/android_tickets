package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.TrainInfoService;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.service.StationInfoService;
import com.mobileclient.service.SeatTypeService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class OrderInfoSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public OrderInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.orderinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_trainNumber = (TextView)convertView.findViewById(R.id.tv_trainNumber);
	  holder.tv_startStation = (TextView)convertView.findViewById(R.id.tv_startStation);
	  holder.tv_endStation = (TextView)convertView.findViewById(R.id.tv_endStation);
	  holder.tv_startDate = (TextView)convertView.findViewById(R.id.tv_startDate);
	  holder.tv_seatType = (TextView)convertView.findViewById(R.id.tv_seatType);
	  holder.tv_seatInfo = (TextView)convertView.findViewById(R.id.tv_seatInfo);
	  holder.tv_totalPrice = (TextView)convertView.findViewById(R.id.tv_totalPrice);
	  holder.tv_startTime = (TextView)convertView.findViewById(R.id.tv_startTime);
	  holder.tv_endTime = (TextView)convertView.findViewById(R.id.tv_endTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_userObj.setText("用户：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getRealName());
	  holder.tv_trainNumber.setText("车次：" + mData.get(position).get("trainNumber").toString());
	  holder.tv_startStation.setText("始发站：" + (new StationInfoService()).GetStationInfo(Integer.parseInt(mData.get(position).get("startStation").toString())).getStationName());
	  holder.tv_endStation.setText("终到站：" + (new StationInfoService()).GetStationInfo(Integer.parseInt(mData.get(position).get("endStation").toString())).getStationName());
	  try {holder.tv_startDate.setText("开车日期：" + mData.get(position).get("startDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_seatType.setText("席别：" + (new SeatTypeService()).GetSeatType(Integer.parseInt(mData.get(position).get("seatType").toString())).getSeatTypeName());
	  holder.tv_seatInfo.setText("座位信息：" + mData.get(position).get("seatInfo").toString());
	  holder.tv_totalPrice.setText("总票价：" + mData.get(position).get("totalPrice").toString());
	  holder.tv_startTime.setText("开车时间：" + mData.get(position).get("startTime").toString());
	  holder.tv_endTime.setText("终到时间：" + mData.get(position).get("endTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_userObj;
    	TextView tv_trainNumber;
    	TextView tv_startStation;
    	TextView tv_endStation;
    	TextView tv_startDate;
    	TextView tv_seatType;
    	TextView tv_seatInfo;
    	TextView tv_totalPrice;
    	TextView tv_startTime;
    	TextView tv_endTime;
    }
} 
