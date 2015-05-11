package cn.edu.hhu.reg.common.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class HttpOutputStream extends BufferedOutputStream {
    public final CharsetEncoder encoder;

    public HttpOutputStream(final OutputStream stream, final String charset, final int bufferSize) {
        super(stream, bufferSize);
        encoder = Charset.forName(charset).newEncoder();
    }

    /**
     * 写入流
     *
     * @param value
     * @return this stream
     * @throws IOException
     */
    public HttpOutputStream write(final String value) throws IOException {
        final ByteBuffer bytes = encoder.encode(CharBuffer.wrap(value));
        super.write(bytes.array(), 0, bytes.limit());
        return this;
    }
}