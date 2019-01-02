package pl.decerto.mule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

public class GoogleSheetsTestCase extends MuleArtifactFunctionalTestCase {

	@Override
	protected String getConfigFile() {
		return "test-mule-config.xml";
	}

	@Test
	public void executeCreateSpreadsheets() throws Exception {
		String spreadsheetId = ((String) flowRunner("createSpreadsheets").run()
				.getMessage()
				.getPayload()
				.getValue());
		assertThat(spreadsheetId, is(org.hamcrest.core.IsNull.notNullValue(String.class)));
	}

}
