package org.usfirst.frc.team5431.threads;

public abstract class TemplateThread extends Thread{
	
	public TemplateThread(double ticks){
		tps=ticks;
	}

	final double tps;// ticks per second

	@Override
	public void run() {
		try{
		init();
		while (true) {
			sleep((long)(1000000000 /tps));
			action();
		}
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	public abstract void action();
	public abstract void init();


}
