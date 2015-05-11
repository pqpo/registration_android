package cn.edu.hhu.reg.common.http;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public static final String METHOD_GET       = "GET";
    public static final String METHOD_POST      = "POST";
    public static final String METHOD_PUT       = "PUT";
    public static final String METHOD_DELETE    = "DELETE";
    public static final String METHOD_TRACE     = "TRACE";
    public static final String METHOD_HEAD      = "HEAD";
    public static final String METHOD_OPTIONS   = "OPTIONS";
    
    public static final String DEFAULT_ENCODING = "UTF-8";
    
    private final String  method;
    private final URL     url;
    
    private String  charset                 = DEFAULT_ENCODING;
    
    private boolean httpsTrustAll           = false;
    private boolean ignoreCloseExceptions   = true;
    private boolean uncompress              = false;
    private int     bufferSize              = 8192;
    
    private int     connectTimeout          = 15000;
    private int     readTimeout             = 15000;
    
    private Map<String, String> headers;
    private Map<String, Object> forms;
    private Map<String, Object> parts;
    private String json="";
    
    /**
     * 构造 HTTP 请求对象
     * @param method 请求方法
     * @param url 请求地址
     * @throws HttpException 请求协议不能识别
     */
    public HttpRequest(String method, CharSequence url) throws HttpException {
        try {
            this.method = method;
            this.url    = new URL(url.toString());
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
    
    /**
     * 构造 HTTP 请求对象
     * @param method 请求方法
     * @param url 请求地址
     */
    public HttpRequest(String method, URL url) {
        this.method = method;
        this.url    = url;
    }
    
    /**
     * 获取请求地址
     * @return 请求地址
     */
    public URL getUrl() {
        return url;
    }
    
    /**
     * 获取请求方法
     * @return 请求方法
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置连接超时
     * @param connectTimeout 超时时间
     * @return 请求对象
     * @throws HttpException 
     */
    public HttpRequest setConnectTimeout(int connectTimeout) throws HttpException {
        if(connectTimeout < 0) throw new HttpException("Timeout must not be negative");
        this.connectTimeout = connectTimeout;
        return this;
    }
    
    /**
     * 获取连接超时
     * @return 超时时间
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }
    
    /**
     * 设置读取超时
     * @param connectTimeout 超时时间
     * @return 请求对象
     * @throws HttpException 
     */
    public HttpRequest setReadTimeout(int readTimeout) throws HttpException {
        if(readTimeout < 1) throw new HttpException("Timeout must not be negative");
        this.readTimeout = readTimeout;
        return this;
    }
    
    /**
     * 获取读取超时
     * @return 超时时间
     */
    public int getReadTimeout() {
        return readTimeout;
    }
    
    /**
     * 设置表单编码
     * @param charset 编码
     * @return 
     */
    public HttpRequest setCharset(String charset) {
        this.charset = charset;
        return this;
    }
    
    /**
     * 获取表单编码
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置请求头参数
     * @param name 请求头参数名
     * @param value 请求头参数值
     * @return 请求对象
     */
    public HttpRequest setHeader(String name, String value) {
        if(headers == null) headers = new HashMap<String, String>();
        headers.put(name, value);
        return this;
    }
    
    /**
     * 获取请求头参数
     * @param name 请求头参数名
     * @return 请求头参数值
     */
    public String getHeader(String name) {
        if(headers == null) return null;
        return headers.get(name);
    }
    
    /**
     * 获取全部请求头参数
     * @return 请求头参数映射
     */
    public Map<String, String> getHeaders() {
        return headers;
    }
    
    /**
     * 放置表单参数
     * @param name 表单参数名
     * @param value 表单参数值
     * @return 请求对象
     */
    public HttpRequest putForm(String name, Object value) {
        if(forms == null) forms = new HashMap<String, Object>();
        forms.put(name, value);
        return this;
    }
    
    /**
     * 放置表单参数
     * @param params 表单键值对
     * @return 请求对象
     */
    public HttpRequest putForm(HashMap<String, Object> params) {
        forms = params;
        return this;
    }
    
    /**
     * 获取表单参数
     * @param name 表单参数名
     * @return 表单参数值
     */
    public Object getForm(String name) {
        if(forms == null) return null;
        return forms.get(name);
    }
    
    /**
     * 获取全部表单参数
     * @return 表单参数映射
     */
    public Map<String, Object> getForms() {
        return forms;
    }

    /**
     * 放置混合体参数
     * @param name 混合体参数名
     * @param value 混合体参数值
     * @return 请求对象
     */
    public HttpRequest putPart(String name, Object value) {
        if(parts == null) parts = new HashMap<String, Object>();
        parts.put(name, value);
        return this;
    }
    
    /**
     * 获取混合体参数
     * @param name 混合体参数名
     * @return  混合体参数值
     */
    public Object getPart(String name) {
        if(parts == null) return null;
        return parts.get(name);
    }
    
    /**
     * 获取全部混合体参数
     * @return 混合体参数映射
     */
    public Map<String, Object> getParts() {
        return parts;
    }
    
    /**
     * 放置json消息体
     * @param  json数据
     */
    public void putJson(String json){
    	this.json = json;
    }
    
    /**
     * 获取json消息
     * @return Json
     */
    public String getJson() {
		return json;
	}
    
    /**
     * 设置是否信任任何SSL证书和主机
     * @param on 是否信任任何SSL证书和主机
     * @return 请求对象
     */
    public HttpRequest setSSLTrustAll(boolean on) {
        httpsTrustAll = on;
        return this;
    }
    
    /**
     * 获取是否信任任何SSL证书和主机
     * @return 是否信任任何SSL证书和主机
     */
    public boolean getSSLTrustAll() {
        return httpsTrustAll;
    }
    
    /**
     * 设置缓存区大小
     * @param size 缓存区大小
     * @return 请求对象
     * @throws HttpException 缓存区大小小于零
     */
    public HttpRequest setBufferSize(final int size) throws HttpException {
        if(size < 1) throw new HttpException("Size must be greater than zero");
        bufferSize = size;
        return this;
    }

    /**
     * 获取缓冲区大小
     * @return 缓存区大小
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * 设置是否使用解压缩
     * @param uncompress 是否使用解压缩
     * @return 请求对象
     */
    public HttpRequest setUncompress(final boolean uncompress) {
        this.uncompress = uncompress;
        return this;
    }
    
    /**
     * 获取是否使用解压缩
     * @return 是否使用解压缩
     */
    public boolean getUncompress() {
        return uncompress;
    }
    
    /**
     * 设置是否忽略连接关闭异常
     * @param ignore 是否忽略连接关闭异常
     * @return 请求对象
     */
    public HttpRequest setIgnoreCloseExceptions(final boolean ignore) {
        ignoreCloseExceptions = ignore;
        return this;
    }
    
    /**
     * 获取是否忽略连接关闭异常
     * @return 是否忽略连接关闭异常
     */
    public boolean getIgnoreCloseExceptions() {
        return ignoreCloseExceptions;
    }
}