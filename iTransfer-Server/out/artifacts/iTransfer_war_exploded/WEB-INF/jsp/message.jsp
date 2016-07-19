<%--
  Created by IntelliJ IDEA.
  User: 宇强
  Date: 2016/4/30 0030
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>消息提示</title>
</head>
<style>
  body,ul,ol,li,p,h1,h2,h3,h4,h5,h6,form,fieldset,table,td,img,div,dl,dt,dd,input{margin:0;padding:0;}
  body{font-size:12px;font-family:"微软雅黑";}
  img{border:none;}
  li{list-style:none;}
  input,select,textarea{outline:none;}
  textarea{resize:none;}
  a{text-decoration:none;}
  /*清浮动*/
  .clearfix:after{content:"";display:block;clear:both;}
  .clearfix{zoom:l;}
  body{ background-image:url(image/bg.png);background-position:left top;background-repeat:repeat-y;background-size:100%;}
  .header{ width:100%;height:100px;background:#09496c;}
  .logo{ margin:0px auto;display:block;padding-top:5px;}
  .content{ width:1080px;background:#000;margin:60px auto;background-color: rgba(0,0,0,0.5);border-radius:5px;overflow:auto;}

  .sendbar,.receivebar{ width:1000px;height:580px;background:#f9fafc;margin:40px;overflow:hidden;}
  .sendbar{ float:left;}
  .receivebar{ float:right;}
  .sendtop,.receivetop{ height:70px;width:1000px;background:#34a0fd;}
  .sendtop p,.receivetop p{ display:block;width:420px;height:70px;text-align:center;font-size:36px;color:#fff;line-height:70px;background-repeat:no-repeat;background-position:100px 20px;float:left;}
  .close{ background-image:url(image/close.png);width:70px;height:70px;float:right;}
  .close:hover{ background-image:url(image/close_sel.png);}

  .sendbtn{ width:200px;height:200px;display:block;background-image:url(image/choose_btn.png);margin:100px auto 20px;}
  .sendbtn:hover{ background-image:url(image/choose_btn_sel.png);}
  span{ display:block;text-align:center;color:#6f6f6f;}
  .text1{ font-size:24px;}
  .text2{ font-size:16px;}

</style>
<body>
<div class="header">
  <a href="${pageContext.request.contextPath}/indexServlet"><img src="image/logo.png" class="logo"></a>
</div>
<!--内容区-->
<div class="content">
  <div class="sendbar">
    <div class="sendtop">
      <p>消息提示</p>
      <a href="${pageContext.request.contextPath}/indexServlet" class="close"></a>
    </div>
    <p class="text1">${message}</p>
  </div>
</div>
</body>
</html>
