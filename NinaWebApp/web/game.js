var numOfTurns = 0;
var cells;
var colors = ["red", "blue", "yellow", "green", "purple", "orange"];
var checkStatusInterval;
$(function () {
    buildBoard();
    checkStatus();
    checkStatusInterval = setInterval(checkStatus, 1000);

});

function checkStatus() {
    checkUserStatus();
    $.ajax({
        url: "getGameState",
        data: {
            numberOfTurns: numOfTurns
        },
        success: function (response) {
            if (response.gameStatus == "NOT_STARTED")
                updateGameNotStartedStatus(response.players.length, response.requiredNumberOfPlayers);
            else {
                updateBoard(response.moves, response.numOfMoves);
                updatePlayersList(response.players, response.currentPlayerIndex, response.myName);
                if (response.gameStatus == "STARTED")
                    enableTurn(response.isMyTurn, response.isHuman);
                else {
                    endGame(response.isBoardFull, response.winners);
                }
            }
        }
    })
}

function enableTurn(isMyTurn, isHuman) {
    if (isMyTurn) {
        $(".gameStatusLabel").text("It's your turn!");

        if (isHuman) {
            $("button").prop("disabled", false);
        }
        else {
            makeAComputerMove();
            $("button").prop("disabled", true);
        }
    }
    else {
        $(".gameStatusLabel").text("Please wait for your turn...");
        $("button").prop("disabled", true);
    }
}

function popinClick() {
    $.ajax({
        data: {
            col: this.getAttribute('col')
        },
        url: "popin",
        success: function (r) {
            if (r.turnSuccess) {
                $("button").prop("disabled", true);
                checkStatus();
            }
            else
                alert(r.errorMessage)
        }
    })
}

function popoutClick() {
    $.ajax({
        data: {
            col: this.getAttribute('col')
        },
        url: "popout",
        success: function (r) {
            if (r.turnSuccess) {
                $("button").prop("disabled", true);
                checkStatus();
            }
            else
                alert(r.errorMessage)
        }
    })
}

function makeAComputerMove() {
    $.ajax({
        url: "makeAComputerMove",
        error: function () {
        },
        success: function () {
            checkStatus();
        }
    })

}

function updateGameNotStartedStatus(numOfPlayers, reqNumOfPlayers) {
    $(".gameStatusLabel").text("Waiting for more players... " + numOfPlayers + "/" + reqNumOfPlayers);
}


function endGame(isBoardFull, winners) {
    var msg;
    clearInterval(checkStatusInterval);
    if (isBoardFull)
        msg = "board is full,game over";
    else {
        msg = "The winners are: ";
        for (var i = 0; i < winners.length; i++) {
            msg += winners[i].name + " ";
        }
    }
    var gameTableDiv = $(".gameTableDiv");
    createMsgDiv(msg).appendTo(gameTableDiv);
}

function createMsgDiv(msg) {
    var msgDiv = $(document.createElement("div"));
    msgDiv.addClass("msgDiv");
    var h3 = $(document.createElement("h3")).text(msg);
    h3.appendTo(msgDiv);
    var button = $(document.createElement("button")).text("Return to lobby");
    button.click(endGameClick);
    button.appendTo(msgDiv);
    return msgDiv;
}
function endGameClick() {
    $.ajax({
        url: "removeGameIndexFromSession",
        success: function () {
            checkUserStatus();
        }
    });
}


function updatePlayersList(players, currentPlayerIndex, myName) {
    var tbody = $(".userListBody");
    tbody.empty();
    for (var i = 0; i < players.length; i++) {
        var tr = $(document.createElement("tr"));
        var td = $(document.createElement("td"));
        createPlayerDiv(players[i], myName).appendTo(td);
        td.appendTo(tr);
        tr.appendTo(tbody);
        if (i == currentPlayerIndex)
            td.addClass("currentPlayerTurn");
        else
            td.addClass("playerTd")
    }
}

function createPlayerDiv(player, myName) {
    var div = $(document.createElement("div"));
    div.addClass("playerDiv");
    var name = $(document.createElement("h5")).text("Name: " + player.name + (myName === player.name ? " (YOU)" : " "));
    name.display = "inline-block";
    var rect = $(document.createElement("div"));
    rect.addClass("rectangle");
    rect.addClass(colors[player.playerIndex]);
    rect.display = "inline-block";
    name.appendTo(div);
    rect.appendTo(div);
    var numOfMoves = $(document.createElement("h5")).text("Number of moves: " + player.numOfMoves);
    var status = $(document.createElement("h5")).text("Status: " + (player.active ? "active" : "inactive"));
    var playerType = $(document.createElement("h5")).text("Player type: " + player.playerType);
    numOfMoves.appendTo(div);
    status.appendTo(div);
    playerType.appendTo(div);
    return div;
}

function updateBoard(moves, numOfMoves) {
    numOfTurns = numOfMoves;
    for (var i = 0; i < moves.length; i++) {
        for (var j = 0; j < moves[i].changedCells.length; j++) {
            var cell = moves[i].changedCells[j];
            var x = cell.location.x;
            var y = cell.location.y;
            var content = cell.content;
            var circle = cells[y][x];
            circle.removeClass(circle.attr('color'));
            var color = cell.content != -1 ? colors[cell.content] : "blank";
            circle.addClass(color);
            circle.attr("color", color);
        }
    }
}


function buildBoard() {
    $.ajax({
        url: "getGameInfo",
        success: function (response) {
            cells = new Array(response.rows);
            var thead = $(".gameTableHead");
            var tbody = $(".gameTableBody");
            var tfoot = $(".gameTableFoot");

            for (var i = 0; i < response.cols; i++) {
                var th = $(document.createElement("th"));
                var button = $(document.createElement("button")).text("popin");
                button.addClass("popinButton");
                button.attr("col", i);
                button.prop("disabled", true);
                button.appendTo(th);
                th.appendTo(thead);

            }
            $(".popinButton").click(popinClick);

            for (var i = 0; i < response.rows; i++) {
                cells[i] = new Array(response.cols);
                var tr = $(document.createElement("tr"));
                for (var j = 0; j < response.cols; j++) {
                    var td = $(document.createElement("td"));
                    var circle = $(document.createElement("div"));
                    circle.attr("color", "blank");
                    circle.addClass("circle");
                    circle.addClass("blank");
                    circle.appendTo(td);
                    td.appendTo(tr);
                    cells[i][j] = circle;
                }
                tr.appendTo(tbody)
            }

            if (response.popout) {
                for (var i = 0; i < response.cols; i++) {
                    var th = $(document.createElement("th"));
                    var button = $(document.createElement("button")).text("popout");
                    button.addClass("popoutButton");
                    button.attr("col", i);
                    button.prop("disabled", true);
                    button.appendTo(th);
                    th.appendTo(tfoot);
                }
                $(".popoutButton").click(popoutClick);

            }
        }
    })
}

function checkUserStatus() {
    $.ajax({
        url: "getUserStatus",
        success: function (status) {
            if (!status.isUserConnected)
                window.location = "signUp.html";
            else if (!status.inActiveGame)
                window.location = "lobby.html";
        }
    })
}

function retire() {
    $.ajax({
        url: "retire",
        success: function () {
            clearInterval(checkStatusInterval);
            checkUserStatus();
        }
    })
}