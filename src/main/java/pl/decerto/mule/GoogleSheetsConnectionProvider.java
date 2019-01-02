package pl.decerto.mule;

import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GoogleSheetsConnectionProvider implements PoolingConnectionProvider<GoogleSheetsConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsConnectionProvider.class);

	@Parameter
	private String clientId;

	@Parameter
	private String clientSecret;

	@Parameter
	private String redirectUris;

	@Parameter
	private String authUri;

	@Parameter
	private String tokenUri;

	@Override
	public GoogleSheetsConnection connect() {
		return new GoogleSheetsConnection(clientId, clientSecret, redirectUris, authUri, tokenUri);
	}

	@Override
	public void disconnect(GoogleSheetsConnection connection) {
		try {
			connection.invalidate();
		} catch (Exception e) {
			LOGGER.error("Error while disconnecting [" + connection + "]: " + e.getMessage(), e);
		}
	}

	@Override
	public ConnectionValidationResult validate(GoogleSheetsConnection connection) {
		return ConnectionValidationResult.success();
	}
}
