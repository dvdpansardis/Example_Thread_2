package br.com.caelum.cilent;

import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception {

		System.out.println("Iniciando cliente");
		
		Socket socket = new Socket("localhost", 12345);
		System.out.println("Estabelecendo conexao");

		Thread threadEnviaComando = new Thread(new TarefaEnviaComando(socket));
		
		Thread threadRecebeDoServidor = new Thread(new TarefaRecebeDoServidor(socket));

		threadRecebeDoServidor.start();
		threadEnviaComando.start();
		
		// faz que a thread que executa essa linha fica esperando (no nosso caso main). 
		// só volta a ser executada quando `threadEnviaComando` terminar. 
		threadEnviaComando.join(); 
		
		System.out.println("Fechando socket do cliente");
		socket.close();
	}

}
