package cn.edu.hhu.reg.common.http;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpResponse {
    private final HttpRequest   request;
    private final InputStream   inputStream;
    
    private int     code;
    private String  message;
    private String  charset;
    private int     contentLength;
    
    protected HttpResponse(HttpRequest request, InputStream inputStream) {
        this.request        = request;
        this.inputStream    = inputStream;
    }
    
    /**
     * 获取HTTP请求对象
     * @return 请求对象
     */
    public HttpRequest getRequest() {
        return request;
    }
    
    /**
     * 设置HTTP响应码
     * @param code HTTP响应码
     */
    protected void setCode(int code) {
        this.code = code;
    }
    
    /**
     * 获取HTTP响应码
     * @return HTTP响应码
     */
    public int getCode() {
        return code;
    }
    
    /**
     * 获取HTTP响应消息
     * @param message HTTP响应消息
     */
    protected void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * 获取响应信息
     * @return 响应信息
     * @throws HttpException
     */
    public String getMessage() throws HttpException {
        return message;
    }
    
    /**
     * 设置响应内容编码
     * @param charset 内容编码
     */
    protected void setCharset(String charset) {
        this.charset = charset;
    }
    
    /**
     * 获取响应内容编码
     * @return 响应内容编码
     */
    public String getCharset() {
        return charset;
    }
    
    /**
     * 设置响应内容编码
     * @param contentLength 
     */
    protected void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }
    
    /**
     * 获取响应内容编码
     * @return 
     */
    public int getContentLength() {
        return contentLength;
    }
    
    /**
     * 接收响应流到文件
     * @param file 文件
     * @throws HttpException
     */
    public void receive(final File file) throws HttpException {
        final OutputStream output;
        try {
            output = new BufferedOutputStream(new FileOutputStream(file), request.getBufferSize());
            receive(output);
        } catch (FileNotFoundException e) {
            throw new HttpException(e);
        }
    }
    
    /**
     * 获取响应内容
     * @param charset 内容编码
     * @return
     * @throws HttpException
     */
    public String body(String charset) throws HttpException {
        final ByteArrayOutputStream output = byteStream();
        try {
            receive(output);
            inputStream.reset();
            if(charset == null) charset = "UTF-8";
            return output.toString(charset);
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
    
    /**
     * 获取响应内容
     * @return 响应内容
     * @throws HttpException
     */
    public String body() throws HttpException {
        return body(charset);
    }
    
    /**
     * 获取一个与内容长度相等的字节流对象
     * @return 字节流对象
     */
    private ByteArrayOutputStream byteStream() {
        if(contentLength > 0) {
            return new ByteArrayOutputStream(contentLength);
        } else {
            return new ByteArrayOutputStream();
        }
    }
    
    /**
     * 接收响应流到输出流
     * @param output 输出流
     * @throws HttpException
     */
    private void receive(final OutputStream output) throws HttpException {
        inputStream.mark(0);
        new HttpCloseOperation<OutputStream>(output, request.getIgnoreCloseExceptions()) {
            @Override
            public OutputStream run() throws IOException {
                final byte[] buffer = new byte[request.getBufferSize()];
                int read;
                while((read = inputStream.read(buffer)) != -1) output.write(buffer, 0, read);
                return output;
            }
        }.call();
        try {
            inputStream.reset();
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
    
    /**
     * 马上关闭响应流，回收资源
     * @throws HttpException 
     */
    public void recyle() throws HttpException {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
}