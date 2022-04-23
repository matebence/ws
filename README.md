## Websocket (JSR 356)

**Technologies before Websocket**
- Cross Frame communication
	- Useage of iframe tag from HTML
- Polling
	- Requests are periodically send to the server
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
- 3 Way Handshake
- Upgrade HTTP to TCP

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
- Text (JSON, XML etc...)
- Binary (images etc...)
- Pong (Ping/Pong frames)

**Apps**
- [Console](https://github.com/matebence/ws/tree/master)
- [Web](https://github.com/matebence/ws/tree/web-app)

**Security**
- Same as any Web app, the first HTTP request has to be authenticated
- After authentication the HTTP protocol is upgraded to TCP

> #### Wildfly setup

```bash
# Download Wildfly
wget https://download.jboss.org/wildfly/24.0.1.Final/wildfly-24.0.1.Final.zip

# Switch to Wildfly directory
cd WILDFLY_HOME

# Start server in standalone mode
standalone.bat

# Connect to management cli
jboss-cli.bat --connect

# Add a Simple Role Decoder which maps the application Roles from the attribute Roles in the File system.
/subsystem=elytron/simple-role-decoder=from-roles-attribute:add(attribute=Roles)

# Let’s define a new filesystem-realm named fsRealm and its respective path on the file system:
/subsystem=elytron/filesystem-realm=WebSocketRealm:add(path=demofs-realm-users,relative-to=jboss.server.config.dir)

# Next, we add some identities to the Realm:
/subsystem=elytron/filesystem-realm=WebSocketRealm:add-identity(identity=bence)
/subsystem=elytron/filesystem-realm=WebSocketRealm:set-password(identity=bence,clear={password="password123"})
/subsystem=elytron/filesystem-realm=WebSocketRealm:add-identity-attribute(identity=bence,name=Roles, value=["user"])

/subsystem=elytron/filesystem-realm=WebSocketRealm:add-identity(identity=ecneb)
/subsystem=elytron/filesystem-realm=WebSocketRealm:set-password(identity=ecneb,clear={password="password123"})
/subsystem=elytron/filesystem-realm=WebSocketRealm:add-identity-attribute(identity=ecneb,name=Roles, value=["admin"])

# Create a new Security Domain which maps our Realm:
/subsystem=elytron/security-domain=fsSD:add(realms=[{realm=WebSocketRealm,role-decoder=from-roles-attribute}],default-realm=WebSocketRealm,permission-mapper=default-permission-mapper)

# We need an Http Authentication Factory which references our Security Domain:
/subsystem=elytron/http-authentication-factory=example-fs-http-auth:add(http-server-mechanism-factory=global,security-domain=fsSD,mechanism-configurations=[{mechanism-name=BASIC,mechanism-realm-configurations=[{realm-name=RealmUsersRoles}]}])

# A Security Domain in the undertow’s subsystem will be associated with our Http Authentication Factory:
/subsystem=undertow/application-security-domain=WebSocketSecurityDomain:add(http-authentication-factory=example-fs-http-auth)

# A Security Domain in the ejb’s subsystem will be associated with our WebSocketSecurityDomain:
/subsystem=ejb3/application-security-domain=WebSocketSecurityDomain:add(security-domain=fsSD) 

# Restart running server
reload
```