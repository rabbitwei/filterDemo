package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//过滤器需要实现Filter类, 重写doFilter(), init(), destroy()方法
public class FirstFilter implements Filter {

    /*  1. init()方法
            与Servlet需要配置自启动才会随着tomcat的启动而执行init()方法不一样。
            Filter一定会随着tomcat的启动自启动。
     */
    @Override
    public void init(FilterConfig var1) throws ServletException {
        System.out.println("启动FirstFilter过滤器");

        /*
            1. Filter是web应用非常重要的一个环节，如果Filter启动失败，或者本身有编译错误，
               不仅这个Filter不能使用，整个web应用会启动失败，导致用户无法访问页面
            2. 在启动tomcat过程中，也会看到这样的字样：
                    严重: Context [] startup failed due to previous errors
                    这常常用于提示Filter启动失败了
         */
        System.out.println("故意制造一个错误");
        /*
        Object o = null;
        o.toString();
        */

    }

    /*
      3.Filter的工作方法:
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
            ServletException {
        /*
            Filter就像一个一个哨卡，用户的请求需要经过Filter并且可以有多个过滤器

            1. 过滤器的工作方法: doFilter的参数类型为 ServletRequest, ServletResponse
               为了能使用request, response. 需要将 ServletRequest 转成子类 HttpServletRequest
                                                 ServletResponse 转成 HttpServletResponse

                HttpServletRequest request = (HttpServletRequest) req;
         */

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse respose = (HttpServletResponse) rep;   //类型转换

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
        String dateStr = format.format(date);
        String ip = request.getRemoteAddr();                        //获取ip地址
        String url = request.getRequestURL().toString();           //获取访问的文件路径

        System.out.printf("%s %s 访问了 %s %n", dateStr, ip, url);

        //过滤器放行，表示继续运行下一个过滤器，或者最终访问的某个servlet,jsp,html等等
        chain.doFilter(request, respose);
    }


    @Override
    public void destroy() {
        System.out.println("销毁了FirstFilter过滤器");
    }

}
