package pl.fox.photo;

public class Handler { //Class made to pass data through classes without StackOverFlow error

    private Processor processor;

    public Handler(Processor processor){
        this.processor = processor;
    }

    public Processor getProcessor(){
        return processor;
    }

}
