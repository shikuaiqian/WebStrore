<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${pageContext.request.contextPath }/admin/js/jquery.js"></script>
<title>无标题文档</title>
 

<style type="text/css"   >
  @import url(../css/table.css)
</style>
</head>

<body>
 
 <form method="post" action="${pageContext.request.contextPath }/UserServlet">
				<input type="hidden" name="op" value="adduser" /> 	
 	
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
														增加用户</span>
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
		<tr>
			<td><table width="100%" border="0" cellpadding="0"
					cellspacing="1" bgcolor="#a8c7ce">
					<tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">用户名</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							    <input type="text" name="username"  placeholder="用户名"  value="${msg.username}"/>
						 
							</div>
						</td>
						 
					</tr>
				 
				 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">昵称</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
						<input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
						 
							</div>
						</td>
						 
					</tr>
					
					 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">密码</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
						<input type="text" name="password" placeholder="密码" value="${msg.password}" required="required"/>
						 
							</div>
						</td>
						 
					</tr>
					
						
			
								
								
					
					 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">email</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
						<input type="text" name="email" placeholder="邮箱" value="${msg.email}" required="required"/>
						 
							</div>
						</td>
						 
					</tr>
					
					
					 <tr>
						<td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div
								align="center">
								<input type="checkbox" name="checkbox" id="checkbox11" />
							</div>
						</td>
						<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">birthday</span>
							</div>
						</td>
						<td width="80%" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="left">
 							 
						<input type="text" placeholder="出生日期" name="birthday" value="${msg.birthday}" size="15" />
						 
							</div>
						</td>
						 
					</tr>			
					
					  
					
					 
					
				</table>
			</td>
		</tr>

		<tr>
			<td height="30">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="33%"><div align="left">
								 
						</td>
						<td width="67%">
						   <div align="right">
						   <input type="submit" value="增加"/>
						   </div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
	 
</body>
</html>
