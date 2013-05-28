package com.anusha.portfolio.codelab;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DAOPictureDataSource extends DAOBase implements DAOPicture {
	// Business Methods
	
	/**
	   * Return all the pictures
	   * @param kind : of kind "Picture"
	   * @return  Pictures
	   */
	  public Iterable<Entity> getAllPictures() {
		return new UtilDataSourceAPI().listEntities(KIND_PICTURE, null, null);
	  }

	  /**
		 * get Picture with picture name
		 * 
		 * @param pictureName
		 *          : get pictureName
		 * @return Iterable<PictureDTO> picture
		 */
		public Iterable<Entity> getSinglePicture(String pictureName) {
			return new UtilDataSourceAPI().listEntities(KIND_PICTURE, PROPERTY_PICTURE_NAME, pictureName);
		}
		
		/**
		 * get Picture with picture key
		 * 
		 * @param pictureKey
		 *          : get pictureKey
		 * @return picture entity
		 */
		public Entity getSinglePictureEntity(String pictureKey) {
		  Iterable<Entity> entities = getSinglePicture(pictureKey);
		  List<Entity> entityList = new ArrayList<Entity>();
		  for (Entity e : entities) {
			  if (e!=null) {
				  entityList.add(e);
			  }
		  }
		  if (!entityList.isEmpty()) {
			  return entityList.remove(0);
		  }
		  return null;
		}
		
		
  /**
   * Create or Update Picture
   * @param pictureName : name of the picture
   * @param pictureIncludeExclude : include or exclude the picture from the portfolio
   * @param pictureDescription : description of the picture
   * @return  created or updated Picture
   */
  public boolean createOrUpdatePicture(DTOPicture pictureDTO) {
	  try {
		Entity subMenu = new DAOSubMenuDataSource().getSingleSubMenuEntity(pictureDTO.getSubMenuName());
	    Entity picture = getSinglePictureEntity(pictureDTO.getPictureName());
		if (picture == null) {
			picture = new Entity(KIND_PICTURE, subMenu.getKey());
		}
	    subMenu.setProperty(PROPERTY_PICTURE_NAME, pictureDTO.getSubMenuName());
	    subMenu.setProperty(PROPERTY_INCLUDE_EXCLUDE, pictureDTO.getPictureIncludeExclude());
	    subMenu.setProperty(PROPERTY_DESCRIPTION, pictureDTO.getPictureDescription());
	    
	    new UtilDataSourceAPI().persistEntity(picture);
	  	return true;
	  } catch (Exception e) {
		 e.printStackTrace();
		 return false;
	  }
  }

  	public void deletePictures(List<Key> keys) {
  		new UtilDataSourceAPI().deleteEntity(keys);
	}
  
  /**
   * Delete picture entity
   * @param pictureKey : picture to be deleted
   * @return status string
   */
  public String deletePicture(String pictureKey) {
	  Key key = KeyFactory.createKey(KIND_PICTURE,pictureKey);	 
	  new UtilDataSourceAPI().deleteEntity(key);
	  return "Picture - \""+pictureKey+"\"" + " is deleted successfully";
  }
  
  /**
   * Get all pictures for a sub menu
   * @param subMenuKey : name of the sub menu
   * @return list of pictures
   */
  
  public Iterable<Entity> getPicturesForSubMenu(String subMenuKey) {
	  return new UtilDataSourceAPI().listEntities(KIND_PICTURE, PROPERTY_SUBMENU_NAME, subMenuKey);
  }
	
}