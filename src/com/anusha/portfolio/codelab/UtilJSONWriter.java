package com.anusha.portfolio.codelab;

import java.util.Map;
import java.util.logging.Level;

import com.google.appengine.api.datastore.Entity;

public class UtilJSONWriter {

	/**
		 * Retrieves Parent and Child entities into JSON String
		 * 
		 * @param entities
		 *            : List of parent entities
		 * @param childKind
		 *            : Entity type for Child
		 * @param fkName
		 *            : foreign-key to the parent in the child entity
		 * @return JSON string
		 */
	  public static String writeJSON(Iterable<Entity> entities, String childKind, String fkName) {
	  	UtilDataSourceAPI.logger.log(Level.INFO, "creating JSON format object for parent child relation");
	  	StringBuilder sb = new StringBuilder();
	  	int i = 0;
	  	sb.append("{\"data\": [");
	  	for (Entity result : entities) {
	  	  Map<String, Object> properties = result.getProperties();
	  	  sb.append("{");
	  	  if (result.getKey().getName() == null)
	  		sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
	  	  else
	  		sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
	  	  for (String key : properties.keySet()) {
	  		sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
	  	  }
	  	  Iterable<Entity> child = new UtilDataSourceAPI().listEntities(childKind, fkName,
	  		String.valueOf(result.getKey().getId()));
	  	  for (Entity en : child) {
	  		for (String key : en.getProperties().keySet()) {
	  		  sb.append("\"" + key + "\" : \"" + en.getProperties().get(key)+ "\",");
	  		}
	  	  }
	  	  sb.deleteCharAt(sb.lastIndexOf(","));
	  	  sb.append("},");
	  	  i++;
	  	}
	  	if (i > 0) {
	  	  sb.deleteCharAt(sb.lastIndexOf(","));
	  	}  
	  	sb.append("]}");
	  	return sb.toString();
	  }

	/**
	 * List the entities in JSON format
	 * 
	 * @param entities  entities to return as JSON strings
	 */
	  public static String writeJSON(Iterable<Entity> entities) {
	    UtilDataSourceAPI.logger.log(Level.INFO, "creating JSON format object");
	  	StringBuilder sb = new StringBuilder();
	  	
	  	int i = 0;
	  	sb.append("{\"data\": [");
	  	for (Entity result : entities) {
	  	  Map<String, Object> properties = result.getProperties();
	  	  sb.append("{");
	  	  if (result.getKey().getName() == null)
	  		sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
	  	  else
	  		sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
	
	  	  for (String key : properties.keySet()) {
	  		sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
	  	  }
	  	  sb.deleteCharAt(sb.lastIndexOf(","));
	  	  sb.append("},");
	  	  i++;
	  	}
	  	if (i > 0) {
	  	  sb.deleteCharAt(sb.lastIndexOf(","));
	  	}  
	  	sb.append("]}");
	  	return sb.toString();
	  }

}
