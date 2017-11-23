package br.com.caelum.server.teste;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratamentoExcecao implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Deu excecao na " + t.getName() + ", erro " + e.getMessage());
	}

}
