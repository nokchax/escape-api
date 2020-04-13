package com.nokchax.escape.exception;

public class CrawlException extends EscapeApiException {

    public CrawlException() {
        super("Fail to crawl");
    }

    public CrawlException(String userId) {
        super("Fail to crawl : " + userId);
    }
}
