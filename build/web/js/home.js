/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
  
   $("#ajaxload").hide();

                    window.localStorage.setItem("pageno",1);
                    //window.localStorage.setItem("no",0);

                   gettransaction(1);

function gettransaction(current) {
    var user = window.localStorage.getItem("portalusername");
    var pass = window.localStorage.getItem("portalpassword");


    my = {
            "username": user,
            "password": pass
        
    },  
             $("#ajaxload").show();
        $.ajax({

            url: "rest/api/get/transaction/"+current,
            contentType: "application/json",
            data: JSON.stringify(my),
            dataType: "json",
            type: "POST",
            success: function (data) {
                 $("#ajaxload").hide();
                obj = data.records;
                console.log(obj);
                $("table > tbody").empty();
                  $("#current").text("Current page "+data.metadata.page);
                  $("#total").text("Total Record(s) "+data.metadata.totalcount);
                  $("#per_page").text("Record(s) Per Page "+data.metadata.perpage);
                for (var i = 0; i < obj.length; i++){
                    // no++;
                      $("table").append('<tr><td>'+obj[i].country+'</td><td>'+obj[i].recipient+'</td><td>'+obj[i].amount+'</td><td>'+obj[i].currency+'</td><td>'+obj[i].merchantreference+'</td><td>'+obj[i].internalreference+'</td><td>'+obj[i].responsemessage+'</td><td>'+obj[i].network+'</td><td>'+obj[i].narration+'</td><td>'+obj[i].processorbalance+'</td><td>'+obj[i].transactiontype+'</td><td>'+obj[i].transactiontime+'</td><td>'+obj[i].email+'</td></tr>');
                      
                }
                //window.localStorage.setItem("no",no);
                //alert("when saved..."+no);

            }


        });
};



/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    
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
        


$("#sign_out").click(function(event){
       window.localStorage.removeItem("portalusername");
       window.localStorage.removeItem("portalpassword");
       window.location="index.html";
    });

$("#export").click(function(){
    $("#table").table2excel({
      //  name:"mymy",
        filename:"data"
    });

   //  $("#table").tableExport();


});

$("#prev").click(function(){
   var page = parseInt(window.localStorage.getItem("pageno"));
    pageno=page-1;
    //no= parseInt(window.localStorage.getItem("no"));
    
        gettransaction(pageno);
         window.localStorage.setItem("pageno",pageno);
});


$("#next").click(function(){    
    var page = parseInt(window.localStorage.getItem("pageno"));
    pageno=page+1;
   //no= parseInt(window.localStorage.getItem("no"));
        gettransaction(pageno);   
         window.localStorage.setItem("pageno",pageno);
});

$("#search").click(function(event){  
    event.preventDefault();
      pageno=$("#num").val();
      //alert(pageno);
        gettransaction(pageno);   
     
});


    });

