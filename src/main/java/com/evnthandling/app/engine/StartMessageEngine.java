package com.evnthandling.app.engine;

import java.util.Iterator;
import java.util.List;

import com.evnthandling.app.message.Message;
import com.evnthandling.app.observer.MessageObserver;
import com.evnthandling.app.observer.Observer;
import com.evnthandling.app.publisher.MessageSource;
import com.evnthandling.app.publisher.Sink;
import com.evnthandling.app.publisher.Source;

public class StartMessageEngine {

	public static void main(String[] args) throws InterruptedException {
		
		Source source = new MessageSource();
		Sink sink = new Sink();
		MessageEngine messageEngine = new MessageEngine(source, sink, 5);
		// Create producer thread
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                	messageEngine.start();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
		// Create consumer thread
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (messageEngine.getSource() instanceof MessageSource) {
						Message message = new Message();
						MessageSource messageSource = (MessageSource) messageEngine.getSource();
						messageSource.publishMessages(message);
						List<Observer> observers = messageSource.getObservers();
						messageEngine.processMessage(observers);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
 
		 // Start both threads
        t1.start();
        t2.start();
 
        // t1 finishes before t2
        t1.join();
        t2.join();
		
		
		
		
	}

}
