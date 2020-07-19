package com.vintrace.exam.filter;

import com.vintrace.exam.properties.LogProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
    );

    private final LogProperties logProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (isAsyncDispatch(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            doFilterWrapped(wrapRequest(httpServletRequest), wrapResponse(httpServletResponse), filterChain);
        }
    }

    protected void doFilterWrapped(ContentCachingRequestWrapper request,
                                   ContentCachingResponseWrapper response,
                                   FilterChain filterChain) throws IOException, ServletException {
        try {
            beforeRequest(request);
            filterChain.doFilter(request, response);
        } finally {
            afterRequest(request, response);
            response.copyBodyToResponse();
        }
    }

    protected void beforeRequest(ContentCachingRequestWrapper request) {
        if (log.isInfoEnabled()) {
            logRequestHeader(request, logProperties.getBeforeRequestPrefix());
        }
    }

    protected void afterRequest(ContentCachingRequestWrapper request,
                                ContentCachingResponseWrapper response) {
        if (log.isInfoEnabled()) {
            logRequestBody(request, logProperties.getAfterRequestPrefix());
            logResponse(response, logProperties.getAfterResponsePrefix(),
                !request.getRequestURI().contains("/actuator"));
        }
    }

    protected void logRequestHeader(ContentCachingRequestWrapper request, String prefix) {
        String queryString = request.getQueryString();
        if (queryString == null) {
            log.info("{} {} {}", prefix, request.getMethod(), request.getRequestURI());
        } else {
            log.info("{} {} {}?{}", prefix, request.getMethod(), request.getRequestURI(), queryString);
        }
    }

    protected void logRequestBody(ContentCachingRequestWrapper request, String prefix) {
        byte[] content = request.getContentAsByteArray();

        if (content.length > 0) {
            logContent(content, request.getContentType(), request.getCharacterEncoding(), null, prefix);
        } else {
            logRequestHeader(request, logProperties.getAfterRequestPrefix());
        }
    }

    protected void logResponse(ContentCachingResponseWrapper response, String prefix, Boolean isContentIncluded) {
        int status = response.getStatus();
        byte[] content = response.getContentAsByteArray();

        if (content.length > 0 && isContentIncluded) {
            logContent(
                    content,
                    response.getContentType(),
                    response.getCharacterEncoding(),
                    HttpStatus.valueOf(status),
                    prefix
            );
        } else {
            log.info("{} Status={};",prefix,status);
        }
    }

    protected void logContent(byte[] content, String contentType, String contentEncoding,
                              HttpStatus status, String prefix) {
        MediaType mediaType = MediaType.valueOf(contentType);
        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
        if (visible) {
            try {
                String contentString = new String(content, contentEncoding);
                log.info("{} Status={}; Content={};",
                        prefix,
                        status != null ? status.value() : "",
                        contentString.replaceAll("[\n\r]", ""));
            } catch (UnsupportedEncodingException exception) {
                log.info("{} Status={}; [{} bytes content]",prefix,status,content.length);
            }
        } else {
            log.info("{} Status={}; [{} bytes content]",prefix,status,content.length);
        }
    }

    private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }
}
