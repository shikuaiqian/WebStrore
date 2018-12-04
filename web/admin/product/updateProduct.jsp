<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>

    <style type="text/css">
        <!--
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

        -->
    </style>
    <script type="text/javascript">
        function checkInput() {
            var pid = document.getElementsByName("pid")[0];
            var pnum = document.getElementsByName("pnum")[0];
            var pname = document.getElementsByName("pname")[0];
            var estorePrice = document.getElementsByName("estorePrice")[0];
            var markPrice = document.getElementsByName("markPrice")[0];
            var description = document.getElementsByName("description")[0];
            // 商品号要为数字
            if (/^\d+$/.test(pid.value)) {
                // 库存要为数字
                if (/^\d+$/.test(pnum.value)) {
                    // 商品名不能为空
                    if (pname.value !== "") {
                        // 价格保留两位小数
                        if (/^\d+(\.\d{1,2})?$/.test(estorePrice.value) && /^\d+(\.\d{1,2})?$/.test(markPrice.value)) {
                            // 商品描述不能为空
                            if (description.value !== "") {
                                return true;
                            } else {
                                alert("商品描述为空，请重新输入...");
                                return false;
                            }
                        } else {
                            alert("请输入正确价格，仅需保留两位小数...");
                            return false;
                        }
                    } else {
                        alert("商品名为空，请重新输入...");
                        return false;
                    }
                } else {
                    alert("库存数输入错误，请输入数字...");
                    return false;
                }
            } else {
                alert("商品号输入错误，请输入数字...");
                return false;
            }
        }
    </script>
</head>
<body>
<form method="post"
      action="${pageContext.request.contextPath }/admin/MultipartProductServlet?op=updateProduct&oldImgUrl=${product.imgUrl}"
      enctype="multipart/form-data" onsubmit="return checkInput()">
    <%--<form method="post" action="${pageContext.request.contextPath }/ProductServlet">--%>
    <%--<input type="hidden" name="op" value="updateProduct"/>--%>
    <%--<input type="hidden" name="imgUrl" value="${product.imgUrl }">--%>

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
														修改商品</span>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td>
                                        <div align="right">
											<span class="STYLE1"> 
												 
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
                <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
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
                                <span class="STYLE10">品牌</span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div align="left">
                                <select name="cid" id="st" onchange="change()">
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.cid}"<c:if test="${category.cid == product.cid}">selected</c:if>>
                                                ${category.cname}
                                        </option>
                                    </c:forEach>
                                </select>
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
                                <span class="STYLE10">商品号</span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="left">

                                <input type="text " name="pid" value="${product.pid}" readonly="readonly"/>

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
                                <span class="STYLE10">总库存</span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="left">

                                <input type="text " name="pnum" value="${product.pnum }"/>

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
                                <span class="STYLE10">商品名称</span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="left">

                                <input type="text " name="pname" width="200" value="${product.pname}"/>

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
                                <span class="STYLE10">商城价:￥</span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="left">

                                <input type="text " name="estorePrice" value="${product.estorePrice}"/><br>

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
                                <span class="STYLE10">市场价:￥</span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="left">

                                <input type="text " name="markPrice" value="${product.markPrice }"/><br>

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
                                <span class="STYLE10">图片</span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="left">


                                <img width="200px" height="180px"
                                     src="${pageContext.request.contextPath}/files/${product.imgUrl}"/>
                                <input type="file" name="imgUrl"/><br>

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
                                <span class="STYLE10">商品描述</span>
                            </div>
                        </td>
                        <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
                            <div
                                    align="left">

                                <textarea name="description" cols="80" rows="5">${product.description}</textarea>

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
                                <input type="button" onclick="history.go(-1);" value="返回"/>
                                <input type="submit" value="修改"/>
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


