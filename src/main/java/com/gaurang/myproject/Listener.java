
package com.gaurang.myproject;


import com.mongodb.MongoCredential;
import com.mongodb.MongoClient;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
public class Listener {


     public void database_mongod(String msg){
             MongoClient mongo = new MongoClient( "localhost" , 27017 );
             MongoCredential credential;
             credential = MongoCredential.createCredential("root","Pass@123",
                      "password".toCharArray());
             MongoDatabase db = mongo.getDatabase("mydb");

             System.out.println("Connected to the database successfully");
              db.getCollection("consumer").insertOne(Document.parse(msg));
              mongo.close();
              System.out.println("Mongo Db database Opeartions  successfully Done");
     }
	@RabbitListener(queues="queue1")
    public void Fanoutforq1recievedMessage(String msg) {
        System.out.println("Queue name is :  queue1");

        System.out.println("Recieved Message: "  + msg);
        database_mongod(msg);
    }
	@RabbitListener(queues="queue2")
    public void Fanoutforq2recievedMessage(String msg) {
        System.out.println("Queue name is :  queue2");

        System.out.println("Recieved Message: "  + msg);
        database_mongod(msg);
    }

	@RabbitListener(queues="wing")
    public void DirectMessage(String msg) {
        System.out.println("Queue name is :  wing" + msg);


        /*System.out.println("Recieved Message: "  + msg);
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
                MongoCredential credential;
                credential = MongoCredential.createCredential("root","Pass@123",
                        "password".toCharArray());
                MongoDatabase db = mongo.getDatabase("mydb");
                System.out.println("Connected to the database successfully");
         db.getCollection("consumer").insertOne(Document.parse(msg));
         mongo.close();

         */
        database_mongod(msg);


         



    }


}
