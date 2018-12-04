<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
		<meta charset="UTF-8">
		<title></title>
		<style type="text/css">
 
				body {
					margin-left: 3px;
					margin-top: 0px;
					margin-right: 3px;
					margin-bottom: 0px;
				}
				
				.STYLE1 {
					color: #e1e2e3;
					font-size: 12px;
				}
				
				.STYLE6 {
					color: #000000;
					font-size: 12px;
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
	<form method="post" action="${pageContext.request.contextPath }/admin/ProductServlet" >
 		<input type="hidden" name="num" value="1">
		 <input type="hidden" name="op" value="multiConditionSearch">
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		 
		 	 <tr>
			<td height="30" colspan="4">
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
														搜索商品</span>
												</td>
											</tr>
										</table>
									</td>
									<td><div align="right">
											<span class="STYLE1"> 
												<!-- <input type="button" value="添加"/> -->  
												<!-- <input type="submit" value="删除" /> -->
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
		
		 
 
							<tr class="STYLE10" >
								<td height="22" align="center" bgColor="#a8c7ce"    >
									商品编号：</td>
								<td class="ta_01" bgcolor="#a8c7ce"><input type="text"
									name="pid" size="15" value="" id="Form1_userName" />
								</td>
								<td height="22" align="center" bgcolor="#a8c7ce"  >
									品牌：</td>
								<td   bgcolor="#a8c7ce">
								
								<select name="cid" id="st" onchange="change()">
									<option value="">无</option>
									<c:forEach items="${categories}" var="category">
									<option value="${category.cid}">${category.cname}</option>
								</c:forEach>	
							    </select>
								
								
							   </td>
							</tr>

							<tr class="STYLE10">
								<td height="22" align="center" bgcolor="#a8c7ce"  >
									商品名称：</td>
								<td   bgcolor="#a8c7ce"><input type="text"
									name="pname" size="15" value="" id="Form1_userName" class="bg" />
								</td>
								<td height="22" align="center" bgcolor="#a8c7ce"  >
									价格区间(元)：</td>
								<td   bgcolor="#a8c7ce">
								<input type="text"
									name="minprice" size="10" value="" />- 
								<input type="text"
									name="maxprice" size="10" value="" /></td>
							</tr>

							<tr class="STYLE10">
								<td width="100" height="22" align="center" bgColor="#fff"
									 ></td>
								<td   bgColor="#ffffff"><font face="宋体"
									color="red"> &nbsp;</font>
								</td>
								<td align="right" bgColor="#ffffff"  ><br>
									<br></td>
								<td align="right" bgColor="#ffffff"  >
									<button type="submit" id="search" name="search"
										value="&#26597;&#35810;" class="button_view">
										&#26597;&#35810;
									</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
									type="reset" name="reset" value="&#37325;&#32622;"
									class="button_view" />
								</td>
							</tr>
			</table>
		</form>	 
	</body>

</html>


