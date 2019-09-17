<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers </title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers</h1>

  <!-- Provide a navigation bar -->
   <div class="navigation">
    <#if currentUser??>
      <a href="/">my home</a> |
      <form id="signout" action="/signout" method="get">
        <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
      </form>
    <#else>
      <a href="/signin">Sign In</a>
    </#if>
   </div>


  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl">

    <div class = "body">
    <p>Welcome to WebCheckers!</p>
    <p>There are ${numPlayers} player(s) active.</p>
    <#if error??>
    <p style="color:red;">${error}</p>
    </#if>
    <#if currentUser??>
        <p>You're signed in as ${currentUser.getName()}</p>
        <p>Other players: </p>
        <#list players as player>
            <#if player.getName() == currentUser.getName()>
            <#else>
            <p> Status: ${player} | Name: <a href="/game?id=${player.getName()}">${player.getName()}</a></p>
            </#if>
            </#list>
            <p> Last Game: </p>
            <#if game??>
                <p>Game: <a href="/replay/game?gameID=1">${game.getRedPlayer().getName()} vs. ${game.getWhitePlayer().getName()}</a>
            </#if>
            <#else>
                <p>You're not signed in.</p>
    </#if>
  </div>
</div>
</body>
</html>
