package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

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

public class TrainInfoSimpleAdapter extends SimpleAdapter { 
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

    public TrainInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.traininfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_trainNumber = (TextView)convertView.findViewById(R.id.tv_trainNumber);
	  holder.tv_startStation = (TextView)convertView.findViewById(R.id.tv_startStation);
	  holder.tv_endStation = (TextView)convertView.findViewById(R.id.tv_endStation);
	  holder.tv_startDate = (TextView)convertView.findViewById(R.id.tv_startDate);
	  holder.tv_seatType = (TextView)convertView.findViewById(R.id.tv_seatType);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_seatNumber = (TextView)convertView.findViewById(R.id.tv_seatNumber);
	  holder.tv_leftSeatNumber = (TextView)convertView.findViewById(R.id.tv_leftSeatNumber);
	  /*设置各个控件的展示内容*/
	  holder.tv_trainNumber.setText("车次：" + mData.get(position).get("trainNumber").toString());
	  holder.tv_startStation.setText("始发站：" + (new StationInfoService()).GetStationInfo(Integer.parseInt(mData.get(position).get("startStation").toString())).getStationName());
	  holder.tv_endStation.setText("终到站：" + (new StationInfoService()).GetStationInfo(Integer.parseInt(mData.get(position).get("endStation").toString())).getStationName());
	  try {holder.tv_startDate.setText("开车日期：" + mData.get(position).get("startDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_seatType.setText("席别：" + (new SeatTypeService()).GetSeatType(Integer.parseInt(mData.get(position).get("seatType").toString())).getSeatTypeName());
	  holder.tv_price.setText("票价：" + mData.get(position).get("price").toString());
	  holder.tv_seatNumber.setText("总座位数：" + mData.get(position).get("seatNumber").toString());
	  holder.tv_leftSeatNumber.setText("剩余座位数：" + mData.get(position).get("leftSeatNumber").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_trainNumber;
    	TextView tv_startStation;
    	TextView tv_endStation;
    	TextView tv_startDate;
    	TextView tv_seatType;
    	TextView tv_price;
    	TextView tv_seatNumber;
    	TextView tv_leftSeatNumber;
    }
} 
