package com.hjy.tim.gateway.service;

import com.hjy.tim.gateway.kit.NetAddressIsReachable;
import com.hjy.tim.common.enums.StatusEnum;
import com.hjy.tim.common.exception.TIMException;
import com.hjy.tim.common.pojo.RouteInfo;
import com.hjy.tim.gateway.cache.ServerCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @since JDK 1.8
 */
@Component
public class CommonBizService {
    private static Logger logger = LoggerFactory.getLogger(CommonBizService.class);


    @Autowired
    private ServerCache serverCache;

    /**
     * check ip and port
     *
     * @param routeInfo
     */
    public void checkServerAvailable(RouteInfo routeInfo) {
        boolean reachable = NetAddressIsReachable.checkAddressReachable(routeInfo.getIp(), routeInfo.getTimServerPort(), 1000);
        if (!reachable) {
            logger.error("ip={}, port={} are not available", routeInfo.getIp(), routeInfo.getTimServerPort());

            // rebuild cache
            serverCache.rebuildCacheList();

            throw new TIMException(StatusEnum.SERVER_NOT_AVAILABLE);
        }

    }
}
