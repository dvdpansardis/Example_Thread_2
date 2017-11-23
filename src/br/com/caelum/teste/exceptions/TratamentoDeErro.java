package br.com.caelum.teste.exceptions;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratamentoDeErro implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		
		System.out.println("***erro na thread " + t.getName() + " erro " + e.getMessage());

	}

}
