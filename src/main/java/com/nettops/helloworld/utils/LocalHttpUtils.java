package com.nettops.helloworld.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LocalHttpUtils {

    private final static String TRACE_ID = "TraceId";

    public static void addTraceIdToResponseHeaders(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        if(request.getHeader(TRACE_ID)!=null) {
            response.setHeader(TRACE_ID, request.getHeader(TRACE_ID));
            log.info("Request with \"" + TRACE_ID + "\" {} was executed", request.getHeader(TRACE_ID));
        }
    }

}
