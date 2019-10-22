package com.esb.user;

import java.util.List;

public interface UserService {
	 
	public User getUserById(long userId);

	public List<User> listUser(int page, int pageSize);
}
