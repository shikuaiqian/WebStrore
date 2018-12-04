<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Books Store, free template</title>
    <meta name="keywords" content="shoes store, free template, ecommerce, online shop, website templates, CSS, HTML"/>
    <meta name="description" content="Shoes Store is a free ecommerce template provided "/>
    <link href="css/templatemo_style.css" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" href="css/nivo-slider.css" type="text/css" media="screen"/>

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

</head>

<body>
<c:if test="${empty topProducts}">
    <jsp:forward page="/MainServlet"/>
</c:if>
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
<div id="templatemo_body_wrapper">
    <div id="templatemo_wrapper">

        <div id="templatemo_header">
            <div id="site_title">
                <h1>
                    <a href="${pageContext.request.contextPath }/index.jsp">
                        Online Books Store</a>
                </h1>
            </div>
            <div id="header_right">
                <p>
                    <c:if test="${!empty user }">
                        <a href="${pageContext.request.contextPath }/user/personal.jsp">我的个人中心</a> |
                    </c:if>
                    <a href="${pageContext.request.contextPath }/user/CartServlet?op=findCart">购物车</a> |
                    <c:if test="${empty user }">
                    <a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> |
                    <a href="${pageContext.request.contextPath }/user/register.jsp">注册</a></p>
                </c:if>
                <c:if test="${!empty user }">
                    ${user.nickname }
                    <a href="${pageContext.request.contextPath }/user/UserServlet?op=logout">退出</a></p>
                </c:if>
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
                    <input type="text" value="${pname}" name="pname" id="keyword" title="keyword"
                           onfocus="clearText(this)"
                           class="txt_field" onkeypress="hint()"/>
                    <input type="submit" name="Search" value="" alt="Search" id="searchbutton" title="Search"
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
        <div class="copyrights">Collect from <a href="#" title="Web商城">Web商城</a></div>

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
                <div id="slider-wrapper">
                    <div id="slider" class="nivoSlider">
                        <c:forEach items="${topProducts}" var="product">
                            <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductsByName&pname=${product.description}">
                                <img src="${pageContext.request.contextPath }/files/${product.imgUrl }" style="width: 680px;height: 300px" alt=""
                                     title="${product.pname }"/>
                            </a>
                        </c:forEach>
                    </div>
                    <div id="htmlcaption" class="nivo-html-caption">
                        <strong>This</strong> is an example of a <em>HTML</em> caption with <a href="#">a link</a>.
                    </div>
                </div>
                <script type="text/javascript" src="js/jquery-1.4.3.min.js"></script>
                <script type="text/javascript" src="js/jquery.nivo.slider.pack.js"></script>
                <script type="text/javascript">
                    $(window).load(function () {
                        $('#slider').nivoSlider();
                    });
                </script>

                <h3>热门商品</h3>
                <c:forEach items="${hotProducts}" var="product" varStatus="vs">
                    <div class="${vs.index % 3 != 2?'product_box':'product_box no_margin_right' }">
                        <h3>${product.pname }</h3>
                        <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductByPid&pid=${product.pid}">
                            <img src="${pageContext.request.contextPath }/files/${product.imgUrl }" width="200px" height="300px" alt=""
                                 title="${product.pname }"/>
                        </a>
                        <p style="width: 200px;overflow: hidden;white-space: nowrap; text-overflow: ellipsis;">${product.description }</p>
                        <p class="product_price">￥ ${product.estorePrice }</p>
                        <c:if test="${empty user }">
                            <a href="javascript:login()" class="addtocart"></a>
                        </c:if>
                        <c:if test="${!empty user }">
                            <a href="javascript:addCart(${product.pid },${user.uid})" class="addtocart"></a>
                        </c:if>
                        <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductByPid&pid=${product.pid}"
                           class="detail"></a>
                    </div>
                    <c:if test="${vs.index % 3 == 2}">
                        <div class="cleaner"></div>
                    </c:if>
                </c:forEach>
            </div>
            <div class="cleaner"></div>
        </div> <!-- END of templatemo_main -->

        <div id="templatemo_footer">
            Copyright (c) 2018 <a href="#">Books 书城</a> | <a href="#">Web工作室</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    function login() {
        alert("请先登录");
        window.location.href = "${pageContext.request.contextPath}/user/login.jsp";
    }

    function addCart(pid, uid) {
        window.location.href = "${pageContext.request.contextPath}/user/CartServlet?op=addCart&pid=" + pid + "&uid=" + uid;
    }
</script>
</body>
</html>