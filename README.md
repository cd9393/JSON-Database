# JSON-Database

## Description
In this stage, you will improve your client and server by adding the ability to work with files.

The server should keep the database on the hard drive in the db.json file and update only after setting a new value or deleting one. You should store the JSON file in the /server/data folder.

Let's think about another important aspect: when your database server becomes very popular, it won’t be able to process a lot of requests because it can only process one request at a time. To avoid that, you can parallelize the server's work using executors, so that every request is parsed and handled in a separate executor's task. The main thread should just wait for incoming requests.

For this kind of functionality, you need synchronization because all your threads will work with the same file. Even after parallelizing, you need to preserve the integrity of the database. Of course, you can't write the file in two separate threads simultaneously, but if no one is currently writing to the file, a lot of threads can read it, and no-one can interrupt the other since no-one is modifying it. This behavior is implemented in java.util.concurrent.locks.ReentrantReadWriteLock class. It allows multiple readers of the resource but only a single writer. Once a writer locks the resource, it waits until all the readers finish reading and only then starts to write. The readers can freely read the file even though other readers locked it, but if the writer locks the file, no readers can read it.

To use this class, you need two locks: read lock and write lock. See the snippet below:

ReadWriteLock lock = new ReentrantReadWriteLock();
Lock readLock = lock.readLock();
Lock writeLock = lock.writeLock();
Every time you want to read the file, invoke readLock.lock(). After reading, invoke readLock.unlock(). Do the same with writeLock, but only when you want to change the data.

Also, you should implement the ability to read a request from a file. If the -in argument followed by the file name was provided, you should read a request from that file. The file will be stored in /client/data.

Here are some examples of the input file contents:

{"type":"set","key":"name","value":"Kate"}
{"type":"get","key":"name"}
{"type":"delete","key":"name"}
