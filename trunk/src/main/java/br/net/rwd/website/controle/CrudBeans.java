package br.net.rwd.website.controle;

import javax.faces.event.AjaxBehaviorEvent;

public interface CrudBeans<T> {

	public void incluir();

	public void salvar();

	public void atualizar();

	public void excluir();

	public void filtrar(AjaxBehaviorEvent event);

	public String retornar();
	
}
