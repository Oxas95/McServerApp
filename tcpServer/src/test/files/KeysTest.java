package test.files;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import mcServerApp.files.Keys;

class KeysTest {

	/* conservee pour exemple
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}
	
	*/
	
	private void testValueWithBounds(Keys k, int inf, int sup) {
		/* test des bornes superieur et inferieur */
		assertFalse(k.check(inf - 1));
		assertTrue(k.check(inf));
		assertTrue(k.check(sup));
		assertFalse(k.check(sup + 1));
		
		/* test de valeurs incorrects */
		assertFalse(k.check(1001.1));
		assertFalse(k.check("1111"));
	}
	
	private void testPassword(Keys k) {
		assertFalse(k.check(""));
		assertFalse(k.check(1202));
		assertFalse(k.check("1234567"));
		assertTrue(k.check("12345678"));
	}
	
	private void testBoolean(Keys k) {
		assertTrue(k.check(true));
		assertTrue(k.check(false));
		assertFalse(k.check("false"));
		assertFalse(k.check(0));
	}
	
	private void testExistingFile(Keys k) {
		final String batchPath = "testFile.txt";
		File file = new File(batchPath);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			file.deleteOnExit();
		}
		
		assertFalse(k.check(""));
		assertFalse(k.check(k));
		assertTrue(k.check(batchPath));
		assertFalse(k.check("../../../../../../../../../../../../../../../../../a".replace("/", File.separator)));
		assertFalse(k.check("src"));
	}
	
	@Test
	void testAppPort() {
		testValueWithBounds(Keys.appPort, 1001, 65534);
	}
	
	@Test
	void testAutoStart() {
		testBoolean(Keys.autoStart);
	}
	
	@Test
	void testBatchPath() {
		testExistingFile(Keys.batchPath);
	}
	
	@Test
	void testRconPassword() {
		testPassword(Keys.rconPassword);
	}
	
	@Test
	void testRconPort() {
		testValueWithBounds(Keys.rconPort, 1001, 65534);
	}
	
	@Test
	void testStartPassword() {
		testPassword(Keys.startPassword);
	}
	
	@Test
	void testStopPassword() {
		testPassword(Keys.stopPassword);
	}
	
	@Test
	void testTimeout() {
		testValueWithBounds(Keys.timeout, 0, 15 * 60);
	}
}
