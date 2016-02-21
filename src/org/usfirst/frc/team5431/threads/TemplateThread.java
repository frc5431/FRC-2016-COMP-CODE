package org.usfirst.frc.team5431.threads;

public abstract class TemplateThread implements Runnable{
	
	public TemplateThread(double ticks){
		tps=ticks;
	}

	final double tps;// ticks per second

	@Override
	public void run() {
		// set the values inside the input boxes to the correct one.
		// otherwise, it will raise errors
		boolean init = false;
		long lastTime = System.nanoTime();
		double ns = 1000000000 / tps;// 10 times per second
		// checks immediately for connection
		double delta = 1;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				if (!init) {
					init();
					init = true;
				}
				action();
				delta--;
			}
		}
	}
	
	public abstract void action();
	public abstract void init();


}
