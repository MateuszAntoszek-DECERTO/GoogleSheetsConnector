package pl.decerto.mule.internal.connection;

import static org.mule.runtime.api.meta.model.display.PathModel.Type.FILE;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleSheetsConnectionProvider implements PoolingConnectionProvider<GoogleSheetsConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsConnectionProvider.class);
	private static final String FAILURE_TEXT = "Missing credentials file path";

	@Parameter
	@DisplayName("Google authentication credentials file (.json)")
	@Path(type = FILE, acceptedFileExtensions = "json")
	private String credentialsFilePath;

	@Parameter
	@DisplayName("Application name")
	private String applicationName;

	@Override
	public GoogleSheetsConnection connect() {
		return new GoogleSheetsConnection(credentialsFilePath, applicationName);
	}

	@Override
	public void disconnect(GoogleSheetsConnection connection) {
		connection.invalidate();
	}

	@Override
	public ConnectionValidationResult validate(GoogleSheetsConnection connection) {
		if (credentialsFilePath == null) {
			LOGGER.error(FAILURE_TEXT);
			return ConnectionValidationResult.failure(FAILURE_TEXT, new RuntimeException(FAILURE_TEXT));
		}

		return ConnectionValidationResult.success();
	}
}
