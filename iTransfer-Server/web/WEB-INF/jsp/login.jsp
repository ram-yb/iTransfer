<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/4/30 0030
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
    <link href="style/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <nav>
   <div class="title">iTransfer</div>
  </nav>
  <div class="content1">
    <p>${message}</p>
      <div>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <table style="text-align: center;margin-left: auto;margin-right: auto;">
            <tr>
                <td>用户名：</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>密&nbsp;码：</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td><input type="reset" value="重置"></td>
                <td><input type="submit" value="登录" name="submit"></td>
            </tr>
            </table>
        </form>
      </div>
  </div>
</body>
</html>
