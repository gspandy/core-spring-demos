package com.gordondickens.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * {@link Service} with hard-coded input data.
 */
public class ExampleServiceToo implements Service, InitializingBean {
	private static final Logger logger = LoggerFactory
			.getLogger(ExampleServiceToo.class);

	public ExampleServiceToo() {
		logger.debug(
				"\n**********\n\tEStoo CONSTRUCTED address = '{}'\n**********",
				this.toString());
	}

	/**
	 * Reads next record from input
	 */
	@Override
	public String getMessage() {
		return "Hello Gordo!";
	}

	@PostConstruct
	private void pcInit() {
		logger.debug("\n**********\n\tES2 post-construct = '{}'\n**********",
				this.toString());
	}

    @PostConstruct
	private void imInit() {
		// logger.debug("\n**********\n\tES2 Init Method = '{}'\n**********",
		// this.toString());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// logger.debug(
		// "\n**********\n\tES2 InitializingBean interface - afterPropertiesSet = '{}'\n*****'",
		// this.toString());
		//
	}

}
