package com.esb.user;

import java.util.List;

import org.mm.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esb.redis.key.UserKey;
import com.github.pagehelper.PageInfo;

import lombok.extern.log4j.Log4j2;





@Log4j2
@RestController
@RequestMapping("/user/*")
public class UserController {
	@SuppressWarnings("all")
	@Autowired
    UserService userService;

    
    @GetMapping("pageList")
    public PageInfo<User> PageList(
            @RequestParam(value="page", required=false, defaultValue="1") int page,
            @RequestParam(value="page-size", required=false, defaultValue="5") int pageSize){
        List<User> result = userService.pageList(page, pageSize);
        // PageInfo包装结果，返回更多分页相关信息
        PageInfo<User> pi = new PageInfo<User>(result);
        return pi;
    }
    
    @GetMapping("list")
    public List<User> list(){
        List<User> result = userService.list();
        return result;
    }
    
    @PostMapping("saveUser")
    public void saveUser(@RequestBody User user) {
    	userService.saveUser(user);
    }

    @GetMapping("getById")
    public User getById(
            @RequestParam(value="id", required=true) String id){
        User user = userService.getById(UserKey.getById,Long.valueOf(id));
        return user;
    }
    
    @RequestMapping("info")
    public Result<User> info(@RequestBody User user) {
        return Result.success(user);
    }
}
