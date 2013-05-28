package com.anusha.portfolio.codelab;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ServletBase extends HttpServlet {
	protected static final String PARAMETER_SEARCH_FOR = "q";
	protected static final String PARAMETER_INCLUDE_EXCLUDE = "includeExclude";
	protected static final String PARAMETER_DESCRIPTION = "description";
	protected static final String PARAMETER_ID = "id";
	protected static final String PARAMETER_ACTION = "action";
	protected static final String ACTION_DELETE = "delete";
	protected static final String ACTION_PUT = "put";
	
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {
	resp.setContentType("application/json; charset=utf-8");
	resp.setHeader("Cache-Control", "no-cache");
  }
}
