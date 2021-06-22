package com.example.gatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Data
    public static class Config{
        //Put the configuration properties configuration 정보임
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter=new OrderedGatewayFilter((exchange, chain) -> { //매개 변수를 필터라는 함수 까지 구현 위해 람다로
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging filter baseMessage: -> {}",config.getBaseMessage());
            if(config.isPreLogger()){
                log.info("Logging Pre filter: request id -> {}",request.getId());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isPostLogger()){
                    log.info("Logging Post filter End: request id -> {}",request.getId());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE);
        return filter;
    }
}
