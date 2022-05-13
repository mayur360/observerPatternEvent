package com.evnthandling.app.engine;

import com.evnthandling.app.observer.Observer;
import com.evnthandling.app.publisher.Sink;
import com.evnthandling.app.publisher.Source;

public abstract class Engine {

	private Source source;
	private Sink sink;
	private int capacity;

	public Engine(Source source, Sink sink, int capacity) {
	this.source = source;
	this.sink = sink;
	this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void start() throws InterruptedException {
		Observer observer = createObserver(sink);
		source.subscribe(observer);
	}

	protected abstract Observer createObserver(Sink sink);

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Sink getSink() {
		return sink;
	}

	public void setSink(Sink sink) {
		this.sink = sink;
	}
	
	

}
