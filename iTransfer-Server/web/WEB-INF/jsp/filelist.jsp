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
    <title>选择文件上传</title>
    <script type="text/javascript">
        function addinput(filename, fullname) {
            var div = document.getElementById("files");

            var li = document.createElement("li");
            li.className = "listfile";

            var img = document.createElement("img");
            img.src = "image/file_icon.png";
            img.className = "fileicon";

            var p = document.createElement("p");
            p.className = "filename";
            p.innerHTML = filename;

            var a = document.createElement("a");
            a.className = "deletefile";
            a.href = "#";
            a.innerHTML = "删除";
            a.onclick = function () {
                //删除表单
                var form = document.getElementById('fileuploadform');
                var files = form.getElementsByTagName("input");
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    if (file.value == fullname) {
                        form.removeChild(file);
                        break;
                    }
                }
                //删除界面显示
                this.parentNode.parentNode.removeChild(this.parentNode);
            }

            li.appendChild(img);
            li.appendChild(p);
            li.appendChild(a);

            div.appendChild(li);
        }

        function addinput1() {
            var file = document.createElement("input");
            file.type = "file";
            file.name="file";
            file.style.display="none";
            file.onchange = function () {
                var start = file.value.lastIndexOf("\\");
                if (start < 0) {
                    start = file.value.lastIndexOf("/");
                }
                var show = file.value.substring(start + 1);
                var form = document.getElementById('fileuploadform');
                form.appendChild(file);
                addinput(show, file.value);
            }
            file.click();
        }

        function upload() {
            var form = document.getElementById('fileuploadform');
            var files = form.getElementsByTagName("input");
            var num = 0;
            for(var i=0;i<files.length;i++){
                if(files[i].type=="file"){
                    num++;
                }
            }
            if(num<=0){
                alert("您没有选择文件哦！");
                return;
            }
            form.submit();
        }
    </script>
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

    /*file表单*/
    input {
        vertical-align: middle;
        margin: 0;
        padding: 0
    }

    .file-box {
        position: relative;
        width: 340px
    }

    .txt {
        height: 22px;
        border: 1px solid #cdcdcd;
        width: 180px;
    }

    .btn {
        background-color: #FFF;
        border: 1px solid #CDCDCD;
        height: 24px;
        width: 70px;
    }

    .file {
        position: absolute;
        top: 0;
        right: 80px;
        height: 24px;
        filter: alpha(opacity:0);
        opacity: 0;
        width: 260px
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

    .btn {
        width: 840px;
        margin: 400px auto;
    }

    .pwd, .pwd_send {
        width: 402px;
        height: 60px;
        background: #d5e9fa;
        border: 1px solid #09F;
        border-radius: 4px;
        display: block;
        text-align: center;
        font-size: 32px;
        color: #7c7c7c;
        font-family: "微软雅黑";
        float: left;
        line-height: 60px;
    }

    .pwd_send {
        color: #ffa426;
    }

    .sendbtn {
        display: block;
        background: #34a0fd;
        width: 402px;
        height: 60px;
        color: #fff;
        font-size: 32px;
        line-height: 60px;
        text-align: center;
        border-radius: 10px;
        float: right;
    }

    .sendbtn:hover {
        background-color: #1782de;
    }

    .listbar {
        width: 356px;
        height: 320px;
        margin: 40px 322px 0px;
        float: left;
    }

    .addfile {
        width: 356px;
        height: 40px;
        background: #34a0fd;
        font-size: 24px;
        text-align: center;
        line-height: 40px;
        color: #fff;
        display: block;
        margin: 0 auto;
    }

    .listfile {
        float: left;
        width: 354px;
        height: 60px;
        border: 1px solid #37b9fc;
        margin: 10px auto 0px;
    }

    .fileicon {
        padding: 2px;
        width: 56px;
        height: 56px;
        float: left;
    }

    .filename {
        width: 200px;
        height: 60px;
        font-size: 16px;
        line-height: 60px;
        color: #6f6f6f;
        overflow: hidden;
        text-indent: 20px;
        float: left;
    }

    .deletefile {
        width: 70px;
        height: 32px;
        font-size: 20px;
        color: #fff;
        line-height: 32px;
        text-align: center;
        margin: 14px 10px;
        float: right;
        display: block;
        background: #ff271c;
    }

    .deletebtn {
        width: 100px;
        height: 100px;
        background-image: url(image/delete_btn.png);
        position: absolute;
        top: 0;
        left: 0;
    }

</style>
<body>
<div class="header">
    <a href="${pageContext.request.contextPath}/indexServlet"><img src="image/logo.png" class="logo"></a>
</div>
<!--内容区-->
<form id="fileuploadform" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data"
      method="post">
    <div class="content">
        <div class="sendbar">
            <div class="sendtop">
                <p>发送文件</p>
                <a href="${pageContext.request.contextPath}/indexServlet" class="close"></a>
            </div>


            <ul class="listbar" id="files">
                <a href="#" onclick="addinput1()" class="addfile">添加上传文件</a>

            </ul>
            <div class="btn">
                <input id="password" type="text" name="password" placeholder="请输入6位数字校验码" class="pwd">
                <!--<input type="text" value="123456" class="pwd_send">-->
                <a href="#" onclick="upload()" class="sendbtn">
                    开始发送
                </a>
            </div>
        </div>
    </div>
</form>
</body>
</html>
