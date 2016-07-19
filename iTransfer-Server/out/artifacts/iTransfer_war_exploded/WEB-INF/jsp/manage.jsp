<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/4/30 0030
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理页面</title>
    <link href="style/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<nav>
  <div class="title">iTransfer</div>
</nav>
<div class="content1">
  <p>${message}</p>
  <div><a href="${pageContext.request.contextPath}/manage?init=true">初始化数据库</a></div>
  </div>
</body>
</html>
