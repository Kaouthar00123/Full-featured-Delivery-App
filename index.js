const express = require('express');
const http = require('http');

const cors = require('cors');
const bodyParser = require('body-parser');

const foodAppRoute = require("./routes");


//----------------------Server
const app = express();
const server = http.createServer(app);

//---------------------Midellwares

// utiliser le middleware cors:  pour permettre à des sites web ou des applications d'accéder à l'API depuis différents domaines.
app.use(cors());
// Utiliser bodyParser pour traiter les corps de requête au format JSON
app.use(bodyParser.json());

// Utiliser bodyParser pour traiter les corps de requête au format URL-encoded
app.use(bodyParser.urlencoded({ extended: true }));

//----------------------Routes
app.get('/projet', (req, res) => {
    res.send('Hello from projet TDM!');
    }); 

app.use('/food_app_esi', foodAppRoute);


app.use((err, req, res, next) => {
    res.status(err.status || 500);
    res.send({
        status: err.status || 500,
        message: err.message,
        });
    });

// if the route is not found
app.use('*', (req, res) => {
    res.status(404).json({ message: 'Not Found' });
  });




//--------------------------------Starting the server
const port =  3000;
app.listen(port, () => {
    console.log(`Server on port ${port}` ) ;

}
);
module.exports = server;
