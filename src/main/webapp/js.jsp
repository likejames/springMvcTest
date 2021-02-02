<%--
  Created by IntelliJ IDEA.
  User: lj
  Date: 2021/1/27
  Time: 下午1:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试jsp</title>
    <script src="js/js.js"></script>
    <script>
        $(function () {
            $("#button").click(function () {
                alert("点击了");
                var username = document.getElementById("username").value;
                var password = document.getElementById("password").value;
                var sendData = {username:username,password:password};
                $.ajax({
                    //编写json格式,设置属性和值
                    url:"user/testAjax",
                    contentType:"application/json;charset=UTF-8",
                    data:JSON.stringify(sendData),
                    dataType:"json",
                    type:"post",
                    success:function (data) {
                        alert(JSON.stringify(data))
                        if (data.code==1){
                            alert("登录失败");
                        }else {
                            alert(JSON.stringify(data));
                        }
                    }
                })
            });
        });
    </script>
</head>
<body>
<%--<button id="btn">登录</button>--%>
<form id="myForm">
    ajax登录名:<input type="text" name="username" id="username">
    ajax密码:<input type="text" name="password" id="password">
    <input id="button" type="button" value="登录">
</form>


<%--<form method="post" action="user/testForm">--%>
<%--    登录名:<input type="text" name="username">--%>
<%--    密码:<input type="text" name="password">--%>
<%--    <input type="submit" value="登录">--%>
<%--</form>--%>
</body>
</html>
