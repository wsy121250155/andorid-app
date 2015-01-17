package com.post.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class FilsInitListener
 *
 */
@WebListener
public class FilsInitListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public FilsInitListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	private static File[] files;

	public static File getFiles(int i) {
		if (i >= files.length || i < 0) {
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i = files.length;
		}
		return files[i];
	}

	public static int getCount() {
		return files.length;
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext context = arg0.getServletContext();
		String root = context.getRealPath("/") + "picture/";
		File dirFile = new File(root);
		files = dirFile.listFiles();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
