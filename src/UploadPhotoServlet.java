import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/** 上传文件 */
public class UploadPhotoServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String filename = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 设置上传文件的大小限制为1M
            factory.setSizeThreshold(1024 * 1024);

            List items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }

            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {

                    // 根据时间戳创建头像文件
                    filename = System.currentTimeMillis() + ".jpg";

                    /*通过getRealPath获取上传文件夹，如果项目在e:/project/j2ee/web,
                      那么就会自动获取到 e:/project/j2ee/web/uploaded*/
                    String photoFolder =request.getServletContext().getRealPath("uploaded");

                    File f = new File(photoFolder, filename);
                    f.getParentFile().mkdirs();

                    // 通过item.getInputStream()获取浏览器上传的文件的输入流
                    InputStream is = item.getInputStream();

                    // 复制文件
                    FileOutputStream fos = new FileOutputStream(f);
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.close();

                } else {
                    /*在遍历Item时(Item即对应浏览器提交的字段)，可以通过item.isFormField来判断是否是常规字段还是提交的文件。
                    当item.isFormField返回true的时候，就表示是常规字段。
                    通过item.getFieldName()和item.getString()就知道分别是哪个字段，以及字段的值*/
                    System.out.println(item.getFieldName());
                    String value = item.getString();
                    value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println(value);
                }
            }

            String html = "<img width='200' height='150' src='uploaded/%s' />";
            response.setContentType("text/html");
            PrintWriter pw= response.getWriter();

            pw.format(html, filename);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
