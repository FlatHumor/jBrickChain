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
        try
        {
            Transaction transaction = new Transaction();
            Class<?> transactionClass = FileTransaction.class.getSuperclass();
            for (Field field : transactionClass.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(transaction, jsonObject.get(field.getName()));
            }
            return transaction;
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
        JSONObject jObject = new JSONObject();
        try
        {
            Class<? extends Transaction> transactionClass = Transaction.class;
            for (Field field : transactionClass.getDeclaredFields())
                jObject.put(field.getName(), field.get(this));

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
