<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Books Store - Shopping Cart</title>
    <meta name="keywords"
          content="shoes store, shopping cart, free template, ecommerce, online shop, website templates, CSS, HTML"/>
    <meta name="description" content="Shoes Store, Shopping Cart, online store template "/>
    <link href="css/templatemo_style.css" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css"/>

    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/ddsmoothmenu.js">


    </script>

    <script type="text/javascript">

        ddsmoothmenu.init({
            mainmenuid: "top_nav", //menu DIV id
            orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
            classname: 'ddsmoothmenu', //class added to menu's outer DIV
            //customtheme: ["#1c5a80", "#18374a"],
            contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
        })

    </script>
    <script type="text/javascript">
        function hint() {

            var nameElement = document.getElementById("keyword");
            var div = document.getElementById("hintContent");

            //获取输入的信息
            var keyword = nameElement.value;
            //如果文本框不没有数据时，把div隐藏，且不向服务器发送请求
            if (keyword === "") {
                div.style.display = "none";
                return;
            }

            //隐藏提示框
            div.innerHTML = "";

            //1.获取XMLHttpRequest对象
            var xmlhttp = new XMLHttpRequest();

            //2.绑定回调函数
            xmlhttp.onreadystatechange = function () {

                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

                    // 如果没有响应内容，隐藏 div
                    if (xmlhttp.responseText === "") {
                        div.style.display = "none";
                        return;
                    }

                    var response = xmlhttp.responseText.split(",");

                    console.log(xmlhttp.responseText);
                    console.log(response);

                    var childdiv = "";

                    for (var i = 0; i < response.length; i++) {
                        childdiv = childdiv +
                            "<div  onmouseover='changeBackground_over(this)' " +
                            "onmouseout='changeBackground_out(this)' " +
                            "onclick='fillNameValue(this)'>" + response[i] + "</div>";
                    }

                    div.innerHTML = childdiv;
                    div.style.display = "block";
                }
            };
            //3.open
            xmlhttp.open("GET", "${pageContext.request.contextPath}/admin/AjaxServlet?ajax=findProductsHintByName&key=" + keyword);
            //4.send
            xmlhttp.send(null);
        }

        function changeBackground_over(div) {
            div.style.background = "white";
        }

        function changeBackground_out(div) {
            div.style.background = "#111";
        }

        function fillNameValue(subDiv) {
            document.getElementById("keyword").value = subDiv.innerText;
            document.getElementById("hintContent").style.display = "none";
        }

        function dispear(div) {
            div.style.display = "none";
        }

    </script>
</head>

<body>
<c:if test="${empty user}">
    <script type="text/javascript">
        location.href = "${pageContext.request.contextPath }/index.jsp";
    </script>
</c:if>
<div id="templatemo_body_wrapper">
    <div id="templatemo_wrapper">

        <div id="templatemo_header">
            <div id="site_title"><h1><a href="${pageContext.request.contextPath }/index.jsp">Online Books
                Store</a></h1></div>
            <div id="header_right">
                <p>
                    <c:if test="${!empty user }">
                        <a href="${pageContext.request.contextPath }/user/personal.jsp">我的个人中心</a> |
                    </c:if>
                    <a href="${pageContext.request.contextPath }/user/CartServlet?op=findCart">购物车</a> |
                    <c:if test="${user == null }">
                        <a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> |
                        <a href="${pageContext.request.contextPath }/user/register.jsp">注册</a>
                    </c:if>
                    <c:if test="${!empty sessionScope.user }">
                        ${user.nickname }
                    <a href="${pageContext.request.contextPath }/user/UserServlet?op=logout">退出</a></p>
                </c:if>
                </p>
                <p>
                    <c:if test="${!empty user }">
                        <a href="${pageContext.request.contextPath }/user/OrderServlet?op=myoid">我的订单</a> |
                    </c:if>
                </p>
            </div>
            <div class="cleaner"></div>
        </div> <!-- END of templatemo_header -->

        <div id="templatemo_menubar" style="position: relative;">
            <div id="top_nav" class="ddsmoothmenu">
                <ul>
                    <li><a href="${pageContext.request.contextPath }/MainServlet" class="selected">主页</a></li>
                </ul>
                <br style="clear: left"/>
            </div> <!-- end of ddsmoothmenu -->
            <div id="templatemo_search" onmouseleave="dispear(document.getElementById('hintContent'))">
                <form action="${pageContext.request.contextPath }/ProductServlet" method="get">
                    <input type="hidden" name="op" value="findProductsByName"/>
                    <input type="text" value="${pname }" name="pname" id="keyword" title="keyword"
                           onfocus="clearText(this)" onblur="clearText(this)" onkeypress="hint()" class="txt_field"/>
                    <input type="submit" name="Search" value=" " alt="Search" id="searchbutton" title="Search"
                           class="sub_btn"/>
                </form>
                <div id="hintContent"
                     style="background-color:#111;
                 display: none;
                 padding: 4px;
                 font-size:14px;
                 width:212px;
                 position: absolute;
                 right:30px;top:44px;
                 z-index: 99;
                 cursor: pointer;"
                     onmouseleave="dispear(this)">
                </div>
            </div>
        </div> <!-- END of templatemo_menubar -->

        <div id="templatemo_main">
            <div id="sidebar" class="float_l">
                <div class="sidebar_box"><span class="bottom"></span>
                    <h3>分类</h3>
                    <div class="content">
                        <ul class="sidebar_list">
                            <c:forEach items="${categories }" var="category" varStatus="vs">
                                <c:if test="${vs.index !=0}">
                                    <c:if test="${vs.index != fn:length(categories)-1 }">
                                        <li>
                                            <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductByCid&cid=${category.cid}">${category.cname}</a>
                                        </li>
                                    </c:if>
                                </c:if>
                                <c:if test="${vs.index==0 }">
                                    <li class="first">
                                        <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductByCid&cid=${category.cid}">${category.cname}</a>
                                    </li>
                                </c:if>
                                <c:if test="${vs.index == fn:length(categories)-1 }">
                                    <li class="last">
                                        <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductByCid&cid=${category.cid}">${category.cname}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

            </div>
            <div id="content" class="float_r">

                <h3>个人资料
                    <span style="text-align:center;padding-top:2px;"><font color="#ff0000">${message}</font></span>
                </h3>
                <hr/>
                <br/>
                <!--
                     （仅限修改昵称，密码，邮箱和出生日期）
                     nickname
                     password
                     email
                     birthday
                 -->
                <form action="${pageContext.request.contextPath }/user/UserServlet" method="post">
                    <input type="hidden" name="op" value="update">
                    <input type="hidden" name="uid" value="${user.uid }"/>
                    用&nbsp;户&nbsp;名： ${user.username }
                    <br/> <br/>
                    昵&nbsp;&nbsp;&nbsp;&nbsp;称：<input type="text" name="nickname" value="${user.nickname }"
                                                      required="required">
                    <br/><br/>
                    密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" value="${user.password }"
                                                      required="required"/>
                    <br/><br/>
                    邮&nbsp;&nbsp;&nbsp;&nbsp;箱：<input type="text" name="email" value="${user.email }"/>
                   <%-- <c:choose>
                        <c:when test="${user.status == 1}">
                            邮箱已验证...
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/user/UserServlet?op=sendVerifyEmail" style="color: darkred;">点击发送邮箱验证...</a>
                        </c:otherwise>
                    </c:choose>--%>

                    <br/> <br/>
                    出生日期：<input type="text" name="birthday" value="${user.birthday}"/>
                    <br/><br/>
                    <%--头像： ${user.headicon}--%>
                    <%--<br/><br/>--%>
                    注册时间： ${user.updateTime }
                    <br/><br/>
                    <%-- <span>请准确填写您的信息，确保货物准确到达</span>
                    <br /><br />
                    详细地址 <input type="text" style="width: 400px" name="address" value="${address }"><span>*</span>
                    <br /><br />
                    联系电话 <input type="text" name="tel" value="${tel }"><span>*</span>
                    <br /><br />
                     联系QQ&nbsp;&nbsp;&nbsp;<input type="text" name="qq" value="${qq }">
                     <br /><br /> --%>
                    <input type="submit" value="保存填写" id="submit">
                    <input type="reset" value="撤销重写" id="reset">
                </form>


            </div>
            <div class="cleaner"></div>
        </div>

        <div id="templatemo_footer">
            Copyright (c) 2018 <a href="#">Books 书城</a> | <a href="#">Web工作室</a>
        </div>

    </div>
</div>

</body>
</html>