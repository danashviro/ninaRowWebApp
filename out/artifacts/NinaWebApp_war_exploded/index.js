

$(function () {
    $.ajax({
        url:"getUserStatus",
        success:function(status)
        {
            if(!status.isUserConnected)
                window.location="signUp.html";
            else if(status.inActiveGame)
                window.location="game.html";
            else
            {
                window.location="lobby.html";
            }
        }
    })
});

