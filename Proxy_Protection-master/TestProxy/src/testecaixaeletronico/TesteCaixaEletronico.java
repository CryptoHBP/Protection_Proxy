package testecaixaeletronico;
import gestaodecontas.*;
import gestaodecaixaeletronico.*;
import java.util.Scanner;

public class TesteCaixaEletronico {

	public static void main(String[] args) {
		
		
		int qtdContas = getInt("Numeros de Contas a Cadastrar: ");
		
		CadastroContas cad = new CadastroContas(qtdContas);
		Terminal t1 = new Terminal(cad);
		
		
		for (int i = 0; i < qtdContas; i++) {
			System.out.println("Dados da " + (i + 1) + "�" + " Conta: ");
			ProxyInterface c = new ContaProxy(getInt("Numero: "), getInt("Senha: "), (double) getInt("Saldo: "));
			cad.insereConta(c); 
		}
		
		t1.iniciaOperacao(); 
	}

	private static int getInt(String string) {
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
