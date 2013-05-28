// Copyright 2011, Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.anusha.portfolio.codelab;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;


/**
 * This is the utility class for all servlets. It provides method for inserting,
 * deleting, searching the entity from data store. Also contains method for
 * displaying the entity in JSON format.
 * 
 * 
 */
public class UtilDataSourceAPI {

	static final Logger logger = Logger.getLogger(UtilDataSourceAPI.class.getCanonicalName());
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();  

	/**
	 * 
	 * @param entity  : entity to be persisted
	 */
	  public void persistEntity(Entity entity) {
	  	UtilDataSourceAPI.log("Saving entity - " + Util.printEntityKindAndProperties(entity));
	  	datastore.put(entity);
	  }
	
		/**
		 * Delete the entity from persistent store represented by the key
		 * @param key : key to delete the entity from the persistent store
		 */
	  public void deleteEntity(Key key) {
	    UtilDataSourceAPI.log("Deleting entity with Key = " + key.toString());
	    datastore.delete(key);  	
	  }
	  
	  /**
	   * Delete list of entities given their keys
	   * @param keys
	   */
	  public void deleteEntity(final List<Key> keys){
	    datastore.delete(new Iterable<Key>() {
			public Iterator<Key> iterator() {
			  return keys.iterator();
			}
	      });    
	  }

	/**
	 * Search and return the entity from datastore.
	 * @param key : key to find the entity
	 * @return  entity
	 */
 
  public Entity findEntity(Key key) {
  	logger.log(Level.INFO, "Search the entity");
  	try {
  	  Entity entity = datastore.get(key);
  	  logger.log(Level.INFO, "Find entity result - "
  	  		+ Util.printEntityKindAndProperties(entity));
  	  return entity;
  	} catch (EntityNotFoundException e) {
  	  return null;
  	}
  }
 

	/***
	 * Search entities based on search criteria
	 * @param kind
	 * @param searchBy
	 *            : Searching Criteria (Property)
	 * @param searchFor
	 *            : Searching Value (Property Value)
	 * @return List all entities of a kind from the cache or datastore (if not
	 *         in cache) with the specified properties
	 */

	public Iterable<Entity> listEntities(String kind, String searchBy,
				String searchFor) {
	  	logger.log(Level.INFO, "Search entities based on search criteria");
	  	Query q = new Query(kind);
	  	if (searchFor != null && !"".equals(searchFor)) {
	  	  q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
	  	}
	  	PreparedQuery pq = datastore.prepare(q);
	  	return pq.asIterable();
	  }
	  
	  
	  /**
	   * Search entities based on ancestor
	   * @param kind
	   * @param ancestor
	   * @return
	   */
	  public Iterable<Entity> listChildren(String kind, Key ancestor) {
	  	logger.log(Level.INFO, "Search entities based on parent");
	  	Query q = new Query(kind);
	  	q.setAncestor(ancestor);
	  	q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
	  	PreparedQuery pq = datastore.prepare(q);
	  	return pq.asIterable();
	  }
	  
	  /**
	   * 
	   * @param kind
	   * @param ancestor
	   * @return
	   */
	  public Iterable<Entity> listChildKeys(String kind, Key ancestor) {
	  	logger.log(Level.INFO, "Search entities based on parent");
	  	Query q = new Query(kind);
	  	q.setAncestor(ancestor).setKeysOnly();
	  	q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
	  	PreparedQuery pq = datastore.prepare(q);
	  	return pq.asIterable();
	  }

	/**
	   * Logger Level INFO
	   */
  private static void log (String message) {
		  logger.log(Level.INFO, message);
	  }

	/**
   * get DatastoreService instance
   * @return DatastoreService instance
   */
  private static DatastoreService getDatastoreServiceInstance(){
	  return datastore;
  }
}