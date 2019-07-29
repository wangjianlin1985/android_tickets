package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderInfo;
import com.mobileserver.util.DB;

public class OrderInfoDAO {

	public List<OrderInfo> QueryOrderInfo(String userObj,String trainNumber,int startStation,int endStation,Timestamp startDate,int seatType) {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		DB db = new DB();
		String sql = "select * from OrderInfo where 1=1";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!trainNumber.equals(""))
			sql += " and trainNumber like '%" + trainNumber + "%'";
		if (startStation != 0)
			sql += " and startStation=" + startStation;
		if (endStation != 0)
			sql += " and endStation=" + endStation;
		if(startDate!=null)
			sql += " and startDate='" + startDate + "'";
		if (seatType != 0)
			sql += " and seatType=" + seatType;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(rs.getInt("orderId"));
				orderInfo.setUserObj(rs.getString("userObj"));
				orderInfo.setTrainObj(rs.getInt("trainObj"));
				orderInfo.setTrainNumber(rs.getString("trainNumber"));
				orderInfo.setStartStation(rs.getInt("startStation"));
				orderInfo.setEndStation(rs.getInt("endStation"));
				orderInfo.setStartDate(rs.getTimestamp("startDate"));
				orderInfo.setSeatType(rs.getInt("seatType"));
				orderInfo.setSeatInfo(rs.getString("seatInfo"));
				orderInfo.setTotalPrice(rs.getFloat("totalPrice"));
				orderInfo.setStartTime(rs.getString("startTime"));
				orderInfo.setEndTime(rs.getString("endTime"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfoList;
	}
	/* ���붩����Ϣ���󣬽��ж�����Ϣ�����ҵ�� */
	public String AddOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¶�����Ϣ */
			String sqlString = "insert into OrderInfo(userObj,trainObj,trainNumber,startStation,endStation,startDate,seatType,seatInfo,totalPrice,startTime,endTime) values (";
			sqlString += "'" + orderInfo.getUserObj() + "',";
			sqlString += orderInfo.getTrainObj() + ",";
			sqlString += "'" + orderInfo.getTrainNumber() + "',";
			sqlString += orderInfo.getStartStation() + ",";
			sqlString += orderInfo.getEndStation() + ",";
			sqlString += "'" + orderInfo.getStartDate() + "',";
			sqlString += orderInfo.getSeatType() + ",";
			sqlString += "'" + orderInfo.getSeatInfo() + "',";
			sqlString += orderInfo.getTotalPrice() + ",";
			sqlString += "'" + orderInfo.getStartTime() + "',";
			sqlString += "'" + orderInfo.getEndTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������Ϣ */
	public String DeleteOrderInfo(int orderId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderInfo where orderId=" + orderId;
			db.executeUpdate(sqlString);
			result = "������Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��������Ϣ */
	public OrderInfo GetOrderInfo(int orderId) {
		OrderInfo orderInfo = null;
		DB db = new DB();
		String sql = "select * from OrderInfo where orderId=" + orderId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderInfo = new OrderInfo();
				orderInfo.setOrderId(rs.getInt("orderId"));
				orderInfo.setUserObj(rs.getString("userObj"));
				orderInfo.setTrainObj(rs.getInt("trainObj"));
				orderInfo.setTrainNumber(rs.getString("trainNumber"));
				orderInfo.setStartStation(rs.getInt("startStation"));
				orderInfo.setEndStation(rs.getInt("endStation"));
				orderInfo.setStartDate(rs.getTimestamp("startDate"));
				orderInfo.setSeatType(rs.getInt("seatType"));
				orderInfo.setSeatInfo(rs.getString("seatInfo"));
				orderInfo.setTotalPrice(rs.getFloat("totalPrice"));
				orderInfo.setStartTime(rs.getString("startTime"));
				orderInfo.setEndTime(rs.getString("endTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfo;
	}
	/* ���¶�����Ϣ */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderInfo set ";
			sql += "userObj='" + orderInfo.getUserObj() + "',";
			sql += "trainObj=" + orderInfo.getTrainObj() + ",";
			sql += "trainNumber='" + orderInfo.getTrainNumber() + "',";
			sql += "startStation=" + orderInfo.getStartStation() + ",";
			sql += "endStation=" + orderInfo.getEndStation() + ",";
			sql += "startDate='" + orderInfo.getStartDate() + "',";
			sql += "seatType=" + orderInfo.getSeatType() + ",";
			sql += "seatInfo='" + orderInfo.getSeatInfo() + "',";
			sql += "totalPrice=" + orderInfo.getTotalPrice() + ",";
			sql += "startTime='" + orderInfo.getStartTime() + "',";
			sql += "endTime='" + orderInfo.getEndTime() + "'";
			sql += " where orderId=" + orderInfo.getOrderId();
			db.executeUpdate(sql);
			result = "������Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
