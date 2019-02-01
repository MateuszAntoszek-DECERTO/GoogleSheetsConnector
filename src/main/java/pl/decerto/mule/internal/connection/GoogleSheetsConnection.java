package pl.decerto.mule.internal.connection;


import static java.lang.Thread.currentThread;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class GoogleSheetsConnection {

	private final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsConnection.class);

	//TODO Move some constants to configuration
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private Sheets sheetsConnection;

	public GoogleSheetsConnection(String resourcePath, String applicationName, AuthenticationType authenticationType) {
		try {
			if (AuthenticationType.SERVICE_ACCOUNT == authenticationType) {
				sheetsConnection = new Sheets.Builder(new NetHttpTransport(), JSON_FACTORY, getCredentials(resourcePath))
						.setApplicationName(applicationName)
						.build();
			} else {
				throw new RuntimeException("Not supported AuthenticationType");
			}

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

	private Credential getCredentials(String resourcePath) throws IOException {
		File file = new File(resolvePath(resourcePath));
		return GoogleCredential.fromStream(new FileInputStream(file))
				.createScoped(SCOPES);
	}

	private static String resolvePath(String pathOrResourceName) {
		URL resource = currentThread().getContextClassLoader().getResource(pathOrResourceName);
		return resource != null ? resource.getPath() : pathOrResourceName;
	}
}
