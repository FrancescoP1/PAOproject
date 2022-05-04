package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.function.Function;


public class AuditService {
    private static AuditService audit = null;
    private BufferedWriter bufferedWriter;

    public static AuditService getAudit() {
        if(audit == null) {
            audit = new AuditService();
        }
        return audit;
    }

    private AuditService() {
        try{
            String filePath = "data/audit.csv";
            new FileWriter(filePath, false).close();
            bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void log(String details) {
        //log format: user id/system, action details (ex launched order: order id), time
        try{
            Timestamp actionTime = Timestamp.from(Instant.now());
            //bufferedWriter.write("witten,\n");
            //System.out.println(details);
            bufferedWriter.write(details + actionTime + "\n");
            bufferedWriter.flush();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

}
