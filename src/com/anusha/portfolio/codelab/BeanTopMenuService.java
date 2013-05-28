package com.anusha.portfolio.codelab;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * This Service class handles all Top Menu entity CRUD 
 * operations calls from the Servlet
 * 
 * Similarity to Jboss tutorial
 * UtilInterface		<contactsDAO> 	CarDAO interface
 * TopMenuServlet		<callingObject> ControllerServlet
 */

public class BeanTopMenuService extends BeanBase implements DAOTopMenu {
	public DAOTopMenu getTopMenuDAO() {
		return new DAOTopMenuDataSource();
	}
	// Business Methods
	
	  /**
	   * Return all the top menus
	   * @return  TopMenus
	   */
	  public Iterable<Entity> getAllTopMenus() {
		return getTopMenuDAO().getAllTopMenus();
	  }

	
	/**
	   * Get top menu entity
	   * @param topMenuName : name of the top menu
	   * @return: top menu entity
	   */
	  public Entity getTopMenu(String topMenuName) {
		return getTopMenuDAO().getTopMenu(topMenuName);
	  }
	  
	
	/**
	   * Create or Update Top Menu
	   * @param subMenuName : name of the top menu
	   * @param topMenuIncludeExclude : include or exclude the top menu from the portfolio
	   * @param topMenuDescription : description of the top menu
	   * @return  created or updated Top Menu
	   */
	  public boolean createOrUpdateTopMenu(DTOTopMenu topMenuDTO) {
		return getTopMenuDAO().createOrUpdateTopMenu(topMenuDTO);
	  }
	  
	public void deleteTopMenus(List<Key> keys) {
		getTopMenuDAO().deleteTopMenus(keys);
	}
	
	/**
	   * Delete top menu entity
	   * @param topMenuKey : top menu to be deleted
	   * @return status string
	   */
	  public String deleteTopMenu(String topMenuKey) {
		  return getTopMenuDAO().deleteTopMenu(topMenuKey);
	  }
	
	// Web Service methods
	public DTOArrayTopMenu findAllTopMenus() {
		DTOArrayTopMenu topMenuDTOArray = new DTOArrayTopMenu();
		List<Entity> topMenuList = new ArrayList<Entity>();
		for (Entity e : getAllTopMenus()) {
			topMenuList.add(e);
		}
		DTOTopMenu[] topMenus = (DTOTopMenu[]) topMenuList.toArray(new DTOTopMenu[0]);
		topMenuDTOArray.setTopMenus(topMenus);
		return topMenuDTOArray;
	}
	
}
