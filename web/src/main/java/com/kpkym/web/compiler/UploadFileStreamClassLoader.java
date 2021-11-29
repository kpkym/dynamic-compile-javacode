package com.kpkym.web.compiler;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class UploadFileStreamClassLoader extends ClassLoader {
    /**
     * 要加载的class的类名
     */
    private String className;
    /**
     * 要加载的调试class的流，可以通过客户端文件上传，也可以通过传递url来获取
     */
    private InputStream inputStream;

    /**
     *
     * @param inputStream 要加载的class 的文件流
     * @param className 类名
     * @param parentWebappClassLoader 父类加载器
     */
    public UploadFileStreamClassLoader(InputStream inputStream, String className, ClassLoader parentWebappClassLoader) {
        super(parentWebappClassLoader);
        this.className = className;
        this.inputStream = inputStream;
    }

    @Override
    protected Class<?> findClass(String name)  {
        byte[] data = getData();
        try {
            String s = new String(data, "utf-8");

        } catch (UnsupportedEncodingException e) {
        }
        return defineClass(className,data,0,data.length);
    }

    private byte[] getData(){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[2048];
            int num = 0;
            while ((num = inputStream.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes, 0,num);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
        }
		return null;
    }
}
