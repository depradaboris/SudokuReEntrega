package GUI;
/*package GUI;

import javax.swing.ImageIcon;

public class EntidadGrafica {
	protected ImageIcon imagen;
	protected String[] imagenes;

	public EntidadGrafica() {
		imagen = new ImageIcon();
		imagenes=new String[] {"/Imagenes/1.png","/Imagenes/2.png","/Imagenes/3.png","/Imagenes/4.png","/Imagenes/5.png","/Imagenes/6.png","/Imagenes/7.png","/Imagenes/8.png","/Imagenes/9.png"};
	}

	public void actualizar(int indice) { //Indice oscila en [0,9]
		if (indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			imagen.setImage(imageIcon.getImage());
		}
	}

	public ImageIcon getImagen() {
		return imagen;
	}

	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}

	public String[] getImagenes() {
		return imagenes;
	}
}*/




import javax.swing.ImageIcon;

public class EntidadGrafica {
	protected ImageIcon imagen;
	protected String[] imagenes;

	public EntidadGrafica() {
		imagen = new ImageIcon();
		imagenes = setImagenes();
	}

	public void actualizar(int indice) { 
		if (indice < imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			imagen.setImage(imageIcon.getImage());
		}
	}

	public ImageIcon getImagen() {
		return imagen;
	}

	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}

	public String[] getImagenes() {
		return imagenes;
	}

	public String[] setImagenes() {
		String[] toReturn = new String[9]; 
		for(int i=0; i<9; i++)
			toReturn[i] = "/Imagenes/"+(i+1)+".png";
		return toReturn;
	}
}