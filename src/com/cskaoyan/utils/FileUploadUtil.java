package com.cskaoyan.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FileUploadUtil {


    private static ServletFileUpload servletFileUpload;

    static {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        servletFileUpload = new ServletFileUpload(factory);
    }

    public static byte[] handleFileUpload(HttpServletRequest request, Map<String, String> parameterMap) throws FileUploadException, UnsupportedEncodingException {
        byte[] uploadFile = null;
        List<FileItem> items;
        items = servletFileUpload.parseRequest(request);
        for (FileItem item : items) {
            if (item.isFormField()) {
                // 简单表单组件数据，键值对
                parameterMap.put(item.getFieldName(), item.getString("utf-8"));
            } else {
                // 上传文件
                parameterMap.put(item.getFieldName(), item.getName());
                uploadFile = item.get();
            }
        }
        return uploadFile;
    }

}
