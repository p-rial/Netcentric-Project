var playingRooms = []

playingRooms.push({  
    name1 : "p",
    name2 : "top",
    score1 : 0,
    score2 : 0,
    roomName:'tai'
})

playingRooms.push({  
    name1 : "p",
    name2 : "top",
    score1 : 0,
    score2 : 0,
    roomName:'topopopopop'
})

playingRooms.push({  
    name1 : "p",
    name2 : "top",
    score1 : 0,
    score2 : 0,
    roomName:'rrrrrrrr'
})
var s1
var s2
playingRooms.forEach( (x,idx) => {
    if(x.roomName == 'tai') {
        playingRooms.splice(idx,1);
    }
})

console.log(playingRooms)

