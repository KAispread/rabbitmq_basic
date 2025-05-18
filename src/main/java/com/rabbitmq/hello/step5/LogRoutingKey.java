package com.rabbitmq.hello.step5;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogRoutingKey {
    ALL_LOG("log.*"),
    INFO("log.info"),
    WARN("log.warn"),
    ERROR("log.error")
    ;

    public final String pattern;
}
