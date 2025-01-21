package com.jsp.SaveImage.ServiceImpl;



import com.jsp.SaveImage.Service.UsersService;
import com.jsp.SaveImage.entity.Users;
import com.jsp.SaveImage.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UsersRepository userRepository;

	@Override
	public Users register(Users user) {
		return userRepository.save(user);
	}

	@Override
	public boolean login(Users user) {
		if (user.getEmail()!=null && user.getPassword()!=null) {
			Users userDb = userRepository.findByEmail(user.getEmail()).orElse(null);
			if(userDb !=null && user.getPassword().equals(userDb.getPassword())) {
				return true;
			}
			
		}
		return false;
	}

	@Override
	public Users getUserByEmailAndPassword(Users user) {
		Users userDb = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).orElse(null);          
		if(userDb!=null) {
			return userDb;
		}
		return null;
	}

	@Override
	public boolean getUserByEmail(String email) {
		Users user= userRepository.findByEmail(email).orElse(null);
		if(user!=null) {
			return true;
		}
		return false;
	}

}
