<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>添加系统用户</title>

    <style type="text/css">
        @import url(../css/table.css);
    </style>
    <script type="text/javascript">
        function checkInput() {
            var password = document.getElementsByName("password")[0];
            var username = document.getElementsByName("username")[0];
            if (username.value === "") {
                alert("请输入用户名...");
                return false;
            }

            if (password.value === "") {
                alert("请输入密码...");
                return false;
            }
            return true;
        }

        function isAdminUsernameAvailable() {
            var username = document.getElementsByName("username")[0];
            var message = document.getElementById("message");
            if (username.value === "") {
                message.innerText = "";
                return;
            }
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (request.readyState == 4 && request.status == 200) {
                    var responseText = request.responseText;
                    if (responseText === "true") {
                        message.innerText = "用户名可用";
                        message.style.color = 'blue';
                    } else {
                        message.innerText = "用户名已存在";
                        message.style.color = 'red';
                    }
                }
            };
            var url = "${pageContext.request.contextPath}/admin/AjaxServlet?ajax=isAdminUsernameAvailable&username=" + username.value;
            request.open("GET",url, true);
            request.send(null);
        }
    </script>
</head>
<body>


<form method="post" action="${pageContext.request.contextPath }/admin/AdminServlet" onsubmit="return checkInput()">
    <input type="hidden" name="op" value="addAdmin"/>

    <table width="100%" border="0" align="center" cellpadding="0"
           cellspacing="0">
        <tr>
            <td height="30">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td height="24" bgcolor="#353c44">
                            <table width="100%"
                                   border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table width="100%" border="0" cellspacing="0"
                                               cellpadding="0">
                                            <tr>
                                                <td width="6%" height="19" valign="bottom">
                                                    <div
                                                            align="center">
                                                        <img src="images/tb.gif" width="14" height="14"/>
                                                    </div>
                                                </td>
                                                <td width="94%" valign="bottom"><span class="STYLE1">
														增加管理员</span>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td>
                                        <div align="right">
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
            <td>

                <table width="100%" border="0" cellpadding="0"
                       cellspacing="1" bgcolor="#a8c7ce">
                    <tr>
                        <td width="4%" height="20" bgcolor="d3eaef" class="STYLE10">
                            <div
                                    align="center">
                                <input type="checkbox" name="checkbox" id="checkbox11"/>
                            </div>
                        </td>
                        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="center">
                                <span class="STYLE10"><span>账号：</span></span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">

                            <div
                                    align="left">
                                <span style="color:red">*</span>

                                <input type="text" name="username" onblur="isAdminUsernameAvailable()"/>
                                <span id="message" class="STYLE6"></span>

                            </div>
                        </td>

                    </tr>

                    <tr>
                        <td width="4%" height="20" bgcolor="d3eaef" class="STYLE10">
                            <div
                                    align="center">
                                <input type="checkbox" name="checkbox" id="checkbox11"/>
                            </div>
                        </td>
                        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="center">
                                <span class="STYLE10"><span>密码：</span></span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">

                            <div
                                    align="left">
                                <span style="color:red">*</span>

                                <input type="text" name="password"/>


                            </div>
                        </td>

                    </tr>


                    <tr>
                        <td width="4%" height="20" bgcolor="d3eaef" class="STYLE10">
                            <div
                                    align="center">
                                <input type="checkbox" name="checkbox" id="checkbox11"/>
                            </div>
                        </td>
                        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="center">
                                <span class="STYLE10"><span>确认密码：</span></span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">

                            <div
                                    align="left">
                                <span style="color:red">*</span>

                                <input type="text" name="password1"/>

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
                        <td width="33%">
                            <div align="left">

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