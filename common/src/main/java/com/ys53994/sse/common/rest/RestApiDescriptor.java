package com.ys53994.sse.common.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestApiDescriptor {

    public static final String VERSION = "v1";

    public static final String BASE_PUBLIC_URL = "/rest/" + VERSION;

    public static final String DOCUMENT_PUBLIC_REST_API = BASE_PUBLIC_URL + "/documents";

    public static final String TOKEN_REQUEST_PARAM = "token";
}
