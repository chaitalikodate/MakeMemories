package com.jsp.SaveImage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jsp.SaveImage.entity.Images;

public interface ImagesRepository extends JpaRepository<Images, Long> {
	
	@Query("select i from Images i where i.user.id=?1 ")
	List<Images> findAllById(long userId);
 	
 	@Query("SELECT i FROM Images i WHERE i.id = :imageId AND i.user.id = :userId")
	Optional<Images> findByIdAndUserId(@Param("imageId") Long imageId, @Param("userId") Long userId);

}
