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
 * This servlet responds to the request corresponding Picture entities. The servlet
 * manages the Picture Entity
 * 
 * 
 */
@SuppressWarnings("serial")
public class ServletPicture extends ServletBase {

  private static final Logger logger = Logger.getLogger(ServletPicture.class.getCanonicalName());
  
  private static final String PARAMETER_PICTURE_SEARCH_BY = "picture-searchby";
  private static final String PARAMETER_PICTURE_NAME = "name";
  private static final String PARAMETER_SUBMENU_NAME = "subMenu";

  
  /**
   * Searches for the entity based on the search criteria and returns result in
   * JSON format
   */

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	super.doGet(req, resp);
	BeanPictureService pictureServiceBean = new BeanPictureService();
    logger.log(Level.INFO, "Obtaining Picture listing");
    String searchBy = req.getParameter(PARAMETER_PICTURE_SEARCH_BY);
    String searchFor = req.getParameter(PARAMETER_SEARCH_FOR);
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = null;
    if (searchFor == null || searchFor.equals("") || searchFor == "*") {
      entities = pictureServiceBean.getAllPictures();
      out.println(UtilJSONWriter.writeJSON(entities));
      return;
    } else if (searchBy == null || searchBy.equals(PARAMETER_PICTURE_NAME)) {
      DTOPicture pictureDTO = new DTOPicture();
      pictureDTO.setPictureName(searchFor);
      Entity singlePicture = pictureServiceBean.getSinglePictureEntity(pictureDTO.getPictureName());
      Set<Entity> result = new HashSet<Entity>();
      result.add(singlePicture);
      out.println(UtilJSONWriter.writeJSON(result));
      return;
  	} else if (searchBy == null || searchBy.equals(PARAMETER_SUBMENU_NAME)){
  	  DTOSubMenu subMenuDTO = new DTOSubMenu();
      subMenuDTO.setSubMenuName(searchFor);
      entities = pictureServiceBean.getPicturesForSubMenu(subMenuDTO.getSubMenuName());
      out.println(UtilJSONWriter.writeJSON(entities));
      return;
  	}
  }

  /**
   * Create / Edit the entity and persist it.
   */
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  
	BeanPictureService pictureServiceBean = new BeanPictureService();
	
    logger.log(Level.INFO, "Creating / Editing Picture");
    PrintWriter out = resp.getWriter();
    DTOPicture pictureDTO = new DTOPicture (
    	req.getParameter(PARAMETER_PICTURE_NAME),
   		req.getParameter(PARAMETER_INCLUDE_EXCLUDE),
   		req.getParameter(PARAMETER_DESCRIPTION),
    	req.getParameter(PARAMETER_SUBMENU_NAME));
    logger.log(Level.INFO, "Parameters of the New Sub Menu : " +
    		pictureDTO.getPictureName()+","+pictureDTO.getPictureIncludeExclude()
    		+","+pictureDTO.getPictureDescription()+","+pictureDTO.getSubMenuName());
    try {
      pictureServiceBean.createOrUpdatePicture(pictureDTO);
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
	  
	BeanPictureService pictureMenuServiceBean = new BeanPictureService();
	DTOPicture pictureDTO = new DTOPicture();
	pictureDTO.setPictureName(req.getParameter(PARAMETER_ID));
    PrintWriter out = resp.getWriter();
    try{
    	pictureMenuServiceBean.deletePicture(pictureDTO.getPictureName());
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