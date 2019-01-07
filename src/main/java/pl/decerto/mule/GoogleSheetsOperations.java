package pl.decerto.mule;

import static org.mule.runtime.extension.api.annotation.param.MediaType.TEXT_PLAIN;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;

public class GoogleSheetsOperations {

	private static final String SPREADSHEET_ID = "spreadsheetId";
	private static final String VALUE_INPUT_OPTION = "RAW";

	@MediaType(TEXT_PLAIN)
	public String createSpreadsheet(String title, @Connection GoogleSheetsConnection connection) throws IOException {
		Sheets sheetsConnection = connection.getSheetsConnection();
		Spreadsheet spreadsheet = new Spreadsheet()
				.setProperties(new SpreadsheetProperties()
						.setTitle(title));
		spreadsheet = sheetsConnection.spreadsheets().create(spreadsheet)
				.setFields(SPREADSHEET_ID)
				.execute();

		return spreadsheet.getSpreadsheetId();
	}

	//TODO simplify method arguments
	@MediaType(TEXT_PLAIN)
	public String insertSpreadsheetValue(String spreadsheetId, String range, String values,
			@Connection GoogleSheetsConnection connection) throws IOException {
		Sheets sheetsConnection = connection.getSheetsConnection();
		List<List<Object>> pharsedValues = Arrays.asList(Arrays.asList(values.split(";")));
		ValueRange body = new ValueRange()
				.setValues(pharsedValues);
		UpdateValuesResponse result =
				sheetsConnection.spreadsheets().values().update(spreadsheetId, range, body)
						.setValueInputOption(VALUE_INPUT_OPTION)
						.execute();

		return result.getUpdatedColumns().toString();
	}
}
