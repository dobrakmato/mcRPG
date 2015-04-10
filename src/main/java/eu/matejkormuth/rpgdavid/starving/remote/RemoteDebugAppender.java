package eu.matejkormuth.rpgdavid.starving.remote;

import io.netty.channel.ChannelHandlerContext;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RemoteDebugAppender {

    private List<WeakReference<ChannelHandlerContext>> handlers;
    private Object lock = new Object();

    public RemoteDebugAppender() {
        this.handlers = new ArrayList<>();
    }

    public void sendAsync(Object obj) {
        // TODO: Invoke from other thread.
        synchronized (lock) {
            this.send0(obj);
        }
    }

    private void send0(Object obj) {
        // Send to all clients.
        for (WeakReference<ChannelHandlerContext> ctx : handlers) {
            if (ctx.get() != null) {
                ctx.get().writeAndFlush(obj);
            }
        }
    }

    public void addHandler(ChannelHandlerContext ctx) {
        synchronized (lock) {
            this.handlers.add(new WeakReference<ChannelHandlerContext>(ctx));

            // Clean up handlers.
            for (Iterator<WeakReference<ChannelHandlerContext>> iterator = handlers.iterator(); iterator.hasNext();) {
                WeakReference<ChannelHandlerContext> handler = iterator.next();
                if (handler.get() == null) {
                    iterator.remove();
                }
            }
        }
    }
}
