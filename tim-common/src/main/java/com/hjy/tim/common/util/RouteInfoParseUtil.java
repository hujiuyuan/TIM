package com.hjy.tim.common.util;

import com.hjy.tim.common.enums.StatusEnum;
import com.hjy.tim.common.exception.TIMException;
import com.hjy.tim.common.pojo.RouteInfo;

/**
 *
 * @since JDK 1.8
 */
public class RouteInfoParseUtil {

    public static RouteInfo parse(String info){
        try {
            String[] serverInfo = info.split(":");
            RouteInfo routeInfo =  new RouteInfo(serverInfo[0], Integer.parseInt(serverInfo[1]),Integer.parseInt(serverInfo[2])) ;
            return routeInfo ;
        }catch (Exception e){
            throw new TIMException(StatusEnum.VALIDATION_FAIL) ;
        }
    }
}
