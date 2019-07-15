<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>

<script language="javascript">
    function clearFilters(obj) 
    {
        var filterForm = obj.form;
    
        for (var i = 0; i < filterForm.elements.length; i++)
        {
            elem = filterForm.elements[i];

            if (elem.type == 'text') {
	            elem.value = '';
            } 
            else if (elem.type == 'select-one') {
            	if(elem.options[0].value=='all') {
            	    elem.value = 'all';
            	}
            	else {
                    elem.value = '';
                }
            }
        }
        filterForm.submit();
    }
</script>

<html-el:button property="clearButton" onclick="clearFilters(this)">
    Limpiar
</html-el:button>
