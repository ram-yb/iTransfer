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
    <title>文件上传结果</title>
</head>
<style>
    body, ul, ol, li, p, h1, h2, h3, h4, h5, h6, form, fieldset, table, td, img, div, dl, dt, dd, input {
        margin: 0;
        padding: 0;
    }

    body {
        font-size: 12px;
        font-family: "微软雅黑";
    }

    img {
        border: none;
    }

    li {
        list-style: none;
    }

    input, select, textarea {
        outline: none;
    }

    textarea {
        resize: none;
    }

    a {
        text-decoration: none;
    }

    /*清浮动*/
    .clearfix:after {
        content: "";
        display: block;
        clear: both;
    }

    .clearfix {
        zoom: l;
    }

    body {
        background-image: url(image/bg.png);
        background-position: left top;
        background-repeat: repeat-y;
        background-size: 100%;
    }

    .header {
        width: 100%;
        height: 100px;
        background: #09496c;
    }

    .logo {
        margin: 0px auto;
        display: block;
        padding-top: 5px;
    }

    .content {
        width: 1080px;
        background: #000;
        margin: 60px auto;
        background-color: rgba(0, 0, 0, 0.5);
        border-radius: 5px;
        overflow: auto;
    }

    .sendbar, .receivebar {
        width: 1000px;
        height: 580px;
        background: #f9fafc;
        margin: 40px;
        overflow: hidden;
    }

    .sendbar {
        float: left;
    }

    .receivebar {
        float: right;
    }

    .sendtop, .receivetop {
        height: 70px;
        width: 1000px;
        background: #34a0fd;
    }

    .sendtop p, .receivetop p {
        display: block;
        width: 420px;
        height: 70px;
        text-align: center;
        font-size: 36px;
        color: #fff;
        line-height: 70px;
        background-repeat: no-repeat;
        background-position: 100px 20px;
        float: left;
    }

    .close {
        background-image: url(image/close.png);
        width: 70px;
        height: 70px;
        float: right;
    }

    .close:hover {
        background-image: url(image/close_sel.png);
    }

    .sendbtn {
        display: block;
        background: #34a0fd;
        width: 840px;
        height: 60px;
        margin: 400px auto;
        color: #fff;
        font-size: 32px;
        line-height: 60px;
        text-align: center;
        border-radius: 10px;
    }

    .sendbtn:hover {
        background-color: #1782de;
    }

    .filebar {
        width: 840px;
        height: 100px;
        margin: 40px 80px 0;
        float: left;
        background-color: #aab7c2;
    }

    .filesize {
        display: block;
        height: 100px;
        line-height: 100px;
        font-size: 30px;
        background-image: url(image/list.png);
        background-repeat: no-repeat;
        background-position: 34px 26px;
        color: #fff;
        text-indent: 120px;
        float: left;
    }

    .cancel_btn {
        display: block;
        float: right;
        width: 128px;
        height: 58px;
        color: #fff;
        font-size: 24px;
        line-height: 58px;
        text-align: center;
        background: #32495c;
        margin: 20px 40px;
    }

    .passwordbar {
        width: 840px;
        height: 300px;
        background: #d1d8df;
        float: left;
        margin: 10px 80px;
    }

    .left_bar, .right_bar {
        width: 420px;
        float: left;
    }

    .left_bar .text, .right_bar .text {
        display: block;
        width: 420px;
        height: 60px;
        line-height: 60px;
        font-size: 24px;
        color: #32495c;
        text-align: center;
    }

    .left_bar .ma {
        margin-left: 110px;
    }

    .fileID, .filePWD {
        width: 420px;
        margin: 20px 0;
    }

    .fileID span, .filePWD span {
        display: block;
        float: left;
        height: 60px;
        line-height: 60px;
        margin: 0 30px;
        font-size: 24px;
        color: #34a0fd;
    }

    .fileID input, .filePWD input {
        width: 230px;
        height: 60px;
        background: #fff;
        border-radius: 4px;
        border: 1px solid #aab7c2;
        line-height: 60px;
        text-align: center;
        color: #ffa426;
        font-size: 24px;
    }

    .downloadbtn {
        display: block;
        background: #34a0fd;
        width: 840px;
        height: 100px;
        margin: 300px auto;
        color: #fff;
        font-size: 32px;
        line-height: 100px;
        text-align: center;
        border-radius: 10px;
    }

    .downloadbtn:hover {
        background-color: #1782de;
    }

</style>
<body>
<div class="header">
    <a href="${pageContext.request.contextPath}/indexServlet"><img src="image/logo.png" class="logo"></a>
</div>
<!--内容区-->
<div class="content">
    <div class="sendbar">
        <div class="sendtop">
            <p>发送文件</p>
            <a href="${pageContext.request.contextPath}/indexServlet" class="close"></a>
        </div>
        <div class="filebar">
            <p class="filesize">文件上传成功！！！</p>
        </div>

        <div class="passwordbar">
            <div class="left_bar">
                <p class="text">方法1.使用iTransfer扫描二维码</p>

                <div class="ma">
                    <img src="${img}">
                </div>
            </div>
            <div class="right_bar">
                <p class="text">方法2.输入文件ID和校验码</p>

                <div class="fileID">
                    <span>文件ID:</span>
                    <input type="text" value="${filecode}">
                </div>
                <div class="filePWD">
                    <span>校验码:</span>
                    <input type="text" value="${password}">
                </div>
            </div>

        </div>
    </div>

</body>
</html>
