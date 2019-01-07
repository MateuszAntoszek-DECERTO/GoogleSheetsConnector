package pl.decerto.mule;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@Operations(GoogleSheetsOperations.class)
@ConnectionProviders(GoogleSheetsConnectionProvider.class)
public class BasicConfiguration {

}
