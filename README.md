# GameStore
Console Game Store App

The game store is a platform where the users can buy games.

The system contains information about users, and games.
Users can register in the system. After successful registration, the user has email, password, full name, list of games and information whether he is an administrator or not.
The first registered user becomes also an administrator. Administrator can manually mark another users as admins.
A game has title, trailer (YouTube Video Id), image thumbnail (URL), size, price, description and release date.
Users can buy games and make orders. Each order has a single buyer (user) and one or many products.

All users can view all games.
All users can view details of each game.
Logged-in users can logout.
Logged in users can add/remove games from their shopping cart .
Logged in users can buy games that are added to the shopping cart and those games are added to the profile of the user and cannot be bought for second time.
Administrators can add, edit or delete games.
Basic user can not add, edit or delete game.

The guest users can register and log in. 
RegisterUser|email|password|confirmPassword|fullName - That command add new user to the database in case of valid parameters. Otherwise print appropriate message informing why the user cannot be registered. 
The requirements for valid parameters are:
Email – must contain @ sign and a period. It must be unique.
Password – length must be at least 6 symbols and must contain at least 1 uppercase, 1 lowercase letter and 1 digit.
Confirm Password – must match the provided password
LoginUser|email|password - That command set the current logged in user if exists. Otherwise print appropriate message.
Logged in user can logout.
Logout – That command log out the user from the system. If there is no logged in user print appropriate message.

Administrator has the option to add/edit/delete games to the catalog. 
AddGame|title|price|size|trailer|thubnailURL|description|releaseDate
EditGame|id|values - A game should be edited in case of valid id. Otherwise print appropriate message.
A game should be added/edited only to the catalog if matches those criteria:
Title – has to begin with uppercase letter and has length between 3 and 100 symbols (inclusive)
Price –  must be a positive number with precision up to 2 digits after floating point
Size – must be a positive number with precision up to 1 digit after floating point
Trailer –  only videos from YouTube are allowed and only their ID should be saved to the database which is a string of exactly 11 characters. 
For example, if the URL to the trailer is https://www.youtube.com/watch?v=edYCtaNueQY, the required part that must be saved into the database is edYCtaNueQY. That would be always the last 11 characters from the provided URL.
Thumbnail URL – it should be a plain text starting with http://, https:// or null
Description – must be at least 20 symbols
DeleteGame|id - A game should be deleted in case of valid id. Otherwise print appropriate message.

The guest users can view games. 
AllGame - print titles and price of all games.
DetailsGame|gameTitle - print details for single game. 

The logged in users can view owned games.
OwnedGame – print games bought by currently logged in user.

Each logged in user should be able to buy game. 
AddItem|gameTitle - add game to shopping cart.
RemoveItem|gameTitle - remove game from shopping cart.
BuyItem – buy all games from shopping cart.
A user can buy a game only once!
If he owns a game, he shouldn't be able to add it to the shopping cart.
