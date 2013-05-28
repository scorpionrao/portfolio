package com.anusha.portfolio.codelab;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public interface DAOSubMenu {
	Iterable<Entity> getAllSubMenus();
	Iterable<Entity> getSingleSubMenu(String subMenuName);
	Entity getSingleSubMenuEntity(String subMenuKey);
	boolean createOrUpdateSubMenu(DTOSubMenu subMenuDTO);
	void deleteSubMenus(List<Key> keys);
	String deleteSubMenu(String subMenuKey);
	Iterable<Entity> getSubMenusForTopMenu(String topMenuKey);
}
