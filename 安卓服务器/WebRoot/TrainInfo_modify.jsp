<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.TrainInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.SeatType" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的StationInfo信息
    List<StationInfo> stationInfoList = (List<StationInfo>)request.getAttribute("stationInfoList");
    //获取所有的SeatType信息
    List<SeatType> seatTypeList = (List<SeatType>)request.getAttribute("seatTypeList");
    TrainInfo trainInfo = (TrainInfo)request.getAttribute("trainInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改车次信息</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var trainNumber = document.getElementById("trainInfo.trainNumber").value;
    if(trainNumber=="") {
        alert('请输入车次!');
        return false;
    }
    var startTime = document.getElementById("trainInfo.startTime").value;
    if(startTime=="") {
        alert('请输入开车时间!');
        return false;
    }
    var endTime = document.getElementById("trainInfo.endTime").value;
    if(endTime=="") {
        alert('请输入终到时间!');
        return false;
    }
    var totalTime = document.getElementById("trainInfo.totalTime").value;
    if(totalTime=="") {
        alert('请输入历时!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="TrainInfo/TrainInfo_ModifyTrainInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><input id="trainInfo.seatId" name="trainInfo.seatId" type="text" value="<%=trainInfo.getSeatId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>车次:</td>
    <td width=70%><input id="trainInfo.trainNumber" name="trainInfo.trainNumber" type="text" size="20" value='<%=trainInfo.getTrainNumber() %>'/></td>
  </tr>

  <tr>
    <td width=30%>始发站:</td>
    <td width=70%>
      <select name="trainInfo.startStation.stationId">
      <%
        for(StationInfo stationInfo:stationInfoList) {
          String selected = "";
          if(stationInfo.getStationId() == trainInfo.getStartStation().getStationId())
            selected = "selected";
      %>
          <option value='<%=stationInfo.getStationId() %>' <%=selected %>><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>终到站:</td>
    <td width=70%>
      <select name="trainInfo.endStation.stationId">
      <%
        for(StationInfo stationInfo:stationInfoList) {
          String selected = "";
          if(stationInfo.getStationId() == trainInfo.getEndStation().getStationId())
            selected = "selected";
      %>
          <option value='<%=stationInfo.getStationId() %>' <%=selected %>><%=stationInfo.getStationName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>开车日期:</td>
    <% DateFormat startDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="trainInfo.startDate"  name="trainInfo.startDate" onclick="setDay(this);" value='<%=startDateSDF.format(trainInfo.getStartDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>席别:</td>
    <td width=70%>
      <select name="trainInfo.seatType.seatTypeId">
      <%
        for(SeatType seatType:seatTypeList) {
          String selected = "";
          if(seatType.getSeatTypeId() == trainInfo.getSeatType().getSeatTypeId())
            selected = "selected";
      %>
          <option value='<%=seatType.getSeatTypeId() %>' <%=selected %>><%=seatType.getSeatTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>票价:</td>
    <td width=70%><input id="trainInfo.price" name="trainInfo.price" type="text" size="8" value='<%=trainInfo.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>总座位数:</td>
    <td width=70%><input id="trainInfo.seatNumber" name="trainInfo.seatNumber" type="text" size="8" value='<%=trainInfo.getSeatNumber() %>'/></td>
  </tr>

  <tr>
    <td width=30%>剩余座位数:</td>
    <td width=70%><input id="trainInfo.leftSeatNumber" name="trainInfo.leftSeatNumber" type="text" size="8" value='<%=trainInfo.getLeftSeatNumber() %>'/></td>
  </tr>

  <tr>
    <td width=30%>开车时间:</td>
    <td width=70%><input id="trainInfo.startTime" name="trainInfo.startTime" type="text" size="20" value='<%=trainInfo.getStartTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>终到时间:</td>
    <td width=70%><input id="trainInfo.endTime" name="trainInfo.endTime" type="text" size="20" value='<%=trainInfo.getEndTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>历时:</td>
    <td width=70%><input id="trainInfo.totalTime" name="trainInfo.totalTime" type="text" size="20" value='<%=trainInfo.getTotalTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
