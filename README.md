# JSON-Database
In this stage, you will implement the simplest connection between one server and one client. The client should send the server a message: something along the lines of Give me a record # N, where N is an integer number. The server should reply A record # N was sent! to the client.
Both the client and the server should print the received messages to the console. Note that they exchange only these texts, not actual database files.

Before a client connects to the server, the server output should be 'Server started!'.

To connect to the server, the client must know its address, which consists of two parts: IP-address and port. The address of your computer is always "127.0.0.1". The port can be any number between 0 and 65535, but preferably greater than 1024.

Let's take a look at this client-side code:

String address = "127.0.0.1";
int port = 23456;
Socket socket = new Socket(InetAddress.getByName(address), port);
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output = new DataOutputStream(socket.getOutputStream());
The client created a new socket, which means that the client tried to connect to the server. Successful creation of a socket means that the client found the server and managed to connect to it.

After that, you can see the creation of DataInputStream and DataOutputStream objects. These are the input and output streams to the server. If you expect data from the server, you need to write input.readUTF(). This returns the String object that the server sent to the client. If you want to send data to the server, you need to write output.writeUTF(stringText), and this message will be sent to the server.

Now let's look at the server-side code:

String address = "127.0.0.1";
int port = 23456;
ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
Socket socket = server.accept();
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
The server created a ServerSocket object that waits for client connections. When a client connects, the method server.accept() returns the Socket connection to this client. After that, you can see the creation of DataInputStream and DataOutputStream objects: these are the input and output streams to this client, now from the server side. To receive data from the client, write input.readUTF(). To send data to the client, write output.writeUTF(stringText). The server should stop after responding to the client.

Note: the server and the client are different programs that run separately. Your server should run from the main method of the /server/Main class, and the client should run from the main method of the /client/Main class. To test your program you should run the server first so a client can connect to the server.
