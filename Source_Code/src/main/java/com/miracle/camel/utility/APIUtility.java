package com.miracle.camel.utility;

import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.RoutesDefinition;

import com.miracle.camel.starter.CamelAppStarter;

public class APIUtility implements Processor {
	private static CamelContext camelContext = null;

	@SuppressWarnings("static-access")
	public void getAllRoutes(String fileName) throws Exception {
		CamelAppStarter app = new CamelAppStarter();
		camelContext = app.getCamelContext();
		File file = new File("//routes//" + fileName);
		InputStream is = new FileInputStream(file);
		RoutesDefinition routeDefinition = camelContext.loadRoutesDefinition(is);
		camelContext.addRouteDefinitions(routeDefinition.getRoutes());
		RouteCache.getInstance().getCache().put(file.getName(), file);
		camelContext.start();
	}

	@Override
	public void process(Exchange exchange){
		String fileName = exchange.getIn().getHeader("CamelFileName").toString().trim();
		System.out.println(fileName+" load routes");
		try {
			if (fileName.endsWith(".xml")) {
				getAllRoutes(fileName);	
			}else {
				System.out.println("Omitting "+fileName+" as it is not in xml format");
			}
		} catch (Exception exception) 
		{
			System.out.println("RouteLoader :"+fileName+" : "+exception.getMessage().toString().trim());
		}
	}
}
