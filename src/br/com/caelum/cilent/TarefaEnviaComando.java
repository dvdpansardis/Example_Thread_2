package br.com.caelum.cilent;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TarefaEnviaComando implements Runnable {

	private Socket socket;

	public TarefaEnviaComando(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			System.out.println("Podemos enviar comandos");
			PrintStream saida;
			saida = new PrintStream(socket.getOutputStream());
			Scanner teclado = new Scanner(System.in);
			while (teclado.hasNext()) {
				String linha = teclado.nextLine();

				if (linha.trim().equals(".")){
					System.out.println("Fim da transmissao cliente");
					break;
				}

				saida.println(linha);
			}
			saida.close();
			teclado.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}

}
