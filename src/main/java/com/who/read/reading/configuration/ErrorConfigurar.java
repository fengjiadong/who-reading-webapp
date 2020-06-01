package com.who.read.reading.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @Classname ErrorConfigurar
 * @date 2020/5/27 3:34 PM
 * @Created by fengjiadong
 */
@Configuration
public class ErrorConfigurar implements ErrorPageRegistrar {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorConfigurar.class);

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage[] errorPages = new ErrorPage[2];
		errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
		errorPages[0] = new ErrorPage(HttpStatus.BAD_REQUEST, "/error.html?code=400");
		errorPages[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.html?code=500");
		errorPages[1] = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html?code=403");
		LOGGER.error("访问错误.");
		registry.addErrorPages(errorPages);
	}
}
