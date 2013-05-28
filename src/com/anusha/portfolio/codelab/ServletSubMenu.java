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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

/**
 * This servlet responds to the request corresponding Sub Menu entities. The servlet
 * manages the Sub Menu Entity
 * 
 * 
 */
@SuppressWarnings("serial")
public class ServletSubMenu extends ServletBase {

  private static final Logger logger = Logger.getLogger(ServletSubMenu.class.getCanonicalName());
  
  private static final String PARAMETER_SUBMENU_SEARCH_BY = "subMenu-searchby";
  private static final String PARAMETER_SUBMENU_NAME = "name";
  private static final String PARAMETER_TOPMENU_NAME = "topMenu";
  private static final String MESSAGE_DELETE_FAILURE = "Cannot delete, as there are pictures associated with this Sub Menu name.";

  
  /**
   * Searches for the entity based on the search criteria and returns result in
   * JSON format
   */

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	super.doGet(req, resp);
	BeanSubMenuService subMenuServiceBean = new BeanSubMenuService();
    logger.log(Level.INFO, "Obtaining Sub Menu listing");
    String searchBy = req.getParameter(PARAMETER_SUBMENU_SEARCH_BY);
    String searchFor = req.getParameter(PARAMETER_SEARCH_FOR);
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = null;
    if (searchFor == null || searchFor.equals("") || searchFor == "*") {
      entities = subMenuServiceBean.getAllSubMenus();
      for (Entity e : entities) {
    	  logger.log(Level.INFO, Util.printEntityKindAndProperties(e));
      }
      out.println(UtilJSONWriter.writeJSON(entities));
      return;
    } else if (searchBy == null || searchBy.equals(PARAMETER_SUBMENU_NAME)) {
      DTOSubMenu subMenuDTO = new DTOSubMenu();
      subMenuDTO.setSubMenuName(searchFor);
      Entity singleSubMenu = subMenuServiceBean.getSingleSubMenuEntity(subMenuDTO.getSubMenuName());
      Set<Entity> result = new HashSet<Entity>();
      result.add(singleSubMenu);
      out.println(UtilJSONWriter.writeJSON(result));
      return;
  	} else if (searchBy.equals(PARAMETER_TOPMENU_NAME)){
  	  DTOTopMenu topMenuDTO = new DTOTopMenu();
      topMenuDTO.setTopMenuName(searchFor);
      entities = subMenuServiceBean.getSubMenusForTopMenu(topMenuDTO.getTopMenuName());
      out.println(UtilJSONWriter.writeJSON(entities));
      return;
  	}
  }

  /**
   * Create / Edit the entity and persist it.
   */
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  
	BeanSubMenuService subMenuServiceBean = new BeanSubMenuService();
	
    logger.log(Level.INFO, "Creating / Editing Sub Menu");
    PrintWriter out = resp.getWriter();
    DTOSubMenu subMenuDTO = new DTOSubMenu (
    	req.getParameter(PARAMETER_SUBMENU_NAME),
   		req.getParameter(PARAMETER_INCLUDE_EXCLUDE),
   		req.getParameter(PARAMETER_DESCRIPTION),
    	req.getParameter(PARAMETER_TOPMENU_NAME));
    logger.log(Level.INFO, "Servlet - Parameters of the New Sub Menu : " +
    		subMenuDTO.getSubMenuName()+","+subMenuDTO.getSubMenuIncludeExclude()
    		+","+subMenuDTO.getSubMenuDescription()+","+subMenuDTO.getTopMenuName());
    try {
      req.setAttribute("SubMenu", subMenuServiceBean.createOrUpdateSubMenu(subMenuDTO));
      
    } catch (ExceptionSubMenu e) {
        e.printStackTrace();
      } catch (Exception e) {
        String msg = Util.getErrorMessage(e);
        out.print(msg);
      }
  }

  /**
   * Delete the sub menu entity
   */
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
//	super.doGet(req, resp);
	  
	BeanSubMenuService subMenuServiceBean = new BeanSubMenuService();
	BeanPictureService pictureMenuServiceBean = new BeanPictureService();
	DTOSubMenu subMenuDTO = new DTOSubMenu ();
	subMenuDTO.setSubMenuName(req.getParameter(PARAMETER_ID));
    PrintWriter out = resp.getWriter();
    try{  
    	Iterable<Entity> entities = pictureMenuServiceBean.getPicturesForSubMenu(subMenuDTO.getSubMenuName());
  	  	for (Entity e : entities) {
  		  if (e != null){
  			  out.println(MESSAGE_DELETE_FAILURE);
  			  return;
  		  }
  	  	}
  	  subMenuServiceBean.deleteSubMenu(subMenuDTO.getSubMenuName());
    } catch (ExceptionSubMenu e) {
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