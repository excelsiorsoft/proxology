package com.excelsiorsoft.proxology;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.excelsiorsoft.proxology.PassthroughInvocationHandler;

import java.util.Random;

@Ignore
public class PassthroughInvocationHandlerPerformanceTest {

    @Rule
    public final ContiPerfRule rule = new ContiPerfRule();

    public interface RandomNumberGenerator {
        long getNumber();
    }

    private static final Random random = new Random();
    private static final RandomNumberGenerator concreteInstance = random::nextLong;
    private static final RandomNumberGenerator proxiedInstance =
            PassthroughInvocationHandler.proxying(concreteInstance, RandomNumberGenerator.class);

    @Test
    @PerfTest(invocations = 1000, warmUp = 1000)
    public void invokeConcrete() {
        getAMillionRandomLongs(concreteInstance);
    }

    @Test
    @PerfTest(invocations = 1000, warmUp = 1000)
    public void invokeProxied() {
        getAMillionRandomLongs(proxiedInstance);
    }

    private void getAMillionRandomLongs(RandomNumberGenerator generator) {
        for (int i = 0; i < 1_000_000; i++) {
            generator.getNumber();
        }
    }
}
