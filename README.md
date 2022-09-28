# qualitylist

Simple class for parsing quality lists. Quality lists are used in several HTTP "accept" header values to indicate a weight, or preference for multiple values. For example, a browser might send the following to indicate language preferences:
```
Accept-Language: fr-CH, fr;q=0.9, en;q=0.8, de;q=0.7, *;q=0.5
```
In english this means the client prefers `fr-CH` but will accept `fr`, `en`, `de`, or anything else that's available (`*`), respectively.

See https://developer.mozilla.org/en-US/docs/Glossary/Quality_values for more information on quality values.

To decode a quality list string to a list of `QValue`:
```
val values = QValue.decode(headers.get("accept-encoding"))
```
To encode a list of `QValue` back to a string:
```
val encoded = values.encode()
```
