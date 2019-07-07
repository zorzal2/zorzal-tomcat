package com.pragma.toolbar.config;

import com.pragma.toolbar.data.DataType;
import com.pragma.toolbar.properties.OrderTypeEnum;


public interface ColumnConfiguration {
	/**
	 * Propiedad representada por esta columna, si no se especifica otra cosa, corresponde
	 * a la propiedad a mostrar, propiedad por la que se ordena y por la que se filtra.
	 * @return
	 */
    public String getProperty();
    /**
     * La propiedad cuyo valor se quiere mostrar. Una columna puede necesitar ser ordenada
     * o filtrada usando cierta propiedad distinta a la que finalmente se usará para mostrar
     * el texto de la columna. Por ejemplo, si la columna es una referencia a un pais, puede
     * decidirse que la entidad Pais tenga un campo <code>posicion</code> que determina su orden
     * en la lista según un criterio de precedencia (por ejemplo, una página de Argentina orientada
     * a usuarios latinoamericanos puede colocar en primer lugar a Argentina, luego
     * el resto de América Latina y después todos los demás en orden alfabético), pero claramente
     * esta propiedad no es la que se usará para mostrar al usuario. En este caso, se puede poner
     * como property o.pais.posicion y como viewable property o.pais.nombre.
     * Por default es igual a <code>property</code>  
     */
    public String getShowProperty();
    /**
     * Propiedad para usar al ordenar. Por defecto debería devolver la propiedad <code>property</code>
     * @return
     */
    public String getOrderProperty();
    
    public String getDecorator();
    public String getTitle();
    public Boolean getAllowFiltering();
    public Boolean getAllowSorting();
    public DataType getDataType();
    public String getAlign();
    public Boolean getEscapeHtml();

    public Boolean getVisible();
    public void setVisible(Boolean isVisible);
    public void restoreVisible();

    public Integer getOrder();
    public void setOrder(Integer order);
    public void restoreOrder();
    
    public OrderTypeEnum getOrderType();
    public void setOrderType(OrderTypeEnum newOrder);
    public void restoreOrderType();
}
