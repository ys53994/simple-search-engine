package com.ys53994.sse.client.cli.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CliUtil {

    /*
     * Wrapper for CLI  in case if need logging in future.
     */
    public static void println(String value) {
        System.out.println(value);
    }
}
