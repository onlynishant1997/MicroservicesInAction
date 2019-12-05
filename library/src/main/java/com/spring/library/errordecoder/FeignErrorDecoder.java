package com.spring.library.errordecoder;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		System.out.println(methodKey);
		if (response.status() == 406) {
			if ((methodKey.toLowerCase()).contains("UserClientUsingFeign".toLowerCase()))
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), "User Already Exist");
		}
		if (response.status() == 404) {
			if ((methodKey.toLowerCase()).contains("UserClientUsingFeign".toLowerCase()))
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), "User Not Found");
			else
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Book Not Found");
		}
		return null;
	}

}
