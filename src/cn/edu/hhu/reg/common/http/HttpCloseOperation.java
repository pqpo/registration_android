package cn.edu.hhu.reg.common.http;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public abstract class HttpCloseOperation<V> extends HttpOperation<V> {

    private final Closeable closeable;

    private final boolean ignoreCloseExceptions;

    /**
     * 构建关闭操作对象
     * @param closeable
     * @param ignoreCloseExceptions
     */
    protected HttpCloseOperation(final Closeable closeable, final boolean ignoreCloseExceptions) {
        this.closeable = closeable;
        this.ignoreCloseExceptions = ignoreCloseExceptions;
    }

    @Override
    protected void done() throws IOException {
        if(closeable instanceof Flushable) ((Flushable) closeable).flush();
        if(ignoreCloseExceptions) {
            try {
                closeable.close();
            } catch (IOException e) {}
        } else {
            closeable.close();
        }
    }
}