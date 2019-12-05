package com.miracle.camel.starter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.model.RoutesDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.miracle.camel.utility.RouteCache;

@SpringBootApplication
@ComponentScan("com.miracle.*")
@ImportResource({ "classpath:camel-context.xml" })
public class CamelAppStarter implements CamelContextAware, CommandLineRunner {
	private static CamelContext camelContext = null;
 
	public static void main(String[] args) {
		try {
			SpringApplication.run(CamelAppStarter.class, args);
		} catch (Exception exception) {
			System.out.println(exception.getMessage().toString().trim());
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void run(String... args) throws Exception {

		File folder = new File("//routes");
		File[] files = folder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});

		for (File file : files) {
			InputStream is = new FileInputStream(file);
			RoutesDefinition routeDefinition = camelContext.loadRoutesDefinition(is);
			camelContext.addRouteDefinitions(routeDefinition.getRoutes());
			RouteCache.getInstance().getCache().put(file.getName(), file);
		}

	}

	@Override
	public void setCamelContext(CamelContext camelContext) {
		CamelAppStarter.camelContext = camelContext;
	}

	@Override
	public CamelContext getCamelContext() {
		return CamelAppStarter.camelContext;
	}

}
