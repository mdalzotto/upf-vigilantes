package br.upf.App.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.TipoOcorrencia;

@FacesConverter(value = "tipoOcorrenciaConverter")
public class TipoOcorrenciaConverter implements Converter {

	@Override
	public TipoOcorrencia getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em = JPAUtil.getEntityManager();
				TipoOcorrencia ret = em.find(TipoOcorrencia.class, Long.parseLong(value));
				em.close();
				return ret;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversão da tipo de ocorrência", "Tipo de ocorrênia inválida."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((TipoOcorrencia) object).getId());
		} else
			return null;
	}
}
