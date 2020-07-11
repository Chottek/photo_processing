public class Handler {

    private Processor processor;

    public Handler(Processor processor){
        this.processor = processor;
    }

    public Processor getProcessor(){
        return processor;
    }

}
