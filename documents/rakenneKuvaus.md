#ChessBoard 
toimii ohjelman sydämmenä. Kaikki pelilautaan tehtävät muutokset toimivat
tämän luokan kautta. Sisältää kaikki shakki nappulat mitä laudalla on. 
Kahdessa väri koodatussa hahsmapissa ja ChessPiece[8][8] arrayssa.

#LogicHandler
Tehtävänä sitoa kaikki säännöt yhteen nippuun ja antaa sääntöjen 
noudattamiselle yhden selkeän luokan.

#MovementHandler
Luokka jonka tehtävänä tarkistaa onko liike laillinen. ShakkiMatin tarkistus
tarvitsee tätä apunaan. Sisältää myös yhden edellisen liikkeen perumisen.

#UI
Pitää sisällään kaiken tarvittavan graafisten komponenttien luomiseen ja 
ohjaamiseen.

GridLayoyt sisältää kaikki shakki ruudut yksittäisinä olioina. Jokainen
shakkiruutu olio lisää päälleen vielä piirto olion joka kuuntelee painalluksia
ja piirtää shakkilaudalta katsottujen ohjeiden mukaan nappulat itseensä.
