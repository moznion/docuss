Docuss [![Build Status](https://travis-ci.org/moznion/docuss.svg?branch=master)](https://travis-ci.org/moznion/docuss) [![javadoc.io](https://javadocio-badges.herokuapp.com/net.moznion/docuss/badge.svg)](https://javadocio-badges.herokuapp.com/net.moznion/docuss)
==

A library to test with describing document for controller layer.

Synopsis
--

```java
final Docuss<HttpEntity, HttpResponse> docuss = new Docuss<>(new YAMLFormatterGenerator(),
                                                             new StandardOutPresenter(),
                                                             new ApacheHttpclient());
docuss.shouldGet(uri, resp -> {
    assertEquals(200, resp.getStatusLine().getStatusCode());
}); // <= tests and outputs request/response information
```

Description
--

This library provides methods to test HTTP request and response of your application easily; and output the request/response information automatically.

Methods receive "a URI of request" and "a lambda to inspect response".
These methods send a request to the specified URI with HTTP method that corresponds to each method.
And the response of the request is passed to given lambda as a parameter.
We must write code into lambda to test the response.

When testing is finished successfully, it outputs contents of request and response as arbitrary format.
For example, it outputs as follows hen the code that is shown on [Synopsis](#synopsis) is given.

```yaml
---
request:
  method: "GET"
  protocol: "HTTP/1.1"
  path: "/foo/bar"
  headers: []
  body: null
  queryParams: {}
response:
  protocol: "HTTP/1.1"
  statusCode: 200
  headers:
  - "Date: Sat, 30 Jul 2016 15:36:35 GMT"
  - "Content-Length: 28"
  - "Server: Jetty(9.2.2.v20140723)"
  body: "{\"msg\": \"Hey\",\n\"value\": 100}"
```

Show the way to control format below.

### Formatter generator

`DocussFormatterGenerator` is an Interface that defines a method to get a formatter generator for results (results = `DocussDocument`).
The class that implements this interface must provide a formatter generator.
Generated formatter is applied to the results to format it.

It means, you can modify the format of output by writing your own formatter generator.

### Presenter

`DocussPresenter` is an Interface that defines a method to output the results.

If you want to change the way (or destination) of output, please implement this interface on your own class.

### HTTP client

`DocussHttpClient` is an Interface that defines methods to handle the operations of HTTP layer.

This library doesn't depend on any specific HTTP client implementations.
So if you want to use this library with any HTTP client, please implement this interface on your own class with the HTTP client.

### More information

Please refer to javadoc.

[![javadoc.io](https://javadocio-badges.herokuapp.com/net.moznion/docuss/badge.svg)](https://javadocio-badges.herokuapp.com/net.moznion/docuss)

See Also
--

This library is inspired by [autodoc](https://github.com/r7kamura/autodoc).

Author
--

moznion (<moznion@gmail.com>)

License
--

```
The MIT License (MIT)
Copyright © 2016 moznion, http://moznion.net/ <moznion@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

