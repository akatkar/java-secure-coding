package secure.coding.chapter01.ids.ids08.solution;

import java.io.FileInputStream;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Keywords {
	private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private static CharBuffer log;
	private static final Object lock = new Object();
	// Map log file into memory, and periodically reload
	static {
		try {
			FileChannel channel = new FileInputStream("path").getChannel();
			// Get the file's size and map it into memory
			int size = (int) channel.size();
			final MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
			Charset charset = Charset.forName("ISO-8859-15");
			final CharsetDecoder decoder = charset.newDecoder();
			log = decoder.decode(mappedBuffer); // Read file into char buffer

			Runnable periodicLogRead = new Runnable() {
				@Override
				public void run() {
					synchronized (lock) {
						try {
							log = decoder.decode(mappedBuffer);
						} catch (CharacterCodingException e) {
							// Forward to handler
						}
					}
				}
			};
			scheduler.scheduleAtFixedRate(periodicLogRead, 0, 5, TimeUnit.SECONDS);
		} catch (Throwable t) {
			// Forward to handler
		}
	}

	public static Set<String> suggestSearches(String search) {
		synchronized (lock) {
			Set<String> searches = new HashSet<>();

			// This compliant solution filters out non-alphanumeric characters (except space and single
			// quote) from the search string, which prevents regex injection previously described.
			StringBuilder sb = new StringBuilder(search.length());
			for (int i = 0; i < search.length(); ++i) {
				char ch = search.charAt(i);
				if (Character.isLetterOrDigit(ch) || ch == ' ' || ch == '\'') {
					sb.append(ch);
				}
			}
			search = sb.toString();

			// Construct regex dynamically from user string
			String regex = "(.*? +public\\[\\d+\\] +.*" + search + ".*)";
			Pattern keywordPattern = Pattern.compile(regex);
			Matcher logMatcher = keywordPattern.matcher(log);
			while (logMatcher.find()) {
				String found = logMatcher.group(1);
				searches.add(found);
			}
			return searches;
		}
	}

}
