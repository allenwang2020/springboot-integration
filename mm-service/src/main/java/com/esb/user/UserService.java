package com.esb.user;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.esb.vo.LoginVo;

public interface UserService {
	 
	public User getUserById(long userId);

	public List<User> pageList(int page, int pageSize);
	
	public List<User> list();
	
	public void saveUser(User user);
	
	public User getById(long id);
	
	public boolean updatePassword(String token, long id, String formPass);
	
	public String login(HttpServletResponse response, LoginVo loginVo);
	
	public void addCookie(HttpServletResponse response, String token, User user);
	
	// public User getByToken(HttpServletResponse response, String token);
	
	
	
	
}
