package com.miracle.itx.utility;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class ITXProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Message msg = exchange.getIn();
		System.out.println("Received message : " + msg.getBody());
		String response = ITXInvoker.transform(msg.getBody().toString(),(String) msg.getHeader("mapName"));
		msg.setBody(response);		
		System.out.println("Transformed message : " + msg.getBody());
	}
}
