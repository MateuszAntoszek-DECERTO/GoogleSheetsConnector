package pl.decerto.mule;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


@Xml(prefix = "basic")
@Extension(name = "Basic")
@Configurations(BasicConfiguration.class)
public class GoogleSheetsExtension {

}