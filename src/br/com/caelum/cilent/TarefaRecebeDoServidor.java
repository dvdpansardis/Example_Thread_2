package br.com.caelum.cilent;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TarefaRecebeDoServidor implements Runnable {

	private Socket socket;

	public TarefaRecebeDoServidor(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			System.out.println("Recebendo dados do servidor");
			Scanner respostaServidor;
			respostaServidor = new Scanner(socket.getInputStream());
			while (respostaServidor.hasNext()) {
				String linha = respostaServidor.nextLine();
				System.out.println(linha);
			}
			respostaServidor.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
