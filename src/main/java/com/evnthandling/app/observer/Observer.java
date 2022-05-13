package com.evnthandling.app.observer;

public interface Observer {
	void onSalt(byte[] salt);

	void onMessage(long id, byte[] message);
}
