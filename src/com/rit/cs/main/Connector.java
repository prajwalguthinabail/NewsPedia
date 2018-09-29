package com.rit.cs.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Connector.java
 * 
 * @author Prajwal Guthinabail Nov 20, 2017
 */
public class Connector extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Connector() {
		System.out.println("Getting ready with the connector...\n");
		ProcessRetrieval.start();
		System.out.println("Connector ready");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	@SuppressWarnings({ "unused" })

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter printer = response.getWriter();
		String uri = request.getRequestURI();
		String searchString = request.getParameter("searchterm");

		searchString = searchString == null ? "" : searchString.trim();

		List<Record> results = new ProcessQuery().process(searchString);

		if (results != null) {
			String ret = new Gson().toJson(results);
			printer.println(ret);
		}

	}

}
