package com.esb.user;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

	
@SuppressWarnings("all")
@Autowired
UserMapper userMapper;

	@Test
	public void test_saveUser() {
		
		User user = new User();
		user.setUserId("4");
		user.setUsername("allenwang");
		user.setPassword("1234");
		user.setPhoneNum("0938735070");
		userMapper.saveUser(user);
	}
	
	@Test
	public void test_list() {
	    //开始进行测试
		assertThat(userMapper.list().size()).isGreaterThan(1);
		
	}
	
}