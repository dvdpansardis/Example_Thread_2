package br.com.caelum.server;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JuntaResultados implements Callable<Void> {

	private Future<String> futureWS;
	private Future<String> futureBanco;
	private PrintStream respondendoCliente;

	public JuntaResultados(Future<String> futureWS, Future<String> futureBanco, PrintStream respondendoCliente) {
		this.futureWS = futureWS;
		this.futureBanco = futureBanco;
		this.respondendoCliente = respondendoCliente;
	}

	@Override
	public Void call() {

		System.out.println("Aguardando dados dos futures WS e Banco");
		
		
		try {
			String resultadoWS = this.futureWS.get(15, TimeUnit.SECONDS);
			String resultadoBanco = this.futureBanco.get(15, TimeUnit.SECONDS);
			
			this.respondendoCliente.println("Resultado comando c2: " + resultadoWS + " - " + resultadoBanco);
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			System.out.println("Timeout na execução do comando C2");
			this.respondendoCliente.println("Timeout na execução do comando C2");
			this.futureWS.cancel(true);
			this.futureBanco.cancel(true);
		}
		
		System.out.println("Finalizou JuntaResultados");
		
		return null;
	}

}
