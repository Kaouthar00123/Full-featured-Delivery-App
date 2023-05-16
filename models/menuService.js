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
const getRestauMenu = async (req, res) => {
    try {
        const con = await pool.getConnection();

        var {restauId} =  req.params;
        restauId = parseInt( restauId ); 
        console.log("req.params = ", req.params, " et restauId entier =  ", restauId)

        const [rows] = await con.query('SELECT * FROM food where restauId = ?', restauId);

        con.release();
        res.json(rows);    

        } catch (err) {
        console.log(err);
        return(res.status(500).send('Erreur lors de la récupération de la liste de menue d"un  restaurants'));
        //return res.status(500).json({ status: 'failed', data: Taches });
    }
};

// GET : Récupérer un restaurant par son ID
const getFoodById =  async (req, res) => {
    try {
        const connection = await pool.getConnection();

        var {foodId} =  req.params;
        foodId = parseInt( foodId ); 
        const [rows] = await connection.query('SELECT * FROM food WHERE FoodId = ?', foodId);
        connection.release();
        if (rows.length > 0) {
            res.json(rows[0]);
        } else {
            res.status(400).send('Food introuvable');
        }
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la récupération du food');
    }
};

// POST : Créer un nouveau restaurant
const postFoodToRestau =  async (req, res) => {
    const { Name, Description, prixUnitaire, image} = req.body;

    var {restauId} =  req.params;
    restauId = parseInt( restauId );

    try {
        const connection = await pool.getConnection();

        const [rows] = await connection.query('SELECT * FROM restau WHERE restauId = ?', restauId);
        if (rows.length > 0) {
        

        await connection.query('INSERT INTO food ( Name, Description, prixUnitaire, image, restauId) VALUES (?, ?, ?, ?, ?)', 
        [ Name, Description, prixUnitaire, image, restauId ]);
        const result = await connection.query('SELECT LAST_INSERT_ID()');
        const ID = result[0];

        connection.release();
        res.json({"ID": ID});
        }
        else {
            res.status(404).send('Erreur, ce restau n"exite pas');
        }
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la création du restaurant');
    }
};

//update food
const updateFoodFromRestau = async (req, res) => {
    try {
    const connection = await pool.getConnection();
    var {foodId} =  req.params;
    foodId = parseInt( foodId );
    const [rows] = await connection.query('SELECT * FROM food WHERE FoodId = ?', foodId);
    const { Name, Description, prixUnitaire, image, rate, review } = req.body;

    if (rows.length > 0) {
        await connection.query('UPDATE food SET Name = ?, Description =? ,prixUnitaire =? ,image = ?, rate = ?, review = ? WHERE FoodId = ?', [Name, Description, prixUnitaire, image, rate, review, foodId] )
        connection.release();
        res.status(200).send('Food updated avec succes'); 
    } else {
        connection.release();
        res.status(400).send('Food introuvable'); 
    }
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la modification du ce food');
    }
};

//delete food
const deleteFoodFromRestau = async (req, res) => {
    try {
    const connection = await pool.getConnection();
    var {foodId} =  req.params;
    foodId = parseInt( foodId );
    const [rows] = await connection.query('SELECT * FROM food WHERE FoodId = ?', foodId);
    if (rows.length > 0) {
        await connection.query('DELETE FROM food WHERE FoodId = ?', foodId)
        res.status(200).send('Food deleted avec succes'); 
    } else {
        res.status(400).send('Food introuvable'); 
    }
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la récupération du restaurant');
    }
};

module.exports = {
    getRestauMenu,
    getFoodById,
    updateFoodFromRestau,
    postFoodToRestau,
    deleteFoodFromRestau
};
