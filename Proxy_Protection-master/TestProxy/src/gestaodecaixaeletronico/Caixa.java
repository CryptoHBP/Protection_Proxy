package gestaodecaixaeletronico;

import gestaodecontas.*;

public class Caixa {
	private Terminal meuCaixaEletronico;
	private CadastroContas bdContas;
	private double saldoCaixa;
	private ProxyInterface conta1, conta2;
	
	public Caixa(Terminal terminal, CadastroContas bd) {
		this.meuCaixaEletronico = terminal;
		this.bdContas = bd;
	}
	
	public double consultaSaldo(int numeroDaConta, int senha) {
		ProxyInterface conta;
		conta = this.bdContas.buscaConta(numeroDaConta);
		if (conta != null){
			return conta.getSaldo(senha);
		}
		else{
			return -1;
		}
	}
	
	
	//adicionado
	public boolean creditaEmDinheiro(int numDaConta, int senha, double valor) {
		conta1 = this.bdContas.buscaConta(numDaConta);
		if (conta1 == null || conta1.creditaValor(senha, valor, "Creditado") == false) 
			return  false;
		else {
			this.saldoCaixa += valor;
			return true;
		}	
	} 
	
	//Adcionado chaque
	public boolean creditaEmChaque(int numContaOrigem, int numContaDestino, double valor) {
		conta1 = bdContas.buscaConta(numContaOrigem);
		conta2 = bdContas.buscaConta(numContaDestino);
		if (conta1 != null && conta2 != null && conta2.creditaCheque(conta1, valor)) {
			return true;
		}
		return false;
	} 
	
	//Adcionado a transferencia
	public boolean transferencia(int numContaOrigem, int senha, int numContaDestino, double valor) {
		conta1 = bdContas.buscaConta(numContaOrigem);
		conta2 = bdContas.buscaConta(numContaDestino);
		
		if (conta1 != null && conta2 != null && conta1.transferePara(conta2 , senha, valor, "Transferencia") ) {
			return true;
		}
		return false;
				
	}
	
	//Adicionado -> extrato
	public String extrato(int numConta, int senha) {
		conta1 = bdContas.buscaConta(numConta);
		
		if (conta1 != null)
			return (conta1.extrato(senha));
		else 
			return null;
	}
	
	
	public boolean efetuaSaque(int numeroDaConta, double valor, int senha) {
		if(valor < 0 || (valor%10) != 0 || valor > 200 || valor > this.saldoCaixa){
			return false;
		}
			ProxyInterface conta = bdContas.buscaConta(numeroDaConta);
			if (conta == null || conta.debitaValor(senha, valor, "Saque Automatico") == false){
				return false;
			}
			
			this.liberaNotas((int)(valor/50));
			this.saldoCaixa -= valor;
			
			if(this.saldoCaixa < 500){
				this.meuCaixaEletronico.setModo(0);
			}
			return true;
	}
	
	public void recarrega() {
		this.saldoCaixa = 500;
		this.meuCaixaEletronico.setModo(1);
	}
	
	private void liberaNotas(int qtd) {
		while(qtd-->0){
			System.out.println("===/ R$50,00 /===");
		}
	}
	
}
