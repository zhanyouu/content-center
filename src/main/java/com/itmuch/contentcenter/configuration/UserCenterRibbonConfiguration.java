package com.itmuch.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

@Configuration
//@RibbonClient(name = "user-center",configuration = RibbonConfiguration.class)
//全局配置
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {
}
