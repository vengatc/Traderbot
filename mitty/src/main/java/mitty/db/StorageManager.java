package mitty.db;

import java.io.File;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;

public class StorageManager {

	static StorageManager instance = new StorageManager();

	private static File myDbEnvPath = new File("/tmp/venky");

	private DataAccessor accessor;

	boolean started = false;

	// Encapsulates the database environment.
	private static DbEnvironment myDbEnv = new DbEnvironment();

   //can be set to manupulate the storage manager for testablity.	
	public static void setPath(String envPath)
	{
		myDbEnvPath =  new File(envPath);
	}
	
	public DataAccessor getAccessor() {
		return accessor;
	}

	static public StorageManager instance() {
		if(instance == null){
			instance= new StorageManager();
		}
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
		System.out.println("myDb environment"+myDbEnvPath);
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