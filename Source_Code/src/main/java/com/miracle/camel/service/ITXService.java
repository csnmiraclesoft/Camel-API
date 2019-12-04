package com.miracle.camel.service;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

public class ITXService {

	public String invokeService(Exchange exchange) {
		Message msg = exchange.getIn();
		String mapName = (String) msg.getHeader("mapName");
		String endpoint = (String) msg.getHeader("endpoint");
		System.out.println(mapName);
		return endpoint;
	}
}
