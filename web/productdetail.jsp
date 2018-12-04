<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Books Store - Product Detail</title>
<meta name="keywords" content="shoes store, product detail, free template, ecommerce, online shop, website templates, CSS, HTML" />
<meta name="description" content="Shoes Store, Product Detail, free ecommerce template provided " />
<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />

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

<script type="text/javascript" src="js/jquery-1-4-2.min.js"></script> 
<link rel="stylesheet" href="css/slimbox2.css" type="text/css" media="screen" /> 
<script type="text/JavaScript" src="js/slimbox2.js"></script>

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
<c:if test="${empty product }">
		<jsp:forward page="/MainServlet"/>
</c:if>
<div id="templatemo_body_wrapper">
<div id="templatemo_wrapper">

	<div id="templatemo_header">
    	<div id="site_title"><h1><a href="${pageContext.request.contextPath }/index.jsp">Online Books Store</a></h1></div>
        <div id="header_right">
        	<p>
	        <c:if test="${!empty user }">
		        <a href="${pageContext.request.contextPath }/user/personal.jsp">我的个人中心</a> |
        	</c:if>
	        <a href="${pageContext.request.contextPath }/user/CartServlet?op=findCart">购物车</a> |
	        <c:if test="${ empty user }">
	        	<a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> |
	        	<a href="${pageContext.request.contextPath }/user/register.jsp">注册</a></p>
	        </c:if>
	        <c:if test="${!empty user }">
	        	${user.nickname }
	        	<a href="${pageContext.request.contextPath }/user/UserServlet?op=lgout">退出</a></p>
	        </c:if> 
		</div>
        <div class="cleaner"></div>
    </div> <!-- END of templatemo_header -->
    
    <div id="templatemo_menubar" style="position: relative;">
    	<div id="top_nav" class="ddsmoothmenu">
            <ul>
                <li><a href="${pageContext.request.contextPath }/MainServlet" class="selected">主页</a></li>
            </ul>
            <br style="clear: left" />
        </div> <!-- end of ddsmoothmenu -->
        <div id="templatemo_search" onmouseleave="dispear(document.getElementById('hintContent'))">
            <form action="${pageContext.request.contextPath }/ProductServlet" method="get">
              <input type="hidden" name="op" value="findProductsByName"/>
              <input type="text" value="${pname }" name="pname" id="keyword" title="keyword"
                     onfocus="clearText(this)" onblur="clearText(this)" onkeypress="hint()" class="txt_field" />
              <input type="submit" name="Search" value=" " alt="Search" id="searchbutton" title="Search" class="sub_btn"  />
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
                    	<c:forEach items="${categories}" var="category" varStatus="vs">
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
        	<h3>${product.pname}</h3>
            <div class="content_half float_l">
        	<%--<a  rel="lightbox[portfolio]" href="images/product/10_l.jpg">--%>
        	<img src="files/${product.imgUrl }" style="width: 200px; height: 300px" /></a>
            </div>
            <div class="content_half float_r">
                <table>
                    <tr>
                        <td width="160">商城价格:</td>
                        <td>${product.estorePrice}</td>
                    </tr>
                    <tr>
                        <td>市场价格:</td>
                        <td>${product.markPrice}</td>
                    </tr>
                    <tr>
                        <td>商品号:</td>
                        <td>${product.pid }</td>
                    </tr>
                    <tr>
                    	<td>购买数量:</td>
                        <td><input type="text" id="snum" value="1" style="width: 20px; text-align: right" />&nbsp;库存:${product.pnum }</td>
                    </tr>
                </table>
                <div class="cleaner h20"></div>

                <c:if test="${empty user }">
	                <a href="javascript:login()" class="addtocart"></a>
	            </c:if>
	            <c:if test="${!empty user }">
	                <a href="javascript:addCart(${product.pid },${user.uid})" class="addtocart"></a>
	            </c:if>
                <a href="javascript:history.go(-1);">返回</a>
			</div>
            <div class="cleaner h30"></div>
            
            <h5>商品描述</h5>
            <p>${requestScope.product.description }</p>	
        </div> 
        <div class="cleaner"></div>
    </div> <!-- END of templatemo_main -->
    
    <div id="templatemo_footer">
    	Copyright (c) 2018 <a href="#">Books 书城</a> | <a href="#">Web工作室</a>
    </div> <!-- END of templatemo_footer -->
    
</div> <!-- END of templatemo_wrapper -->
</div> <!-- END of templatemo_body_wrapper -->
<script type="text/javascript">
		function login(){
			alert("请先登录");
			window.location.href="${pageContext.request.contextPath}/user/login.jsp";
		}
		function addCart(pid,uid){
			var snum = $("#snum").val();
			window.location.href="${pageContext.request.contextPath}/user/CartServlet?op=addCart&pid="+pid+"&uid="+uid+"&snum="+snum;
		}
	</script>
</body>
</html>