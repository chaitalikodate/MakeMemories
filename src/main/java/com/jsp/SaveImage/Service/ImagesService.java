package com.jsp.SaveImage.Service;

import java.sql.Blob;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jsp.SaveImage.entity.Images;


@Service
public interface ImagesService {
	
	 public Images create(Images image);
	 
	    public List<Images> viewAll(long UserId);
	    
	    public Images viewById(long id);
	    
		public void updateImage(long imageId, String note, Blob image);
		
		public boolean deleteImage(long imageId);

}
