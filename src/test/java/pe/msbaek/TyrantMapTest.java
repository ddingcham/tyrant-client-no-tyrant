package pe.msbaek;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class TyrantMapTest {

	static final String TEMPORAL_TEST_PREFIX = "tEMp";

	@Rule
	public TestName testName = new TestName();

	private TyrantMap map;

	@Test(expected = IllegalArgumentException.class)
	public void tEMp_generateWithNull() {
		new TyrantMap(null);
	}

	@Test
	public void tEMp_openWithMock() throws IOException {
		new TyrantMap(new TyrantSocket()).open();
	}

	@Test
	public void get_retrives_what_was_put() throws IOException {
//		byte[] key = "key".getBytes(); // step 2.2
//		byte[] value = "value".getBytes(); // step 2.3
//
//		TyrantMap map = new TyrantMap(); // step 2.1
//		map.put(key, value);
//		assertThat(map.get(key), is(value)); // step 1

		byte[] key = {'k', 'e', 'y'};
		byte[] value = {'v', 'a', 'l', 'u', 'e'};

		map.put(key, value);
		assertThat(map.get(key), is(value));
	}

	@After
	public void tearDown() throws IOException {
		if(isTemporalTest()) return;
		map.close();
	}

	@Before
	public void setUp() throws IOException {
		if(isTemporalTest()) return;
		map = new TyrantMap();
		map.open();
	}

	private boolean isTemporalTest() {
		return testName.getMethodName().contains(TEMPORAL_TEST_PREFIX);
	}

}
