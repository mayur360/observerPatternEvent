package com.evnthandling.app.observer;

import com.evnthandling.app.publisher.Sink;

public class MessageObserver implements Observer {

	private long id;
	private byte[] salt;
	private byte[] message;
	
	
	public long getId() {
		return id;
	}

	public byte[] getSalt() {
		return salt;
	}

	public byte[] getMessage() {
		return message;
	}

	@Override
	public void onSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public void onMessage(long id, byte[] message) {
		this.id = id;
		this.message = message;
		
		//sink.
	}

	

	

}
