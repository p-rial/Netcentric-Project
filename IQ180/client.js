const num =0;

var btn = document.getElementById('name2');
var createroom = document.getElementById('create');
var textbox  = document.getElementById('name');
// var textbox2  = document.getElementById('roomnamebox');
var socket = io("http://localhost:3000");
btn.addEventListener('click',function() {
    console.log(textbox.value)
    socket.emit('user connect', textbox.value)
})
// var x = []

socket.on('online player', playerArray => {
    console.log(playerArray)
    const i =0;
    for(let player of playerArray){
        writeEvent(player.name);
        
        // x[i]=player.name;
    }
    // playerArray.forEach(player => {
    //     writeEvent(player.name)
    // });
    // while(i< playerArray.length){
    //     writeEvent(playerArray[i].name);
    //     i++;
    // }
})

socket.on('new user connect',function(name){
console.log(name);
writeEvent(name.name)
})


const writeEvent = (text)=>{
    const parent =document.querySelector('#player');
    const el = document.createElement('li');

    el.innerHTML = text;
    parent.appendChild(el);

}


const writeEventroom = (text)=>{
    const parent =document.querySelector('#room');
    const el = document.createElement('li');
    el.className='roomCreate';
    el.setAttribute("id","roomid");
    // el.onclick= function(){
    //  num++;
    //  if(num==2)
    // }
 
    el.innerHTML = text;
    parent.appendChild(el);

}
function roomCreate(){
    document.getElementById('hide').classList.remove('box3');
    document.getElementById('hide').classList.add('show');
    document.getElementById('room').classList.add('opacity');
    document.getElementById('player').classList.add('opacity');
    document.getElementById('event').classList.add('opacity');
   
}
// createroom.addEventListener('click',function() {
//     console.log(textbox2.value);
//     // socket.emit('create room', textbox2.value);
//     document.getElementById('hide').classList.add('box3');
//     document.getElementById('hide').classList.remove('show');
//     document.getElementById('room').classList.remove('opacity');
//     document.getElementById('player').classList.remove('opacity');
//     document.getElementById('event').classList.remove('opacity');
 
// })
function removeclass(){
    var textbox2  = document.getElementById('roomnamebox');
    socket.emit('create room', {hostname : textbox.value,
                                roomName : textbox2.value});
    
    document.getElementById('hide').classList.add('box3');
    document.getElementById('hide').classList.remove('show');
    document.getElementById('room').classList.remove('opacity');
    document.getElementById('player').classList.remove('opacity');
    document.getElementById('event').classList.remove('opacity');
    document.getElementById('textloader').classList.remove('textloader');
        document.getElementById('textloader').classList.add('show2');
        document.getElementById('loader').classList.remove('loader');
        document.getElementById('loader').classList.add('loader2');
        document.getElementById('hide').classList.add('show');
    document.getElementById('room').classList.add('opacity');
    document.getElementById('player').classList.add('opacity');
    document.getElementById('event').classList.add('opacity');

}
socket.on('new room created', room => {
    console.log(room);
    writeEventroom(room.roomName);
   
})
socket.on('online room',roomArray=>{
    console.log(roomArray)
    roomArray.forEach(room => {
           writeEventroom(room.roomName)
         });
})

socket.on('user disconnected', name => {
    console.log(name);
   var parg = document.getElementById('player');
   if (parg.hasChildNodes()) {
    var children = parg.childNodes;
  console.log(children);
    for (var i = 0; i < children.length; i++) {
        if(name === parg.childNodes[i].textContent)
            parg.removeChild(parg.childNodes[i])
      // do something with each child as children[i]
      // NOTE: List is live, adding or removing children will change the list
    }
  }
//     var a = playerArray.indexOf(name.name);
//     var b =name;
//     var list = document.getElementById("player");
//     list.removeChild(list.childNodes[a]);

// })
// document.getElementById('myroom').addEventListener('click',function(){

})
// document.getElementsByClassName('roomCreate').addEventListener('click',function(){
//     document.getElementsByClassName('loader').classList.add('box3');
//     document.getElementById('hide').classList.remove('show');
//     document.getElementById('room').classList.remove('opacity');
//     document.getElementById('player').classList.remove('opacity');
//     document.getElementById('event').classList.remove('opacity');
// }
// )
// function loadGame(){
//     document.getElementById('loader').classList.add('show');
// }
// document.getElementById('roomid').addEventListener('click',loadGame);
function showhow2play(){
    document.getElementById('how2play').classList.remove('how2play');
    document.getElementById('how2play').classList.add('show');
    document.getElementById('room').classList.add('opacity');
    document.getElementById('player').classList.add('opacity');
    document.getElementById('event').classList.add('opacity');
}
function hidehow2play(){
    document.getElementById('how2play').classList.add('how2play');
    document.getElementById('how2play').classList.remove('show');
    document.getElementById('room').classList.remove('opacity');
    document.getElementById('player').classList.remove('opacity');
    document.getElementById('event').classList.remove('opacity');
}
function changelanguage(){
    document.getElementById('roomlan').innerHTML='ห้อง'
    document.getElementById('playerlan').innerHTML='รายชื่อผู้เล่น'
    document.getElementById('playhow').innerHTML=
    `วิธีเล่น<br>
    ๑. เข้าห้องหรือสร้างห้อง<br>
    ๒.คำตอบสุดท้ายที่ต้องการจะขึ้นมาตอนแรก<br>
    ๓.เลือกตัวเลขและสัญลักษณ์เพื่อทำการคำนวณให้ได้ค่าคำตอบ<br>
    ๔. เลขทั้งหมดจะต้องถูกใช้ทุกตัว
    `

}
function changelanguage2(){
    document.getElementById('roomlan').innerHTML='Room'
    document.getElementById('playerlan').innerHTML='Player list'
    document.getElementById('playhow').innerHTML=
    `
    How to Play<br>
    1.Join or Create a Room<br>
    2.You will be prompted with an answer<br>
    3.Press on the buttons to select numbers and operators<br>
    4.Select the correct combination of Numbers and Operator the match the Answer.
    `
    var textthai ='วิธีเล่น'
    document.getElementById('btnlan').value= textthai;
    document.getElementById('createplayer').innerHTML='สร้าง';

}