package com.cn.test.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : lj
 * @since : 2021/2/1
 */
@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get");
        //转发
        req.getRequestDispatcher("/j.jsp").forward(req, resp);
        //重定向
//        resp.sendRedirect(req.getContextPath()+"/home/lj/桌面/springMvcTest/src/main/webapp/WEB-INF/pages/success.jsp");

//        resp.setCharacterEncoding("utf-8");
//        resp.setContentType("text/html;charset=utf-8");
//        PrintWriter writer = resp.getWriter();
//        writer.println("<html>");
//        writer.println("<body>欢迎"+"</body>");
//        writer.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post");
    }
}
