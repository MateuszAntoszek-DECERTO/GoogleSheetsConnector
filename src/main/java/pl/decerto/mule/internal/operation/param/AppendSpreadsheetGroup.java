package pl.decerto.mule.internal.operation.param;

import java.util.List;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import pl.decerto.mule.internal.metadata.SheetNameValueProvider;

public class AppendSpreadsheetGroup {

	@Parameter
	@Summary("Id of spreadsheet")
	private String spreadsheetId;

	@Parameter
	@Summary("Sheet Name")
	@OfValues(SheetNameValueProvider.class)
	private String sheetName;

	@Parameter
	@Summary("List of values to insert into sheet")
	private List<Object> columns;

	public String getSpreadsheetId() {
		return spreadsheetId;
	}

	public void setSpreadsheetId(String spreadsheetId) {
		this.spreadsheetId = spreadsheetId;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<Object> getColumns() {
		return columns;
	}

	public void setColumns(List<Object> columns) {
		this.columns = columns;
	}
}
