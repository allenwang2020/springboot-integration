package com.esb.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import lombok.extern.log4j.Log4j2;





@Log4j2
@RestController
@RequestMapping("/user/*")
public class UserController {
	@SuppressWarnings("all")
    @Autowired
    UserMapper userMapper;
	@Autowired
    UserService userService;

    @GetMapping("list")
    public List<User> list() {
 
        return userMapper.list();
        
    }

    @GetMapping("list/{username}")
    public List<User> listByUsername(@PathVariable("username") String username) {
        return userMapper.listByUsername(username);
    }

    @GetMapping("get/{username}/{password}")
    public User get(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userMapper.get(username, password);
    }

    @GetMapping("get/bad/{username}/{password}")
    public User getBadUser(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userMapper.getBadUser(username, password);
    }
    
    
    @GetMapping("listUser")
    public PageInfo<User> listUser(
            @RequestParam(value="page", required=false, defaultValue="1") int page,
            @RequestParam(value="page-size", required=false, defaultValue="5") int pageSize){


        List<User> result = userService.listUser(page, pageSize);
        // PageInfo包装结果，返回更多分页相关信息
        PageInfo<User> pi = new PageInfo<User>(result);
        log.info("test");
        return pi;
    }
   

}
