package pl.decerto.mule;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

@Operations(GoogleSheetsOperations.class)
@ConnectionProviders(GoogleSheetsConnectionProvider.class)
public class BasicConfiguration {

  @Parameter
  private String configId;

  public String getConfigId(){
    return configId;
  }
}
