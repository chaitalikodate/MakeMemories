package com.jsp.SaveImage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.SaveImage.entity.Users;


public interface UsersRepository extends JpaRepository<Users, Long>{
	
	Optional<Users> findByEmail(String email);
	
	@Query("select u from Users u where u.email=?1 and u.password=?2")
	Optional<Users> findByEmailAndPassword(String email,String password);
	
	

}
