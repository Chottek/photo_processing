package pl.fox.photo;

public class Handler { //Class made to pass data back and forth between classes without StackOverFlow error

    private final ImageFileProcessor imageFileProcessor;

    public Handler(ImageFileProcessor imageFileProcessor){
        this.imageFileProcessor = imageFileProcessor;
    }

    public ImageFileProcessor getImageFileProcessor(){
        return imageFileProcessor;
    }

}
