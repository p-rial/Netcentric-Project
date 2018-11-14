var a = [];
var numbercount=0;
var x =localStorage.getItem("test");
const y =`Player 1: ${x}`;
function receivename(){
    document.getElementById("playername").innerHTML= y;
}
window.onload=receivename();
    // var ran =Math.floor((Math.random()*9)+1);
    // var ran2 =Math.floor((Math.random()*9)+1);
    // var ran3 =Math.floor((Math.random()*9)+1);
    // var ran4 =Math.floor((Math.random()*9)+1);
    // var ran5 =Math.floor((Math.random()*9)+1);
    var total="";
    var total2="";
var i =0;
function randomnumber(){
    
    for(var i=0;i<5;i++){
        a[i]=Math.floor((Math.random()*9)+1);
        }
return a;
}
function randomresult(){
  randomnumber();


    document.getElementById("numbutton").innerHTML=a[0];
    document.getElementById("numbutton2").innerHTML=a[1];
    document.getElementById("numbutton3").innerHTML=a[2];
    document.getElementById("numbutton4").innerHTML=a[3];
    document.getElementById("numbutton5").innerHTML=a[4];
}
function clearbox(){
    total="";
     document.getElementById("textbox").value=total;
 document.getElementById("textbox").classList.remove("redtext");
    document.getElementById("numbutton").classList.remove("is-none");
    document.getElementById("numbutton2").classList.remove("is-none");
    document.getElementById("numbutton3").classList.remove("is-none");
    document.getElementById("numbutton4").classList.remove("is-none");
    document.getElementById("numbutton5").classList.remove("is-none");
       document.getElementById("numbutton").classList.remove("greenyellow");
    document.getElementById("numbutton2").classList.remove("greenyellow");
    document.getElementById("numbutton3").classList.remove("greenyellow");
    document.getElementById("numbutton4").classList.remove("greenyellow");
    document.getElementById("numbutton5").classList.remove("greenyellow");
        document.getElementById("numbutton").disabled=false;
     document.getElementById("numbutton2").disabled=false;
     document.getElementById("numbutton3").disabled=false;
     document.getElementById("numbutton4").disabled=false;
     document.getElementById("numbutton5").disabled=false;
    numbercount=0;
}

function clickbtn1(){
    total = total+a[0];
     document.getElementById("textbox").value=total;
   document.getElementById("numbutton").classList.add("is-none");
    numbercount++;
  
        document.getElementById("numbutton").classList.add("greenyellow");
    document.getElementById("numbutton2").classList.add("greenyellow");
    document.getElementById("numbutton3").classList.add("greenyellow");
    document.getElementById("numbutton4").classList.add("greenyellow");
    document.getElementById("numbutton5").classList.add("greenyellow");
    document.getElementById("numbutton2").disabled=true;
     document.getElementById("numbutton3").disabled=true;
     document.getElementById("numbutton4").disabled=true;
     document.getElementById("numbutton5").disabled=true;
    
    
}
function clickbtn2(){ 
    total=total+a[1];
     document.getElementById("textbox").value=total; 
    numbercount++;
        document.getElementById("numbutton").classList.add("greenyellow");
    document.getElementById("numbutton2").classList.add("greenyellow");
    document.getElementById("numbutton3").classList.add("greenyellow");
    document.getElementById("numbutton4").classList.add("greenyellow");
    document.getElementById("numbutton5").classList.add("greenyellow");
    document.getElementById("numbutton2").classList.add("is-none");
  
     document.getElementById("numbutton").disabled=true;
     document.getElementById("numbutton3").disabled=true;
     document.getElementById("numbutton4").disabled=true;
     document.getElementById("numbutton5").disabled=true;
}
function clickbtn3(){
        total=total+a[2];
         document.getElementById("textbox").value=total;
    numbercount++;
        document.getElementById("numbutton3").classList.add("is-none");
        document.getElementById("numbutton").classList.add("greenyellow");
    document.getElementById("numbutton2").classList.add("greenyellow");
    document.getElementById("numbutton3").classList.add("greenyellow");
    document.getElementById("numbutton4").classList.add("greenyellow");
    document.getElementById("numbutton5").classList.add("greenyellow");
     document.getElementById("numbutton2").disabled=true;
     document.getElementById("numbutton").disabled=true;
     document.getElementById("numbutton4").disabled=true;
     document.getElementById("numbutton5").disabled=true;
//        document.getElementById("numbutton3").disabled = true;
}
function clickbtn4(){
        total=total+a[3];
         document.getElementById("textbox").value=total;
    numbercount++;
    document.getElementById("numbutton4").classList.add("is-none");
        document.getElementById("numbutton").classList.add("greenyellow");
    document.getElementById("numbutton2").classList.add("greenyellow");
    document.getElementById("numbutton3").classList.add("greenyellow");
    document.getElementById("numbutton4").classList.add("greenyellow");
    document.getElementById("numbutton5").classList.add("greenyellow");
     document.getElementById("numbutton2").disabled=true;
     document.getElementById("numbutton3").disabled=true;
     document.getElementById("numbutton").disabled=true;
     document.getElementById("numbutton5").disabled=true;
}
function clickbtn5(){
        total=total+a[4];
         document.getElementById("textbox").value=total;
    numbercount++;
    document.getElementById("numbutton5").classList.add("is-none");
        document.getElementById("numbutton").classList.add("greenyellow");
    document.getElementById("numbutton2").classList.add("greenyellow");
    document.getElementById("numbutton3").classList.add("greenyellow");
    document.getElementById("numbutton4").classList.add("greenyellow");
    document.getElementById("numbutton5").classList.add("greenyellow");
     document.getElementById("numbutton2").disabled=true;
     document.getElementById("numbutton3").disabled=true;
     document.getElementById("numbutton4").disabled=true;
     document.getElementById("numbutton").disabled=true;
}
function clickbtn6(){
    total=total+"+";
        document.getElementById("textbox").value=total;
       document.getElementById("numbutton").classList.remove("greenyellow");
    document.getElementById("numbutton2").classList.remove("greenyellow");
    document.getElementById("numbutton3").classList.remove("greenyellow");
    document.getElementById("numbutton4").classList.remove("greenyellow");
    document.getElementById("numbutton5").classList.remove("greenyellow");
      document.getElementById("numbutton").disabled=false;
     document.getElementById("numbutton2").disabled=false;
     document.getElementById("numbutton3").disabled=false;
     document.getElementById("numbutton4").disabled=false;
     document.getElementById("numbutton5").disabled=false;
    
}
function clickbtn7(){
    total=total+"-";
         document.getElementById("textbox").value=total;
       document.getElementById("numbutton").classList.remove("greenyellow");
    document.getElementById("numbutton2").classList.remove("greenyellow");
    document.getElementById("numbutton3").classList.remove("greenyellow");
    document.getElementById("numbutton4").classList.remove("greenyellow");
    document.getElementById("numbutton5").classList.remove("greenyellow");
     document.getElementById("numbutton").disabled=false;
     document.getElementById("numbutton2").disabled=false;
     document.getElementById("numbutton3").disabled=false;
     document.getElementById("numbutton4").disabled=false;
     document.getElementById("numbutton5").disabled=false;
}
function clickbtn8(){
    total=total+"*";
         document.getElementById("textbox").value=total;
       document.getElementById("numbutton").classList.remove("greenyellow");
    document.getElementById("numbutton2").classList.remove("greenyellow");
    document.getElementById("numbutton3").classList.remove("greenyellow");
    document.getElementById("numbutton4").classList.remove("greenyellow");
    document.getElementById("numbutton5").classList.remove("greenyellow");
     document.getElementById("numbutton").disabled=false;
     document.getElementById("numbutton2").disabled=false;
     document.getElementById("numbutton3").disabled=false;
     document.getElementById("numbutton4").disabled=false;
     document.getElementById("numbutton5").disabled=false;
}
function clickbtn9(){
    total=total+"/";
         document.getElementById("textbox").value=total;
       document.getElementById("numbutton").classList.remove("greenyellow");
    document.getElementById("numbutton2").classList.remove("greenyellow");
    document.getElementById("numbutton3").classList.remove("greenyellow");
    document.getElementById("numbutton4").classList.remove("greenyellow");
    document.getElementById("numbutton5").classList.remove("greenyellow");
     document.getElementById("numbutton").disabled=false;
     document.getElementById("numbutton2").disabled=false;
     document.getElementById("numbutton3").disabled=false;
     document.getElementById("numbutton4").disabled=false;
     document.getElementById("numbutton5").disabled=false;
}
function clickbtn10(){
    total=total+"(";
         document.getElementById("textbox").value=total;
}
function clickbtn11(){
    total=total+")";
         document.getElementById("textbox").value=total;
}
function sendresult(){
    var x= eval(total);
    alert(x);
}
function randomans(){
    
    var one =a[0].toString();
    var two = a[1].toString();
    var three = a[2].toString();
    var four =a[3].toString();
    var five =a[4].toString();
    var ans = one+randomsymbol()+two+randomsymbol()+three+randomsymbol()+four+randomsymbol()+five;
    var finalans =eval(ans);
    var finalans2 =parseInt(finalans,10);
    if(finalans2%1==0&&finalans2>0){
        var x ="="+" "+finalans2;
;       document.getElementById("result").innerHTML=x;
        total2=finalans2;

    }else{
        randomans();
    }
    
    
  
    function randomsymbol(){
        var n =Math.floor((Math.random()*4)+1);
        if(n==1){
            return "+";
        }
        else if(n==2){
            return "-";
        }
        else if(n==3){
            return "*";
        }
        else {
            return "/";
        }
    }
}
 var n =2;
function checkresult(){
 
    var y = eval(total);
 
  
    
    
    if(total2==y){
        randomans();
        randomresult();
        clearbox();
       second=60;
        n++;
        i++;
        var x= "Score:"+i;
        document.getElementById("score").innerHTML=x;
         document.getElementById("countdown").classList.remove("redtext");
    }else{
        document.getElementById("textbox").classList.add("redtext");
    }
    
}
var second =60;

function secondpass(){
   var minutes = Math.round((second-30)/60);
    var remainsec = second%60;
    if(remainsec<10){
        remainsec="0"+remainsec;
    }
    document.getElementById("countdown").innerHTML=minutes+":"+remainsec;
    if(second==0){
       second=60;
        randomresult();
        randomans();
        secondpass();
        clearbox();
         
    
     
         document.getElementById("countdown").classList.remove("redtext");
    }else if(second<11){
        document.getElementById("countdown").classList.add("redtext");
        second--;
    }
       else{
        second--;
    }

}
function mintimer(){
    secondpass();
    setInterval("secondpass()",1000); 
}
//   var countdownTimer =setInterval("secondpass()",1000); 
////function mintimer(){
//    secondpass();
//      var countdownTimer =setInterval("secondpass()",1000); 
//}
function checkbtn(){
   
    if(numbercount<5)
        document.getElementById("box").classList.add("is-none");
  else if(numbercount=5){
       document.getElementById("box").classList.remove("is-none");
  }
}
setInterval("checkbtn()",100);
function erasestart(){
     document.getElementById("boxstart").classList.add("is-none");
}