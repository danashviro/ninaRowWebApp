$(function () {
    setSignInStatus();
    setPlayersList();
    setGamesList();
    setInterval(setPlayersList, 2000);
    setInterval(setGamesList, 2000);
    setInterval(setSignInStatus, 2000);
});

function setSignInStatus() {
    $.ajax({
        url: "getUserStatus",
        success: function (status) {
            if (!status.isUserConnected)
                window.location = "signUp.html";
            else if (status.inActiveGame)
                window.location = "game.html";
            else {
                $('.userNameSpan').text("welcome " + status.connectedUsername);
            }
        }
    })
}

function setPlayersList() {
    $.ajax(
        {
            url: "getUsersList",
            success: function (userNames) {
                var usersTable = $('.usersList');
                usersTable.empty();
                for (var i = 0; i < userNames.length; i++) {
                    var line = $(document.createElement('tr'));
                    var col = $(document.createElement('td')).text(userNames[i]);
                    col.appendTo(line);
                    line.appendTo(usersTable);
                }
            }
        }
    )
}

$(function () {
    $("#uploadForm").submit(function () {
        var file1 = this[0].files[0];
        var formData = new FormData();
        formData.append("fake-key-1", file1);
        $.ajax({
            method: 'POST',
            data: formData,
            url: this.action,
            processData: false,
            contentType: false,
            success: function (r) {
                alert(r);
                setGamesList();
            }
        });
        return false;
    })
});

function submitFile() {
    $("#uploadForm").submit();
}



function setGamesList() {
    $.ajax({
        url: "getGamesList",
        error:function(){

        },
        success: function (games) {
            var gamesList = $(".gamesList");
            gamesList.empty();
            if (games != null) {
                for (var i = 0; i < games.length; i++) {
                    var line = $(document.createElement("tr"));
                    var gameName = $(document.createElement("td")).text(games[i].gameName);
                    var gameCreator = $(document.createElement("td")).text(games[i].gameCreator);
                    var gameType = $(document.createElement("td")).text(games[i].gameType);
                    var boardSize = $(document.createElement("td")).text(games[i].rows + " X " + games[i].cols);
                    var gameTarget = $(document.createElement("td")).text(games[i].targetSize);
                    var gameStatus = $(document.createElement("td")).text(games[i].gameStatus=="NOT_STARTED" ? "inactive" : "Active");
                    var numberOfPlayers = $(document.createElement("td")).text(games[i].activePlayersCounter.numOfActivePlayers + " / " + games[i].requiredNumberOfPlayers);
                    var join = $(document.createElement("td"));
                    var joinGameButton = $(document.createElement("button")).text("Join game");
                    joinGameButton.addClass('joinButton');
                    joinGameButton.attr('index',i);
                    joinGameButton.prop("disabled",games[i].gameStatus!="NOT_STARTED");
                    gameName.appendTo(line);
                    gameCreator.appendTo(line);
                    boardSize.appendTo(line);
                    gameTarget.appendTo(line);
                    gameType.appendTo(line);
                    gameStatus.appendTo(line);
                    numberOfPlayers.appendTo(line);
                    joinGameButton.appendTo(join);
                    join.appendTo(line);
                    line.appendTo(gamesList);
                }
                $(".joinButton").click(function(){
                    $.ajax({
                        data: {
                            gameIndex: this.getAttribute('index')
                        },
                        url: "joinGame",
                        success: function (r) {
                            if (r) {
                                window.location = "game.html";
                            }
                            else {
                                alert("Game is full,unable to join game");
                            }
                        }
                    })
                })
            }
        }
    })
}

function logout() {
    $.ajax({
        url: "logout"
    })

}

