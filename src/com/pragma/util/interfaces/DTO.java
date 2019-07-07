package com.pragma.util.interfaces;

/**
 * Los Data Transfer Object (DTO) guardan un conjunto de datos para poder ser transferidos como una unidad. Pueden, por ejemplo, guardar parte de los datos de un objeto del negocio cuando no se necesita la totalidad de los datos de este objeto o datos de varios objetos distintos del negocio. La comunicaci�n entre acciones y servicios se realiza mediante el intercambio de DTOs. Un ejemplo de su uso es el caso del encabezado de las p�ginas relacionadas con proyectos. En este encabezado se muestran los datos b�sicos del proyecto con el que se est� operando para darle al usuario una referencia contextual. Al momento de construir este encabezado, una acci�n pide a un servicio los datos del proyecto. Para evitar recuperar de la base de datos y transmitir la totalidad de los datos con todos los objetos involucrados (localizaci�n, instrumento, presupuestos, evaluaciones, etc.), un servicio llena un DTO con solo un resumen de estos datos y lo entrega a la acci�n que lo requiri�. 
 *
 */
public interface DTO {
	//public Serializable getId();
}
