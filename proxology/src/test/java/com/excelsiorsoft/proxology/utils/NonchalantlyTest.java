package com.excelsiorsoft.proxology.utils;

import org.junit.Test;

import com.excelsiorsoft.proxology.utils.Nonchalantly;

import java.io.IOException;

public class NonchalantlyTest {

    @Test(expected = OutOfMemoryError.class)
    public void passesOnError() {
        Nonchalantly.invoke(() -> { throw new OutOfMemoryError(); });
    }

    @Test(expected = IllegalStateException.class)
    public void doesNotAttemptNonsensicalConversion() throws IOException {
        Nonchalantly.<Void, IOException>invoke(() -> {
            throw new IllegalStateException();
        });
    }

}
