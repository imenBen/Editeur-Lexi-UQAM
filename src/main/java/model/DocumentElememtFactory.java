package model;

/* 
 * une classe factory pour instancier les documentelement selon leur type
 */
public class DocumentElememtFactory implements IDocumentElememtFactory {
    public DocumentElememtFactory(){}
    /*
     * Instancier les éléments sans décorateur
     */
    public DocumentElement getDocumentElementInstance(String type){
        switch(type){

            case "body": return new Root();
            case "p": return new Paragraph();
            case "span": return new Span();
            case "img": return new Image();
            default: return new Character();
        }
    }
    
    /*
     * Instancier les éléments avec décorateur
     */
    
    public DocumentElement getDocumentElementInstance(String type, DocumentElement decoratee){
        switch(type){
            case "b": return new Bold(decoratee);
            case "i": return new Italic(decoratee);
            case "u": return new Underline(decoratee);
            case "font": return new Font(decoratee);
            default: return new Character();

            
        }
    }
}
