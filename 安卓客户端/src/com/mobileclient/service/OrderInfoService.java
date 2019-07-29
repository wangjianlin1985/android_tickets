package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.util.HttpUtil;

/*订单信息管理业务逻辑层*/
public class OrderInfoService {
	/* 添加订单信息 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("userObj", orderInfo.getUserObj());
		params.put("trainObj", orderInfo.getTrainObj() + "");
		params.put("trainNumber", orderInfo.getTrainNumber());
		params.put("startStation", orderInfo.getStartStation() + "");
		params.put("endStation", orderInfo.getEndStation() + "");
		params.put("startDate", orderInfo.getStartDate().toString());
		params.put("seatType", orderInfo.getSeatType() + "");
		params.put("seatInfo", orderInfo.getSeatInfo());
		params.put("totalPrice", orderInfo.getTotalPrice() + "");
		params.put("startTime", orderInfo.getStartTime());
		params.put("endTime", orderInfo.getEndTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询订单信息 */
	public List<OrderInfo> QueryOrderInfo(OrderInfo queryConditionOrderInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=query";
		if(queryConditionOrderInfo != null) {
			urlString += "&userObj=" + URLEncoder.encode(queryConditionOrderInfo.getUserObj(), "UTF-8") + "";
			urlString += "&trainNumber=" + URLEncoder.encode(queryConditionOrderInfo.getTrainNumber(), "UTF-8") + "";
			urlString += "&startStation=" + queryConditionOrderInfo.getStartStation();
			urlString += "&endStation=" + queryConditionOrderInfo.getEndStation();
			if(queryConditionOrderInfo.getStartDate() != null) {
				urlString += "&startDate=" + URLEncoder.encode(queryConditionOrderInfo.getStartDate().toString(), "UTF-8");
			}
			urlString += "&seatType=" + queryConditionOrderInfo.getSeatType();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderInfoListHandler orderInfoListHander = new OrderInfoListHandler();
		xr.setContentHandler(orderInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderInfo> orderInfoList = orderInfoListHander.getOrderInfoList();
		return orderInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setUserObj(object.getString("userObj"));
				orderInfo.setTrainObj(object.getInt("trainObj"));
				orderInfo.setTrainNumber(object.getString("trainNumber"));
				orderInfo.setStartStation(object.getInt("startStation"));
				orderInfo.setEndStation(object.getInt("endStation"));
				orderInfo.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				orderInfo.setSeatType(object.getInt("seatType"));
				orderInfo.setSeatInfo(object.getString("seatInfo"));
				orderInfo.setTotalPrice((float) object.getDouble("totalPrice"));
				orderInfo.setStartTime(object.getString("startTime"));
				orderInfo.setEndTime(object.getString("endTime"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderInfoList;
	}

	/* 更新订单信息 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("userObj", orderInfo.getUserObj());
		params.put("trainObj", orderInfo.getTrainObj() + "");
		params.put("trainNumber", orderInfo.getTrainNumber());
		params.put("startStation", orderInfo.getStartStation() + "");
		params.put("endStation", orderInfo.getEndStation() + "");
		params.put("startDate", orderInfo.getStartDate().toString());
		params.put("seatType", orderInfo.getSeatType() + "");
		params.put("seatInfo", orderInfo.getSeatInfo());
		params.put("totalPrice", orderInfo.getTotalPrice() + "");
		params.put("startTime", orderInfo.getStartTime());
		params.put("endTime", orderInfo.getEndTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除订单信息 */
	public String DeleteOrderInfo(int orderId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "订单信息信息删除失败!";
		}
	}

	/* 根据记录编号获取订单信息对象 */
	public OrderInfo GetOrderInfo(int orderId)  {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setUserObj(object.getString("userObj"));
				orderInfo.setTrainObj(object.getInt("trainObj"));
				orderInfo.setTrainNumber(object.getString("trainNumber"));
				orderInfo.setStartStation(object.getInt("startStation"));
				orderInfo.setEndStation(object.getInt("endStation"));
				orderInfo.setStartDate(Timestamp.valueOf(object.getString("startDate")));
				orderInfo.setSeatType(object.getInt("seatType"));
				orderInfo.setSeatInfo(object.getString("seatInfo"));
				orderInfo.setTotalPrice((float) object.getDouble("totalPrice"));
				orderInfo.setStartTime(object.getString("startTime"));
				orderInfo.setEndTime(object.getString("endTime"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderInfoList.size();
		if(size>0) return orderInfoList.get(0); 
		else return null; 
	}
}
