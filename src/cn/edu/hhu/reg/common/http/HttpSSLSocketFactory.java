package cn.edu.hhu.reg.common.http;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpSSLSocketFactory {
    private static SSLSocketFactory AllTrustedFactory;
    private static HostnameVerifier AllTrustedVerier;
    
    public static SSLSocketFactory getAllTrustedFactory() throws HttpException {
        if(AllTrustedFactory == null) {
            final TrustManager[] tm = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
            };
            try {
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tm, new SecureRandom());
                AllTrustedFactory = context.getSocketFactory();
            } catch (GeneralSecurityException e) {
                IOException ioException = new IOException(
                    "Security exception configuring SSL context");
                ioException.initCause(e);
                throw new HttpException(ioException);
            }
        }
        return AllTrustedFactory;
    }
    
    public static HostnameVerifier getAllTrustedVerifier() {
        if(AllTrustedVerier == null) {
            AllTrustedVerier = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
        }
        return AllTrustedVerier;
    }
}