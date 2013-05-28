package com.anusha.portfolio.codelab;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public interface DAOPicture {
	Iterable<Entity> getAllPictures();
	Iterable<Entity> getSinglePicture(String pictureName);
	Entity getSinglePictureEntity(String pictureKey);
	boolean createOrUpdatePicture(DTOPicture pictureDTO);
	void deletePictures(List<Key> keys);
	String deletePicture(String pictureKey);
	Iterable<Entity> getPicturesForSubMenu(String subMenuKey);
}
