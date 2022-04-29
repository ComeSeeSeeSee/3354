package com.fastPuter.website.common;

public class FastPuterException extends RuntimeException {

    public FastPuterException() {
    }

    public FastPuterException(String message) {
        super(message);
    }


    public static void fail(String message) {
        throw new FastPuterException(message);
    }

}
