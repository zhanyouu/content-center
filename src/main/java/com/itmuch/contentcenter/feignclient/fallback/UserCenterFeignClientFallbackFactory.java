package com.itmuch.contentcenter.feignclient.fallback;

import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserCenterFeignClientFallbackFactory implements FallbackFactory {

    @Override
    public UserCenterFeignClient create(Throwable throwable) {
        return new UserCenterFeignClient() {
            @Override
            public UserDTO findById(Integer id) {
                log.warn("降级或者限流了",throwable);
                UserDTO userDTO = new UserDTO();
                userDTO.setWxNickname("默认用户");
                return userDTO;            }
        };
    }
}
