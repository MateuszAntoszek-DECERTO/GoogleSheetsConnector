package pl.decerto.mule.internal.config;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import pl.decerto.mule.internal.connection.GoogleSheetsConnectionProvider;
import pl.decerto.mule.internal.operation.GoogleSheetsOperations;

@Operations(GoogleSheetsOperations.class)
@ConnectionProviders(GoogleSheetsConnectionProvider.class)
public class BasicConfiguration {

}
