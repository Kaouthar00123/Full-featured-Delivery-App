const mysql = require('mysql2/promise');

// Configuration de la connexion à la base de données
const pool  = mysql.createPool({
    connectionLimit : 10,
    host            : 'localhost',
    user            : 'root',
    password        : '',
    database        : 'foodapp'
})




// GET : Récupérer la liste des restaurants
const getAllRestau = async (req, res) => {
    try {
        const con = await pool.getConnection();

        const [rows] = await con.query('SELECT * FROM restau');
        con.release();
        res.json(rows);
    } catch (err) {
        console.log(err);
        return(res.status(500).send('Erreur lors de la récupération de la liste des restaurants'));
        //return res.status(500).json({ status: 'failed', data: Taches });
    }
};

// GET : Récupérer un restaurant par son ID
const getRestauById =  async (req, res) => {
    try {
        var {restauId} =  req.params;
        restauId = parseInt( restauId ); 

        const connection = await pool.getConnection();
        const [rows] = await connection.query('SELECT * FROM restau WHERE RestauId = ?', restauId);
        connection.release();
        if (rows.length > 0) {
            res.json(rows[0]);
        } else {
            res.status(404).send('Restaurant introuvable');
        }
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la récupération du restaurant');
    }
};

// POST : Créer un nouveau restaurant
const postRestau =  async (req, res) => {
    const { Name, image, MapAddress, Address, category, Email, Numphone, fblink, instalink } = req.body;
    try {
        const connection = await pool.getConnection();

        await connection.query('INSERT INTO restau (Name, image, MapAddress, Address, category, Email, Numphone, fblink, instalink) VALUES (?,     ?, ?,   ?, ?,   ?, ?,   ?, ? )', 
        [ Name, image, MapAddress, Address, category, Email, Numphone, fblink, instalink ]);
        const result = await connection.query('SELECT LAST_INSERT_ID()');
        const ID = result[0];

        connection.release();
        res.json({"ID": ID});
        
        connection.release();
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la création du restaurant');
    }
};
// put : update un nouveau restaurant
const updateRestau =  async (req, res) => {
    const { Name, image, MapAddress, Address, category, Email, Numphone, fblink, instalink, stars, viewers} = req.body;
    try {
        const connection = await pool.getConnection();

        var {restauId} =  req.params;
        restauId = parseInt( restauId ); 

        const [rows] = await connection.query('SELECT * FROM restau WHERE RestauId = ?', restauId);
        if (rows.length > 0) {

            //await connection.query('UPDATE restau SET Name = ${Name}, Descript =${Descript},  image =${image},  MapAdress = ${MapAdress}, Address = ${Address}, category = ${category}, Email= ${Email}, Numphone= ${Numphone}, fblink=${fblink},  instalink= ${instalink}, starts= ${starts}, viewers= ${viewers} WHERE RestauId = ${restauId}', );
            await connection.query('UPDATE restau SET Name = ?,  image = ?, MapAddress = ?, Address = ?, category = ?, Email = ?, Numphone = ?, fblink = ?, instalink = ?, stars = ?, viewers = ? WHERE RestauId = ?', [Name, image, MapAddress, Address, category, Email, Numphone, fblink, instalink, stars, viewers, restauId]);
            res.status(200).send('Restaurant updated avec succe');

        } else {
            res.status(400).send('Restaurant introuvable'); 
        }
        
        connection.release();
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la mise à jour du restaurant');
    }
};

//delete restaurent
const deleteRestau = async (req, res) => {
    try {
    var {restauId} =  req.params;
    restauId = parseInt( restauId ); 
    const connection = await pool.getConnection();
    const [rows] = await connection.query('SELECT * FROM restau WHERE RestauId = ?', restauId);
    if (rows.length > 0) {
        const [result] = await connection.query('DELETE FROM restau WHERE RestauId = ?', restauId)
        res.status(200).send('Restaurant deleted avec succes'); 
    } else {
        res.status(400).send('Restaurant introuvable'); 
    }

    connection.release();
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la récupération du restaurant');
    }
};

module.exports = {
    getAllRestau,
    getRestauById,
    postRestau,
    updateRestau,
    deleteRestau
};
