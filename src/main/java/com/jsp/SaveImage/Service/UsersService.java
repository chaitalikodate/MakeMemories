package com.jsp.SaveImage.Service;

import org.springframework.stereotype.Service;

import com.jsp.SaveImage.entity.Users;

@Service
public interface UsersService {
	public Users register(Users user);
	
	public boolean login(Users user);
	
	public boolean getUserByEmail(String email);
	
	public Users getUserByEmailAndPassword(Users user);

}
