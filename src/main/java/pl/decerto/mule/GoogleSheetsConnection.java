package pl.decerto.mule;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GoogleSheetsConnection {

	private final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsConnection.class);

	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final String APPLICATION_NAME = "Google sheets connector sample";
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private Sheets sheetsConnection;

	public GoogleSheetsConnection(String clientId, String clientSecret, String redirectUris, String authUri, String tokenUri) {
		try {
			NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			Details details = getDetails(clientId, clientSecret, redirectUris, authUri, tokenUri);
			sheetsConnection = new Sheets.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport, details))
					.setApplicationName(APPLICATION_NAME)
					.build();
		} catch (Exception e) {
			LOGGER.error("Error while connecting", e);
		}
	}

	public Sheets getSheetsConnection() {
		return sheetsConnection;
	}

	public void invalidate() {
		sheetsConnection = null;
	}

	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, Details details) throws IOException {
		GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
		clientSecrets.setInstalled(details);
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	private Details getDetails(String clientId, String clientSecret, String redirectUris, String authUri, String tokenUri) {
		Details installed = new Details();
		installed.setAuthUri(authUri);
		installed.setClientId(clientId);
		installed.setClientSecret(clientSecret);
		installed.setRedirectUris(Arrays.asList(redirectUris.split(";")));
		installed.setTokenUri(tokenUri);
		return installed;
	}
}
