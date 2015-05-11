package cn.edu.hhu.reg.common.http;

import java.io.IOException;
import java.util.concurrent.Callable;

public abstract class HttpOperation<V> implements Callable<V> {

    protected abstract V run() throws HttpException, IOException;

    protected abstract void done() throws IOException;

    public V call() throws HttpException {
        boolean thrown = false;
        try {
            return run();
        } catch (HttpException e) {
            thrown = true;
            throw e;
        } catch (IOException e) {
            thrown = true;
            throw new HttpException(e);
        } finally {
            try {
                done();
            } catch (IOException e) {
                if(!thrown) throw new HttpException(e);
            }
        }
    }
}