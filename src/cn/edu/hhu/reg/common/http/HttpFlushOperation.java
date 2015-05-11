package cn.edu.hhu.reg.common.http;

import java.io.Flushable;
import java.io.IOException;

public abstract class HttpFlushOperation<V> extends HttpOperation<V> {

    private final Flushable flushable;

    /**
     * 构建刷写操作对象
     * @param flushable
     */
    protected HttpFlushOperation(final Flushable flushable) {
        this.flushable = flushable;
    }

    @Override
    protected void done() throws IOException {
        flushable.flush();
    }
}