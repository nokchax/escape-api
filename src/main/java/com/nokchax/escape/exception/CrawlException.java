package com.nokchax.escape.exception;

public class CrawlException extends EscapeApiException {

    public CrawlException(String userId) {
        super("Fail to crawl : " + userId);
    }
}
