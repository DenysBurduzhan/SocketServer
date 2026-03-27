# 💬 Socket Chat (Java)

A simple multithreaded chat application in Java using sockets.

---

## 🚀 Features

* Multiple clients can connect to the server
* Unique usernames (handshake on connection)
* Public chat (message broadcasting)
* Private messaging (`/pm`)
* Notifications when users join or leave
* Multithreading (each client handled in a separate thread)

---

## 🏗️ Project Architecture

### Server

* Starts the server on port `4045`
* Accepts incoming connections
* Passes clients to a thread pool

### ClientHandler

* Handles a single client
* Reads and sends messages
* Processes commands (`/pm`, `END`, `/exit`)

### ClientManager

* Stores connected clients
* Sends messages to all clients (broadcast)
* Handles private messaging

### ChatClient

* Client-side application
* Sends messages to the server
* Reads incoming messages in a separate thread

### MessageReader

* Dedicated thread for reading messages from the server

---

## ⚙️ How to Run

### 1. Start the Server

```bash
run Server.main()
```

Expected output:

```
start
```

---

### 2. Start Clients

Run multiple instances:

```bash
run Main.main()
```

⚠️ In IntelliJ IDEA, enable:

```
Allow multiple instances
```

---

## 💡 Usage

### Enter Username

After connecting:

```
Enter your name:
```

The username must be unique.

---

### Send a Public Message

```
Hello everyone
```

Result:

```
Denis: Hello everyone
```

---

### Send a Private Message

```
/pm Alex Hi!
```

Result:

For the receiver:

```
[PM] Denis: Hi!
```

For the sender:

```
[PM to Alex]: Hi!
```

---

### Exit the Chat

```
/exit
```

---

## 🧪 Testing

Recommended setup:

* 1 server
* 2–5 clients

Test the following:

* message broadcasting
* private messaging
* duplicate username handling
* client disconnection

---

## 🛠️ Technologies

* Java Core
* Sockets (TCP)
* Multithreading (ExecutorService)
* Collections (CopyOnWriteArrayList)

---

## 📌 Future Improvements

* [ ] `/users` command (list of online users)
* [ ] Message timestamps
* [ ] GUI (Swing)
* [ ] Web version (Servlets / HTTP)
* [ ] REST API
* [ ] WebSocket chat

---

## 📚 What I Learned

* Working with sockets
* Multithreading
* Client-server architecture
* Command handling
* Thread synchronization

---

## 👨‍💻 Author

Denys Burduzhan

---

## 💬 Notes

This project was created for learning purposes to practice Java Core, networking, and multithreading.
