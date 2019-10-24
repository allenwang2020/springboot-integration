package com.esb.user;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("userService")
public class UserServiceImpl implements UserService {

	@SuppressWarnings("all")
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserById(long userId) {
        return null;
    }

    @Override
    public List<User> pageList(int page, int pageSize) {
        List<User> result = null;
        try {
            // 调用pagehelper分页，采用starPage方式。starPage应放在Mapper查询函数之前
            PageHelper.startPage(page, pageSize); //每页的大小为pageSize，查询第page页的结果
            PageHelper.orderBy("user_id ASC "); //进行分页结果的排序
            result = userMapper.list();
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        return result;
    }
    
    @Override
    public List<User> list() {
        List<User> result = null;
        try {
            result = userMapper.list();
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        return result;
    }
    
    @Override
    public User get(String username,String password) {
    	 User user = new User() ;
    	try {
    		 user =  userMapper.get(username, password);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    	return user;
    }
    
    
    @Override
    public void saveUser(User user) {
    	try {
    		  userMapper.saveUser(user);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
      
    }
}