const mysql = require('mysql2/promise');

// Configuration de la connexion à la base de données
const pool  = mysql.createPool({
    connectionLimit : 10,
    host            : 'localhost',
    user            : 'root',
    password        : '',
    database        : 'foodapp'
})


// GET : Récupérer un restaurant par son ID
const authentif =  async (req, res) => {
    try {
        var {email, password} =  req.body;

        const connection = await pool.getConnection();
        const [rows] = await connection.query('SELECT * FROM users WHERE email = ?', email);
        if (rows.length > 0) {
            const [exsite] = await connection.query('SELECT id FROM users WHERE email = ? and password = ?', [email, password]);        
            if (exsite.length == 1) {
                res.json({"ID user": exsite});
            }
            else{
                res.status(404).send('password faut');
            }
        } else {
            res.status(404).send('User introuvable, email n existe pas');
        }
        connection.release();
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la authentification du ce user');
    }
};

// POST : Créer un nouveau user: Registration avec verfication 
//-----si ce Email existe deja, verf avec hachage de password, et dowload photo

const postUser =  async (req, res) => {
    const { FirtName, LastName, email, phoneNumber, address, password, profilePicture} = req.body;
    try {
        const connection = await pool.getConnection();

        await connection.query('INSERT INTO users (FirtName, LastName, email, phoneNumber, address, password, profilePicture) VALUES (?,?     ?, ?,   ?, ?,   ? )', 
        [FirtName, LastName, email, phoneNumber, address, password, profilePicture  ]);
        const result = await connection.query('SELECT LAST_INSERT_ID()');
        const ID = result[0];

        connection.release();
        res.json({"User ID": ID});
        
        connection.release();
    } catch (err) {
        console.log(err);
        res.status(500).send('Erreur lors de la création du user');
    }
};


module.exports = {
    authentif,
    postUser
};
