package com.lts.nio.loop;

import com.lts.nio.processor.NioProcessor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Robert HG (254963746@qq.com) on 1/24/16.
 */
public class FixedNioSelectorLoopPool implements NioSelectorLoopPool {

    private final NioSelectorLoop[] pool;

    private final AtomicInteger nextIndex = new AtomicInteger();

    public FixedNioSelectorLoopPool(final int size, String prefix, NioProcessor processor) {
        if (size <= 0) {
            throw new IllegalArgumentException("We can't create a pool with no Selectorloop in it");
        }

        pool = new NioSelectorLoop[size];

        for (int i = 0; i < size; i++) {
            pool[i] = new NioSelectorLoop(prefix + "-NioSelectorLoop-I/O-" + i, processor);
        }
    }

    @Override
    public NioSelectorLoop getSelectorLoop() {
        return pool[Math.abs(nextIndex.incrementAndGet() % pool.length)];
    }

}
