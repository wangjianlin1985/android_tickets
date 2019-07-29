package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.TrainInfoDAO;
import com.chengxusheji.domain.TrainInfo;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.dao.StationInfoDAO;
import com.chengxusheji.domain.StationInfo;
import com.chengxusheji.dao.SeatTypeDAO;
import com.chengxusheji.domain.SeatType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: �û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String trainNumber;
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
    public String getTrainNumber() {
        return this.trainNumber;
    }

    /*�������Ҫ��ѯ������: ʼ��վ*/
    private StationInfo startStation;
    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }
    public StationInfo getStartStation() {
        return this.startStation;
    }

    /*�������Ҫ��ѯ������: �յ�վ*/
    private StationInfo endStation;
    public void setEndStation(StationInfo endStation) {
        this.endStation = endStation;
    }
    public StationInfo getEndStation() {
        return this.endStation;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String startDate;
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStartDate() {
        return this.startDate;
    }

    /*�������Ҫ��ѯ������: ϯ��*/
    private SeatType seatType;
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    public SeatType getSeatType() {
        return this.seatType;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource UserInfoDAO userInfoDAO;
    @Resource TrainInfoDAO trainInfoDAO;
    @Resource StationInfoDAO stationInfoDAO;
    @Resource SeatTypeDAO seatTypeDAO;
    @Resource OrderInfoDAO orderInfoDAO;

    /*��������OrderInfo����*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*��ת�����OrderInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�TrainInfo��Ϣ*/
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryAllTrainInfoInfo();
        ctx.put("trainInfoList", trainInfoList);
        /*��ѯ���е�StationInfo��Ϣ*/
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        /*��ѯ���е�SeatType��Ϣ*/
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "add_view";
    }

    /*���OrderInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(orderInfo.getUserObj().getUser_name());
            orderInfo.setUserObj(userObj);
            TrainInfo trainObj = trainInfoDAO.GetTrainInfoBySeatId(orderInfo.getTrainObj().getSeatId());
            orderInfo.setTrainObj(trainObj);
            StationInfo startStation = stationInfoDAO.GetStationInfoByStationId(orderInfo.getStartStation().getStationId());
            orderInfo.setStartStation(startStation);
            StationInfo endStation = stationInfoDAO.GetStationInfoByStationId(orderInfo.getEndStation().getStationId());
            orderInfo.setEndStation(endStation);
            SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(orderInfo.getSeatType().getSeatTypeId());
            orderInfo.setSeatType(seatType);
            orderInfoDAO.AddOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderInfo��Ϣ*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(userObj, trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(userObj, trainNumber, startStation, endStation, startDate, seatType);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("trainNumber", trainNumber);
        ctx.put("startStation", startStation);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        ctx.put("endStation", endStation);
        ctx.put("startDate", startDate);
        ctx.put("seatType", seatType);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryOrderInfoOutputToExcel() { 
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(userObj,trainNumber,startStation,endStation,startDate,seatType);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderInfo��Ϣ��¼"; 
        String[] headers = { "�û�","����","ʼ��վ","�յ�վ","��������","ϯ��","��λ��Ϣ","��Ʊ��","����ʱ��","�յ�ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderInfoList.size();i++) {
        	OrderInfo orderInfo = orderInfoList.get(i); 
        	dataset.add(new String[]{orderInfo.getUserObj().getRealName(),
orderInfo.getTrainNumber(),orderInfo.getStartStation().getStationName(),
orderInfo.getEndStation().getStationName(),
new SimpleDateFormat("yyyy-MM-dd").format(orderInfo.getStartDate()),orderInfo.getSeatType().getSeatTypeName(),
orderInfo.getSeatInfo(),orderInfo.getTotalPrice() + "",orderInfo.getStartTime(),orderInfo.getEndTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"OrderInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯOrderInfo��Ϣ*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(trainNumber == null) trainNumber = "";
        if(startDate == null) startDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(userObj, trainNumber, startStation, endStation, startDate, seatType, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(userObj, trainNumber, startStation, endStation, startDate, seatType);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("trainNumber", trainNumber);
        ctx.put("startStation", startStation);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        ctx.put("endStation", endStation);
        ctx.put("startDate", startDate);
        ctx.put("seatType", seatType);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡOrderInfo����*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderId(orderId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryAllTrainInfoInfo();
        ctx.put("trainInfoList", trainInfoList);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("orderInfo",  orderInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡOrderInfo����*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderId(orderId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<TrainInfo> trainInfoList = trainInfoDAO.QueryAllTrainInfoInfo();
        ctx.put("trainInfoList", trainInfoList);
        List<StationInfo> stationInfoList = stationInfoDAO.QueryAllStationInfoInfo();
        ctx.put("stationInfoList", stationInfoList);
        List<SeatType> seatTypeList = seatTypeDAO.QueryAllSeatTypeInfo();
        ctx.put("seatTypeList", seatTypeList);
        ctx.put("orderInfo",  orderInfo);
        return "front_show_view";
    }

    /*�����޸�OrderInfo��Ϣ*/
    public String ModifyOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(orderInfo.getUserObj().getUser_name());
            orderInfo.setUserObj(userObj);
            TrainInfo trainObj = trainInfoDAO.GetTrainInfoBySeatId(orderInfo.getTrainObj().getSeatId());
            orderInfo.setTrainObj(trainObj);
            StationInfo startStation = stationInfoDAO.GetStationInfoByStationId(orderInfo.getStartStation().getStationId());
            orderInfo.setStartStation(startStation);
            StationInfo endStation = stationInfoDAO.GetStationInfoByStationId(orderInfo.getEndStation().getStationId());
            orderInfo.setEndStation(endStation);
            SeatType seatType = seatTypeDAO.GetSeatTypeBySeatTypeId(orderInfo.getSeatType().getSeatTypeId());
            orderInfo.setSeatType(seatType);
            orderInfoDAO.UpdateOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderInfo��Ϣ*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
