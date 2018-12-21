import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class FileRepository implements Repository
{
    private String directory;
    private String extension;
    
    public FileRepository(String directory) 
    {
        this.directory = directory;
        if (!directory.endsWith(File.separator))
            this.directory += File.separator;
        this.extension = ".brick";
    }
    
    @Override
    public void saveBrick(Brick brick) 
    {
        String brickFilename = directory
            .concat(Integer.toString(brick.getIdentificator()))
            .concat(extension);
        File brickFile = new File(brickFilename);
        if (brickFile.exists()) {
            System.out.println("Such file already exists");
            return;
        }
        try
        {
            FileWriter writer = new FileWriter(brickFile);
            writer.write(brick.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Brick loadBrick(int identificator)
    {
        String brickFilename = directory
            .concat(Integer.toString(identificator))
            .concat(extension);
        File brickFile = new File(brickFilename);
        if (!brickFile.exists()) {
            System.out.println("No such brick file found");
            return null;
        }
        try 
        {
            FileReader fileReader = new FileReader(brickFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder brickBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                brickBuilder.append(line);
            brickBuilder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    @Override
    public List<Integer> getIdentificators() {
        Pattern filenamePattern = Pattern.compile("\\d+?" + extension);
        File bricksDirectory = new File(directory);
        File[] brickFiles = bricksDirectory.listFiles();
        List<Integer> brickNumbers = new ArrayList<>();
        if (brickFiles == null || brickFiles.length == 0)
            return brickNumbers;
        for (final File brickFile : brickFiles)
        {
            if (filenamePattern.matcher(brickFile.getName()).find())
            {
                String brickNumber = brickFile.getName().split("\\.")[0];
                brickNumbers.add(Integer.valueOf(brickNumber));
            }
        }
        Collections.sort(brickNumbers);
        return brickNumbers;
    }
    
    @Override
    public List<Brick> getBricks() {
        return null;
    }
}
