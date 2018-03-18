# Java Simple Search Engine


### Prereqisites

* JDK 1.8
* Maven 3+


### Download
* Git checkout or download as zip

### Build
Open terminal in project root folder and execute commands below to build project

``` mvn clean install ```


### Run Server

* Open terminal in project root folder and execute commands below 

 ```
 cd server/target
 java -jar server.jar
 ```
 
### Run Client

* Open another terminal in project root folder and execute commands below 

 ```
 cd client/target
 java -jar client.jar
 ```
### Client Example
 
Put documents into the search engine by key
```
 java -jar client.jar -p "key1 document content1"
 java -jar client.jar -p "key2 document content2"
 java -jar client.jar -p "key3 document content3"
 ```
Get document by key
 ```
 java -jar client.jar -g key1
 java -jar client.jar -g key3
 ```
 
Search on a string of tokens to return keys of all documents that contain all tokens in the set
 ```
 java -jar client.jar -s "content1 content2"
```



