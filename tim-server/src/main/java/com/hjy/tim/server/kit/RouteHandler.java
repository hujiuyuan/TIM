package com.hjy.tim.server.kit;

import com.hjy.tim.server.config.AppConfiguration;
import com.hjy.tim.server.util.SessionSocketHolder;
import com.hjy.tim.common.core.proxy.ProxyManager;
import com.hjy.tim.common.pojo.TIMUserInfo;
import com.hjy.tim.gateway.api.RouteApi;
import com.hjy.tim.gateway.api.vo.req.ChatReqVO;
import io.netty.channel.socket.nio.NioSocketChannel;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @since JDK 1.8
 */
@Component
public class RouteHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(RouteHandler.class);

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private AppConfiguration configuration;

    /**
     * 用户下线
     *
     * @param userInfo
     * @param channel
     * @throws IOException
     */
    public void userOffLine(TIMUserInfo userInfo, NioSocketChannel channel) throws IOException {
        if (userInfo != null) {
            LOGGER.info("Account [{}] offline", userInfo.getUserName());
            SessionSocketHolder.removeSession(userInfo.getUserId());
            //清除路由关系
            clearRouteInfo(userInfo);
        }
        SessionSocketHolder.remove(channel);

    }


    /**
     * 清除路由关系
     *
     * @param userInfo
     * @throws IOException
     */
    public void clearRouteInfo(TIMUserInfo userInfo) {
        RouteApi routeApi = new ProxyManager<>(RouteApi.class, configuration.getGatewayUrl(), okHttpClient).getInstance();
        Response response = null;
        ChatReqVO vo = new ChatReqVO(userInfo.getUserId(), userInfo.getUserName());
        try {
            response = (Response) routeApi.offLine(vo);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        } finally {
            response.body().close();
        }
    }

}
