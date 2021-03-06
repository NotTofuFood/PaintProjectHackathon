#!/usr/bin/python3

import selectors
import socket
import types

host = "127.0.0.1"
port = 1145
max_packet_size = 4096

sel = selectors.DefaultSelector()
lsock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
lsock.bind((host, port))
lsock.listen()
print('listening on', (host, port))
lsock.setblocking(False)
sel.register(lsock, selectors.EVENT_READ, data=None)

#gets called when a connection is opened
def accept_wrapper(sock):
	conn, addr = sock.accept()  # Should be ready to read
	print('accepted connection from', addr)
	conn.setblocking(False)
	data = types.SimpleNamespace(addr=addr, inb=b'', outb=b'')
	events = selectors.EVENT_READ | selectors.EVENT_WRITE
	sel.register(conn, events, data=data)

#takes the input from client
#returns output
#if returns None, nothing is sent back
def handle_request(input, socket):
	input = input.decode("utf-8").strip()
	args = input.split(" ")
	type = args[0]
	if(type == "CIRCLE" or type == "ERASE"):
		#send to everyone else
		print(type)
		for s in sockets:
			if(s != socket):
				s.send(bytes(input+"\n", "utf-8"))
		return
	else:
		print("SOMETHING ELSE: " + type)
	#if we reached here, the type of the packet was invalid, so send back an error
	return "ERROR: INVALID PACKET"

sockets = []
#gets called when data is recieved by client
def service_connection(key, mask):
	sock = key.fileobj
	data = key.data
	if mask & selectors.EVENT_READ:
		done = False
		while(not done):
			try:
				recv_data = sock.recv(max_packet_size)  # Should be ready to read
			except:
				print('connection forcibly closed by', data.addr)
				if(sock in sockets):
					sockets.remove(sock)
				sel.unregister(sock)
				sock.close()
				return
			if recv_data:
				data.outb += recv_data
				for i in recv_data:
					if(chr(i) == '\n'): # if this packet has a newline, we are done
						done = True
			else:
				print('closing connection to', data.addr)
				if(sock in sockets):
					sockets.remove(sock)
				sel.unregister(sock)
				sock.close()
				return
	if mask & selectors.EVENT_WRITE:
		if data.outb:
			# send data to client
			print('got ', repr(data.outb), ' from ', data.addr)
			if(not key.fileobj in sockets):
				sockets.append(key.fileobj)
			output = handle_request(data.outb, key.fileobj)
			if(output != None):
				sent = sock.send(bytes(output+"\n", 'utf-8'))  # Should be ready to write
			newlineIndex = data.outb.find(bytes('\n', 'utf-8'))
			if(newlineIndex != -1):
				data.outb = data.outb[newlineIndex+1:] # if it was a good packet, remove the recieved data

# Main Loop
while True:
	events = sel.select(timeout=None)
	for key, mask in events:
		if key.data is None:
			accept_wrapper(key.fileobj)
		else:
			service_connection(key, mask)
