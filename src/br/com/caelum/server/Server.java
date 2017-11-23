package br.com.caelum.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

	private ExecutorService threadPool;
	private ServerSocket servidor;
	private AtomicBoolean estaRodando = new AtomicBoolean(false);
	private BlockingQueue<String> filaComandos;

	public Server() throws IOException {
		System.out.println("Iniciando servidor");

		this.servidor = new ServerSocket(12345);

		ThreadFactory defaultFactory = Executors.defaultThreadFactory();

		// this.threadPool = Executors.newFixedThreadPool(4, new
		// FabricaDeThreads(defaultFactory));
		this.threadPool = Executors.newCachedThreadPool(new FabricaDeThreads(defaultFactory));
		// this.threadPool = Executors.newCachedThreadPool();

		this.filaComandos = new ArrayBlockingQueue<>(2);

		this.estaRodando.set(true);

		iniciarConsumidores();
	}

	private void iniciarConsumidores() {

		int numConsumidores = 2;
		for (int i = 0; i < numConsumidores; i++) {

			TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
			this.threadPool.execute(tarefa);

		}

	}

	public static void main(String[] args) throws Exception {

		Server server = new Server();
		server.rodar();
		server.parar();

	}

	public void parar() throws IOException {
		this.estaRodando.set(false);

		servidor.close();

		threadPool.shutdown();
	}

	public void rodar() throws IOException {
		while (estaRodando.get()) {

			try {
				Socket socket = servidor.accept();

				System.out.println("Aceitando novo client na porta " + socket.getPort());

				threadPool.execute(new DistribuirTarefa(socket, filaComandos, this, threadPool));

				// Thread threadCliente = new Thread(new
				// DistribuirTarefa(socket));
				// threadCliente.start();
			} catch (SocketException e) {
				System.out.println("SocketException, esta rodando? " + this.estaRodando);
			}

		}

	}

}
