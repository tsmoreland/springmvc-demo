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
package com.moreland.web.springdemo.security.xss;

import org.owasp.encoder.Encode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServerRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServerRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        return Encode.forJavaScript(Encode.forHtml(super.getHeader(name)));
    }

    @Override
    public String getParameter(String name) {
        return Encode.forJavaScript(Encode.forHtml(super.getParameter(name)));
    }

    @Override
    public String[] getParameterValues(String name) {
        var values = super.getParameterValues(name);
        if (values == null ) {
            return null;
        }
        var escapedValues = new String[values.length];
        for (int i=0; i< values.length; i++) {
            escapedValues[i] = Encode.forJavaScript(Encode.forHtml(values[i]));
        }
        return escapedValues;
    }
}