package com.esb.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.esb.redis.key.UserKey;
import com.esb.user.User;
import com.esb.user.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
    private UserService userService;


    @Test
    public void get() {
    	String prefix = UserKey.getById.getPrefix();
    	log.info(prefix);
        final User user = userService.getById(UserKey.getById,18181818181L);
        log.info("[getById] - [{}]", user);
       
    }
}
