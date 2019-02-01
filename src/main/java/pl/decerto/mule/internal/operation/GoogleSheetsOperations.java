package pl.decerto.mule.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.TEXT_PLAIN;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.util.Arrays;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import pl.decerto.mule.internal.connection.GoogleSheetsConnection;
import pl.decerto.mule.internal.operation.param.AppendSpreadsheetGroup;

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

	@MediaType(TEXT_PLAIN)
	public String appendSpreadsheetValues(@ParameterGroup(name = "Group") AppendSpreadsheetGroup group,
			@Connection GoogleSheetsConnection connection) throws IOException {
		Sheets sheetsConnection = connection.getSheetsConnection();
		ValueRange body = new ValueRange()
				.setValues(Arrays.asList(group.getColumns()));
		AppendValuesResponse result =
				sheetsConnection.spreadsheets().values().append(group.getSpreadsheetId(), group.getSheetName(), body)
						.setValueInputOption(VALUE_INPUT_OPTION)
						.execute();

		return result.getUpdates().getUpdatedColumns().toString();
	}
}
