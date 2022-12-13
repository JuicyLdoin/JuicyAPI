package net.juicy.api.utils.util;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bson.Document;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MongoDBUtil {
    static MongoClient mongoClient;
    static MongoCollection<Document> collection;

    public MongoDBUtil(String mongoAdress, String dbName, String collectionName) {

        mongoClient = MongoClients.create(mongoAdress);
        MongoDatabase db = mongoClient.getDatabase(dbName);
        collection = db.getCollection(collectionName);

    }

    public static void delete(long objectID){

        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();

            try {

                BasicDBObject search = new BasicDBObject();
                search.append("_id", objectID);

                collection.deleteOne(search);

                session.commitTransaction();

            } catch (Exception e) {

                session.abortTransaction();

            }

        }

    }
    public static <T> void setValue(long objectID, String fieldName, T value){

        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();

            try {

                BasicDBObject search = new BasicDBObject();
                search.append("_id", objectID);
                search.append(fieldName, value);
                BasicDBObject changeObject = new BasicDBObject();
                changeObject.append("$set", new BasicDBObject(fieldName, value));

                collection.updateMany(search, changeObject);

                session.commitTransaction();

            } catch (Exception e) {

                session.abortTransaction();

            }

        }

    }
    public static void save(Map<String, String> arg){

        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();

            try {

                List<String> sortedList = new ArrayList<>(arg.keySet());
                Collections.sort(sortedList);

                Document newDock = new Document();

                sortedList.forEach(s -> newDock.append(s, arg.get(s)));

                collection.insertOne(newDock);

                session.commitTransaction();

            } catch (Exception e) {

                session.abortTransaction();

            }

        }

    }
    public static void save(Object obj){

        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();

            try {

                Field[] fields = obj.getClass().getFields();
                Document newDock = new Document();


                Arrays.stream(fields).forEach(field -> {
                    try {

                        if (!field.getType().equals(Transient.class))
                            newDock.append(field.getName(), field.get(obj));

                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

                collection.insertOne(newDock);

                session.commitTransaction();

            } catch (Exception e) {

                session.abortTransaction();

            }

        }

    }







//    public static boolean checkPassword(String passwdHash, String name, String surname){
//
//        //переписать на поэтапныый чекер
//
//        Document checkName = new Document("name", name);
//        if (collection.find(checkName)){
//            Document checkSurname = new Document("name", name);
//        }
//
//        Document tempDock = new Document("passwdHash", passwdHash).append("name", name).append("surname", surname);
//
//
//        return collection.find(tempDock) != null;
//    }
}
