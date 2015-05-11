package cn.edu.hhu.reg.common.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

public class HttpTransfer {
    private final HttpRequest request;
    
    private final HttpURLConnection connection;
    
    private HttpOutputStream output;
    
    private String  boundary;
    private boolean multipart;
    private boolean form;
    
    private HttpTransfer(HttpRequest request) throws HttpException {
        this.request = request;
        try {
            connection = (HttpURLConnection) request.getUrl().openConnection();
            connection.setRequestMethod(request.getMethod());
            connection.setConnectTimeout(request.getConnectTimeout());
            connection.setReadTimeout(request.getReadTimeout());
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
    
    /**
     * 执行一个HTTP请求，获取响应对象
     * @param request HTTP请求对象
     * @return HTTP响应对象
     * @throws HttpException
     */
    public static HttpResponse execute(HttpRequest request) throws HttpException {
        HttpTransfer transfer = new HttpTransfer(request);
        transfer.start();
        HttpResponse response = transfer.end();
        return response;
    }
    
    /**
     * 开启请求流并写入HTTP请求内容
     * @throws HttpException
     */
    private void start() throws HttpException {
        if(request.getSSLTrustAll()) trustAllSSL();
        headers(request.getHeaders());
        header("Charset", request.getCharset());
        if(request.getMethod().equals(HttpRequest.METHOD_POST)){
        	forms(request.getForms());
        	parts(request.getParts());
        	json(request.getJson());
        }
    }

	/**
     * 关闭请求流等待响应流并产生响应对象
     * @return HTTP响应对象
     * @throws HttpException
     */
    private HttpResponse end() throws HttpException {
        int code;
        String message;
        String charset;
        int contentLength;
        InputStream stream;
        byte[] bytes;
        try {
            closeOutput();
            code = connection.getResponseCode();
            message = connection.getResponseMessage();
            charset = getParameter(connection.getHeaderField("Content-Type"), "charset");
            contentLength = connection.getHeaderFieldInt("Content-Length", -1);
            if(code < HttpURLConnection.HTTP_BAD_REQUEST) {
                stream = connection.getInputStream();
            } else {
                stream = connection.getErrorStream();
                if(stream == null) {
                    stream = connection.getInputStream();
                }
            }
            if(request.getUncompress() &&
                    "gzip".equals(connection.getHeaderField("Content-Encoding"))) {
                stream = new GZIPInputStream(stream);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            final byte[] buffer = new byte[request.getBufferSize()];
            int read;
            while((read = stream.read(buffer)) != -1) os.write(buffer, 0, read);
            bytes = os.toByteArray();
        } catch(IOException e) {
            throw new HttpException(e);
        }
        InputStream bis = new ByteArrayInputStream(bytes);
        HttpResponse response = new HttpResponse(request, bis);
        response.setCode(code);
        response.setMessage(message);
        response.setCharset(charset);
        response.setContentLength(contentLength);
        connection.disconnect();
        return response;
    }
    
    /**
     * 写入请求头参数
     * @param headers 请求头参数映射
     */
    private void headers(final Map<String, String> headers) {
        if(headers != null && !headers.isEmpty()) {
            for(Entry<String, String> header : headers.entrySet()) {
                header(header.getKey(), header.getValue());
            }
        }
    }
    
    /**
     * 写入请求头参数
     * @param name 请求头参数名
     * @param value 请求头参数值
     */
    private void header(final String name, final String value) {
        connection.setRequestProperty(name, value);
    }
    
    private void json(String json) throws HttpException {
    	if("".equals(json)) return;
    	contentType("application/json");
    	try {
    		openOutput();//开启post输出流
    		output.write(json);
    	} catch (IOException e) {
    		throw new HttpException(e);
    	}
	}
    
    /**
     * 写入表单内容
     * @param forms 表单映射
     * @param charset 编码
     * @throws HttpException
     */
    private void forms(final Map<String, Object> forms) throws HttpException {
        if(forms != null && !forms.isEmpty()) {
            for(Entry<String, Object> entry : forms.entrySet()) {
                form(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 写入表单内容
     * @param name 表单参数名
     * @param value 表单参数值
     * @param charset 编码
     * @throws HttpException
     */
    private void form(final String name, final Object value) throws HttpException {
        final boolean first = !form;
        if(first) {
            contentType("application/x-www-form-urlencoded");
            form = true;
        }
        try {
            openOutput();//开启post输出流
            if(!first) output.write('&');
            if(value != null) {
            	output.write(name);
            	output.write('=');
                output.write(value.toString());
            }
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    /**
     * 写入混合体内容
     * @param parts 混合体映射(String,File,InputStream)
     * @throws HttpException
     */
    private void parts(final Map<String, Object> parts) throws HttpException {
        if(parts != null && !parts.isEmpty()) {
            for(Entry<String, Object> entry : parts.entrySet()) {
                Object value = entry.getValue();
                if(value instanceof String) {
                    part(entry.getKey(), (String) value);
                } else if(value instanceof File) {
                    try {
                        InputStream is = new BufferedInputStream(new FileInputStream((File) value));
                        part(entry.getKey(), is);
                    } catch (IOException e) {
                        throw new HttpException(e);
                    }
                } else if(value instanceof InputStream) {
                    part(entry.getKey(), (InputStream) value);
                } 
            }
        }
    }

    /**
     * 写入混合体内容
     * @param name 参数名
     * @param part 混合体内容
     * @throws HttpException
     */
    private void part(final String name, final String part) throws HttpException {
        try {
            startPart(name);
            output.write(part);
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
    
    /**
     * 写入混合体内容
     * @param name 参数名
     * @param part 混合体输入流
     * @throws HttpException
     */
    private void part(final String name, final InputStream part) throws HttpException {
        try {
            startPart(name);
            copy(part, output);
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
    
    /**
     * 写入混合体开头
     * @throws IOException
     */
    private void startPart(final String name) throws IOException {
        if(!multipart) {
            boundary = "---------linkit----" + (new Random()).nextLong();
            multipart = true;
            header("Connection", "Keep-Alive");
            contentType("multipart/form-data; boundary=" + boundary);
            openOutput();
            output.write("--" + boundary + "\r\n");
        } else {
        	openOutput();
        	output.write("\r\n--" + boundary + "\r\n");
        }
        output.write("Content-Disposition: form-data; "+
        "name=\"file\";filename=\""+
        name +"\"\r\n\r\n");
    }
    
    /**
     * 打开请求流
     * @throws IOException
     */
    private void openOutput() throws IOException {
        if(output != null) return;
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        String charset = getParameter(connection.getRequestProperty("Content-Type"), "charset");
        if(charset == null) charset = "UTF-8";
        output = new HttpOutputStream(connection.getOutputStream(), charset,
                request.getBufferSize());
    }
    
    /**
     * 关闭请求流
     * @throws IOException
     */
    private void closeOutput() throws IOException {
        if(output == null) return;
        if(multipart) output.write("\r\n--" + boundary + "--\r\n");
        if(request.getIgnoreCloseExceptions()) {
            try {
                output.close();
            } catch (IOException ignored) {}
        } else {
            output.close();
        }
        output = null;
    }
    
    
    /**
     * 输入流转入输出流
     * @param input 输入流
     * @param output 输出流
     * @throws HttpException 
     */
    private void copy(final InputStream input, final OutputStream output) throws HttpException {
        new HttpCloseOperation<InputStream>(input, request.getIgnoreCloseExceptions()) {
            @Override
            public InputStream run() throws IOException {
                final byte[] buffer = new byte[request.getBufferSize()];
                int read;
                while((read = input.read(buffer)) != -1) output.write(buffer, 0, read);
                return input;
            }
        }.call();
    }
    
    /**
     * 解析 响应头 参数
     * @param value 响应头内容
     * @param parameter 需要获取的参数名
     * @return
     */
    private String getParameter(final String value, final String parameter) {
        if(value == null || value.length() == 0) return null;

        final int length = value.length();
        int start = value.indexOf(';') + 1;
        if(start == 0 || start == length) return null;

        int end = value.indexOf(';', start);
        if(end == -1) end = length;

        while(start < end) {
            int nameEnd = value.indexOf('=', start);
            if(nameEnd != -1 && nameEnd < end &&
                    parameter.equals(value.substring(start, nameEnd).trim())) {
                String paramValue = value.substring(nameEnd + 1, end).trim();
                int valueLength = paramValue.length();
                if(valueLength != 0) {
                    if (valueLength > 2 && '"' == paramValue.charAt(0) &&
                            '"' == paramValue.charAt(valueLength - 1)) {
                        return paramValue.substring(1, valueLength - 1);
                    } else {
                        return paramValue;
                    }
                }
            }
            start = end + 1;
            end = value.indexOf(';', start);
            if(end == -1) end = length;
        }
        return null;
    }
    
    /**
     * 设置 请求头 内容类型
     * @param value 内容类型值
     * @return
     */
    private void contentType(final String value) {
            header("Content-Type", value);
    }

    /**
     * 信任任何SSL证书和主机
     * @throws HttpException 
     */
    private void trustAllSSL() throws HttpException {
        if(connection instanceof HttpsURLConnection) {
            HttpsURLConnection c = (HttpsURLConnection) connection;
            c.setSSLSocketFactory(HttpSSLSocketFactory.getAllTrustedFactory());
            c.setHostnameVerifier(HttpSSLSocketFactory.getAllTrustedVerifier());
        }
    }
    
    
    /**
     * 处理地址查询参数
     * @param url The base url.
     * @param params The parameters map.
     * @param check Keep the url is right.
     * @return 
     */
    public static URL mapUrl(final String url, final Map<?, ?> params, boolean check)
            throws HttpException {
        final StringBuilder result = new StringBuilder(url);

        if(url.indexOf(':') + 2 == url.lastIndexOf('/')) result.append('/');

        if(params != null && !params.isEmpty()) {
            final int queryStart    = url.indexOf('?');
            final int lastChar      = result.length() - 1;
            if(queryStart == -1) {
                result.append('?');
            } else if (queryStart < lastChar && url.charAt(lastChar) != '&') {
                result.append('&');
            }
            
            Iterator<?> iterator = params.entrySet().iterator();
            Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
            result.append(entry.getKey().toString());
            result.append('=');
            Object value = entry.getValue();
            if(value != null) result.append(value);
            while(iterator.hasNext()) {
                result.append('&');
                entry = (Entry<?, ?>) iterator.next();
                result.append(entry.getKey().toString());
                result.append('=');
                value = entry.getValue();
                if(value != null) result.append(value);
            }
        }
        
        try {
            URL parsed = new URL(result.toString());
            String host = parsed.getHost();
            int port = parsed.getPort();
            if(port != -1) host = host + ':' + Integer.toString(port);
            return new URI(parsed.getProtocol(), host, parsed.getPath(), parsed.getQuery(),
                    null).toURL();
        } catch (IOException e) {
            throw new HttpException(e);
        } catch (URISyntaxException e) {
			throw new HttpException(e);
		}
    }

}