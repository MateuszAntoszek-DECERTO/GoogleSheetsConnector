# Google Sheets Connector

Add this dependency to your application pom.xml

```

		 <dependency>
		    <groupId>pl.decerto.mule</groupId>
		    <artifactId>google-sheets-connector</artifactId>
		    <version>0.0.1</version>
		    <classifier>mule-plugin</classifier>
  		</dependency>
```

Set connection configuration.

```

	<basic:connection
				clientId="ClientId"
				clientSecret="Password"
				redirectUris="urn:ietf:wg:oauth:2.0:oob;http://localhost"
				authUri="https://accounts.google.com/o/oauth2/auth"
				tokenUri="https://www.googleapis.com/oauth2/v3/token"
		/>
```
	
