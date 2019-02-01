package pl.decerto.mule.internal.metadata;

import static java.util.stream.Collectors.toSet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.decerto.mule.internal.connection.GoogleSheetsConnection;

public class SheetNameValueProvider implements ValueProvider {

	private final Logger LOGGER = LoggerFactory.getLogger(SheetNameValueProvider.class);

	@Connection
	private GoogleSheetsConnection connection;

	@Parameter
	private String spreadsheetId;

	@Override
	public Set<Value> resolve() {
		try {
			Spreadsheet spreadsheet = connection.getSheetsConnection()
					.spreadsheets()
					.get(spreadsheetId).execute();
			return getSheetsTitles(spreadsheet);
		} catch (IOException e) {
			LOGGER.error("Error while fetching sheets titles", e);
			return Collections.emptySet();
		}
	}

	private Set<Value> getSheetsTitles(Spreadsheet spreadsheet) {
		return spreadsheet.getSheets().stream()
				.map(o -> o.getProperties().getTitle())
				.map(ValueBuilder::newValue)
				.map(ValueBuilder::build)
				.collect(toSet());
	}
}
