package com.evnthandling.app.demo;

import com.evnthandling.app.message.Message;
import com.evnthandling.app.observer.MessageObserver;
import com.evnthandling.app.publisher.MessageSource;

public class MessageObserverDemo {

	public static void main(String[] args) {
		MessageObserver msgObs = new MessageObserver();
		Message message = new Message();
		
		//Subscribe and Publish messages
		MessageSource msgSource = new MessageSource();
		msgSource.subscribe(msgObs);
		//msgSource.publishMessages(message);

	}

}
