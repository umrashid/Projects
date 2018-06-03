// Created by Umair Rashid - 2/15/2018
/*
GAME RULES:

- The game has 2 players, playing in rounds
- In each turn, a player rolls a dice as many times as he whishes. Each result get added to his ROUND score
- BUT, if the player rolls a 1, all his ROUND score gets lost. After that, it's the next player's turn
- The player can choose to 'Hold', which means that his ROUND score gets added to his GLBAL score. After that, it's the next player's turn
- The first player to reach 100 points on GLOBAL score wins the game

*/
/* Changes to the rules of the games:
1. A player looses his Entire score when he rolls two 6 in a row.
2. Add an input field so that Players can set their winning score.
3. Add another dice to the game so there are two dices now. Player looses his current score when one of them is a 1.
*/

var currentScore = 0;
var dice, dice1 = 0;
var atLeastOneturnCheck = false;
var player1 = true;
var playerscores = [0,0];


function rollDice() {
    atLeastOneturnCheck = true;
    var diceroll1 = Math.ceil(Math.random() * 6);
    var diceroll2 = Math.ceil(Math.random() * 6);
    console.log(diceroll1);
    console.log(diceroll2);
    document.getElementById("diceimage").src = "dice-" + diceroll1 + ".png"
    document.getElementById("diceimage1").src = "dice-" + diceroll2 + ".png"
    dice = diceroll1;
    dice1 = diceroll2;
    calculateCurrentScore();
    updateCurrentScores();
    checkForWinner();
}

function calculateCurrentScore() {
    if (dice === 1 || dice1 == 1 || (dice === 6 && dice1 === 6)) {
        setTimeout(changeAlert,100);
        currentScore = 0;
        holdScore();
        
    } else {
        currentScore += (dice+dice1);
        console.log("Current Score is " + currentScore)
    }
    
}

function updateCurrentScores(){
    if (player1 === true) {
        document.getElementById("current-0").innerHTML = currentScore;
    } else {
        document.getElementById("current-1").innerHTML = currentScore;
    }
}

function holdScore() {
    if(atLeastOneturnCheck)
        {
            if (player1 === true) {
                playerscores[0] += currentScore;
                document.getElementById("score-0").innerHTML = playerscores[0];
                currentScore = 0;
                document.getElementById("current-0").innerHTML = currentScore;
                changeActivePlayer();
            }else{
                playerscores[1] += currentScore;
                document.getElementById("score-1").innerHTML = playerscores[1];
                currentScore = 0;
                document.getElementById("current-1").innerHTML = currentScore;
                changeActivePlayer();
            }
        }
        else
        {
            alert("You haven't used your turn!");
        }
}

function changeActivePlayer(){
    if (player1 === true)
        {
            player1 = false;
            document.getElementById("panel-0").className = "player-0-panel";
            document.getElementById("panel-1").className = "player-1-panel active";
            atLeastOneturnCheck = false;
            
        }
    else
        {
            player1 = true;
            document.getElementById("panel-1").className = "player-1-panel";
            document.getElementById("panel-0").className = "player-0-panel active";
            atLeastOneturnCheck = false;
        }
}

function newGame(){
     document.getElementById("roll").disabled= false;
     document.getElementById("hold").disabled= false;
    for (var i = 0; i < 2; i++){
        document.getElementById("score-" + i).innerHTML = 0;
        document.getElementById("current-" + i).innerHTML = 0;
        document.getElementById("winner-" + i).style.display ="none";
    }
   
    player1 = true;
    currentScore = 0;
    dice = 0;
    atLeastOneturnCheck = false;
    for(var i = 0; i < playerscores.length; i++)
        {
            playerscores[i] = 0;
        }
     document.getElementById("panel-1").className = "player-1-panel";
     document.getElementById("panel-0").className = "player-0-panel active";
     
    
}

function changeAlert(){
    if(currentScore <= 10){
    alert("Opps! You lost your score. Its other player turn now!");
    }
    else
        {
            alert("Opps! looks like someone got greedy. Its other player turn now!");
        }
}

function checkForWinner(){
    checkScore = checkWinScore();
    if (player1){
        if((playerscores[0] + currentScore) >= checkScore ){
            document.getElementById("winner-0").style.display ="block";
            document.getElementById("roll").disabled= "true";
            document.getElementById("hold").disabled= "true";
        }
        
    }else{
         if((playerscores[1] + currentScore) >= checkScore ){
            document.getElementById("winner-1").style.display ="block";
            document.getElementById("roll").disabled= "true";
            document.getElementById("hold").disabled= "true";
         }
        
    }
}
function checkWinScore(){
    value = document.getElementById("inputscore").value;
    if (value === "")
        {
            return 100;
        }
    else
        {
            return value;
        }
}