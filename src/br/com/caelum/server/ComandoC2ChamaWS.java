package br.com.caelum.server;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String> {

	private PrintStream respondendoCliente;

	public ComandoC2ChamaWS(PrintStream respondendoCliente) {
		this.respondendoCliente = respondendoCliente;
	}

	@Override
	public String call() throws Exception {

		System.out.println("Servidor recebeu comando C2 - WS");

		respondendoCliente.println("Processando comando C2 - WS");

		Thread.sleep(15000);

		int numero = new Random().nextInt(100) + 1;
		
		// throw new RuntimeException("deu ruim!");

		respondendoCliente.println("Servidor finalizou comando C2  - WS com sucesso");

		return String.valueOf(numero);
	}

}
