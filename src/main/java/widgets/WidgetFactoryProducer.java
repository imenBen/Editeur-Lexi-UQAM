package widgets;

public  class WidgetFactoryProducer {
	private static WidgetFactoryProducer uniqueInstance = null;

	 private WidgetFactoryProducer(){
	       
	    }
	 
	 public static WidgetFactoryProducer getInstance() {
		 if (uniqueInstance == null)
			 uniqueInstance =  new WidgetFactoryProducer();
		 return uniqueInstance;
	 }

    public  WidgetFactory getFactory(String environment){

        //retourner une concrete factory selon l'environnement
        switch(environment) {
            case "Win": return new WinWidgetFactory();
            case "Linux": return new LinuxWidgetFactory();
        }

        return null;
    }

}