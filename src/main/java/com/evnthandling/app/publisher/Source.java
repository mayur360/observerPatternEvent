package com.evnthandling.app.publisher;

import com.evnthandling.app.observer.Observer;

public interface Source {
	void subscribe(Observer observer);
}


