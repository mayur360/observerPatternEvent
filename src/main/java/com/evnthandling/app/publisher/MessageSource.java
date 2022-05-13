package com.evnthandling.app.publisher;

import java.util.ArrayList;
import java.util.List;

import com.evnthandling.app.message.Message;
import com.evnthandling.app.observer.Observer;

public class MessageSource implements Source {
	
	private List<Observer> observers = new ArrayList<>();

	public List<Observer> getObservers() {
		return observers;
	}

	@Override
	public void subscribe(Observer observer) {
		 observers.add(observer);
	}
	
	public void publishMessages(Message message) throws InterruptedException {
		System.out.println("INSIDE publishMessages");
		MessagePublisher messagePublisher = new MessagePublisher();
		messagePublisher.notifyUpdate(observers, message);

	}
}


