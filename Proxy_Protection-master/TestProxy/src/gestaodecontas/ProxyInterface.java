package gestaodecontas;

public interface ProxyInterface {
	public int getNumero();
	public int getSenha();
	public double getSaldo(int senha);
	public String[] gethistorico();
	public double[] getvalorLancamento();
	public boolean debitaValor(int senha, double valor, String operacao);
	public boolean creditaValor(int senha, double valor, String operacao);
	public boolean transferePara(ProxyInterface destino, int senha, double valor, String operacao);
	public boolean creditaCheque(ProxyInterface conta1, double valor);
	public String extrato(int senha);
	public boolean autentica(int senh,int num);
	public void transfere();
}
