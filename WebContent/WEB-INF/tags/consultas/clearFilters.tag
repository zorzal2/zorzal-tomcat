<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>

<script language="javascript">
    function updateAction(obj) {
        var filterForm = obj.form;
    	filterForm.action = "/fontar/LimpiarConsulta.do";
        filterForm.submit();
    }
</script>

<html-el:button property="clearButton" onclick="updateAction(this)">Limpiar</html-el:button>