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
package moreland.spring.sample.jpademo.controllers.api;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import moreland.spring.sample.jpademo.model.response.ProblemDetail;
import moreland.spring.sample.jpademo.shared.AddFailureException;

@ControllerAdvice
public class ExceptionAdvisor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return new ResponseEntity<Object>(
            ProblemDetail.create(HttpStatus.BAD_REQUEST, "Illegal Arguments", e, request, LOGGER),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { AddFailureException.class })
    public ResponseEntity<Object> handleAddFailureException(AddFailureException e, WebRequest request) {
        LOGGER.error("Add failure, Bad request: " + e.getMessage(), e);
        return new ResponseEntity<Object>(
            ProblemDetail.create(HttpStatus.BAD_REQUEST, "Invalid object or internal error", e, request, LOGGER),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
        LOGGER.error("Item not found: " + e.getMessage(), e);
        return new ResponseEntity<Object>(
            ProblemDetail.create(HttpStatus.NOT_FOUND, "Item not found", e, request, LOGGER),
            HttpStatus.NOT_FOUND);
    }
}
