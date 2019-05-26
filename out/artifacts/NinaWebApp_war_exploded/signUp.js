
$(function () {
    setSignInStatus();
    setInterval(setSignInStatus, 2000);
    $("#signUpForm").submit(function () {
        if (this.username.value !== "") {
            $.ajax({
                data: $(this).serialize(),
                url: this.action,
                success: function (r) {
                    if (r)
                        alert("The username you chose is already taken, please choose another username!");
                    else
                        window.location = "lobby.html";
                }
            });
        }
        else
        {
           alert("Please pick a username!");
        }

        return false;
    })
});

function setSignInStatus() {
    $.ajax({
        url: "getUserStatus",
        success: function (status) {
            if (!status.isUserConnected) {}
            else if (status.inActiveGame)
                window.location = "game.html";
            else {
                window.location = "lobby.html";
            }
        }
    })
}