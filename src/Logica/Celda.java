package Logica;

import GUI.EntidadGrafica;

public class Celda {
	protected Integer valor;
	protected EntidadGrafica entidadGrafica;

	public Celda() {
		valor = null; //Si está null es que "no hay nada"
		entidadGrafica = new EntidadGrafica();
	}

	/**
	 * Actualiza el valor de la celda haciendo que oscile en [0..9].
	 */
	public void actualizar() {
		if(valor != null && valor < getCantElementos()){
			valor++;
			entidadGrafica.actualizar(valor - 1);
		}
		else{
			valor = 0;
		}

	}

	public int getCantElementos() {
		return entidadGrafica.getImagenes().length;
	}

	public Integer getValor() {
		return valor; 
	}

	public void setValor(Integer valor) {
		if (valor!=null && valor != 0 && valor < getCantElementos()+1) {
			this.valor = valor;
			entidadGrafica.actualizar(this.valor-1); //-1
		}
		else 
			this.valor = 0; // ESTO LO MODIFIQUE
	}

	public EntidadGrafica getEntidadGrafica() {
		return entidadGrafica;
	}

	public void setEntidadGrafica(EntidadGrafica entidadGrafica) {
		this.entidadGrafica = entidadGrafica;
	}
}