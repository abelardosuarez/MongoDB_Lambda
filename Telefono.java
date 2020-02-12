package familiares;

public class Telefono {
	int codigo;
	int numero;
	public Telefono() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Telefono(int codigo, int numero) {
		super();
		this.codigo = codigo;
		this.numero = numero;
	}
	public Telefono(int codigo) {
		super();
		this.codigo = codigo;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		result = prime * result + numero;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefono other = (Telefono) obj;
		if (codigo != other.codigo)
			return false;
		if (numero != other.numero)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Telefono [codigo=" + codigo + ", numero=" + numero + "]";
	}
	
}
