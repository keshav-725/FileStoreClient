# FileStoreClient

The File Store Client is a command-line interface for interacting with the File Store Server. It allows users to add, list, remove, update files, perform word count, and find frequency words.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

## Build and Run

1. Clone the repository

`
git clone git@github.com:keshav-725/FileStoreClient.git
`

2. Move to Client Directory 

`cd FileStoreClient`

3. Build the Client Project

`
mvn clean package
`

4. Run the server

`
 java -jar target/{jarFileName} <command> [<file1> <file2> ...]
`
### Replace <command> with one of the supported operations:

#### add: Add files to the server.
#### ls: List files in the server.
#### rm: Remove files from the server.
#### update: Update files in the server.
#### wc: Perform word count on files in the server.
#### freq-words: Find frequency words in files in the server.

#### Replace <file1> <file2> ... with the files to be used for the operation.
