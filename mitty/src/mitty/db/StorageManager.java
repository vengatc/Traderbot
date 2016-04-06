package mitty.db;

import java.io.File;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;

public class StorageManager {

	static StorageManager instance = new StorageManager();

	private static File myDbEnvPath = new File("/tmp/venky4");

	private DataAccessor accessor;

	boolean started = false;

	// Encapsulates the database environment.
	private static DbEnvironment myDbEnv = new DbEnvironment();

	public DataAccessor getAccessor() {
		return accessor;
	}

	static public StorageManager instance() {
		if (!instance.started) {
			synchronized (StorageManager.class) {
				if (!instance.started) {
					instance.start();
				}
			}
		}
		return instance;
	}

	void start() {

		if (!myDbEnvPath.exists()) {
			myDbEnvPath.mkdirs();
		}
		boolean readOnly = false;
		myDbEnv.setup(myDbEnvPath, // path to the environment home
				false); // is this environment read-only?

		// Open the data accessor. This is used to retrieve
		// persistent objects.
		accessor = new DataAccessor(myDbEnv.getEntityStore());
		started = true;

	}

	public void sync() {

		myDbEnv.sync();

	}

	public void flush() {
   //syncs only the entity store.
		myDbEnv.flush();

	}

	public void close() {
		System.out.println("closing storage");
		myDbEnv.close();

		started = false;
	}

}