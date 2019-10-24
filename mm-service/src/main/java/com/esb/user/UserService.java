package com.esb.user;

import java.util.List;

public interface UserService {
	 
	public User getUserById(long userId);

	public List<User> pageList(int page, int pageSize);
	
	public List<User> list();
	
	public void saveUser(User user);
	
	public User get(String username,String password);
	
}
