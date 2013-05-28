package com.anusha.portfolio.codelab;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class BeanPictureService extends BeanBase {
	public DAOPicture getPictureDAO() {
		return new DAOPictureDataSource();
	}
	// Business Methods
	
	/**
	   * Return all the pictures
	   * @param kind : of kind "Picture"
	   * @return  Pictures
	   */
	  public Iterable<Entity> getAllPictures() {
	    return getPictureDAO().getAllPictures();
	  }

	  /**
		 * get Picture with picture name
		 * 
		 * @param pictureName
		 *          : get pictureName
		 * @return picture entity
		 */
		private Iterable<Entity> getSinglePicture(String pictureName) {
			return getPictureDAO().getSinglePicture(pictureName);
		}
		
		/**
		 * get picture with picture key
		 * 
		 * @param pictureKey
		 *          : get pictureKey
		 * @return picture entity
		 */
		public Entity getSinglePictureEntity(String pictureKey) {
			return getPictureDAO().getSinglePictureEntity(pictureKey);
		}
		
		
  /**
   * Create or Update Picture
   * @return  true or false
   */
  public boolean createOrUpdatePicture(DTOPicture pictureDTO) {
	  return getPictureDAO().createOrUpdatePicture(pictureDTO);
  }

  	public void deletePictures(List<Key> keys) {
		getPictureDAO().deletePictures(keys);
	}
  
  /**
   * Delete picture entity
   * @param pictureKey : picture to be deleted
   * @return status string
   */
  public String deletePicture(String pictureKey) {
	  getPictureDAO().deletePicture(pictureKey);
	  return "Picture - \""+pictureKey+"\"" + " is deleted successfully";
  }
  
  /**
   * Get all pictures for a sub menu
   * @param subMenuKey : name of the sub menu
   * @return list of pictures
   */
  
  public Iterable<Entity> getPicturesForSubMenu(String subMenuKey) {
	  return getPictureDAO().getPicturesForSubMenu(subMenuKey);
  }	
  
}