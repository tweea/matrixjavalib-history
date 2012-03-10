<%@ include file="/pages/common/top.jsp" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/matrix-resource.tld" prefix="resource" %>
<%@ page import="com.matrix.system.organization.*" %>
<%@ page import="com.matrix.system.organization.helper.*" %>
<%@ page import="com.matrix.system.security.*" %>
<%@ page import="com.matrix.websystem.ui.UIStyle" %>
<%@ page import="com.matrix.websystem.security.*" %>
<%
	String imgpath = request.getContextPath();
	String uiStyle = UIStyle.getUIStyle(request);

	SecurityEntityManager seManager = SecurityEntityManager.getInstance();
	SecuritySession sSession = WebLogin.getSession(request);

	String customerId = request.getParameter("customerId");
	String customerType = (String)request.getAttribute("customerType");
	boolean canModify = customerId.equals(sSession.getActiveCustomerId()) || CustomerManager.getInstance().isSubCustomer(customerId, sSession.getActiveCustomerId());
%>
<script type="text/javascript">
<!--
	function doDelete(customerId)
	{
		if(confirm("是否确认删除单位及所有所属用户?")){
			document.location.href = "<%=imgpath%>/action/customer/delete.do?customerId=" + customerId;
		}
	}
-->
</script>
<%@ include file="/pages/common/locations.jsp" %>
<%
	if(canModify && sSession.checkAccess("system.customer.add", PredefinedSecurityEntities.PERMISSION)){
		if(CustomerTypeManager.getInstance().getSubTypes(customerType).size() != 0){
%>
<a href="<%=imgpath%>/action/customer/add.do?parentCustomerId=<%=customerId%>">增加单位</a> |
<%
		}
	}
	if(canModify && sSession.checkAccess("system.customer.modify", PredefinedSecurityEntities.PERMISSION)){
%>
<a href="<%=imgpath%>/action/customer/edit.do?customerId=<%=customerId%>">修改单位信息</a> |
<%
	}
	if(canModify && sSession.checkAccess(seManager.getCodedObject("system.customer.delete"), PredefinedSecurityEntities.PERMISSION)){
		if(!CustomerManager.getInstance().getCustomer(customerId).isDefaultRoot()){
			if(!CustomerManager.getInstance().hasSubCustomer(customerId) && !DepartmentManager.getInstance().hasSubDepartment(Department.getRootDepartmentId(customerId))){
%>
<a href="javascript:doDelete('<%=customerId%>');">删除单位</a> |
<%
			}
		}
	}
%>
<a href="<%=imgpath%>/action/customer/search.do">查询单位信息</a>
<%@ include file="/pages/common/locatione.jsp" %>
<br/>
<table class="table1">
	<tr>
		<td class="td1title" align="center" colspan="2">
			单位信息
		</td>
	</tr>
    <tr>
 		<td class="td1name" width="140" align="left">
 			单位名称
 		</td>
 		<td class="td1value" align="left">
			<bean:write name="Customer" property="customerName"/>
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			单位编码
 		</td>
 		<td class="td1value" align="left">
			<bean:write name="Customer" property="customerId"/>
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			单位级别
 		</td>
 		<td class="td1value" align="left">
			<%=CustomerTypeManager.getInstance().getCustomerType(customerType).getName()%>
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			上级单位
 		</td>
 		<td class="td1value" align="left">
			<%=(String)request.getAttribute("parentCustomerName")%>
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			联系人
 		</td>
 		<td class="td1value" align="left">
			<bean:write name="Customer" property="contact" /> &nbsp;
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			电话
 		</td>
 		<td class="td1value" align="left">
			<bean:write name="Customer" property="telephone" /> &nbsp;
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			传真
 		</td>
 		<td class="td1value" align="left">
			<bean:write name="Customer" property="fax" /> &nbsp;
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			手机
 		</td>
 		<td class="td1value" align="left">
			<bean:write name="Customer" property="mobile" /> &nbsp;
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			电子邮件
 		</td>
 		<td class="td1value" align="left">
			<bean:write name="Customer" property="email" /> &nbsp;
 		</td>
	</tr>
	<tr>
 		<td class="td1name" width="140" align="left">
 			邮政编码
 		</td>
 		<td class="td1value" align="left">
			<bean:write name="Customer" property="postCode" /> &nbsp;
 		</td>
	</tr>
</table>
<%@ include file="/pages/common/bottom.jsp" %>