package pl.fox.photo;

public class Handler { //Class made to pass data through classes without StackOverFlow error

    private ImageFileProcessor imageFileProcessor;

    public Handler(ImageFileProcessor imageFileProcessor){
        this.imageFileProcessor = imageFileProcessor;
    }

    public ImageFileProcessor getImageFileProcessor(){
        return imageFileProcessor;
    }

}
