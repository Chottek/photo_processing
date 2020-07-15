import org.junit.Assert;
import org.junit.Test;
import pl.fox.photo.*;

import java.io.File;

public class ProcessingTest {

    private ImageFileProcessor processor;

    @Test
    public void checkIfReaderReturnsNonEmptyList() {
        processor = new ImageFileProcessor();
        Handler handler = new Handler(processor);
        ImgReader reader = new ImgReader(handler);
        File f = new File(processor.getConfigHandler().getInputFolder());
        if (f.isDirectory() && f.listFiles().length > 0) {
            reader.readPhotos(f);
            Assert.assertTrue(reader.getImages().size() > 0);
        }
    }

    @Test
    public void checkDefaultValuesOfConfigHandler(){
        processor = new ImageFileProcessor();
        ConfigHandler configHandler = new ConfigHandler();

        Assert.assertEquals(configHandler.getInputFolder(), "in");
        Assert.assertEquals(configHandler.getOutputFolder(), "out");
        Assert.assertEquals(configHandler.getMaxThreads(), 12);
    }

    @Test
    public void checkMaxPoolSize(){
        processor = new ImageFileProcessor();
        processor.process(71);
        Assert.assertTrue(processor.getPool().toString()
                .contains("pool size = " + processor.getConfigHandler().getMaxThreads()));
    }
}
