package com.miracle.camel.service;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.miracle.itx.utility.ITXInvoker;

public class ITXService {

	@SuppressWarnings("static-access")
	public String process(Exchange exchange) {
		Message msg = exchange.getIn();
		String mapName = (String) msg.getHeader("mapName");
		String endpoint = (String) msg.getHeader("endpoint");
		ITXInvoker itxInvoker = new ITXInvoker();
		msg.setBody(itxInvoker.transform(msg.getBody().toString(),mapName));
		System.out.println(msg.getBody().toString());
		System.out.println(mapName);
		return endpoint;
	}
}
