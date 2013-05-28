package com.anusha.portfolio.codelab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;

public class Util {

	/**
	   * Print Entity Kind and Properties
	   * @param entity
	   * @return
	   */
	  static String printEntityKindAndProperties(Entity entity) {
			return "Kind="+entity.getKind()+",Key="+entity.getKey()+
					",Properties="+entity.getProperties().toString();
		}

	/**
		 * Utility method to send the error back to UI
		 * @param data
		 * @param resp
		 * @throws IOException 
		 */
	  public static String getErrorMessage(Exception ex) throws IOException{
	    return "Error:"+ex.toString();
	  }
	  
	  /**
	   * Remove null from Iterable<Entity>
	   * @param Iterable<Entity>
	   */
	  public static Iterable<Entity> removeNull(Iterable<Entity> entities) {
		  List<Entity> list = new ArrayList<Entity>();
		  for (Entity e : entities) {
	  		  if (e != null){
	  			list.add(e);
	  		  }
	  	  }
		  return list;
	  }

}
