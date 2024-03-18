package edu.miu.cs.cs544.integration.logging;

public class LoggerImpl implements Logger {
    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}
