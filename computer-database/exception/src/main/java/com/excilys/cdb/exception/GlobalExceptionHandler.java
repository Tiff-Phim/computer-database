package com.excilys.cdb.exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice(basePackages = { "com.excilys.cdb.webapp.controller" })
public class GlobalExceptionHandler { // extends ResponseEntityExceptionHandler

	private static final String ERROR_403_PAGE = "forward:/views/403.html";
	private static final String ERROR_404_PAGE = "forward:/views/404.html";
	private static final String ERROR_500_PAGE = "forward:/views/500.html";

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//	@ExceptionHandler(NoHandlerFoundException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ModelAndView handleError404(HttpServletRequest request, NoHandlerFoundException e) {
//		logger.error("Request: " + request.getRequestURI() + " raised " + e);
//		return new ModelAndView("error");
//	}

//	@ExceptionHandler(NoHandlerFoundException.class)
//	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Page not found")
//	public ModelAndView handleResourceNotFoundException(NoHandlerFoundException e) {
//		logger.error("Request raised " + e.getClass().getSimpleName());
//		return new ModelAndView(ERROR_404_PAGE);
//	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleError404(HttpServletRequest request, Exception e) {
		logger.error("Request raised " + e.getClass().getSimpleName());
//		ModelAndView mv = new ModelAndView(ERROR_404_PAGE);
//		mv.addObject("exception", e);
		// mav.addObject("errorcode", "404");
		return ERROR_404_PAGE;
	}

	@ExceptionHandler(NoClassDefFoundError.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "NoClassDefFoundError occured")
	public ModelAndView handle404Error(NoClassDefFoundError e) {
		logger.error("Request raised " + e.getClass().getSimpleName());
		return new ModelAndView(ERROR_404_PAGE);
	}

	@ExceptionHandler(ComputerNotFoundException.class)
//	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "ComputerNotFoundException occured")
	public String handleComputerNotFound(ComputerNotFoundException e) {
		logger.error("I'm HERE"); //TODO
		logger.error("Request raised " + e.getClass().getSimpleName());
		return ERROR_404_PAGE;
	}

//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(Exception.class)
//	public String notFoundHandler() {
//		logger.debug("Item not found. HTTP 500 returned.");
//		return ERROR_500_PAGE;
//	}

	@ExceptionHandler({ SQLException.class })
	public String databaseError(Exception exception) {
		// Nothing to do. Return value 'databaseError' used as logical view name
		// of an error page, passed to view-resolver(s) in usual way.
		logger.error("Request raised " + exception.getClass().getSimpleName());
		return "databaseError";
	}

//	public static final String DEFAULT_ERROR_VIEW = "error";
//
//	@ExceptionHandler(value = Exception.class)
//	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//		// If the exception is annotated with @ResponseStatus rethrow it and let
//		// the framework handle it - like the OrderNotFoundException example
//		// at the start of this post.
//		// AnnotationUtils is a Spring Framework utility class.
//		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//			throw e;
//
//		// Otherwise setup and send the user to a default error-view.
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("exception", e);
//		mav.addObject("url", req.getRequestURL());
//		mav.setViewName(DEFAULT_ERROR_VIEW);
//		return mav;
//	}
}
