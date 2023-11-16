package com.jun.utils;

import org.springframework.web.context.request.RequestContextHolder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author 35238
 * @date 2023/7/22 0022 21:19
 */
public class WebUtils {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static void renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void setDownLoadHeader(String filename,  HttpServletResponse response) throws UnsupportedEncodingException {

//        ServletContext context  使用下面的  需要在参数中使用
//        String mimeType = context.getMimeType(filename);//获取文件的mime类型
//        response.setHeader("content-type",mimeType);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fname= URLEncoder.encode(filename,"UTF-8");
        response.setHeader("Content-disposition","attachment; filename="+fname);


    }
}