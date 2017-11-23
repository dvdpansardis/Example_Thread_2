package br.com.caelum.server;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {

	//private static int numero = 1;
	private ThreadFactory defaultFactory;

	public FabricaDeThreads(ThreadFactory defaultFactory) {
		this.defaultFactory = defaultFactory;
	}

	@Override
	public Thread newThread(Runnable r) {
		
		//Thread thread = new Thread(r, "Thread servidor de tarefas " + numero);
		
		Thread thread = defaultFactory.newThread(r);
		
		//numero++;
		
		thread.setUncaughtExceptionHandler(new TratamentoExcecao());
		thread.setDaemon(true);
		
		return thread;
	}

}
