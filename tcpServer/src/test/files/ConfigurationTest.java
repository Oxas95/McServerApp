package test.files;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mcServerApp.files.Configuration;

class ConfigurationTest {

	final String path 				= "src\\test\\files\\jsonFilesTest\\";
	final String path2 				= "src\\test\\files\\jsonFilesTestOriginals\\";
	final String array 				= path + "array"			+ ".json";
	final String arrayInValue 		= path + "arrayInValue"		+ ".json";
	final String batInvalide		= path + "batInvalide"		+ ".json";
	final String batInvalide2		= path + "batInvalide2"		+ ".json";
	final String cleEnTrop 			= path + "cleEnTrop"		+ ".json";
	final String duplicate 			= path + "duplicate"		+ ".json";
	final String errorSyntax 		= path + "errorSyntax" 		+ ".json";
	final String generation 		= path + "generation" 		+ ".json";
	final String jsonVide 			= path + "jsonVide"			+ ".json";
	final String missing 			= path + "missing" 			+ ".json";
	final String passwordInvalide 	= path + "passwordInvalide" + ".json";
	final String samePassword 		= path + "samePassword" 	+ ".json";
	final String valide 			= path + "valide" 			+ ".json";
	final String vide 				= path + "vide" 			+ ".json";
	
	@BeforeEach
	void setUp() throws Exception {
		File[] subFiles = new File(path).listFiles();
		for(File subFile : subFiles) subFile.delete();
		
		FileUtils.copyDirectory(new File(path2), new File(path));
	}
	
	@AfterEach
	void tearDown() throws Exception {
		File[] subFiles = new File(path).listFiles();
		for(File subFile : subFiles) subFile.delete();
	}
	
	@Test
	void testArray() {
		assertFalse((new Configuration(array, false)).isValid());
	}
	
	@Test
	void testArrayInValue() {
		assertFalse((new Configuration(arrayInValue, false)).isValid());
	}
	
	@Test
	void testBatInvalide() {
		assertFalse((new Configuration(batInvalide, false)).isValid());
	}
	
	@Test
	void testBatInvalide2() {
		assertFalse((new Configuration(batInvalide2, false)).isValid());
	}
	
	@Test
	void testCleEnTrop() {
		assertFalse((new Configuration(cleEnTrop, false)).isValid());
	}
	
	@Test
	void testDuplicate() {
		assertFalse((new Configuration(duplicate, false)).isValid());
	}
	
	@Test
	void testErrorSyntax() {
		assertFalse((new Configuration(errorSyntax, false)).isValid());
	}
	
	@Test
	void testGeneration() {
		assertFalse((new Configuration(generation, false)).isValid());
	}
	
	@Test
	void testJsonVide() {
		assertFalse((new Configuration(jsonVide, false)).isValid());
	}
	
	@Test
	void testMissing() {
		assertFalse((new Configuration(missing, false)).isValid());
	}
	
	@Test
	void testPasswordInvalide() {
		assertFalse((new Configuration(passwordInvalide, false)).isValid());
	}
	
	@Test
	void testSamePassword() {
		assertFalse((new Configuration(samePassword, false)).isValid());
	}
	
	@Test
	void testVide() {
		assertFalse((new Configuration(vide, false)).isValid());
	}
	
	@Test
	void testValide() {
		assertTrue((new Configuration(valide, false)).isValid());
	}
}
