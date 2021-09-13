package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//@FeignClient(value = "user-center",configuration = UserCenterFeignConfiguration.class)
@FeignClient(value = "user-center")
public interface UserCenterFeignClient {
    @GetMapping("users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
