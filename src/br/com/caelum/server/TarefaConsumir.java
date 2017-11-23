package br.com.caelum.server;

import java.util.concurrent.BlockingQueue;

public class TarefaConsumir implements Runnable {

	private BlockingQueue<String> fila;

	public TarefaConsumir(BlockingQueue<String> fila) {
		this.fila = fila;
	}

	@Override
	public void run() {

		try {

			String comando = null;

			//A documentação do BlockingQueue já diz que ele pode ser usado assim de maneira ThreadSafe.
			while ((comando = this.fila.take()) != null) {
				System.out.println("Consumindo comando " + comando + " - thread " + Thread.currentThread().getName());

				Thread.sleep(5000);
			}

		} catch (InterruptedException e) {

			throw new RuntimeException(e);

		}

	}

}
