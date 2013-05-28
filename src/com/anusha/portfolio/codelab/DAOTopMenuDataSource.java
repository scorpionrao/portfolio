package com.anusha.portfolio.codelab;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * This Service class handles all Top Menu entity CRUD 
 * operations calls from the Servlet
 * 
 * Similarity to Jboss tutorial
 * UtilInterface		<contactsDAO> 	CarDAO interface
 * TopMenuServlet		<callingObject> ControllerServlet
 */

public class DAOTopMenuDataSource extends DAOBase implements DAOTopMenu {

	// Business Methods
	
	  /**
	   * Return all the top menus
	   * @return  TopMenus
	   */
	  public Iterable<Entity> getAllTopMenus() {
		return new UtilDataSourceAPI().listEntities(KIND_TOPMENU, null, null);
	  }

	
	/**
	   * Get top menu entity
	   * @param topMenuName : name of the top menu
	   * @return: top menu entity
	   */
	  public Entity getTopMenu(String topMenuName) {
		Key key = KeyFactory.createKey(KIND_TOPMENU,topMenuName);
	  	return new UtilDataSourceAPI().findEntity(key);
	  }
	  
	
	/**
	   * Create or Update Top Menu
	   * @param subMenuName : name of the top menu
	   * @param topMenuIncludeExclude : include or exclude the top menu from the portfolio
	   * @param topMenuDescription : description of the top menu
	   * @return  created or updated Top Menu
	   */
	  public boolean createOrUpdateTopMenu(DTOTopMenu topMenuDTO) {
		  try {
		  	Entity topMenu = getTopMenu(topMenuDTO.getTopMenuName());
		  	if (topMenu == null) {
		  	  topMenu = new Entity(KIND_TOPMENU, topMenuDTO.getTopMenuName());
		  	}
		  	topMenu.setProperty(PROPERTY_INCLUDE_EXCLUDE, topMenuDTO.getTopMenuIncludeExclude());
		  	topMenu.setProperty(PROPERTY_DESCRIPTION, topMenuDTO.getTopMenuDescription());
		  	new UtilDataSourceAPI().persistEntity(topMenu);
		  	return true;
		  } catch (Exception e) {
			 e.printStackTrace();
			 return false;
		  }
	  }
	  
	public void deleteTopMenus(List<Key> keys) {
		new UtilDataSourceAPI().deleteEntity(keys);
	}
	
	/**
	   * Delete top menu entity
	   * @param topMenuKey : top menu to be deleted
	   * @return status string
	   */
	  public String deleteTopMenu(String topMenuKey) {
		  Key key = KeyFactory.createKey(KIND_TOPMENU,topMenuKey);	 
		  new UtilDataSourceAPI().deleteEntity(key);
		  return "Top Menu - \""+topMenuKey+"\"" + " is deleted successfully";
		  
	  }
	
}

