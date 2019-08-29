package com.mgs.dlt.request.base;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *   2018/4/8.
 */
@Getter
public class Requestor {
    private static final Logger LOG = LoggerFactory.getLogger(Requestor.class);

    private String invoker = "merchant";
    private String operatorName = "merchant";
    private Integer userId = 1;
    private String languageType = "CN";
    private volatile String opClientIP = null;

    public Requestor() {
        if (null == opClientIP) {
            synchronized (this) {
                if (null == opClientIP) {
                    try {
                        opClientIP = InetAddress.getLocalHost().getHostAddress();//获得本机IP
                    } catch (UnknownHostException e) {
                        LOG.error("获取本机IP失败", e);
                    }
                }
            }
        }
    }
}
