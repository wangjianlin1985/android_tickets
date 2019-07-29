package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.TrainInfo;
import com.mobileclient.util.HttpUtil;

/*车次信息管理业务逻辑层*/
public class TrainInfoService {
	/* 添加车次信息 */
	public String AddTrainInfo(TrainInfo trainInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", trainInfo.getSeatId() + "");
		params.put("trainNumber", trainInfo.getTrainNumber());
		params.put("startStation", trainInfo.getStartStation() + "");
		params.put("endStation", trainInfo.getEndStation() + "");
		params.put("startDate", trainInfo.getStartDate().toString());
		params.put("seatType", trainInfo.getSeatType() + "");
		params.put("price", trainInfo.getPrice() + "");
		params.put("seatNumber", trainInfo.getSeatNumber() + "");
		params.put("leftSeatNumber", trainInfo.getLeftSeatNumber() + "");
		params.put("startTime", trainInfo.getStartTime());
		params.put("endTime", trainInfo.getEndTime());
		params.put("totalTime", trainInfo.getTotalTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TrainInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询车次信息 */
	public List<TrainInfo> QueryTrainInfo(TrainInfo queryConditionTrainInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "TrainInfoServlet?action=query";
		if(queryConditionTrainInfo != null) {
			urlString += "&trainNumber=" + URLEncoder.encode(queryConditionTrainInfo.getTrainNumber(), "UTF-8") + "";
			urlString += "&startStation=" + queryConditionTrainInfo.getStartStation();
			urlString += "&endStation=" + queryConditionTrainInfo.getEndStation();
			if(queryConditionTrainInfo.getStartDate() != null) {
				urlString += "&startDate=" + URLEncoder.encode(queryConditionTrainInfo.getStartDate().toString(), "UTF-8");
			}
			urlString += "&seatType=" + queryConditionTrainInfo.getSeatType();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		TrainInfoListHandler trainInfoListHander = new TrainInfoListHandler();
		xr.setContentHandler(trainInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<TrainInfo> trainInfoList = trainInfoListHander.getTrainInfoList();
		return trainInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<TrainInfo> trainInfoList = new ArrayList<TrainInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				TrainInfo trainInfo = new TrainInfo();
				trainInfo.setSeatId(object.getInt("seatId"));
				trainInfo.setTrainNumber(object.getString("trainNumber"));
				trainInfo.setStartStation(object.getInt("startStation"));
				trainInfo.setEndStation(object.getInt("endStation"));
				trainInfo.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				trainInfo.setSeatType(object.getInt("seatType"));
				trainInfo.setPrice((float) object.getDouble("price"));
				trainInfo.setSeatNumber(object.getInt("seatNumber"));
				trainInfo.setLeftSeatNumber(object.getInt("leftSeatNumber"));
				trainInfo.setStartTime(object.getString("startTime"));
				trainInfo.setEndTime(object.getString("endTime"));
				trainInfo.setTotalTime(object.getString("totalTime"));
				trainInfoList.add(trainInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trainInfoList;
	}

	/* 更新车次信息 */
	public String UpdateTrainInfo(TrainInfo trainInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", trainInfo.getSeatId() + "");
		params.put("trainNumber", trainInfo.getTrainNumber());
		params.put("startStation", trainInfo.getStartStation() + "");
		params.put("endStation", trainInfo.getEndStation() + "");
		params.put("startDate", trainInfo.getStartDate().toString());
		params.put("seatType", trainInfo.getSeatType() + "");
		params.put("price", trainInfo.getPrice() + "");
		params.put("seatNumber", trainInfo.getSeatNumber() + "");
		params.put("leftSeatNumber", trainInfo.getLeftSeatNumber() + "");
		params.put("startTime", trainInfo.getStartTime());
		params.put("endTime", trainInfo.getEndTime());
		params.put("totalTime", trainInfo.getTotalTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TrainInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除车次信息 */
	public String DeleteTrainInfo(int seatId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seatId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TrainInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "车次信息信息删除失败!";
		}
	}

	/* 根据记录编号获取车次信息对象 */
	public TrainInfo GetTrainInfo(int seatId)  {
		List<TrainInfo> trainInfoList = new ArrayList<TrainInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seatId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TrainInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				TrainInfo trainInfo = new TrainInfo();
				trainInfo.setSeatId(object.getInt("seatId"));
				trainInfo.setTrainNumber(object.getString("trainNumber"));
				trainInfo.setStartStation(object.getInt("startStation"));
				trainInfo.setEndStation(object.getInt("endStation"));
				trainInfo.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				trainInfo.setSeatType(object.getInt("seatType"));
				trainInfo.setPrice((float) object.getDouble("price"));
				trainInfo.setSeatNumber(object.getInt("seatNumber"));
				trainInfo.setLeftSeatNumber(object.getInt("leftSeatNumber"));
				trainInfo.setStartTime(object.getString("startTime"));
				trainInfo.setEndTime(object.getString("endTime"));
				trainInfo.setTotalTime(object.getString("totalTime"));
				trainInfoList.add(trainInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = trainInfoList.size();
		if(size>0) return trainInfoList.get(0); 
		else return null; 
	}
}
