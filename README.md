Zero Copy Send in the JVM
=========================

This is a small example project that demonstrates how a client can send some data on disk to a socket while avoiding the kernel copying the data into user space. See this [wikipedia article](https://en.wikipedia.org/wiki/Zero-copy) for more info.

To run the eaxmple, execute:

	mvn compile exec:java -Dexec.mainClass=billslacum.ZeroCopy
	
It should print out some lorem-ipsum goodness and then exit.