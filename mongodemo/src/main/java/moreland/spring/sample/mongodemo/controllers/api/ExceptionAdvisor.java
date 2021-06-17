//
// Copyright © 2021 Terry Moreland
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
// to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
// and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
package moreland.spring.sample.mongodemo.controllers.api;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import moreland.spring.sample.mongodemo.model.response.ProblemDetail;

@ControllerAdvice
public class ExceptionAdvisor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return new ResponseEntity<Object>(
            ProblemDetail.create(HttpStatus.BAD_REQUEST, "Illegal Arguments", e, request, LOGGER),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
        LOGGER.error("Item not found: " + e.getMessage(), e);
        return new ResponseEntity<Object>(
            ProblemDetail.create(HttpStatus.NOT_FOUND, "Item not found", e, request, LOGGER),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
    public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, WebRequest request) {
        LOGGER.error("bad request, unsupported media type: " + e.getMessage(), e);
        return new ResponseEntity<Object>(
            ProblemDetail.create(HttpStatus.BAD_REQUEST, "Unsupported content/type", e, request, LOGGER),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { IncorrectResultSizeDataAccessException.class })
    public ResponseEntity<Object> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException e, WebRequest request) {
        LOGGER.error("incorrect data size, likely item not found, " + e.getMessage(), e);

        if (request instanceof ServletWebRequest && ((ServletWebRequest) request).getRequest().getMethod().toUpperCase().equals("GET")) {
            return new ResponseEntity<Object>(
                ProblemDetail.create(HttpStatus.NOT_FOUND, "Item not found.", e, request, LOGGER),
                HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(
            ProblemDetail.create(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid server error", e, request, LOGGER),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleAddFailureException(Exception e, WebRequest request) {
        LOGGER.error("Internal server request: " + e.getMessage(), e);
        return new ResponseEntity<Object>(
            ProblemDetail.create(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid server error", e, request, LOGGER),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
