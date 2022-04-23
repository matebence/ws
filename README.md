## Websocket (JSR 356)

**Technologies before Websocket**
- Cross Frame communication
	- Useage of iframe tag from HTML
- Polling
	- Request are periodically send to the server
- Long Polling
	- The server connection stays open
	- Unidirectional, only the server send the data
- AJAX
	- Connecting to the server only when its required

**Websocket**
- Bidirectional
- Full duplexed commnucation

**Difference between SSE and WS**
- Websockets and SSE (Server Sent Events) are both capable of pushing data to browsers, however they are not competing technologies.
- Websockets connections can both send data to the browser and receive data from the browser. A good example of an application that could use websockets is a chat application.
- SSE connections can only push data to the browser. Online stock quotes, or twitters updating timeline or feed are good examples of an application that could benefit from SSE.

**Process of opening WS protocol**
- HTTP request
- 3 Way Handsake
- Upgrade to HTTP to TCP

**With and without SSL**
- ws://address?url=ecneb
- wss://address?url=ecneb

**Websocket Methodologies**
- Methods
	- onOpen() onError() onClose()
	- send() receive()
	- extends Endpoint
	- implements ServerApplicationConfig
- Annotations
	- @ServerEndpoint("/")
	- @OnOpen and @OnClose
	- @OnMessage and @nError

**Three type of messeages**
- Test
- Binary
- Pong