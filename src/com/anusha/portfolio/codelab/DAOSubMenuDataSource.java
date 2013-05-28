package com.anusha.portfolio.codelab;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DAOSubMenuDataSource extends DAOBase implements DAOSubMenu {
	private static final Logger logger = Logger.getLogger(DAOSubMenuDataSource.class.getCanonicalName());
	
	// Business Methods
	
	/**
	   * Return all the sub menus
	   * @param kind : of kind "SubMenu"
	   * @return  SubMenus
	   */
	  public Iterable<Entity> getAllSubMenus() {
		return new UtilDataSourceAPI().listEntities(KIND_SUBMENU, null, null);
	  }

	  /**
		 * get Sub Menu with sub menu name
		 * 
		 * @param subMenuName
		 *          : get subMenuName
		 * @return item entity
		 */
		public Iterable<Entity> getSingleSubMenu(String subMenuName) {
			return new UtilDataSourceAPI().listEntities(KIND_SUBMENU, PROPERTY_SUBMENU_NAME, subMenuName);
		}
		
		/**
		 * get Sub Menu with sub menu key
		 * 
		 * @param subMenuKey
		 *          : get subMenuKey
		 * @return sub menu entity
		 */
		public Entity getSingleSubMenuEntity(String subMenuKey) {
		  Iterable<Entity> entities = getSingleSubMenu(subMenuKey);
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
   * Create or Update Sub Menu
   * @param pictureName : name of the sub menu
   * @param subMenuIncludeExclude : include or exclude the sub menu from the portfolio
   * @param subMenuDescription : description of the sub menu
   * @return  created or updated Sub Menu
   */
  public boolean createOrUpdateSubMenu(DTOSubMenu subMenuDTO) {
	  try {
		logger.log(Level.INFO, "DAOSubMenuDataSource - Parameters of the New Sub Menu : " +
		    subMenuDTO.getSubMenuName()+","+subMenuDTO.getSubMenuIncludeExclude()
		    	+","+subMenuDTO.getSubMenuDescription()+","+subMenuDTO.getTopMenuName());
		Entity topMenu = new DAOTopMenuDataSource().getTopMenu(subMenuDTO.getTopMenuName());
	    Entity subMenu = getSingleSubMenuEntity(subMenuDTO.getSubMenuName());
		if (subMenu == null) {
	  	  subMenu = new Entity(KIND_SUBMENU, topMenu.getKey());
		}
	    subMenu.setProperty(PROPERTY_SUBMENU_NAME, subMenuDTO.getSubMenuName());
	    subMenu.setProperty(PROPERTY_INCLUDE_EXCLUDE, subMenuDTO.getSubMenuIncludeExclude());
	    subMenu.setProperty(PROPERTY_DESCRIPTION, subMenuDTO.getSubMenuDescription());
	    subMenu.setProperty(PROPERTY_SUBMENU_TOPMENU_COLUMN, subMenuDTO.getTopMenuName());
	    
	    new UtilDataSourceAPI().persistEntity(subMenu);
	  	return true;
	  } catch (Exception e) {
		 e.printStackTrace();
		 return false;
	  }
  }

  	public void deleteSubMenus(List<Key> keys) {
  		new UtilDataSourceAPI().deleteEntity(keys);
	}
  
  /**
   * Delete sub menu entity
   * @param subMenuKey : sub menu to be deleted
   * @return status string
   */
  public String deleteSubMenu(String subMenuKey) {
	  Key key = KeyFactory.createKey(KIND_SUBMENU,subMenuKey);	 
	  new UtilDataSourceAPI().deleteEntity(key);
	  return "Sub Menu - \""+subMenuKey+"\"" + " is deleted successfully";
  }
  
  /**
   * Get all sub menus for a top menu
   * @param topMenuName : name of the top menu
   * @return list of items
   */
  
  public Iterable<Entity> getSubMenusForTopMenu(String topMenuKey) {
	  //	Key ancestorKey = KeyFactory.createKey(KIND_TOPMENU, topMenuName);
	  //	return Util.listChildren(KIND_SUBMENU, ancestorKey);
	  return new UtilDataSourceAPI().listEntities(KIND_SUBMENU, PROPERTY_SUBMENU_TOPMENU_COLUMN, topMenuKey);
  	  //return Helper.removeNull(utilInterface.listEntities(KIND_SUBMENU, PROPERTY_TOPMENU_NAME, topMenuKey));
  }	
}
