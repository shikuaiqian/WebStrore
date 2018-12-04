<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${pageContext.request.contextPath }/admin/js/jquery.js"></script>
<title>无标题文档</title>


<style type="text/css">
 body {
	margin-left: 3px;
	margin-top:  0px;
	margin-right: 3px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #e1e2e3;
	font-size: 12px;
}

.STYLE6 {
	color: #000000;
	font-size: 12;
}

.STYLE10 {
	color: #000000;
	font-size: 12px;
}

.STYLE19 {
	color: #344b50;
	font-size: 12px;
}
.STYLE21 {
	font-size: 12px;
	color: #3b6375;
}
.STYLE22 {
	font-size: 12px;
	color: #295568;
}
</style>
</head>

<body>
	<form action="${pageContext.request.contextPath }/ProductServlet" method="post">
	<input type="hidden" name="op" value="deleteMulti"/>
	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="30">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="24" bgcolor="#353c44"><table width="100%"
								border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><table width="100%" border="0" cellspacing="0"
											cellpadding="0">
											<tr>
												<td width="6%" height="19" valign="bottom"><div
														align="center">
														<img src="images/tb.gif" width="14" height="14" />
													</div>
												</td>
												<td width="94%" valign="bottom"><span class="STYLE1">
														订单详情</span>
												</td>
											</tr>
										</table>
									</td>
									<td><div align="right">
											<span class="STYLE1"> 
												<!-- <input type="button" value="添加"/> -->  
												<input type="submit" value="删除" />
												&nbsp;&nbsp;
											</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><table width="100%" border="0" cellpadding="0"
					cellspacing="1" bgcolor="#a8c7ce">
					<tr>
						<td width="2%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">订单商品号</span>
							</div>
						</td>
						<td width="5%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">订单号</span>
							</div>
						</td>
						<td width="8%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">商品号</span>
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">购买数量</span>
							</div>
						</td>
						 
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">基本操作</span>
							</div>
						</td>
					</tr>
					<c:forEach items="${orderitems }" var="orderitem">
						<tr>
							<td height="2" bgcolor="#FFFFFF">
								<div align="center">
									<input type="checkbox" name="pid" id="pid" value="${orderitem.itemid }" />
								</div>
							</td>
							<td height="15" bgcolor="#FFFFFF" class="STYLE6">
								<div align="center">
									<span class="STYLE19">${orderitem.itemid}</span>
								</div>
							</td>
							<td height="5" bgcolor="#FFFFFF" class="STYLE19">
								<div align="center">${orderitem.oid}</div>
							</td>
							<td height="8" bgcolor="#FFFFFF" class="STYLE19">
								<div align="center">${orderitem.pid}</div>
							</td>
							<td height="10" bgcolor="#FFFFFF" class="STYLE19">
								<div align="center">${orderitem.buynum}</div>
							</td>
							 
							<td height="10" bgcolor="#FFFFFF">
								<div align="center" class="STYLE21">
									<a href="#">删除</a> 
								</div>
							</td>
					</tr>
					</c:forEach>
					
				</table>
			</td>
		</tr>

		<tr>
			<td height="30">
			 
			</td>
		</tr>
	</table>
	</form>
 
</body>
</html>
