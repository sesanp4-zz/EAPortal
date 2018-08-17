/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPackage;

import Utilities.Actions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("api")
public class Controller {

    @Context
    private UriInfo context;
    
     CloseableHttpClient client;
    CloseableHttpResponse response;
    Gson gson = new Gson();
    JsonObject obj,obj1;
    HttpPost post;
    HttpGet get;

    /**
     * Creates a new instance of Controller
     */
    public Controller() {
    }

    /**
     * Retrieves representation of an instance of Controller.Controller
     * @return an instance of java.lang.String
     */
    
   @Path("login")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces (MediaType.APPLICATION_JSON)
    public String login(UserRequest userRequest) throws IOException, NoSuchAlgorithmException{
        
        userRequest.setPublickey("pubkey2");
        userRequest.getUserinfo().setPassword(new HashPassword().getHashCodeFromString(userRequest.getUserinfo().getPassword()));
        
        client = HttpClients.createDefault();

        List<NameValuePair> form1= new ArrayList<NameValuePair>();
        form1.add(new BasicNameValuePair("grant_type", "client_credentials"));
        form1.add(new BasicNameValuePair("client_id","pk_DashY4mZYwU7SEdiQ"));
        form1.add(new BasicNameValuePair("client_secret", "sk_hbV2OGBhggipwBbw7565E"));

        post = new HttpPost("https://seerbitapigateway.com/seerbit/api/v1/auth");
        post.setEntity(new UrlEncodedFormEntity(form1));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        response= client.execute(post);
       // System.out.println(userRequest.getUsername());
        String result= EntityUtils.toString(response.getEntity());
           
        JsonObject obj = new JsonParser().parse(result).getAsJsonObject();
        
        Gson gson = new Gson();

        post = new HttpPost("https://seerbitapigateway.com/seerbitwh/api/v1/merchant/account/authenticate");
        post.setEntity(new StringEntity(gson.toJson(userRequest)));
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "Bearer "+ obj.get("access_token").getAsString());
        response =client.execute(post);

        String status =  EntityUtils.toString(response.getEntity());
         //System.out.println(status);
        return status;
              
        
    }
    
    
    
    @Path("get/transaction/{start}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String gettransaction(@PathParam("start") String start,UserInfo info) throws URISyntaxException, IOException, NoSuchAlgorithmException{
          
//         client = HttpClients.createDefault();
//         
//        URIBuilder ub = new URIBuilder("https://staging.seerbitapigateway.com/seerbitwh/api/v1/payout/transactions");
//        ub.setParameter("perpage", "5").setParameter("page", start);
//        get = new HttpGet(ub.build());
//           
//        get.setHeader("Content-type", "application/json");
//        get.setHeader("Authorization", "Bearer "+ new Actions().test(info.getUsername(),info.getPassword()));
//        response =client.execute(get);
//        String msg=EntityUtils.toString(response.getEntity());
//       
//        JsonObject ent = new JsonParser().parse(msg).getAsJsonObject();
//
//        System.out.println(ent.toString());

//        return gson.toJson(ent);
//Will ned to look into that later


        client = HttpClients.createDefault();
        List<NameValuePair> form1= new ArrayList<NameValuePair>();
        form1.add(new BasicNameValuePair("grant_type", "client_credentials"));
        form1.add(new BasicNameValuePair("client_id","pk_DashY4mZYwU7SEdiQ"));
        form1.add(new BasicNameValuePair("client_secret", "sk_hbV2OGBhggipwBbw7565E"));

        post = new HttpPost("https://seerbitapigateway.com/seerbit/api/v1/auth");
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
        obj1.addProperty("username", info.getUsername());
        obj1.addProperty("password", new HashPassword().getHashCodeFromString(info.getPassword()));
        obj.add("userinfo", obj1);
        post = new HttpPost("https://seerbitapigateway.com/seerbitwh/api/v1/merchant/account/authenticate");
        post.setEntity(new StringEntity(obj.toString()));
       
        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "Bearer "+ token);
        response =client.execute(post);
        
        //System.out.println(obj.toString());

        String res1 = EntityUtils.toString(response.getEntity());
        obj1 = new JsonParser().parse(res1).getAsJsonObject();
        URIBuilder ub = new URIBuilder("https://seerbitapigateway.com/seerbitwh/api/v1/payout/transactions");
        ub.setParameter("perpage", "20").setParameter("page", start);
        get = new HttpGet(ub.build());

        get.setHeader("Content-type", "application/json");
        get.setHeader("Authorization", "Bearer "+ obj1.get("access_token").getAsString());
        response =client.execute(get);
        String Ent=EntityUtils.toString(response.getEntity());

        JsonObject ent = new JsonParser().parse(Ent).getAsJsonObject();

        return ent.toString();

        
    }
    
    
        @Path("createmerchant")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces (MediaType.APPLICATION_JSON)
    public String createMerchant(String req) throws Exception{
      
            System.out.println(req);
           
        client = HttpClients.createDefault();

        List<NameValuePair> form1= new ArrayList<NameValuePair>();
        form1.add(new BasicNameValuePair("grant_type", "client_credentials"));
        form1.add(new BasicNameValuePair("client_id","testpublickey"));
        form1.add(new BasicNameValuePair("client_secret", "testprivatekey"));

        post = new HttpPost("https://seerbitapigateway.com/seerbit/api/v1/auth");
        post.setEntity(new UrlEncodedFormEntity(form1));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        response= client.execute(post);
        String result= EntityUtils.toString(response.getEntity());

        JsonObject obj = new JsonParser().parse(result).getAsJsonObject();
        
       
        post = new HttpPost("https://seerbitapigateway.com/seerbitwh/api/v1/merchant/account/create");
        post.setEntity(new StringEntity(req));

        post.setHeader("Content-type", "application/json");
        post.setHeader("Authorization", "Bearer "+ obj.get("access_token").getAsString());
        response =client.execute(post);

        return EntityUtils.toString(response.getEntity());
            
    }
    
    @Path("test")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void test(String req){
      obj = new JsonParser().parse(req).getAsJsonObject();
    }
    
    
}
