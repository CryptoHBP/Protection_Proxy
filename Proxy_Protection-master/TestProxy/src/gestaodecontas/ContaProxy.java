package gestaodecontas;

public class ContaProxy implements ProxyInterface{
	private int numero;
	private int senha;
	private double saldo;
	private String[] historico;
	private double valorLancamento[];
	private int ultimoLancamento;
	private ProxyInterface conta = null;
	
	public ContaProxy(int numero,int senha,double saldo) {
		this.numero = numero;
		this.senha = senha;
		this.saldo = saldo;
		this.historico = new String[11];
		this.valorLancamento = new double[11];
	}
	
	public void transfere() {
		this.numero = conta.getNumero();
		this.senha = conta.getSenha();
		this.saldo = conta.getSaldo(senha);
		this.historico = conta.gethistorico();
		this.valorLancamento = conta.getvalorLancamento();
		conta = null;
	}
	
	public ProxyInterface getConta() {
		if(this.conta == null) {
			this.conta = new Conta(this.numero,this.senha,this.saldo,this.historico,this.valorLancamento,this.ultimoLancamento);
		}
		return this.conta;
	}
	
	public int getNumero() {
		autentica(this.senha,this.numero);
		int num = conta.getNumero();
		return num;
	}
	public int getSenha() {
		autentica(this.senha,this.numero);
		int num = conta.getSenha();
		return num;
	}
	
	public double getSaldo(int senha) {
		if(autentica(senha,this.numero)) {
			double num = conta.getSaldo(senha);
			transfere();
			return num;
		}
		return -1;
	}
	public String[] gethistorico() {
		autentica(this.senha,this.numero);
		String[] his = conta.gethistorico();
		transfere();
		return his;
	}
	public double[] getvalorLancamento() {
		autentica(this.senha,this.numero);
		double[] valor = conta.getvalorLancamento();
		transfere();
		return valor;
	}
	
	public boolean debitaValor(int senha, double valor, String operacao) {
		if(autentica(senha,this.numero)) {
			if(valor < 0 | this.senha != senha | this.saldo < valor){
				transfere();
				return false;
			}
			else {
				conta.debitaValor(senha, valor, operacao);
				transfere();
				return true;
			}
		}
		return false;
	}
	
	//Adcionado
	public boolean creditaValor(int senha, double valor, String operacao) {
		if(valor > 0) {
			if(autentica(senha,this.numero)) {
				conta.creditaValor(senha, valor, operacao);
				transfere();
				return true;
			}
		}
		return false;
		
	}
	
	//Adcionado
		public boolean transferePara(ProxyInterface destino, int senha, double valor, String operacao) {
			if(autentica(senha,this.numero)) {
				destino.autentica(destino.getSenha(),destino.getNumero());
				conta.transferePara(destino, senha, valor, operacao);
				transfere();
				return true;
			}
			return false;
		}
		
		//adcionado
		public boolean creditaCheque(ProxyInterface conta1, double valor) {
			if(autentica(this.senha,this.numero)) {
				conta1.autentica(conta1.getSenha(),conta1.getNumero());
				conta.creditaCheque(conta1,valor);
				transfere();
				return true;
			}
			return false;
		}
	// Extrato
	public String extrato(int senha) {
		if(autentica(senha,this.numero)) {
			String str = conta.extrato(senha);
			transfere();
			return str;
		}
		return null;
	}
	
	public boolean autentica(int senh,int num) {
		if(this.senha == senh && this.numero == num) {
			this.conta = getConta();
			return true;
		}
		return false;
	}

}
