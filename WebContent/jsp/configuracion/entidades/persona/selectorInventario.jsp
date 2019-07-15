<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<table class="filtros">
	<tr>
		<td width="45%">			
			<rapidFilters:textFilter  property="nombre"  title="app.label.nombre"  filterType="filter.type.string.contains" classNameType="java.lang.String"/>
		</td>
		<td width="45%">				
			<rapidFilters:textFilter  property="cuit"  title="app.label.cuit"  filterType="filter.type.string.contains"  classNameType="java.lang.String"/>
		</td>				
		<td>
			<rapidFilters:applyFilters />
		</td>
		<td>
			<rapidFilters:clearFilters />
		</td>
	</tr>
</table>