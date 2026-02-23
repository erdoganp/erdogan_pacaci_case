package com.insiderOne.utilities;

import com.insiderOne.base.Driver;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.jetbrains.annotations.Contract;
import org.testng.Assert;


public final class Log {
    private static final int DEFAULT_MATCH_LEVEL = 2;


    private Log() {
        throw new IllegalStateException("Log Utility Class");
    }

    static {
        Configurator.setRootLevel(Level.DEBUG);
    }

    public static <T> void pass(T message) {
        pass(message, DEFAULT_MATCH_LEVEL);
    }

    public static <T> void pass(T message, int level) {
        if (message == null) return;
        Allure.step(message.toString(), Status.PASSED);
    }

    public static <T> void warning(T message) {
        warning(message, DEFAULT_MATCH_LEVEL);
    }

    public static <T> void warning(T message, int level) {
        if (message == null) return;
        Allure.step(message.toString(), Status.BROKEN);
    }

    public static <T> void error(T message) {
        error(message, null, DEFAULT_MATCH_LEVEL);
    }


    public static <T> void error(T message, Throwable e, int level) {
        warning(message, ++level);
        var exceptionMessage = e == null ? "" : "\n\n" + e.getMessage();
        throw new IllegalStateException(message + exceptionMessage, e);
    }

    public static <T> void fail(T message) {
        fail(message, null, DEFAULT_MATCH_LEVEL);
    }

    public static <T> void fail(T message, Throwable e) {
        fail(message, e, DEFAULT_MATCH_LEVEL);
    }

    public static <T> void fail(T message, Throwable e, int level) {
        if (message == null) return;
        Allure.step(message.toString(), Status.FAILED);
        Assert.fail(message.toString(), e);
    }



}
