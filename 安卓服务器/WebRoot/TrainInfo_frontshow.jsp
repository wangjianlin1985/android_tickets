<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.TrainInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.StationInfo" %>
<%@ page import="com.chengxusheji.domain.SeatType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的StationInfo信息
    List<StationInfo> stationInfoList = (List<StationInfo>)request.getAttribute("stationInfoList");
    //获取所有的SeatType信息
    List<SeatType> seatTypeList = (List<SeatType>)request.getAttribute("seatTypeList");
    TrainInfo trainInfo = (TrainInfo)request.getAttribute("trainInfo");

%>
<HTML><HEAD><TITLE>查看车次信息</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><%=trainInfo.getSeatId() %></td>
  </tr>

  <tr>
    <td width=30%>车次:</td>
    <td width=70%><%=trainInfo.getTrainNumber() %></td>
  </tr>

  <tr>
    <td width=30%>始发站:</td>
    <td width=70%>
      <%=trainInfo.getStartStation().getStationName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>终到站:</td>
    <td width=70%>
      <%=trainInfo.getStartStation().getStationName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>开车日期:</td>
        <% java.text.DateFormat startDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=startDateSDF.format(trainInfo.getStartDate()) %></td>
  </tr>

  <tr>
    <td width=30%>席别:</td>
    <td width=70%>
      <%=trainInfo.getSeatType().getSeatTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>票价:</td>
    <td width=70%><%=trainInfo.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>总座位数:</td>
    <td width=70%><%=trainInfo.getSeatNumber() %></td>
  </tr>

  <tr>
    <td width=30%>剩余座位数:</td>
    <td width=70%><%=trainInfo.getLeftSeatNumber() %></td>
  </tr>

  <tr>
    <td width=30%>开车时间:</td>
    <td width=70%><%=trainInfo.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>终到时间:</td>
    <td width=70%><%=trainInfo.getEndTime() %></td>
  </tr>

  <tr>
    <td width=30%>历时:</td>
    <td width=70%><%=trainInfo.getTotalTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
