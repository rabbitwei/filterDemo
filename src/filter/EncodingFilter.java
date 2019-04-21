package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {
    public void init(FilterConfig var1) throws ServletException {
        System.out.println("执行中文处理的过滤器");
    }

    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
            ServletException {

        /*
            获取UTF-8编码的中文，但是如果有很多servlet都需要获取中文，那么就必须在每个Servlet中增加这段代码。
            有一个简便的办法，那就是通过Filter过滤器进行中文处理 ，那么所有的Servlet都不需要单独处理了。

         */
        //将ServletRequest ==> HttpServletRequest, ServletResponse ===> HttpServletResponse;
        //将doFilter的两个参数转型为 HttServletRequest, HttpServletResponse
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;

        //在过滤器中统一处理中文问题
        // request.setCharacterEncoding("utf-8");           //request是处理用户请求的中文问题
        response.setContentType("text/html; charset=utf-8");    //response是处理响应的中文问题
        chain.doFilter(request, response);          //处理完后, 交给下一个过滤器或资源处理
    }

    public void destroy() {

    }
}
