package com.anusha.portfolio.codelab;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public interface DAOTopMenu {
	Iterable<Entity> getAllTopMenus();
	Entity getTopMenu(String topMenuName);
	boolean createOrUpdateTopMenu(DTOTopMenu topMenuDTO);
	void deleteTopMenus(List<Key> keys);
	String deleteTopMenu(String topMenuKey);
}
