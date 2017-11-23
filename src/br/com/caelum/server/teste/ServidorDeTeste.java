package br.com.caelum.server.teste;

public class ServidorDeTeste {

	// private boolean estaRodando = false; //Fica em cache
	// private AtomicBoolean estaRodando = new AtomicBoolean(false); //Maneira
	// mais atual de corrigir o problema
	private volatile boolean estaRodando = false;

	public static void main(String[] args) throws InterruptedException {
		ServidorDeTeste servidor = new ServidorDeTeste();
		servidor.rodar();
		servidor.alterandoAtributo();
	}

	private void rodar() {
		Thread tarefa = new Thread(new Runnable() {

			public void run() {
				System.out.println("Servidor começando, estaRodando = " + estaRodando);

				while (!estaRodando) {
				}

				if(estaRodando){
					throw new RuntimeException("erro teste!!!");
				}
				
				System.out.println("Servidor rodando, estaRodando = " + estaRodando);

				while (estaRodando) {
				}

				System.out.println("Servidor terminando, estaRodando = " + estaRodando);
			}
		}); 
		
		tarefa.setUncaughtExceptionHandler(new TratamentoExcecao());
		
		tarefa.start();
	}

	private void alterandoAtributo() throws InterruptedException {
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = true");
		estaRodando = true;

		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = false");
		estaRodando = false;
	}

}
