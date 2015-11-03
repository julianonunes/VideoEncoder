package videoencoder.encoding;

import static org.junit.Assert.*;

import org.junit.Test;

import videoencoder.encoding.base.IEncoder;

public class EncodingTest {

	private static final String TEST_FILE = "http://dinamica-sambatech.s3.amazonaws.com/sample.dv";
	
	@Test
	public void encodeMustReturnJobId() {
		IEncoder encoder = new Zencoder();
		
		assertNotNull(encoder.encode(TEST_FILE));
	}

}
