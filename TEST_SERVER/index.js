var express = require('express')
var app = express();
var http = require('http').Server(app);
var path = require('path')
var io = require('socket.io')(http);

var playerArray = []
var roomArray = []
var playingRoom = []

// app.get('/', function (req, res) {
//     res.sendFile(path.join(__dirname, '../iq180/index.html'));
   
// });

app.use(express.static(path.join(__dirname, "../iq180")));

io.on('connection', function (socket) {

    socket.on('user connect', function (playerName) {
        playerArray.push(new Player(playerName, socket.id))

        socket.nickname = playerName
       
        console.log(`Player : ${playerName} connected`)
        playerArray.forEach(player => console.log(`Current PlayerList : ${player.name} ${player.socketId}`));

        socket.emit('online room', roomArray)
        socket.emit('socket', socket.id)
        socket.emit('online player', playerArray)

        socket.broadcast.emit('new user connect', {name : `${playerName}`,
                                                   socketId : `${socket.id}` });
    });

    socket.on('create room', function (room) { 
        roomArray.push(new Room(room.hostName, room.roomName))
        
        socket.join(room.roomName)

        console.log(`Room created : ${room.hostName} ${room.roomName}`)
        io.emit('new room created', room) 
    })

    socket.on('JOIN_ROOM', (roomName, hostName, playerName2) => {
        if (getRoomName(socket)) socket.leave(getRoomName(socket))


        // Room obj
        playingRoom.push({
            name1 : hostName,
            name2 : playerName2,
            score1 : 0,
            score2 : 0,
            roomName : roomName
        })

        socket.join(roomName,() => {
            io.in(getRoomName(socket)).emit('start game', hostName, playerName2, roomName)
        })
    })

    socket.on('game start', roomName => {
        var equation = getSuitableEquation()
        console.log(equation + " = " + eval(equation))
        
        var x = io.in(roomName).clients()

        if (roomName === "")  socket.emit('game info', equation, eval(equation), arrOfNum, "")
        else {
                io.in(roomName).clients((err , client) => {
                    var whoFirst = () => Math.floor(Math.random() * 2)
                    io.in(roomName).emit('game info', equation, eval(equation), arrOfNum, client[whoFirst()])
                });
            }            
    })

    const getRoomName = socket => Object.values(socket.rooms)[0]

    socket.on('room aborted', function(name) {
        console.log(name)
        const index = roomArray.findIndex(room => room.hostName === name)
        console.log(index)
        io.emit('room disconnected', roomArray[index].hostName) 
        roomArray.splice(index, 1)

        roomArray.forEach(room => console.log(`Current RoomList : ${room.hostName} ${room.roomName}`));
    })

    socket.on('disconnect', function () {
        const index = playerArray.findIndex(player => player.socketId === socket.id);

        socket.broadcast.emit('user disconnected', playerArray[index].name)
        playerArray.splice(index, 1)
        
        console.log(`user disconnected ` + socket.id)
        // playerArray.forEach(player => console.log(`Current PlayerList : ${player.name} ${player.socketId}`));
    })

    // EVENT while playing game 

    socket.on('change turn no answer', roomName => {
        socket.to(roomName).emit('your turn', "")
    })

    socket.on('change turn answer', (roomName, timeLeft) => {
        socket.to(roomName).emit('your turn', timeLeft)
    })

    socket.on('round result', (roomName, winner) => {
        var s1
        var s2
        if (winner === "")  {
            playingRoom.forEach( x => {
                if(x.roomName === roomName) {
                    s1 = x.score1
                    s2 = x.score2
                }
            })
            io.in(roomName).emit('round result', winner, s1, s2)
            console.log("Draw")
        }
        else {
            playingRoom.forEach( x => {
                if(x.roomName === roomName) {
                    if(winner === x.name1)  x.score1++ 
                    else x.score2++
                    
                    s1 = x.score1
                    s2 = x.score2
                }
            })
            io.in(roomName).emit('round result', winner, s1, s2)
            console.log(`Winner is : ${winner}`)
        }
    })

    socket.on('reset score', roomName => {
        playingRoom.forEach( x => {
            if(x.roomName === roomName) {
                x.score1 = 0
                x.score2 = 0
                socket.in(roomName).emit('reset score', x.score1, x.score2)
            }
        })
        
    })

    socket.on('quit game', roomName => {
        playingRoom.forEach( (x,index) => {
            if(x.roomName === roomName) {
                io.in(roomName).emit('leave room')
                playingRoom.splice(index,1)
            }
        })
    })

})

http.listen(3000, function () {
    console.log('server listening on port 3000');
})

// METHOD

const randomNum = () => Math.floor(Math.random() * 10)
const randomOperator = () => Math.floor(Math.random() * 4)
var arrOfNum;

const generateEquation = () => {
    let operations = ""
    const arrOfOperators = ["+", "-", "*", "/"]
    arrOfNum = Array(5)
        .fill(0,0,5)
        .map(() => randomNum());

    arrOfNum.forEach((num, i) => {
        if(i === 4) operations += num;
        else operations = operations + num + arrOfOperators[randomOperator()];
    });
    return operations ;
};

const getSuitableEquation = () => {
    var equation = generateEquation()
    var result = eval(equation)

    while(!Number.isInteger(result)) {
        equation = generateEquation()
        result = eval(equation)
        console.log(result)
    }
    return equation
}


class Player {
    
    constructor(name, socketId) {
        this.name = name
        this.socketId = socketId
    }

}

class Room {

    constructor(hostName, roomName) {
        this.hostName = hostName
        this.roomName = roomName
    }

}