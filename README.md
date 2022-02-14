# JSON-Database
In this stage, you will store the database in the JSON format. To work with JSON, we recommend to use GSON library made by Google. It is also included in our project setup. It is a good idea to get familiar with the library beforehand: see zetcode.com for some explanations!

In this stage, you should store the database as a Java JSON object.

The keys should be strings (no more limited integer indexes), and the values should be strings, as well.

Example of JSON database:

{
    "key1": "String value",
    "key2": 2,
    "key3": true
}
Also, you should send to the server a valid JSON (as a string) which includes all the parameters needed to execute the request. Below are a few examples for the set, get, and delete requests. Don't worry about multiple lines: the GSON library can represent them as a single line. Also, don't worry about extra spaces before and after quotes.

Here is what the set request format should look like:

{ "type": "set", "key": "Secret key", "value": "Secret value" }
The responses should be in the JSON format. Please consider the examples below.

{ "response": "OK" }
The get request

{ "type": "get", "key": "Secret key" }
The delete request

{ "type": "delete", "key": "Key that doesn't exist" }
In the case of a get request with a key stored in the database:

{ "response": "OK", "value": "Secret value" }
In the case of a get or delete request with a key that doesn't exist:

{ "response": "ERROR", "reason": "No such key" }
The arguments will be passed to the client in the following format:

-t set -k "Some key" -v "Here is some text to store on the server"
-t is the type of the request, and -k is the key. -v is the value to save in the database: you only need it in case of a set request.
