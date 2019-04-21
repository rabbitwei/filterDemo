package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {


        //前端发送过来的数据
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        System.out.println("user: " + user + " password: " + password);
        if ("rabbit".equals(user) && "12345".equals(password)) {
            response.getWriter().println(1);
            //Session是在关闭浏览器的时候失效后20分钟后
            request.getSession().setAttribute("userName", user);
        }
        else
            response.getWriter().println(0);
    }
}
