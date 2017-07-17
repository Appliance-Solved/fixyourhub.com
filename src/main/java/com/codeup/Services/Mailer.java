package com.codeup.Services;
import com.codeup.Models.Appointment;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
/**
 * Created by larryg on 7/16/17.
 */
public class Mailer {
    private String from = "fixithub.appointments@gmail.com";
    private String password = "codeup123";
    private String requestSub = "You have a new appointment request!";
    private String confirmSub = "Your service request has been confirmed.";


        public static void send(String from,String password,String to,String sub,String msg){
            //Get properties object
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            //get Session
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from,password);
                        }
                    });
            //compose message
            try {
                MimeMessage message = new MimeMessage(session);
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
                message.setSubject(sub);
                message.setContent(msg, "text/html");
                //send message
                Transport.send(message);
                System.out.println("message sent successfully");
            } catch (MessagingException e) {throw new RuntimeException(e);}

        }

    public Mailer() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequestSub() {
        return requestSub;
    }

    public void setRequestSub(String requestSub) {
        this.requestSub = requestSub;
    }

    public String getConfirmSub() {
        return confirmSub;
    }

    public void setConfirmSub(String confirmSub) {
        this.confirmSub = confirmSub;
    }

    public String printApplianceType (int appliance_id){

        if(appliance_id == 0) {
            return "Refrigerator";
        }else if (appliance_id == 1) {
            return "Stove";
        }else if(appliance_id == 2) {
            return "Washer";
        }else if(appliance_id == 3) {
            return "Dryer";
        }else if(appliance_id == 4) {
            return "Dishwasher";
        }else if(appliance_id == 5) {
            return "Microwave";
        }else if(appliance_id == 6) {
            return "Ice Machine";
        }else {
            return "Other";
        }
    }

  public String requestMsg(Appointment appointment){
      String message = "<h1>Fixithub.solutions</h1>";
      message += "<h2>You have received a new service request";
      message += "<p>" + appointment.getUser().getName() +  " has requested your service on their appliance.</p>" +
              "<p>Below are the details of the request:</p>" +
              "<p>Requested for " + appointment.formatDate(appointment.getDate()) + " between " + appointment.formatTime(appointment.getStartTime()) + "-" + appointment.formatTime(appointment.getStopTime()) +  "</p>" +
              "<p>Address: " + appointment.getUser().getAddress() + "</p>" +
              "<p>" + appointment.getUser().getCity() + ", " + appointment.getUser().getState() + " " + appointment.getUser().getZipcode() + "</p>" +
              "<p>Complaint: " + printApplianceType(appointment.getServiceRecords().getUserAppliance().getAppliance_id()) + "- " + appointment.getServiceRecords().getComplaint() + "</p>" +
              "<br /><p>Please <a href='fixithub.solutions/login'>login</a> to confirm or decline this appointment</p>" +
              "<br /><br /><p>-Thank You,<br /> From the Fixithub Team</p>";

    return message;
  }
}

