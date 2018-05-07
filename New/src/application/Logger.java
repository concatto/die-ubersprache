package application;

import java.util.function.Consumer;

public class Logger {
	private static Consumer<String> consumer = null;
	
	public static void initialize(Consumer<String> consumer) {
		Logger.consumer = consumer;
	}
	
	public static void warn(String warning) throws IllegalStateException {
		if (Logger.consumer == null) {
			throw new IllegalStateException("The logger was not initialized. Invoke the initialize(Consumer) function.");
		}
		
		Logger.consumer.accept(warning);
	}
}
