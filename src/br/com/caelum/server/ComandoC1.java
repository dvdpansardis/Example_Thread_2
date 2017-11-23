package br.com.caelum.server;

import java.io.PrintStream;

public class ComandoC1 implements Runnable {

	private PrintStream respondendoCliente;

	public ComandoC1(PrintStream respondendoCliente) {
		this.respondendoCliente = respondendoCliente;
	}

	@Override
	public void run() {

		System.out.println("Executando comando C1");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		respondendoCliente.println("Comando C1 executado com sucesso");
	}

}
