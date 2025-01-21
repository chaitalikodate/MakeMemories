package com.jsp.SaveImage.ServiceImpl;

import java.sql.Blob;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.SaveImage.Service.ImagesService;
import com.jsp.SaveImage.entity.Images;
import com.jsp.SaveImage.repository.ImagesRepository;



@Service
public class ImagesServiceImpl implements ImagesService{
	
	@Autowired
    private ImagesRepository imageRepository;

	@Override
	public Images create(Images image) {
		return imageRepository.save(image);
	}

	@Override
	public List<Images> viewAll(long userId) {
		return (List<Images>) imageRepository.findAllById(userId);
	}

	@Override
	public Images viewById(long id) {
		 return imageRepository.findById(id).get();
	}

	@Override
	public void updateImage(long imageId, String note, Blob image) {
		 Images existingImage;
			try {
				existingImage = imageRepository.findById(imageId)
				            .orElseThrow(() -> new Exception("Image not found"));
				existingImage.setImage(image);
				 existingImage.setNote(note);
			        imageRepository.save(existingImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public boolean deleteImage(long imageId) {
		Images image =imageRepository.findById(imageId).orElse(null);
		if(image!=null) {
			imageRepository.delete(image);
			return true;
		}
		return false;
	}

}
