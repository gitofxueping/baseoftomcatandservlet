import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**跳转：服务器跳转和客户端跳转，中文问题*/
public class LoginServlet extends HttpServlet {

   /*(1)获取中文的参数
       ①login.html中加上
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       ②login.html：form的method修改为post
       ③在servlet进行解码：
         request.setCharacterEncoding("UTF-8");
    (2)返回中文的响应
         在Servlet中进行编码：
         response.setContentType("text/html; charset=UTF-8");*/

    public void init(ServletConfig config){
        System.out.println("init of LoginServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        String html = null;
        if ("admin".equals(name) && "123".equals(password))
           request.getRequestDispatcher("success.html").forward(request,response);
        else
            response.sendRedirect("fail.html");

    }
}
