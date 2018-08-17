/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import ControllerPackage.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author centricgateway
 */
public class Actions {
    
    
    CloseableHttpClient client;
    CloseableHttpResponse response,response1;
    Gson gson = new Gson();
    JsonObject obj,obj1;
    HttpPost post,post1;
    HttpGet get;
    
    
  public String get_token(String username,String password) throws UnsupportedEncodingException, IOException, URISyntaxException{
       
        client =  HttpClients.createDefault();
        
        List<NameValuePair> form= new ArrayList<NameValuePair>();
        form.add(new BasicNameValuePair("grant_type", "client_credentials"));
        form.add(new BasicNameValuePair("client_id","testpublickey"));
        form.add(new BasicNameValuePair("client_secret", "testprivatekey"));

        post = new HttpPost("https://staging.seerbitapigateway.com/seerbit/api/v1/auth");
        post.setEntity(new UrlEncodedFormEntity(form));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        response= client.execute(post);
        String msg = EntityUtils.toString(response.getEntity());
        obj = new JsonParser().parse(msg).getAsJsonObject();
        String token = obj.get("access_token").getAsString();
        
        // The 'token' returned is authorization token
        
        obj = new JsonObject();
        obj1= new JsonObject();
        
        obj.addProperty("publickey", "pubkey2");
        obj1.addProperty("username", username);
        obj1.addProperty("password", password);
        obj.add("userinfo", obj1);
       
        
        //This is get the access token
        
        post = new HttpPost("https://staging.seerbitapigateway.com/seerbitwh/api/v1/merchant/account/authenticate");
        StringEntity ent = new StringEntity(obj.toString());
        post.setEntity(ent);
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "Bearer " + token);

        response= client.execute(post);
        String msg2 = EntityUtils.toString(response.getEntity());
        obj = new JsonParser().parse(msg).getAsJsonObject();
        String access_token = obj.get("access_token").getAsString();
       
        
        
        client = HttpClients.createDefault();
         
        URIBuilder ub = new URIBuilder("https://staging.seerbitapigateway.com/seerbitwh/api/v1/payout/transactions");
        ub.setParameter("perpage", "5").setParameter("page", "1");
        get = new HttpGet(ub.build());
           
        get.setHeader("Content-type", "application/json");
        get.setHeader("Authorization", "Bearer "+ access_token);
        response =client.execute(get);
        String msg1=EntityUtils.toString(response.getEntity());
         //System.out.println(msg);
        JsonObject ent1 = new JsonParser().parse(msg1).getAsJsonObject();
        
        return ent1.toString();
  }  
  
  
  public String test(String username, String password) throws UnsupportedEncodingException, IOException{
  
         client = HttpClients.createDefault();
        List<NameValuePair> form1= new ArrayList<NameValuePair>();
        form1.add(new BasicNameValuePair("grant_type", "client_credentials"));
        form1.add(new BasicNameValuePair("client_id","testpublickey"));
        form1.add(new BasicNameValuePair("client_secret", "testprivatekey"));

        post = new HttpPost("https://staging.seerbitapigateway.com/seerbit/api/v1/auth");
        post.setEntity(new UrlEncodedFormEntity(form1));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        response= client.execute(post);
        String result= EntityUtils.toString(response.getEntity());

        obj = new JsonParser().parse(result).getAsJsonObject();
        String token =obj.get("access_token").getAsString();
        gson = new Gson();
        
        obj = new JsonObject();
        obj1= new JsonObject();
        
        obj.addProperty("publickey", "pubkey2");
        obj1.addProperty("username", username);
        obj1.addProperty("password", password);
        obj.add("userinfo", obj1);;
        post = new HttpPost("https://staging.seerbitapigateway.com/seerbitwh/api/v1/merchant/account/authenticate");
        post.setEntity(new StringEntity(obj.toString()));
      
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "Bearer "+ token);
        response =client.execute(post);
        
        //System.out.println(gson.toJson(obj.toString()));

        String res1 = EntityUtils.toString(response.getEntity());
        JsonObject obj1 = new JsonParser().parse(res1).getAsJsonObject();

        get = new HttpGet("https://staging.seerbitapigateway.com/seerbitwh/api/v1/airtime/transactions?perpage=5&page=1");

        get.setHeader("Content-type", "application/json");
        get.setHeader("Authorization", "Bearer "+ obj1.get("access_token").getAsString());
        response1 =client.execute(get);
        String Ent=EntityUtils.toString(response1.getEntity());

        JsonObject ent = new JsonParser().parse(Ent).getAsJsonObject();


        return gson.toJson(ent);
  
  }
  
    
    public static void main(String[] args) throws IOException, UnsupportedEncodingException, URISyntaxException {
        //System.out.println(new Actions().get_token("r@gmail.com","b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86"));
        System.out.println(new Actions().test("r@gmail.com","b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86"));
    }
}
