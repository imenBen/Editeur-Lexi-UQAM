package model;

/* 
 * une interface factory pour instancier les documentelement selon leur type
 */
public interface IDocumentElememtFactory {
	/*
     * Instancier les éléments sans décorateur
     */
    public DocumentElement getDocumentElementInstance(String type);
    
    /*
     * Instancier les éléments avec décorateur
     */
    public DocumentElement getDocumentElementInstance(String type, DocumentElement decoratee);
}
