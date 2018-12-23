package ru.inpleasure.brickchain.FileBased;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.json.JSONException;
import ru.inpleasure.brickchain.Brick;
import ru.inpleasure.brickchain.Repository;

public class FileRepository implements Repository
{
    private String directory;
    private String extension;
    private WeakReference<List<Brick>> bricksReference;
    
    public FileRepository(String directory) 
    {
        this.directory = directory;
        if (!directory.endsWith(File.separator))
            this.directory += File.separator;
        this.extension = ".brick";
        bricksReference = new WeakReference<>(new ArrayList<>());
    }
    
    private void initChainDirectory()
    {
        File chainDirectory = new File(directory);
        if (!chainDirectory.exists())
            chainDirectory.mkdirs();
    }
    
    @Override
    public void saveBrick(Brick brick) 
    {
        initChainDirectory();
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
            FileBrick fBrick = new FileBrick(brick);
            FileWriter writer = new FileWriter(brickFile);
            writer.write(fBrick.toString());
            writer.flush();
            writer.close();
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
            bufferedReader.close();
            fileReader.close();
                
            JSONObject jsonObject = new JSONObject(brickBuilder.toString());
            return FileBrick.createFromJson(jsonObject);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<File> getBricksFiles()
    {
        Pattern filenamePattern = Pattern.compile("\\d+?" + extension);
        File bricksDirectory = new File(directory);
        File[] brickFiles = bricksDirectory.listFiles();
        List<File> brickFilesList = new ArrayList<>();
        if (brickFiles == null)
            return brickFilesList;
        for (final File brickFile : brickFiles)
        {
            if (filenamePattern.matcher(brickFile.getName()).find())
                brickFilesList.add(brickFile);
        }
        return brickFilesList;
    }

    private int parseIdentificator(String filename) {
        String brickNumber = filename.split("\\.")[0];
        return Integer.valueOf(brickNumber);
    }
    
    @Override
    public List<Integer> getIdentificators() {
        List<File> brickFiles = getBricksFiles();
        List<Integer> brickNumbers = new ArrayList<>();
        if (brickFiles == null || brickFiles.size() == 0)
            return brickNumbers;
        for (final File brickFile : brickFiles) {
            int brickIdentificator = parseIdentificator(brickFile.getName());
            brickNumbers.add(brickIdentificator);
        }
        Collections.sort(brickNumbers);
        return brickNumbers;
    }
    
    @Override
    public List<Brick> getBricks()
    {
        if (bricksReference.get() == null)
        {
            List<Brick> bricks = new ArrayList<>();
            List<Integer> bricksIdentificators = getIdentificators();
            for (int identificator : bricksIdentificators)
            {
                Brick brick = loadBrick(identificator);
                bricks.add(brick);
            }
            bricksReference = new WeakReference<>(bricks);
        }
        return bricksReference.get();
    }
}
