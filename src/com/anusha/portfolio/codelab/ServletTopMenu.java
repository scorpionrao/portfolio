/**
 * Copyright 2011 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anusha.portfolio.codelab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

/**
 * This servlet responds to the request corresponding to Top Menu entities. The servlet
 * manages the Top Menu Entity
 * 
 * 
 */
@SuppressWarnings("serial")
public class ServletTopMenu extends ServletBase {

  private static final Logger logger = Logger.getLogger(ServletTopMenu.class.getCanonicalName());
  private static final String PARAMETER_TOPMENU_NAME = "name";
  private static final String MESSAGE_DELETE_FAILURE = "Cannot delete, as there are sub menus associated with this Top Menu name.";

  /**
   * Searches for the entity based on the search criteria and returns result in
   * JSON format
   */

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	super.doGet(req, resp);
	BeanTopMenuService topMenuServiceBean = new BeanTopMenuService();
    logger.log(Level.INFO, "Obtaining Top Menu listing");
    String searchFor = req.getParameter(PARAMETER_SEARCH_FOR);
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = null;
    if (searchFor == null || searchFor.equals("") || searchFor == "*") {
      entities = topMenuServiceBean.getAllTopMenus();
      out.println(UtilJSONWriter.writeJSON(entities));
    } else {
      DTOTopMenu topMenuDTO = new DTOTopMenu();
      topMenuDTO.setTopMenuName(searchFor);
      Entity singleTopMenu = topMenuServiceBean.getTopMenu(topMenuDTO.getTopMenuName());
      if (singleTopMenu != null) {
        Set<Entity> result = new HashSet<Entity>();
        result.add(singleTopMenu);
        out.println(UtilJSONWriter.writeJSON(result));
      }
    }
  }

  /**
   * Create / Edit the entity and persist it.
   */
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  
	BeanTopMenuService topMenuServiceBean = new BeanTopMenuService();
	
    logger.log(Level.INFO, "Creating / Editing Top Menu");
    PrintWriter out = resp.getWriter();
    DTOTopMenu topMenuDTO = new DTOTopMenu (
    	req.getParameter(PARAMETER_TOPMENU_NAME),
    	req.getParameter(PARAMETER_INCLUDE_EXCLUDE),
    	req.getParameter(PARAMETER_DESCRIPTION));
    log("Parameters of the New Top Menu : " + topMenuDTO.getTopMenuName()+
    		","+topMenuDTO.getTopMenuIncludeExclude()+","+topMenuDTO.getTopMenuDescription());
    try {
      
    	topMenuServiceBean.createOrUpdateTopMenu(topMenuDTO);
    } catch (ExceptionTopMenu e) {
      e.printStackTrace();
    } catch (Exception e) {
      String msg = Util.getErrorMessage(e);
      out.print(msg);
    }
  }

  /**
   * Delete the top menu entity
   */
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	BeanTopMenuService topMenuServiceBean = new BeanTopMenuService();
	BeanSubMenuService subMenuServiceBean = new BeanSubMenuService();
	DTOTopMenu topMenuDTO = new DTOTopMenu ();
	topMenuDTO.setTopMenuName(req.getParameter(PARAMETER_ID));
    PrintWriter out = resp.getWriter();
    try{  
    	Iterable<Entity> entities = subMenuServiceBean.getSubMenusForTopMenu(topMenuDTO.getTopMenuName());
  	  	for (Entity e : entities) {
  		  if (e != null){
  			  out.println(MESSAGE_DELETE_FAILURE);
  			  return;
  		  }
  	  	}
  	  topMenuServiceBean.deleteTopMenu(topMenuDTO.getTopMenuName());
    } catch (ExceptionTopMenu e) {
        e.printStackTrace();
    } catch(Exception e) {
    	String msg = Util.getErrorMessage(e);
        out.print(msg);
    }    
  }

  /**
   * Redirect the call to doDelete or doPut method
   */
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter(PARAMETER_ACTION);
    if (action.equalsIgnoreCase(ACTION_DELETE)) {
      doDelete(req, resp);
      return;
    } else if (action.equalsIgnoreCase(ACTION_PUT)) {
      doPut(req, resp);
      return;
    }
  }

}