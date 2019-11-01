package com.ddh.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * NettySocketConfig
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/1/19
 */
@Configuration
public class NettySocketConfig {

    @Resource
    private MyProperties myProperties;

    private static final Logger logger = LoggerFactory.getLogger(NettySocketConfig.class);

    @Bean
    public SocketIOServer socketIOServer() {
        /*
         * 创建Socket，并设置监听端口
         */
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        // 设置主机名，默认是0.0.0.0
        // config.setHostname("localhost");
        // 设置监听端口
        config.setPort(myProperties.getSocketPort());
        // 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
        config.setUpgradeTimeout(10000);
        // Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
        config.setPingInterval(myProperties.getPingInterval());
        // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(myProperties.getPingTimeout());
        // 握手协议参数使用JWT的Token认证方案
        config.setAuthorizationListener(data -> {
            // 可以使用如下代码获取用户密码信息
//            String token = data.getSingleUrlParam("token");

//            var token = handshakeData._query.auth_token;
//            if (vfglobal.isLogin > 0 || (token && vfglobal.token_Map.hasOwnProperty(token))) {    //说明存在
//                callback(null, true);
//            } else {
//                if (!token) {                 //不存在token 拦截
//                    callback({data: '不存在token时不能连接'}, false);
//                    vfglobal.MyLog('拦截连接了');
//                } else {
//                    //查询是否存在
//                    vfUser.User.find({"auth_token": token}, function (err, result) {
//                        var user = result[0]; //user这个变量是一个User的实例。
//                        if (user) {
//                            vfglobal.token_Map[token] = user.name;
//                            callback(null, true);
//                            // vfglobal.MyLog('查询后放行 >> 放行连接');
//                        } else {
//                            callback(null, false);      //不存在的token时需要 拦截
//                            // vfglobal.MyLog('查询后拦截 >> 拦截连接了');
//                        }
//                    });
//                }
//            }
            return true;
        });
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
