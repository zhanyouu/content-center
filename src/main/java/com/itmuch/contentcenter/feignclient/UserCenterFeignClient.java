package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import com.itmuch.contentcenter.feignclient.fallback.UserCenterFeignClientFallback;
import com.itmuch.contentcenter.feignclient.fallback.UserCenterFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//@FeignClient(name = "user-center",configuration = UserCenterFeignConfiguration.class)
@FeignClient(name = "user-center",
//        fallback = UserCenterFeignClientFallback.class,
        //可以打出异常日志
        fallbackFactory = UserCenterFeignClientFallbackFactory.class

)
public interface UserCenterFeignClient {
    @GetMapping("users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
