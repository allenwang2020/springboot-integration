package com.esb.user;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.mm.exception.GlobalException;
import org.mm.result.CodeMsg;
import org.mm.util.MD5Util;
import org.mm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.esb.redis.key.KeyPrefix;
import com.esb.redis.key.UserKey;
import com.esb.redis.util.RedisUtil;
import com.esb.vo.LoginVo;
import com.github.pagehelper.PageHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("userService")
@CacheConfig(cacheNames = "user") //指定cache的名字,这里指定了 caheNames，下面的方法的注解里就可以不用指定 value 属性了
public class UserServiceImpl implements UserService {

	@SuppressWarnings("all")
    @Autowired
    UserMapper userMapper;
	
	@Autowired
    RedisUtil redisUtil;
	
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
            PageHelper.orderBy("id ASC "); //进行分页结果的排序
            result = userMapper.list();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }
    
    @Override
    public List<User> list() {
        List<User> result = null;
        try {
            result = userMapper.list();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }
    
    
    
    @Override
    public void saveUser(User user) {
    	try {
    		  userMapper.saveUser(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
      
    }
    
    /**
     * 以下為seckill功能
     */
    
    public static final String COOKIE_NAME_TOKEN = "token";

    @Cacheable(key = "#prefix.prefix + '' + #id.toString()")
    public User getById(KeyPrefix prefix ,long id) {
        User user = userMapper.getById(id);
        return user;
    }

    /**
     * 典型緩存同步場景：更新密碼
     */
    @CachePut(key = "#prefix.prefix + '' + #id.toString()")
    public boolean updatePassword(KeyPrefix prefix ,String token, long id, String formPass) {
    	//取user
        User user = getById(prefix,id);
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新資料庫
        user.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        userMapper.update(user);
       
        return true;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判斷手機號碼是否存在
        User user = getById(UserKey.getById,Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //驗證密碼
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成唯一id作為token
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    /**
     * 將token做為key，用戶訊息做為value 存入redis模擬session
     * 同時將token存入cookie，保存登錄狀態
     */
    @CachePut(key = "#token")
    public void addCookie(HttpServletResponse response, String token, User user) {
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");//設置為網站根目錄
        response.addCookie(cookie);
    }
    
    /**
     * 根據token取得用戶訊息
     */
    @Cacheable(key =  "'userkey'.concat(#token.toString())")
    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String realKey = UserKey.token.getPrefix()+token;
        User user = (User)redisUtil.get(realKey);
        //延長有效期，有效期等于最后一次操作+有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }
    
}