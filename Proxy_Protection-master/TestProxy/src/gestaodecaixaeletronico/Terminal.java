package gestaodecaixaeletronico;

import gestaodecontas.*;
import java.util.*;

public class Terminal {
	private Caixa meuCaixa;
	private int modoAtual;
	
	public Terminal(CadastroContas bd) {
		this.meuCaixa = new Caixa(this, bd);
	}
	
	public void iniciaOperacao() {
		int opcao;
		opcao = this.getOpcao();
		
		while (opcao != 8) {
			
			switch (opcao) {

				case 1:
					double saldo = this.meuCaixa.consultaSaldo(getInt("Numero da Conta"), getInt("Senha"));
					if (saldo != -1) {
						System.out.println("Saldo Atual: " + saldo);
					} else {
						System.out.println("Conta/senha inválida");
					}
					break;
				case 2:
					boolean b = this.meuCaixa.efetuaSaque(getInt("Numero da Conta"), (double) getInt("Valor"), getInt("Senha"));
					if (b) {
						System.out.println("Retire o dinheiro");
					} else {
						System.out.println("Pedido de saque recusado");
					}
					break;
				
				case 3:
					boolean c = this.meuCaixa.creditaEmDinheiro(getInt("Numero da Conta"), getInt("Senha"), (double) getInt("Valor"));
					if (c) 
						System.out.println("Creditado!\n");
					else 
						System.out.println("Não Creditado!\n");
					break; 
				case 4: 
					boolean d = this.meuCaixa.transferencia(getInt("Numero da Conta1"), getInt("Senha: "), getInt("Numero da Conta2"), (double) getInt("Valor"));
					if (d) 
						System.out.println("Transferido!\n");
					else 
						System.out.println("Nao Transferido!\n");
			
					break; 
				
				case 5:
					boolean e = this.meuCaixa.creditaEmChaque(getInt("Numero da Conta1"), getInt("Numero da Conta2"), (double) getInt("Valor"));
					
					if (e) 
						System.out.println("Creditado!");
					else 
						System.out.println("Não Creditado!\n");
					
					break;
				case 6:
					String extrato = this.meuCaixa.extrato(getInt("Numero da conta: "), getInt("Senha: "));
					if (extrato != null) 
						System.out.println(extrato);
					else 
						System.out.println("Conta/senha inválida");
					break;
		
				case 7:
					this.meuCaixa.recarrega();
					break;
			}
			opcao = getOpcao();
		}
	}
	
	public void setModo(int modo) {
		if (modo == 0 || modo == 1) {
			this.modoAtual = modo;
		}
	}
	
	private int getOpcao() {
		int opcao;
		do {
			if (this.modoAtual == 1) {
				opcao = getInt("Opcao: 1ConsultaSaldo, 2Saque, 3Credita Em Dinheiro, 4Transferencia, 5Credita Em Cheque, 6Extrato, 8Sair");
				if (opcao != 1 & opcao != 2 & opcao != 3 & opcao != 4 & opcao != 5 & opcao != 6 & opcao != 8) {
					opcao = 0;
				}
			} else {
				opcao = getInt("Opcao: 7Recarrega, 8Sair");
				if (opcao != 7 & opcao != 8) { 
					opcao = 0;
				}
			}
		} while (opcao == 0);
		return opcao;
	}
	
	private int getInt(String string) {
		Scanner r = new Scanner(System.in);
		System.out.println("Entre com " + string);
		if (r.hasNextInt()) {
			return r.nextInt();
		}
		String st = r.next();
		System.out.println("Erro na Leitura de Dados" + st);
		return 0;
	}
	
	
	
	
	
	
	
	
	
}
