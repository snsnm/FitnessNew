package com.example.steve.fitness;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends AsyncTask<Void, Void, Void> {

    //Declaring Variabes

    private Context context;
    private Session session;

    //Information to send email
    private String email, subject, message;

    //Progessdialog to show while sendong email

    private ProgressDialog progressDialog;

    //Class Constructor

    public SendMail(Context context, String email, String subject, String message){

        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;

    }

    protected void onPreExecute(){
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context, "Sending message", "Please wait...", false,false );
    }

    protected Void doInBackground(Void... params){
        Properties props = new Properties();

        //Confirfuring properties for gmail
        //If u are not using gmail, u may need to change the values

//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "587");
//        props.put("mail.smtp.socketFactory.Class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.socketFactory.fallback", "false");
//


        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });
        try{
            Transport transport = session.getTransport();
            //Creatinf MimeMessage object
            Message mm = new MimeMessage(session);

            //setting sender adress
            Log.d("yo", "uoooo");
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //ADDING A RECEIVER
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            //Adding subject
            mm.setSubject(subject);
            //adding Message
            mm.setText(message);
            Log.d("yo", "badd");


            transport.connect();
            //Sending email
            Transport.send(mm);
            Log.d("o", "lamee");
            transport.close();

        }catch (MessagingException e){
            e.printStackTrace();
            e.toString();

        }
        return null;
    }
}
