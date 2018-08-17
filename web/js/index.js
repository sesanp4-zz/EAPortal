/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    
    $("#ajaxload").hide();
     
     $("#login").click(function(event){
         event.preventDefault();
         username=$("#username").val();
         password=$("#password").val();
         
         window.localStorage.setItem("portalusername",username);
         window.localStorage.setItem("portalpassword",password);
         
        
         
         my = {
        "userinfo": {
            "username": username,
            "password": password
        }
    };
        
        $("#ajaxload").show();
        
         $.ajax({             
             url: "rest/api/login",
            contentType: "application/json",
            data: JSON.stringify(my),
            dataType: "json",
            type: "POST",
            success: function (data) {
               
               $("#ajaxload").hide();
               
               if(data.message==="Authentication Successful"){
                   
                   if(data.usertype==="merchant"){
                       window.location = "home.html";
                   }else if(data.usertype==="submerchant"){
                       //window.location = "subhome.html";
                      
                   }
                                      
               }else{
                   $("#ajaxload").hide();
                       alert(data.message);
                   }
            }
             
         });
      
     });
     
     
             $("#register").click(function(){

            var username = $("#username").val();
            var password = $("#password").val();
            var rcnumber = $("#rcnumber").val();
            var email = $("#email").val();
            var fname = $("#firstname").val();
            var lname = $("#lastname").val();
            var mob = $("#mobile").val();
            var phone = $("#phonenumber").val();
            var city = $("#city").val();
            var state = $("#state").val();
            var country = $("#country").val();        
            var add1 = $("#addressline1").val();
            var add2 = $("#addressline2").val();
            var bizname = $("#businessname").val();
            var web = $("#website").val();
            
            
            mydata = {
                "publickey": "pubkey2",
                "merchantinfo": {
                    "businessname": bizname,
                    "website": web,
                    "rcnumber": rcnumber,
                    "phonenumber": phone,
                    "email": email
                },
                "userinfo": {
                    "username": username,
                    "password": password,
                    "firstname": fname,
                    "lastname": lname,
                    "mobile": mob
                },
                "merchantaddress": {
                    "addressline1": add1,
                    "addressline2": add2,
                    "city": city,
                    "state": state,
                    "country": country,
                    
                }
            },
    
                $.ajax({

                    url: "rest/api/createmerchant",
                    contentType: "application/json",
                    data: JSON.stringify(mydata),
                    dataType: "json",
                    type: "POST",
                    success:function(data){
                        
                        alert(data.message);
                       

                    }
                });

        });
      
    });