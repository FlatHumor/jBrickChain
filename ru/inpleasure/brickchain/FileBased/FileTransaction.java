package ru.inpleasure.brickchain.FileBased;

import org.json.JSONException;
import org.json.JSONObject;
import ru.inpleasure.brickchain.Transaction;

import java.lang.reflect.Field;

public class FileTransaction extends Transaction
{
    public FileTransaction(Transaction transaction)
    {
        sender = transaction.getSender();
        receiver = transaction.getReceiver();
        content = transaction.getContent();
        timestamp = transaction.getTimestamp();
    }

    public static Transaction createFromJson(JSONObject jsonObject)
    {
        return new Transaction();
    }

    @Override
    public String toString()
    {
        JSONObject jObject = new JSONObject();
        try
        {
            Class<? extends Transaction> transactionClass = Transaction.class;
            for (Field field : transactionClass.getDeclaredFields())
                jObject.put(field.getName(), field.get(this));

            System.out.println(jObject.toString(2));
            return jObject.toString(2);
        }
        catch (JSONException e) {
            return e.getMessage();
        }
        catch (IllegalAccessException e) {
            return e.getMessage();
        }
    }
}
