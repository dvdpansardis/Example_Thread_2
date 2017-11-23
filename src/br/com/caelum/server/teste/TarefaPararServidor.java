package br.com.caelum.server.teste;

public class TarefaPararServidor implements Runnable {

    private ServidorDeTeste2 servidor;

    //recebendo o servidor como parametro
    public TarefaPararServidor(ServidorDeTeste2 servidor) {
        this.servidor = servidor;
    }

    public void run() {
        //chamando o método estaRodando()
        System.out.println("Servidor comecando, estaRodando=" + this.servidor.estaRodando());
        while (!this.servidor.estaRodando()) {
        }

        System.out.println("Servidor rodando, estaRodando=" + this.servidor.estaRodando());

        while (this.servidor.estaRodando()) {
        }

        System.out.println("Servidor terminando, estaRodando=" + this.servidor.estaRodando());
    }
}
