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
package moreland.spring.sample.jpademo.model.response;

import java.net.URI;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class ProblemDetail {
    private final URI type;
    private final String title;
    private final String detail;
    private final Integer status;
    private final URI instance;
    
    public ProblemDetail(URI type, String title, String detail, Integer status, URI instance) {
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.instance = instance;
    }

    public static ProblemDetail create(HttpStatus status,  String title, Exception e, WebRequest request, Logger logger) {

        logger.error(e.getLocalizedMessage(), e);

        URI instance;
        if (request instanceof ServletWebRequest servletRequest) {
            instance = URI.create(servletRequest.getRequest().getRequestURI());
        } else {
            logger.warn("Unable to determine request path");
            instance = null;
        }
        return new ProblemDetail(
            URI.create("https://httpstatuses.com/" + status.value()),
            title,
            e.getLocalizedMessage(),
            Integer.valueOf(status.value()),
            instance);
    }

    public static final MediaType JSON_MEDIA_TYPE =
        MediaType.valueOf("application/problem+json");

    public URI getType() {
        return type;
    }
    public String getTitle() {
        return title;
    }
    public String getDetail() {
        return detail;
    }
    public Integer getStatus() {
        return status;
    }
    public URI getInstance() {
        return instance;
    }
}
