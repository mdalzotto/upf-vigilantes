package br.upf.App.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.BoletoOcorrencia;
import br.upf.App.model.BoletoTipo;
import br.upf.App.model.OcorrenciaDataLocal;
import br.upf.App.model.TipoOcorrencia;
import org.primefaces.model.file.UploadedFile;

import br.upf.App.model.Local;
import br.upf.App.model.Vigilante;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@ViewScoped
public class BoletoOcorrenciaController implements Serializable {

	private Boolean editando;
	private List<BoletoOcorrencia> lista;
	private BoletoOcorrencia selecionado;
	private BoletoTipo tipoSelecionado;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;
	
	private UploadedFile file;
	

	public String init() {
		if (!loginController.getPermissaoVigilante()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}

	public void incluirTipo() {
		tipoSelecionado = new BoletoTipo();
		tipoSelecionado.setBoletoOcorrencia(selecionado);
	}

	public void salvarTipo() {
		if (selecionado.getBoletoTipos() == null)
			selecionado.setBoletoTipos(new ArrayList<BoletoTipo>());
		selecionado.getBoletoTipos().add(tipoSelecionado);
		// tratar arquivo aqui
		if (file != null) {
			tipoSelecionado.setArquivoBytes(file.getContent());
			tipoSelecionado.setArquivoNome(file.getFileName());
			tipoSelecionado.setArquivoTipo(file.getContentType());
		}

	}
	
	public void download(Integer linha) throws IOException {
		BoletoTipo ts = selecionado.getBoletoTipos().get(linha);
		byte[] b = ts.getArquivoBytes();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		res.setContentType(ts.getArquivoTipo());
		res.setHeader("Content-disposition", "inline;filename="+ts.getArquivoNome()); // diretamente na p�gina
		//res.setHeader("Content-disposition", "attachment;filename=arquivo.pdf"); // baixar ou salvar
		res.getOutputStream().write(b);
		FacesContext.getCurrentInstance().responseComplete();		
	}
	
	

	public void cancelarTipo() {

	}

	public void excluirTipo(Integer linha) {
		selecionado.getBoletoTipos().remove(linha.intValue());
	}

	public List<TipoOcorrencia> tipoOcorrencias(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<TipoOcorrencia> results = em.createQuery("from TipoOcorrencia where upper(descricao) like " + "'%"
				+ query.trim().toUpperCase() + "%' " + "order by descricao").getResultList();
		em.close();
		return results;
	}

	public List<Vigilante> funcionarios(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<Vigilante> results = em.createQuery(
				"from Vigilante where upper(nome) like " + "'%" + query.trim().toUpperCase() + "%' " + "order by nome")
				.getResultList();
		em.close();
		return results;
	}

	public List<Local> locais(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<Local> results = em.createQuery(
				"from Local where upper(nome) like " + "'%" + query.trim().toUpperCase() + "%' " + "order by nome")
				.getResultList();
		em.close();
		return results;
	}

	public BoletoOcorrenciaController() {
		setEditando(false);
		carregarLista();
	}

	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from BoletoOcorrencia").getResultList();
		em.close();
	}

	public void incluir() {
		editando = true;
		selecionado = new BoletoOcorrencia();
	}

	public void salvar() {
		try {

			editando = false;
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(selecionado);

			try {
				OcorrenciaDataLocal ocorLoc = null;
				// Pegar data da ocorrencia
				for (BoletoTipo bol : selecionado.getBoletoTipos()) {
					Date dataOcor = bol.getBoletoOcorrencia().getDataHora();
					TipoOcorrencia tipoOcor = bol.getTipoOcorrencia();
					Query qry = em
							.createQuery("from OcorrenciaDataLocal where data = :data and tipoOcorrencia.id = :idTipo");
					qry.setParameter("data", dataOcor);
					qry.setParameter("idTipo", tipoOcor.getId());
					List<OcorrenciaDataLocal> lista = qry.getResultList();
					if (lista.size() < 1 && ocorLoc == null) {
						ocorLoc = new OcorrenciaDataLocal();
						ocorLoc.setTipoOcorrencia(tipoOcor);
						ocorLoc.setData(dataOcor);
						ocorLoc.setOcorrenciaNaData(0);
					} else {
						if (ocorLoc == null || ocorLoc.getId() != null)
							ocorLoc = lista.get(0);
					}
					ocorLoc.setOcorrenciaNaData(ocorLoc.getOcorrenciaNaData() + 1);
					ocorLoc = em.merge(ocorLoc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			em.getTransaction().commit();
			em.close();
			selecionado = null;
			carregarLista();
			JSFUtil.messagemDeSucesso("Registro salvo com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.messagemDeErro("Orreu um erro ao salvar.");
		}
	}

	public void alterar() {
		EntityManager em = JPAUtil.getEntityManager();
		selecionado = em.find(BoletoOcorrencia.class, selecionado.getId());
		em.close();
		editando = true;
	}

	public void excluir() {
		try {

			editando = false;
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(selecionado));
			em.getTransaction().commit();
			em.close();
			selecionado = null;
			carregarLista();
			JSFUtil.messagemDeSucesso("Registro excluido com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.messagemDeErro("Orreu um erro ao excluir.");
		}
	}

	public void cancelar() {
		editando = false;
		selecionado = null;
	}

	public Boolean getEditando() {
		return editando;
	}

	public void setEditando(Boolean editando) {
		this.editando = editando;
	}

	public List<BoletoOcorrencia> getLista() {
		return lista;
	}

	public void setLista(List<BoletoOcorrencia> lista) {
		this.lista = lista;
	}

	public BoletoOcorrencia getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(BoletoOcorrencia selecionado) {
		this.selecionado = selecionado;
	}

	public BoletoTipo getTipoSelecionado() {
		return tipoSelecionado;
	}

	public void setTipoSelecionado(BoletoTipo tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
}
