/**
 * Exception handler class used for returning exception
 * 
 * @author 463657
 *
 */
package com.fsd.casestudy.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fsd.casestudy.controller.ProjectManagerController;

@ControllerAdvice(assignableTypes = { ProjectManagerController.class })
public class TaskExceptionHandler {

	@ExceptionHandler(Exception.class)
	public @ResponseBody RestError handleCustomException(Exception ex, HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		if (ex instanceof TaskException) {

			response.setStatus(((TaskException) ex).getReturnStatus());
			return ((TaskException) ex).transformException();
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			RestError restError = returnRestError();
			return restError;
		}

	}

	public RestError returnRestError() {
		RestError restError = new RestError();
		Exceptions exception = new Exceptions();
		exception.setCode("1001");
		exception.setMessage("One or more of the request parameters are missing/wrong. Please correct the request.");
		exception.setDetail("One or more of the request parameters are missing/wrong. Please correct the request.");
		Exceptions exceptions[] = new Exceptions[1];
		exceptions[0] = exception;
		restError.setExceptions(exceptions);
		return restError;
	}
}
