package br.com.caelum.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefa implements Runnable {

	private Socket socket;
	private Server server;
	private ExecutorService threadPool;
	private BlockingQueue<String> filaComandos;

	public DistribuirTarefa(Socket socket, BlockingQueue<String> filaComandos, Server server, ExecutorService threadPool) {
		this.socket = socket;
		this.filaComandos = filaComandos;
		this.server = server;
		this.threadPool = threadPool;
	}

	@Override
	public void run() {

		try {
			System.out.println("Distribuindo tarefa para " + socket);

			Scanner entradaCliente = new Scanner(socket.getInputStream());

			PrintStream respondendoCliente = new PrintStream(socket.getOutputStream());

			while (entradaCliente.hasNext()) {
				String comando = entradaCliente.nextLine();

				System.out.println("Comando recebido " + comando);
				
				switch (comando) {

				case "c1": {
					ComandoC1 c1 = new ComandoC1(respondendoCliente);
					threadPool.execute(c1);
					break;
				}
				case "c2": {
					ComandoC2ChamaWS c2WS = new ComandoC2ChamaWS(respondendoCliente);
					ComandoC2AcessaBanco c2Banco = new ComandoC2AcessaBanco(respondendoCliente);
					Future<String> futureWS = threadPool.submit(c2WS);
					Future<String> futureBanco = threadPool.submit(c2Banco);
					
					threadPool.submit(new JuntaResultados(futureWS, futureBanco, respondendoCliente));
					
					break;
				}
				case "c3":{
					this.filaComandos.put(comando); //lembrando, bloqueia se tiver cheia
					respondendoCliente.println("Comando c3 adicionado na fila");
					break;
				}
				case "fim": {
					respondendoCliente.println("servidor terminado");
					server.parar();
					break;
				}
				default: {
					respondendoCliente.println("comando não encontrado");
				}

				}

			}

			entradaCliente.close();
		} catch (

		Exception e) {
			throw new RuntimeException(e);
		}

	}

}
