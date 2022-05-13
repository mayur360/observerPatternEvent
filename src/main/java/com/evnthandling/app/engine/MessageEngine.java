package com.evnthandling.app.engine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.evnthandling.app.observer.MessageObserver;
import com.evnthandling.app.observer.Observer;
import com.evnthandling.app.publisher.MessagePublisher;
import com.evnthandling.app.publisher.MessageSource;
import com.evnthandling.app.publisher.Sink;
import com.evnthandling.app.publisher.Source;

public class MessageEngine extends Engine {
	
	

	public MessageEngine(Source source, Sink sink, int capacity) {
		super(source, sink, capacity);
	}

	@Override
	protected Observer createObserver(Sink sink) {
		Observer observer = new MessageObserver();
		return observer;
	}
	
	@Override
	public void start() throws InterruptedException {
		
		//crete observer
		Observer observer = createObserver(getSink());
		System.out.println("INSIDE START PRODUCER");
		 while (true) {
             synchronized (this)
             {
                 // producer thread waits while list
                 // is full
            	 int listSize = 1;
            	 if(getSource() instanceof MessageSource) {
            		 MessageSource messageSource = new MessageSource();
            		 listSize = messageSource.getObservers().size();
            	 }
            	 
                 while (listSize == getCapacity())
                     wait();
                 
                 getSource().subscribe(observer);
                 
				// notifies the consumer thread that
				// now it can start consuming
				notify();

				// makes the working of program easier
				// to understand
				Thread.sleep(1000);
             }
		 }
	}
	
	

	public void processMessage( List<Observer> observers) throws InterruptedException {
		System.out.println("INSIDE processMessage CONSUMER");
//		if(observer instanceof MessageObserver) {
//			MessageObserver messageObserver = (MessageObserver) observer;
//			//messageObserver.onSalt(getSource().getSalt());
//			//Random rd = new Random();
//			//messageObserver.onMessage(rd.nextInt(), getSource().getMessage());
//		}
		
		 while (true) {
			 synchronized (this)
			{
				// consumer thread waits while list
                 // is empty
				while (observers.size() == 0)
					wait();
				
				for (Iterator<Observer> iter = observers.iterator(); iter.hasNext(); ) {
					Observer observerObj = iter.next();
					
					//HASH processing here
					byte[] saltValues = ((MessageObserver)observerObj).getSalt();
					byte[] messageValues = ((MessageObserver)observerObj).getMessage();
					byte[] concateByteArray = concateByteArray(saltValues, messageValues);
					MessageDigest md;
					byte[] hashedByteArray = new byte[] {};
					try {
						md = MessageDigest.getInstance("SHA-256");
						md.update(concateByteArray);

						hashedByteArray = md.digest();

						System.out.println(toHexString(hashedByteArray));
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//call sink
					Random rd = new Random();
					getSink().publishHash(rd.nextInt(), saltValues, messageValues, hashedByteArray);
					
					iter.remove();
				}

				// Wake up producer thread
				notify();

				// and sleep
				Thread.sleep(1000);
			}
		 }
		
		
	}
	
	public String toHexString(byte[] bytes) {
		Formatter result = new Formatter();
		try (result) {
			for (var b : bytes) {
				result.format("%02x", b & 0xff);
			}
			return result.toString();
		}
	}

	public byte[] concateByteArray(byte[] salt, byte[] message) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outputStream.write(salt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] result = outputStream.toByteArray();
		System.out.println(new String(result));
		return result;
	}

}
