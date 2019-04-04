package pe.msbaek;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pe.msbaek.mock.io.TyrantSocket;

import java.io.*;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class TyrantMapTest {

	private final byte[] key = new byte[]{'k', 'e', 'y'};
	private final byte[] value = new byte[]{'v', 'a', 'l', 'u', 'e'};

	private TyrantMap tyrantMap;

	@Test
	public void get_retrives_what_was_put() throws IOException {
		tyrantMap.put(key, value);
		assertThat(tyrantMap.get(key), is(value));
	}

	@Test
	public void get_returns_null_if_key_not_found() throws IOException {
		assertThat(tyrantMap.get(key), is(nullValue()));
	}

	@Test
	public void clear_deletes_all_values() throws IOException {
		tyrantMap.put(key, value);
		tyrantMap.clear();
		assertThat(tyrantMap.get(key), is(nullValue()));
	}

	@Test
	public void remove_removes_specified_element() throws IOException {
		tyrantMap.put(key, value);
		tyrantMap.remove(key);
		assertThat(tyrantMap.get(key), is(nullValue()));
	}

	@Test
	public void remove_missing_key_does_nothing() throws IOException {
		tyrantMap.remove(key);
	}

	@Test
	public void empty_map_size_should_be_zero() throws IOException {
		assertThat(tyrantMap.size(), is(0l));
	}

	@Test
	public void one_element_map_size_should_be_one() throws IOException {
		tyrantMap.put(key, value);
		assertThat(tyrantMap.size(), is(1l));
	}

	@Test
	public void iterate_over_an_empty_map() {
		for(byte [] ignored : tyrantMap)
			fail();
	}

	@Test
	public void iterate_over_an_two_element_map() throws IOException {
		int counter = 0;
		tyrantMap.put(key, value);
		tyrantMap.put("key2".getBytes(), value);
		for(byte [] ignored : tyrantMap) {
			assertThat(ignored, is(value));
			counter++;
		}

		assertThat(counter , is(2));
	}

	@Test
	public void remove_one_element() throws IOException {
		tyrantMap.put(key, value);
		Iterator<byte[]> all = tyrantMap.iterator();
		all.next();
		all.remove();
		assertThat(tyrantMap.get(key), is(nullValue()));
	}

	@Test(expected = IllegalStateException.class)
	public void remove_before_next() throws IOException {
		tyrantMap.put(key, value);
		Iterator<byte[]> all = tyrantMap.iterator();
		all.remove();
		assertThat(tyrantMap.get(key), is(nullValue()));
	}

	@After
	public void tearDown() throws IOException {
		tyrantMap.close();
	}

	@Before
	public void setUp() throws IOException {
		tyrantMap = new TyrantMap(new TyrantSocket());
		tyrantMap.open();
	}

}
