package com.evnthandling.app.publisher;

import java.util.Iterator;
import java.util.List;

import com.evnthandling.app.message.Message;
import com.evnthandling.app.observer.MessageObserver;
import com.evnthandling.app.observer.Observer;

public class MessagePublisher {

	
	public void notifyUpdate(List<Observer> observers, Message message) {
//		for(Observer observerObj : observers) {
//			if(observerObj instanceof MessageObserver) {
//				MessageObserver messageObserver = (MessageObserver) observerObj;
//				messageObserver.onSalt(message.getSalt());
//				messageObserver.onMessage(10L, message.getMessage());
//			}
//		}
		
		for (Iterator<Observer> iter = observers.iterator(); iter.hasNext(); ) {
			Observer observerObj = iter.next();
			if(observerObj instanceof MessageObserver) {
				MessageObserver messageObserver = (MessageObserver) observerObj;
				messageObserver.onSalt(message.getSalt());
				messageObserver.onMessage(10L, message.getMessage());
			}
		}
    }
}
