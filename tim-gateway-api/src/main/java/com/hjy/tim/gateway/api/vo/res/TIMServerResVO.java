package com.hjy.tim.gateway.api.vo.res;

import com.hjy.tim.common.pojo.RouteInfo;

import java.io.Serializable;

/**
 * @since JDK 1.8
 */
public class TIMServerResVO implements Serializable {

    private String ip;
    private Integer timServerPort;
    private Integer httpPort;

    public TIMServerResVO(RouteInfo routeInfo) {
        this.ip = routeInfo.getIp();
        this.timServerPort = routeInfo.getTimServerPort();
        this.httpPort = routeInfo.getHttpPort();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getTimServerPort() {
        return timServerPort;
    }

    public void setTimServerPort(Integer timServerPort) {
        this.timServerPort = timServerPort;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }
}
