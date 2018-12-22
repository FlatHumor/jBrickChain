package ru.inpleasure.brickchain.FileBased;

import org.json.JSONException;
import org.json.JSONObject;
import ru.inpleasure.brickchain.Brick;

import java.lang.reflect.Field;

public class FileBrick extends Brick
{
    public FileBrick(Brick brick)
    {
        identificator = brick.getIdentificator();
        headerHash = brick.getHeaderHash();
        previousBrickHash = brick.getPreviousBrickHash();
        bits = brick.getBits();
        nonce = brick.getNonce();
        timestamp = brick.getTimestamp();
        transaction = brick.getTransaction();
    }

    public static Brick createFromJson(JSONObject jsonObject)
    {
        try
        {
            Brick brick = new Brick();
            Class<?> brickClass = FileBrick.class.getSuperclass();
            for (Field field : brickClass.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(brick, jsonObject.get(field.getName()));
            }
            return brick;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            Class<?> brickClass = Brick.class;
            for (Field field : brickClass.getDeclaredFields())
                jsonObject.put(field.getName(), field.get(this));

            FileTransaction fTransaction = new FileTransaction(transaction);
            jsonObject.put("transaction", fTransaction.toString());

            System.out.println(jsonObject.toString(2));
            return jsonObject.toString(2);
        }
        catch (JSONException e) {
            return e.getMessage();
        }
        catch (IllegalAccessException e) {
            return e.getMessage();
        }
    }
}
