package com.challenge.itausegdev.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Such Product")
public class ProductNotFoundException extends RuntimeException {
}
