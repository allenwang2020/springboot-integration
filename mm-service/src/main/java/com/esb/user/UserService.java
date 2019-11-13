package com.esb.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.esb.redis.key.KeyPrefix;
import com.esb.vo.LoginVo;

public interface UserService {
	 
	public User getUserById(long userId);

	public List<User> pageList(int page, int pageSize);
	
	public List<User> list();
	
	public void saveUser(User user);
	
	public User getById(KeyPrefix prefix,long id);
	
	public boolean updatePassword(KeyPrefix prefix,String token, long id, String formPass);
	
	public Map<String,Object> login(LoginVo loginVo);
	
	public void addCookie(String token, User user);
	
    public User getByToken(String token);
	
	
	
	
}
