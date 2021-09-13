package com.itmuch.usercenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.itmuch")
@RunWith(SpringRunner.class)
@SpringBootTest
class UserCenterApplicationTests {

	@Test
	void contextLoads() {
	}

}
