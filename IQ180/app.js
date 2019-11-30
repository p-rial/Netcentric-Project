

function getname (){
    var x =document.getElementById("name").value;
    
    var getinput = x;
    localStorage.setItem("test", getinput);

    
}

document.addEventListener("keypress", function(e){
    if(e.keyCode === 13){
        window.open("index2.html");
        getname();
    }
});
function tolobby(){
    var x =  ` 
    <div class="wrapper" id="wrapper">
        <ul id="room" class="box">
            <li id='roomlan'>Room</li>
        </ul>
        <ul id="player" class="box">
            <li id='playerlan'>Player list</li>
           
        </ul>
    </div>
    <form id="event" class="box2">
        <button type='button' onclick='roomCreate()' id='createplayer'class="btn">Create</button>
        <button  id='btnlan' type='button'onclick='showhow2play()'class="btn">How to play</button>
        <button  type='button'onclick='changelanguage()'class="btn">ไทย</button>
        <button  type='button'onclick='changelanguage2()'class="btn">Eng</button>
           </form>
    <form id="hide" class="box3">
        <input type="text" placeholder="Room name" id="roomnamebox" >
        <button type='button'onclick='removeclass()'id='create'>Confirm</button>
    </form>
    <p  id='textloader'class='textloader'>Waiting for your opponent<p>
    <div id='loader' class="loader"></div>
    <div id='how2play' class='how2play'>
    <p id='playhow'>
    How to Play<br>
    1.Join or Create a Room<br>
    2.You will be prompted with an answer<br>
    3.Press on the buttons to select numbers and operators<br>
    4.Select the correct combination of Numbers and Operator the match the Answer.</p>
    <button type='button'onclick='hidehow2play()'id='create'>back</button>
    </div>
    `;
  document.getElementById("main").innerHTML=x;
}



