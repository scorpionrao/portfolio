package com.anusha.portfolio.codelab;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class BeanSubMenuService extends BeanBase {
	
	private static final Logger logger = Logger.getLogger(BeanSubMenuService.class.getCanonicalName());
	  
	
	public DAOSubMenu getSubMenuDAO() {
		return new DAOSubMenuDataSource();
	}
	// Business Methods
	
	/**
	   * Return all the sub menus
	   * @param kind : of kind "SubMenu"
	   * @return  SubMenus
	   */
	  public Iterable<Entity> getAllSubMenus() {
	    return getSubMenuDAO().getAllSubMenus();
	  }

	  /**
		 * get Sub Menu with sub menu name
		 * 
		 * @param subMenuName
		 *          : get subMenuName
		 * @return item entity
		 */
		private Iterable<Entity> getSingleSubMenu(String subMenuName) {
			return getSubMenuDAO().getSingleSubMenu(subMenuName);
		}
		
		/**
		 * get Sub Menu with sub menu key
		 * 
		 * @param subMenuKey
		 *          : get subMenuKey
		 * @return sub menu entity
		 */
		public Entity getSingleSubMenuEntity(String subMenuKey) {
			return getSubMenuDAO().getSingleSubMenuEntity(subMenuKey);
		}
		
		
  /**
   * Create or Update Sub Menu
   * @param pictureName : name of the sub menu
   * @param subMenuIncludeExclude : include or exclude the sub menu from the portfolio
   * @param subMenuDescription : description of the sub menu
   * @return  created or updated Sub Menu
   */
  public boolean createOrUpdateSubMenu(DTOSubMenu subMenuDTO) {
	  logger.log(Level.INFO, "Service - Parameters of the New Sub Menu : " +
	    		subMenuDTO.getSubMenuName()+","+subMenuDTO.getSubMenuIncludeExclude()
	    		+","+subMenuDTO.getSubMenuDescription()+","+subMenuDTO.getTopMenuName());
	    
	  return getSubMenuDAO().createOrUpdateSubMenu(subMenuDTO);
  }

  	public void deleteSubMenus(List<Key> keys) {
		getSubMenuDAO().deleteSubMenus(keys);
	}
  
  /**
   * Delete sub menu entity
   * @param subMenuKey : sub menu to be deleted
   * @return status string
   */
  public String deleteSubMenu(String subMenuKey) {
	  getSubMenuDAO().deleteSubMenu(subMenuKey);
	  return "Sub Menu - \""+subMenuKey+"\"" + " is deleted successfully";
  }
  
  /**
   * Get all sub menus for a top menu
   * @param topMenuName : name of the top menu
   * @return list of items
   */
  
  public Iterable<Entity> getSubMenusForTopMenu(String topMenuKey) {
	  return getSubMenuDAO().getSubMenusForTopMenu(topMenuKey);
  }	
  
}