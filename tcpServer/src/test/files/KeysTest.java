package test.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import mcServerApp.files.InvalidKeysValueException;
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
	
	/** Verifier la methode check() */
	
	private void testValueWithBoundsCheck(Keys k, int inf, int sup) {
		/* test des bornes superieur et inferieur */
		assertFalse(k.check(inf - 1));
		assertTrue(k.check(inf));
		assertTrue(k.check(sup));
		assertFalse(k.check(sup + 1));
		
		/* test de valeurs incorrects */
		assertFalse(k.check(1001.1));
		assertFalse(k.check("1111"));
	}
	
	private void testPasswordCheck(Keys k) {
		assertFalse(k.check(""));
		assertFalse(k.check(1202));
		assertFalse(k.check("1234567"));
		assertTrue(k.check("12345678"));
	}
	
	private void testBooleanCheck(Keys k) {
		assertTrue(k.check(true));
		assertTrue(k.check(false));
		assertFalse(k.check("false"));
		assertFalse(k.check(0));
	}
	
	private void testExistingFileCheck(Keys k) {
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
	void testAppPortCheck() {
		testValueWithBoundsCheck(Keys.appPort, 1001, 65534);
	}
	
	@Test
	void testAutoStartCheck() {
		testBooleanCheck(Keys.autoStart);
	}
	
	@Test
	void testBatchPathCheck() {
		testExistingFileCheck(Keys.batchPath);
	}
	
	@Test
	void testNoGuiCheck() {
		testBooleanCheck(Keys.noGui);
	}
	
	@Test
	void testRconPasswordCheck() {
		testPasswordCheck(Keys.rconPassword);
	}
	
	@Test
	void testRconPortCheck() {
		testValueWithBoundsCheck(Keys.rconPort, 1001, 65534);
	}
	
	@Test
	void testStartPasswordCheck() {
		testPasswordCheck(Keys.startPassword);
	}
	
	@Test
	void testStopPasswordCheck() {
		testPasswordCheck(Keys.stopPassword);
	}
	
	@Test
	void testTimeoutCheck() {
		testValueWithBoundsCheck(Keys.timeout, 0, 15 * 60);
	}
	
	/** Verifier la methode parse */
	
	private void testIntegerParse(Keys k) throws InvalidKeysValueException {
		assertEquals(k.parse("100"), 100);
		assertThrows(InvalidKeysValueException.class, () -> {
			k.parse("100.01");
		});
		assertThrows(InvalidKeysValueException.class, () -> {
			k.parse("aaaaaaa");
		});
	}
	
	private void testBooleanParse(Keys k) throws InvalidKeysValueException {
		assertEquals(k.parse("true"), true);
		assertEquals(k.parse("false"), false);
		assertEquals(k.parse("TRUE"), true);
		assertEquals(k.parse("falSE"), false);
		assertThrows(InvalidKeysValueException.class, () -> {
			k.parse("truelkdfjsf");
		});
		assertThrows(InvalidKeysValueException.class, () -> {
			k.parse("aaaaaaa");
		});
	}
	
	@Test
	void testAppPortParse() throws InvalidKeysValueException {
		testIntegerParse(Keys.appPort);
	}
	
	@Test
	void testAutoStartParse() throws InvalidKeysValueException {
		testBooleanParse(Keys.autoStart);
	}
	
	@Test
	void testBatchPathParse() throws InvalidKeysValueException {
		assertTrue(Keys.batchPath.parse("testStr").equals("testStr"));
	}
	
	@Test
	void testNoGuiParse() throws InvalidKeysValueException {
		testBooleanParse(Keys.noGui);
	}
	
	@Test
	void testRconPasswordParse() throws InvalidKeysValueException {
		assertTrue(Keys.rconPassword.parse("testStr").equals("testStr"));
	}
	
	@Test
	void testRconPortParse() throws InvalidKeysValueException {
		testIntegerParse(Keys.rconPort);
	}
	
	@Test
	void testStartPasswordParse() throws InvalidKeysValueException {
		assertTrue(Keys.startPassword.parse("testStr").equals("testStr"));
	}
	
	@Test
	void testStopPasswordParse() throws InvalidKeysValueException {
		assertTrue(Keys.stopPassword.parse("testStr").equals("testStr"));
	}
	
	@Test
	void testTimeoutParse() throws InvalidKeysValueException {
		testIntegerParse(Keys.timeout);
	}
}
