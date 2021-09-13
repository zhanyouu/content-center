package com.itmuch.contentcenter;

import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.entity.content.Share;
import com.itmuch.contentcenter.feignclient.TestBaiduFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private ShareMapper shareMapper;
    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping("/test")
    public List<Share> testInsert(){
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
    public List<ServiceInstance> getInstances(){
        //查询指定服务的所有实例信息
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("user-content");
        return serviceInstanceList;
    }
    @Autowired
    private TestBaiduFeignClient testBaiduFeignClient;
    @GetMapping("baidu")
    public String baiduIndex(){
        return testBaiduFeignClient.index();
    }
}
