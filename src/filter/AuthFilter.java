package filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        System.out.println("启动认证过滤器");
    }

    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
            ServletException {

        /*
            登录过滤器的大概:
             1. 判断用户请求的是否登录页:
                  如果是登录页, 过滤器就需要将任务交给其他资源处理
             2. 如果用户没有登录过就直接请求其他页面, 判断是否有session,
                 如果没有,将该用户的请求到登录页
                 如果有, 那么过滤器需要将任务交给其他资源处理
         */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;

        //url和uri区别: uri:只有资源文件名, url: 完整的路径
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();

        System.out.println("uri: " + uri);
        System.out.println("url: " + url);

        //如果访问的资源是以css或者js结尾的，那么就不需要判断是否登录
        if (uri.endsWith("*.css") || uri.endsWith("*.js")) {
            /*
                过滤器放行，表示继续运行下一个过滤器，或者最终访问的某个serlvet,jsp,html等等
                如果没有这一句，就不会执行后面的业务功能了。
                这里的return可以结束当前的Filter,但是其目的不是结束当前filter,而是返回，
                从而"不运行"后面的"如果Session中没有用户信息就跳转" 的功能。
                因为 /login和/login.html恰恰就是在没有登录的时候，也需要访问的页面
             */
            chain.doFilter(request, response);      //过滤器放行，表示继续运行下一个过滤器
            return;                                 //如果访问的是css, js等文件, 不需要判断后面的登录问题了
        }

        if (uri.endsWith("login.html") || uri.endsWith("login")) {
            chain.doFilter(request, response);      //将任务交给以下一个环节处理
            return;                                 //如果已经是在访问登录页了, 就不需要再判断Session了
        }

        //获取Session
        String user = (String)request.getSession().getAttribute("userName");
        if (null == user) {     //防止空异常的错误
            response.sendRedirect("login.html");
        }
        chain.doFilter(request, response);  //将任务交给下一个环节处理
    }

    public void destroy() {
        System.out.println("认证过滤器放行");
    }
}
