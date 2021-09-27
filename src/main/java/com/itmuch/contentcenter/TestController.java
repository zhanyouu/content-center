package com.itmuch.contentcenter;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import com.itmuch.contentcenter.domain.entity.content.Share;
import com.itmuch.contentcenter.feignclient.TestBaiduFeignClient;
import com.itmuch.contentcenter.sentineltest.TestControllerBlockHandlerClass;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.parser.Entity;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class TestController {
    @Autowired
    private ShareMapper shareMapper;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/test")
    public List<Share> testInsert() {
        Share share = new Share();
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setCover("xxx");
        share.setBuyCount(1);
        shareMapper.insertSelective(share);
        List<Share> shareList = shareMapper.selectAll();
        return shareList;
    }

    @GetMapping("test2")
    public List<ServiceInstance> getInstances() {
        //查询指定服务的所有实例信息
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("user-content");
        return serviceInstanceList;
    }

    @Autowired
    private TestBaiduFeignClient testBaiduFeignClient;
    @Autowired
    private TestService testService;

    @GetMapping("baidu")
    public String baiduIndex() {
        return testBaiduFeignClient.index();
    }

    @GetMapping("/test-a")
    public String testA() {
        testService.common();
        return "test-a";
    }

    @GetMapping("/test-b")
    public String testB() {
        testService.common();
        return "test-b";
    }

    @GetMapping("/test-sentinel-api")
    public String testSentinelAPI(@RequestParam(required = false) String a) {
        Entry entry = null;
        String resourceName = "test-sentinel-api";
        //可以实现调用来源，还可以标记调用
        ContextUtil.enter(resourceName, "test-wfw");
        try {
            //定义资源，监控，保护api（最核心）
            entry = SphU.entry(resourceName);

            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("a内容不能为空");
            }
            return a;
        } catch (BlockException e) {
            e.printStackTrace();
            log.warn("限流或者降级了", e);
            return "限流或者降级了";
        } catch (IllegalArgumentException e) {
            //对我们异常进行统计
            Tracer.trace(e);
            return "参数非法";
        } finally {
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }
    }

    @GetMapping("/test-sentinel-resource")
    @SentinelResource(value = "test-sentinel-api",
            blockHandler = "block",
            blockHandlerClass = TestControllerBlockHandlerClass.class,
            fallback = "fallback")
    public String testSentinelResource(@RequestParam(required = false) String a) {
        if (StringUtils.isBlank(a)) {
            throw new IllegalArgumentException("a can not null");
        }
        return a;
    }

    /**
     * '
     * 处理限流或者降级
     *
     * @param a
     * @param e
     * @return
     */
    public String block(String a, BlockException e) {
        log.warn("限流或者降级了,block", e);
        return "限流或者降级了,block";
    }

    /**
     * 处理降级，sentinel1.6可以throwable
     *
     * @param a
     * @return
     */
    public String fallback(String a) {
        log.warn("限流或者降级了,fallback");
        return "限流或者降级了,fallback";
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/restSentinelTest")
    public UserDTO test(@PathVariable Integer userId) {

        return restTemplate.getForObject("http://user-center/users/{userId}", UserDTO.class, userId);
    }
    @Value("${your.configuration}")
    private String yourConfiguration;
    @GetMapping("/test-config")
    public String testConfiguration(){
        return this.yourConfiguration;
    }
}
